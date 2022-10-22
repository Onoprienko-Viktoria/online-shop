package com.onoprienko.onlineshop.utils;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class DataSourceFactoryTest {

    @Disabled
    @Test
    void getConnection() throws SQLException {
        String url = "jdbc:postgresql://localhost:5433/testdb";
        DataSourceFactory dataSourceFactory = new DataSourceFactory(url,
                "pass", "user");

        assertEquals(dataSourceFactory.getJdbcPassword(), "pass");
        assertEquals(dataSourceFactory.getJdbcUrl(), url);
        assertEquals(dataSourceFactory.getJdbcUser(), "user");

        DataSource dataSource = dataSourceFactory.create();
        try (Connection connection = dataSource.getConnection()) {
            assertFalse(connection.isClosed());
        }

    }
}