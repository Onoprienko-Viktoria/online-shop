package com.onoprienko.onlineshop.entity;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
public class Product {
    private Long id;
    private String name;
    private Double price;
    private LocalDateTime creationDate;
}
