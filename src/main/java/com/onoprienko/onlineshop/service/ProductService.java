package com.onoprienko.onlineshop.service;

import com.onoprienko.onlineshop.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> findAll(String word);

    void add(Product product);

    void edit(Product product);

    void remove(Long id);

    Product getById(long id);
}
