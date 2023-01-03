package com.onoprienko.onlineshop.web.controller;

import com.onoprienko.onlineshop.entity.Product;
import com.onoprienko.onlineshop.service.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.ui.ModelMap;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductControllerTest {
    private final ProductService productService = Mockito.mock(ProductService.class);
    private final ProductsController productController = new ProductsController(productService);
    Product testProduct = Product.builder()
            .id(1L)
            .name("one")
            .price(1.1)
            .build();

    @Test
    void getAddPageReturnsNameOfAddProductPage() {
        String response = productController.getAddPage();
        assertEquals(response, "add_product");
    }

    @Test
    void getEditPageReturnsNameOfEditProductPageAndSetModelMap() {
        ModelMap modelMap = new ModelMap();
        String response = productController.getEditPage("1", modelMap);
        assertEquals(response, "edit_product");
        assertEquals(modelMap.getAttribute("id"), "1");
    }


    @Test
    void addProductReturnRedirectToProducts() {
        String response = productController.add(Product.builder().name("test").price(11.0)
                .build(), new ModelMap());

        assertEquals(response, "redirect:/products");
    }

    @Test
    void addProductReturnExceptionAndRedirectToAddProduct() {
        Mockito.when(productService.add(testProduct)).thenThrow(new RuntimeException("Exception"));
        ModelMap modelMap = new ModelMap();
        String response = productController.add(testProduct, modelMap);

        assertEquals(modelMap.getAttribute("errorMessage"), "Your product has not been added! Exception");
        assertEquals(response, "add_product");
    }

    @Test
    void editProductReturnRedirectToProducts() {
        String response = productController.edit(Product.builder().name("test").price(11.0)
                .build(), new ModelMap());

        assertEquals(response, "redirect:/products");
    }

    @Test
    void editProductReturnExceptionAndRedirectToEditProduct() {
        Mockito.when(productService.edit(testProduct)).thenThrow(new RuntimeException("Exception"));
        ModelMap modelMap = new ModelMap();
        String response = productController.edit(testProduct, modelMap);

        assertEquals(modelMap.getAttribute("errorMessage"), "Your product has not been edited! Exception");
        assertEquals(response, "edit_product");
    }

    @Test
    void removeProductReturnRedirect() {
        String remove = productController.remove(1L);

        assertEquals(remove, "redirect:/products");
    }

    @Test
    @SuppressWarnings("unchecked")
    void getAllProductsReturnProducts() {
        ModelMap modelMap = new ModelMap();
        Mockito.when(productService.findAll(null)).thenReturn(List.of(testProduct, testProduct));
        String productsList = productController.findAll(null, modelMap);

        assertEquals(productsList, "products_list");
        List<Product> products = (List<Product>) modelMap.getAttribute("products");
        assertNotNull(products);
        assertEquals(products.size(), 2);
        assertEquals(products.get(0).getId(), 1L);
        assertEquals(products.get(0).getPrice(), 1.1);
        assertEquals(products.get(0).getName(), "one");
        assertEquals(products.get(1).getId(), 1L);
        assertEquals(products.get(1).getPrice(), 1.1);
        assertEquals(products.get(1).getName(), "one");

        Mockito.verify(productService, Mockito.times(1)).findAll(null);
    }

    @Test
    @SuppressWarnings("unchecked")
    void getAllProductsWithWordReturnProducts() {
        ModelMap modelMap = new ModelMap();
        Mockito.when(productService.findAll("on")).thenReturn(List.of(testProduct));
        String productsList = productController.findAll("on", modelMap);

        assertEquals(productsList, "products_list");
        List<Product> products = (List<Product>) modelMap.getAttribute("products");
        assertNotNull(products);
        assertEquals(products.size(), 1);
        assertEquals(products.get(0).getId(), 1L);
        assertEquals(products.get(0).getPrice(), 1.1);
        assertEquals(products.get(0).getName(), "one");

        Mockito.verify(productService, Mockito.times(1)).findAll("on");
    }

    @Test
    @SuppressWarnings("unchecked")
    void getAllProductsWithWordReturnVoidList() {
        ModelMap modelMap = new ModelMap();
        Mockito.when(productService.findAll("on")).thenReturn(Collections.emptyList());
        String productsList = productController.findAll("on", modelMap);

        assertEquals(productsList, "products_list");
        List<Product> products = (List<Product>) modelMap.getAttribute("products");
        assertNotNull(products);
        assertTrue(products.isEmpty());

        Mockito.verify(productService, Mockito.times(1)).findAll("on");
    }
}