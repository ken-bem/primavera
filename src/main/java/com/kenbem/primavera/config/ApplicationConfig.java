package com.kenbem.primavera.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.NamingConventions;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.util.List;

@Configuration
@EnableTransactionManagement
@EnableConfigurationProperties
public class ApplicationConfig implements WebMvcConfigurer {

    private final String[] ALLOWED_ORIGINS = new String[]{ "*" };
    private final String[] ALLOWED_METHODS = new String[]{ "HEAD", "OPTIONS", "GET", "POST", "PUT", "PATCH", "DELETE" };

    private final Environment environment;
    private final long MAX_AGE_SECS = 3600;

    public ApplicationConfig(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry
                .addMapping("/**")
                .allowedOrigins(ALLOWED_ORIGINS)
                .allowedMethods(ALLOWED_METHODS)
                .maxAge(MAX_AGE_SECS);
    }

    @Bean
    public CorsFilter corsFilter(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        CorsConfiguration config = new CorsConfiguration();

        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        source.registerCorsConfiguration("/v2/api-docs", config);

        return new CorsFilter(source);
    }


    public void extendMessageConverters(List<HttpMessageConverter<?>> converters){
        for(HttpMessageConverter converter: converters){
            if (converter instanceof MappingJackson2HttpMessageConverter){
                ObjectMapper mapper = ((MappingJackson2HttpMessageConverter) converter).getObjectMapper();
                mapper.registerModule(new Hibernate5Module());
            }
        }
    }

    @Bean
    public ModelMapper modelMapper(){
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
                .setSourceNamingConvention(NamingConventions.JAVABEANS_MUTATOR);

        return modelMapper;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor(){
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("lang");
        return localeChangeInterceptor;
    }

}
