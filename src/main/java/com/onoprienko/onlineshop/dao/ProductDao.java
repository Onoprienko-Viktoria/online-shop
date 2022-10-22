package com.onoprienko.onlineshop.dao;


import com.onoprienko.onlineshop.entity.Product;

import java.util.List;

public interface ProductDao {

    List<Product> findAll();

    void add(Product product);

    void remove(Long id);

    void edit(Product product);

    List<Product> findAllByWordIn(String words);

    Product getById(long id);
}
