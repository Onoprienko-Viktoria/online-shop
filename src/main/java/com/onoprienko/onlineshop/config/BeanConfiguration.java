package com.onoprienko.onlineshop.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import javax.sql.DataSource;

@Configuration
public class BeanConfiguration {
    @Value("${jdbc.url}")
    private String databaseUrl;
    @Value("${jdbc.user}")
    private String databaseUser;
    @Value("${jdbc.pass}")
    private String databasePass;

    @Value("${templates.path}")
    private String templatesPath;


    @Bean
    public DataSource dataSource() {
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setPassword(databasePass);
        hikariDataSource.setUsername(databaseUser);
        hikariDataSource.setJdbcUrl(databaseUrl);
        return hikariDataSource;
    }

    @Bean
    public FreeMarkerConfigurer freeMarkerConfigurer() {
        FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
        freeMarkerConfigurer.setTemplateLoaderPath(templatesPath);
        return freeMarkerConfigurer;
    }

    @Bean
    public FreeMarkerViewResolver freeMarkerViewResolver() {
        FreeMarkerViewResolver freeMarkerViewResolver = new FreeMarkerViewResolver();
        freeMarkerViewResolver.setSuffix(".ftl");
        freeMarkerViewResolver.setCache(true);
        return freeMarkerViewResolver;
    }

}
