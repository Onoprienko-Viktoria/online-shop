package com.onoprienko.onlineshop.dao;


import com.onoprienko.onlineshop.entity.Product;

import java.util.List;

public interface ProductDao {

    List<Product> findAll();

    void addProduct(Product product);

    void removeProduct(Long id);

    void editProduct(Product product);

    List<Product> findProductsByWordIn(String words);

    Product getProductById(long id);
}
