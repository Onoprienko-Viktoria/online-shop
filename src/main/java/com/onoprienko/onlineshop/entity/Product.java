package com.onoprienko.onlineshop.entity;

import lombok.*;

import java.sql.Date;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
public class Product {
    private long id;
    private String name;
    private double price;
    private LocalDateTime creationDate;
}
