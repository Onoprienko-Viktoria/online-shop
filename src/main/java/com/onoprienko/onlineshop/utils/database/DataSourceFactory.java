package com.onoprienko.onlineshop.utils.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.sql.DataSource;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
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
