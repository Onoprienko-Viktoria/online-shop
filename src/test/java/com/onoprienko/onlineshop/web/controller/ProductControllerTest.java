package com.onoprienko.onlineshop.web.controller;

import com.onoprienko.onlineshop.entity.Product;
import com.onoprienko.onlineshop.service.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.ui.ModelMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductControllerTest {
    private final ProductService productService = Mockito.mock(ProductService.class);
    private final ProductsController productController = new ProductsController(productService);

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
    void editProductReturnRedirectToProducts() {
        String response = productController.edit(Product.builder().name("test").price(11.0)
                .build(), new ModelMap());

        assertEquals(response, "redirect:/products");
    }

    @Test
    void removeProductReturnRedirect() {
        String remove = productController.remove(1L);

        assertEquals(remove, "redirect:/products");
    }
}