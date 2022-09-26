package com.onoprienko.onlineshop.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.Objects;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class Product {
    private long id;
    private String name;
    private double price;
    private Date creationDate;

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", creationDate=" + creationDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product product)) return false;
        return getId() == product.getId() && Double.compare(product.getPrice(), getPrice()) == 0 && Objects.equals(getName(), product.getName()) && Objects.equals(getCreationDate(), product.getCreationDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getPrice(), getCreationDate());
    }
}
