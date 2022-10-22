package com.onoprienko.onlineshop.entity;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
public class User {
    private String name;
    private String email;
    private String password;
    private String role;
    private String sole;
}
