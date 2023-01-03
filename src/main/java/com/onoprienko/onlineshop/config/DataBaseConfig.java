package com.onoprienko.onlineshop.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataBaseConfig {
    @Value("${jdbc.url}")
    private String databaseUrl;
    @Value("${jdbc.user}")
    private String databaseUser;
    @Value("${jdbc.pass}")
    private String databasePass;


    @Bean
    public DataSource dataSource() {
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setPassword(databasePass);
        hikariDataSource.setUsername(databaseUser);
        hikariDataSource.setJdbcUrl(databaseUrl);
        return hikariDataSource;
    }
}
