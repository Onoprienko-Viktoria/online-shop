package com.onoprienko.onlineshop.security.entity;

import com.onoprienko.onlineshop.entity.Product;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Session {
    private String token;
    private String role;
    private Integer timeToLive;
    private LocalDateTime expire;
    private List<Product> cart;
}
