package com.onoprienko.onlineshop.service.impl;

import com.onoprienko.onlineshop.dao.ProductDao;
import com.onoprienko.onlineshop.entity.Product;
import com.onoprienko.onlineshop.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductDao productDao;

    @Override
    public List<Product> findAll(String word) {
        List<Product> productsByWordIn = productDao.findProductsByWordIn(word);
        log.info("Get all products with word {},  {}", word, productsByWordIn.toString());
        return productsByWordIn;
    }

    @Override
    public List<Product> findAll() {
        List<Product> products = productDao.findAll();
        log.info("Get all products {}", products.toString());
        return products;
    }

    @Override
    public void addProduct(Product product) {
        product.setCreationDate(Date.valueOf(LocalDate.now()));
        productDao.addProduct(product);
        log.info("Add product to database {}", product);
    }

    @Override
    public void removeProduct(Long id) {
        productDao.removeProduct(id);
        log.info("Remove product from database, id: {}", id);
    }

    @Override
    public Product getProduct(long id) {
        Product productById = productDao.getProductById(id);
        log.info("Find product with id {}, {}", id, productById);
        return productById;
    }

    @Override
    public void editProduct(Product product) {
        productDao.editProduct(product);
        log.info("Edit product {}", product);
    }
}

