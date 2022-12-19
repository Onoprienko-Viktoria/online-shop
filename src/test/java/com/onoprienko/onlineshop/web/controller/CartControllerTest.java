package com.onoprienko.onlineshop.web.controller;

import com.onoprienko.onlineshop.entity.Product;
import com.onoprienko.onlineshop.security.entity.Session;
import com.onoprienko.onlineshop.service.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.ui.ModelMap;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CartControllerTest {
    ProductService productService = Mockito.mock(ProductService.class);
    CartController cartController = new CartController(productService);
    List<Product> products = List.of(Product.builder().id(1L).build());
    Session session = Session.builder().token("token").cart(products).build();

    @Test
    void getCartReturnCartNameAndProductsInModelMap() {
        ModelMap modelMap = new ModelMap();

        String cart = cartController.getCart(session, modelMap);

        assertEquals(cart, "user_cart");

        assertEquals(modelMap.getAttribute("products"), products);

    }

    @Test
    void addProductReturnRedirectToProducts() {
        ModelMap modelMap = new ModelMap();
        Product product = Product.builder().id(2L).build();
        Mockito.when(productService.getById(2L)).thenReturn(product);
        cartController.add(2L, session, modelMap);

        assertEquals(session.getCart().size(), 1);
        assertEquals(session.getCart().get(1), product);

        Mockito.verify(productService, Mockito.times(1)).getById(1);
    }

    @Test
    void removeFromCartReturnRedirectToCart() {

        String remove = cartController.remove(1L, session);

        assertEquals(remove, "redirect:cart");
        assertTrue(session.getCart().isEmpty());
    }
}