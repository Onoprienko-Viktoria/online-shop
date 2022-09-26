package com.onoprienko.onlineshop.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class Session {
    private String token;
    private String role;
    private LocalDateTime expire;
    private List<Product> cart;

    @Override
    public String toString() {
        return "Session{" +
                "token='" + token + '\'' +
                ", role='" + role + '\'' +
                ", expire=" + expire +
                ", cart=" + cart +
                '}';
    }
}
