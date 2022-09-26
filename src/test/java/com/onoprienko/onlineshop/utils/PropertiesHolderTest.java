package com.onoprienko.onlineshop.utils;

import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class PropertiesHolderTest {
    @Test
    public void readPropertiesFileReturnCorrectProperties() {
        String PROPERTIES_PATH = "configuration.properties";
        PropertiesHolder propertiesHolder = new PropertiesHolder();
        Properties properties = propertiesHolder.getProperties(PROPERTIES_PATH);

        assertNotNull(properties);

        String dbUrl = "jdbc:postgresql://localhost:5433/testdb";
        assertEquals(dbUrl, properties.getProperty("db.url"));

        String user = "user";
        assertEquals(user, properties.getProperty("db.user"));

        String password = "pass";
        assertEquals(password, properties.getProperty("db.password"));

        String reportUrl = "./src/test/java/resources/reports";
        assertEquals(reportUrl, properties.getProperty("db.report.url"));
    }


    @Test
    public void readPropertiesFromFileThatDontExistReturnError() {
        PropertiesHolder propertiesHolder = new PropertiesHolder();
        assertThrows(RuntimeException.class, () -> propertiesHolder.getProperties("/src/main"));
    }

    @Test
    public void readPropertiesFromEmptyFile() {
        String EMPTY_PROPERTIES_PATH = "empty_configuration.properties";
        PropertiesHolder propertiesHolder = new PropertiesHolder();
        Properties properties = propertiesHolder.getProperties(EMPTY_PROPERTIES_PATH);

        assertNull(properties.getProperty("db.url"));
        assertNull(properties.getProperty("db.user"));
        assertNull(properties.getProperty("db.password"));
        assertNull(properties.getProperty("db.report.url"));
    }

    @Test
    public void readPropertiesFromHalfEmptyFile() {
        String HALF_EMPTY_PROPERTIES_PATH = "half_empty_configuration.properties";
        PropertiesHolder propertiesHolder = new PropertiesHolder();
        Properties properties = propertiesHolder.getProperties(HALF_EMPTY_PROPERTIES_PATH);

        assertNull(properties.getProperty("db.url"));
        assertNull(properties.getProperty("db.user"));

        String password = "pass";
        assertEquals(password, properties.getProperty("db.password"));

        String reportUrl = "./src/test/java/resources/reports";
        assertEquals(reportUrl, properties.getProperty("db.report.url"));
    }


}