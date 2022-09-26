package com.onoprienko.onlineshop.service;

import com.onoprienko.onlineshop.dao.ProductDao;
import com.onoprienko.onlineshop.entity.Product;
import com.onoprienko.onlineshop.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceTest {
    private final ProductDao productDao = Mockito.mock(ProductDao.class);
    private final ProductService productService = new ProductServiceImpl(productDao);

    Product testProductOne = Product.builder().id(1L)
            .creationDate(Date.valueOf(LocalDate.now()))
            .name("test")
            .price(10.2).build();
    Product testProductTwo = Product.builder().id(2L)
            .creationDate(Date.valueOf(LocalDate.now()))
            .name("test 21")
            .price(1.2).build();
    Product testProductThree = Product.builder().id(3L)
            .creationDate(null)
            .name("t")
            .price(0).build();

    @Test
    void findAllReturnCorrectValues() {
        Mockito.when(productDao.findAll()).thenReturn(List.of(testProductOne, testProductTwo, testProductThree));

        List<Product> all = productService.findAll();

        assertNotNull(all);
        assertEquals(3, all.size());
        assertEquals(1L, all.get(0).getId());
        assertEquals(2L, all.get(1).getId());
        assertEquals(3L, all.get(2).getId());

        assertEquals("test", all.get(0).getName());
        assertEquals("test 21", all.get(1).getName());
        assertEquals("t", all.get(2).getName());

        assertEquals(10.2, all.get(0).getPrice());
        assertEquals(1.2, all.get(1).getPrice());
        assertEquals(0, all.get(2).getPrice());

        Mockito.verify(productDao, Mockito.times(1)).findAll();
    }

    @Test
    void findAllReturnEmptyList() {
        Mockito.when(productDao.findAll()).thenReturn(new ArrayList<>());

        List<Product> all = productService.findAll();

        assertNotNull(all);
        assertTrue(all.isEmpty());

        Mockito.verify(productDao, Mockito.times(1)).findAll();
    }

    @Test
    void addProductWorkCorrect() {
        productService.addProduct(testProductThree);

        Mockito.verify(productDao, Mockito.times(1)).addProduct(testProductThree);
    }

    @Test
    void editProductWorkCorrect() {
        productService.editProduct(testProductOne);

        Mockito.verify(productDao, Mockito.times(1)).editProduct(testProductOne);
    }

    @Test
    void removeProductWorkCorrect() {
        productService.removeProduct(11L);

        Mockito.verify(productDao, Mockito.times(1)).removeProduct(11L);
    }

    @Test
    void findProductsByWordsInReturnListOfProducts() {
        Mockito.when(productDao.findProductsByWordIn("test")).thenReturn(List.of(testProductOne, testProductThree));

        List<Product> all = productService.findAll("test");

        assertNotNull(all);
        assertEquals(2, all.size());
        assertEquals(1L, all.get(0).getId());
        assertEquals(3L, all.get(1).getId());

        assertEquals("test", all.get(0).getName());
        assertEquals("t", all.get(1).getName());

        assertEquals(10.2, all.get(0).getPrice());
        assertEquals(0, all.get(1).getPrice());

        Mockito.verify(productDao, Mockito.times(1)).findProductsByWordIn("test");
    }

    @Test
    public void getProduct() {
        Mockito.when(productDao.getProductById(3)).thenReturn(testProductThree);

        Product product = productService.getProduct(3L);

        assertNotNull(product);
        assertEquals(product.getId(), testProductThree.getId());
        assertEquals(product.getPrice(), testProductThree.getPrice());
        assertEquals(product.getName(), testProductThree.getName());
        assertEquals(product.getCreationDate(), testProductThree.getCreationDate());

        Mockito.verify(productDao, Mockito.times(1)).getProductById(3);
    }
}