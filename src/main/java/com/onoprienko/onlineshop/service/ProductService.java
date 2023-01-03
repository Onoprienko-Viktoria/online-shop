package com.onoprienko.onlineshop.service;

import com.onoprienko.onlineshop.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> findAll(String word);

    Product add(Product product);

    Product edit(Product product);

    Long remove(Long id);

    Product getById(long id);
}
