package com.onoprienko.onlineshop.service;

import com.onoprienko.onlineshop.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> findAll(String word);

    List<Product> findAll();

    void addProduct(Product product);

    void editProduct(Product product);

    void removeProduct(Long id);

    Product getProduct(long id);
}
