package com.kenbem.primavera.config;

import nz.net.ultraq.thymeleaf.LayoutDialect;
import nz.net.ultraq.thymeleaf.decorators.strategies.GroupingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

@Configuration
public class TemplateConfig {


    @Bean
    public SpringResourceTemplateResolver templateResolver(){
        final SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setCacheable(false);

        templateResolver.setTemplateMode("LEGACYHTML5");
        templateResolver.setPrefix("classpath:/templates/");
        templateResolver.setSuffix(".html");

        return templateResolver;
    }


    @Bean
    public SpringTemplateEngine templateEngine(){
        final SpringTemplateEngine springTemplateEngine = new SpringTemplateEngine();

        springTemplateEngine.addDialect(new SpringSecurityDialect());
        springTemplateEngine.addDialect(layoutDialect());
        springTemplateEngine.addTemplateResolver(templateResolver());
        return springTemplateEngine;
    }

    @Bean
    public ThymeleafViewResolver viewResolver(){

        final ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(templateEngine());
        viewResolver.setCharacterEncoding("UTF-8");
        viewResolver.setOrder(1);

        return viewResolver;
    }

    @Bean
    public LayoutDialect layoutDialect(){
        return new LayoutDialect(new GroupingStrategy());
    }
}
