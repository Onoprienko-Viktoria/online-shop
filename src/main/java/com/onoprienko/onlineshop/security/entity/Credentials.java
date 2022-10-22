package com.onoprienko.onlineshop.security.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class Credentials {
    private String password;
    private String email;
}
