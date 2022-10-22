package com.onoprienko.onlineshop.utils.database;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class DataBaseProperties {
    private String url;
    private String pass;
    private String user;
}
