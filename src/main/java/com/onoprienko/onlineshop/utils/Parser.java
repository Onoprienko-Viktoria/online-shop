package com.onoprienko.onlineshop.utils;

import com.onoprienko.onlineshop.utils.database.DataBaseProperties;

public class Parser {

    public static DataBaseProperties parseDatabaseUrl(String dbUrl) {
        String prefix = "jdbc:postgresql://";
        String url = prefix + dbUrl.substring(dbUrl.indexOf("@") + 1);

        String substringDbUrl = dbUrl.substring(dbUrl.indexOf("//") + 2);

        String user = substringDbUrl.substring(0, substringDbUrl.indexOf(":"));

        String pass = substringDbUrl.substring(substringDbUrl.indexOf(":") + 1, substringDbUrl.indexOf("@"));

        return DataBaseProperties.builder()
                .pass(pass)
                .url(url)
                .user(user)
                .build();
    }
}
