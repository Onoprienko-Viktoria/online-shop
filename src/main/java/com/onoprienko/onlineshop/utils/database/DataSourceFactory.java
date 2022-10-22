package com.onoprienko.onlineshop.utils.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.sql.DataSource;

@Getter
@AllArgsConstructor
public class DataSourceFactory {
    private String jdbcUrl;
    private String jdbcPassword;
    private String jdbcUser;

    public DataSourceFactory(DataBaseProperties dataBaseProperties) {
        this.jdbcUrl = dataBaseProperties.getUrl();
        this.jdbcPassword = dataBaseProperties.getPass();
        this.jdbcUser = dataBaseProperties.getUser();
    }

    public DataSource create() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(jdbcUrl);
        if (jdbcUser != null && jdbcPassword != null) {
            hikariConfig.setUsername(jdbcUser);
            hikariConfig.setPassword(jdbcPassword);
        }

        return new HikariDataSource(hikariConfig);
    }
}
