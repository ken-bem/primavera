package com.kenbem.primavera.config;

import com.kenbem.primavera.security.login.CustomLoginFailureHandler;
import com.kenbem.primavera.security.login.CustomLoginSuccessHandler;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final static String[] PUBLIC = {"/**"};


    private final UserDetailsService userDetailsService;

    public SecurityConfig(@Qualifier("userDetailsServiceImp")
                                  UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.authenticationProvider(authProvider());

        auth
                .inMemoryAuthentication()
                .withUser("admin")
                .password(encoder().encode("password"))
                .roles("USER");

    }

    @Override
    public void configure(HttpSecurity http) throws Exception{
        http
                .csrf()
                .disable()
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers(PUBLIC).permitAll()
                .antMatchers("/**").authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .successHandler(new CustomLoginSuccessHandler())
                .failureHandler(new CustomLoginFailureHandler())
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll();
    }

    @Override
    public void configure(WebSecurity web) throws Exception{
        web
                .ignoring()
                .antMatchers("/resources/**",
                        "/static/**",
                        "/css/**",
                        "/js/**",
                        "/images/**",
                        "/api/**");
    }

    @Bean
    public BCryptPasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(encoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception{
        return authenticationManager();
    }
}
