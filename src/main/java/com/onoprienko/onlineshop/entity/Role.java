package com.onoprienko.onlineshop.entity;


import java.util.List;

public enum Role {
    GUEST(List.of("/products", "/registration", "/login")),
    USER(List.of("/products", "/registration", "/login", "/cart")),
    ADMIN(List.of("/products", "/registration", "/login", "/product"));

    private final List<String> paths;

    Role(List<String> paths) {
        this.paths = paths;
    }

    public List<String> getPaths() {
        return paths;
    }
}
