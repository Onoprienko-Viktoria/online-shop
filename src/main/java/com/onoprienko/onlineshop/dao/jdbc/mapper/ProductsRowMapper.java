package com.onoprienko.onlineshop.dao.jdbc.mapper;

import com.onoprienko.onlineshop.entity.Product;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class ProductsRowMapper {
    public Product mapRow(ResultSet resultSet) throws SQLException {
        long id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        double price = resultSet.getDouble("price");
        Timestamp creationDate = resultSet.getTimestamp("creation_date");
        return Product.builder()
                .id(id)
                .name(name)
                .price(price)
                .creationDate(creationDate.toLocalDateTime())
                .build();
    }
}
