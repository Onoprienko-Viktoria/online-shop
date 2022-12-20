package com.onoprienko.onlineshop.service.impl;

import com.onoprienko.onlineshop.dao.ProductDao;
import com.onoprienko.onlineshop.entity.Product;
import com.onoprienko.onlineshop.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
@Setter
public class DefaultProductService implements ProductService {
    private final ProductDao productDao;

    @Override
    public List<Product> findAll(String word) {
        if (word != null) {
            log.info("Get all products with word {}", word);
            return productDao.findAllByWordIn(word);
        }
        log.info("Get all products");
        return productDao.findAll();
    }


    @Override
    public void add(Product product) {
        product.setCreationDate(LocalDateTime.now());
        productDao.add(product);
        log.info("Add product to database {}", product);
    }

    @Override
    public void remove(Long id) {
        productDao.remove(id);
        log.info("Remove product from database, id: {}", id);
    }

    @Override
    public Product getById(long id) {
        Product productById = productDao.getById(id);
        log.info("Find product with id {}, {}", id, productById);
        return productById;
    }

    @Override
    public void edit(Product product) {
        productDao.edit(product);
        log.info("Edit product {}", product);
    }
}

