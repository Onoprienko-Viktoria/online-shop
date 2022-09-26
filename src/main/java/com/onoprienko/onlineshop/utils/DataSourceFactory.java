package com.onoprienko.onlineshop.utils;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.sql.DataSource;
import java.sql.SQLException;

@Getter
@Setter
@AllArgsConstructor
public class DataSourceFactory {
    private String jdbcUrl;
    private String jdbcPassword;
    private String jdbcUser;

    public DataSourceFactory(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public DataSource create() throws SQLException {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(jdbcUrl);
        if (jdbcUser != null && jdbcPassword != null) {
            hikariConfig.setUsername(jdbcUser);
            hikariConfig.setPassword(jdbcPassword);
        }

        return new HikariDataSource(hikariConfig);
    }
}
