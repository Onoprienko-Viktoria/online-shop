package com.onoprienko.onlineshop.utils;

import com.onoprienko.onlineshop.utils.database.DataBaseProperties;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ParserTest {

    @Test
    void parseDatabaseUrl() {
        String startUrl = "postgres://testuser:passtest@localhost:5432/database";
        String urlTest = "jdbc:postgresql://localhost:5432/database";
        String passTest = "passtest";
        String passUser = "testuser";
        DataBaseProperties dataBaseProperties = Parser.parseDatabaseUrl(startUrl);

        assertEquals(dataBaseProperties.getUrl(), urlTest);
        assertEquals(dataBaseProperties.getPass(), passTest);
        assertEquals(dataBaseProperties.getUser(), passUser);
    }
}