package com.onoprienko.onlineshop.web.controller;

import com.onoprienko.onlineshop.entity.Product;
import com.onoprienko.onlineshop.security.entity.Cart;
import com.onoprienko.onlineshop.service.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.ui.ModelMap;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class CartControllerTest {
    ProductService productService = Mockito.mock(ProductService.class);
    Product testProduct = Product.builder()
            .id(1L)
            .name("one")
            .price(1.1)
            .build();
    Cart cart = new Cart();
    CartController cartController = new CartController(productService, cart);


    @Test
    @SuppressWarnings("unchecked")
    void getVoidCartReturnVoidListInModelMap() {
        ModelMap modelMap = new ModelMap();


        String cart = cartController.getCart(modelMap);

        assertEquals(modelMap.getAttribute("products"), Collections.EMPTY_LIST);
        assertEquals("user_cart", cart);
        List<Product> products = (List<Product>) modelMap.getAttribute("products");
        assertNotNull(products);
        assertTrue(products.isEmpty());
    }

    @Test
    @SuppressWarnings("unchecked")
    void getCartReturnProductsInModelMap() {
        ModelMap modelMap = new ModelMap();
        cart.getProducts().add(testProduct);
        cart.getProducts().add(testProduct);

        String cart = cartController.getCart(modelMap);

        assertEquals(modelMap.getAttribute("products"), List.of(testProduct, testProduct));
        assertEquals("user_cart", cart);
        List<Product> products = (List<Product>) modelMap.getAttribute("products");
        assertNotNull(products);
        assertEquals(products.size(), 2);
        assertEquals(products.get(0).getId(), 1L);
        assertEquals(products.get(0).getPrice(), 1.1);
        assertEquals(products.get(0).getName(), "one");
        assertEquals(products.get(1).getId(), 1L);
        assertEquals(products.get(1).getPrice(), 1.1);
        assertEquals(products.get(1).getName(), "one");
    }


    @Test
    void addProductReturnRedirectToProductsAndAddProductToCart() {
        ModelMap modelMap = new ModelMap();
        Product product = Product.builder()
                .id(2L)
                .price(2.2)
                .name("two").build();
        Mockito.when(productService.getById(2L)).thenReturn(product);
        String addRedirect = cartController.add(2L, modelMap);

        List<Product> products = cart.getProducts();

        assertEquals(products.size(), 1);
        assertTrue(products.contains(product));
        assertEquals(products.get(0).getName(), "two");
        assertEquals(products.get(0).getId(), 2L);
        assertEquals(products.get(0).getPrice(), 2.2);

        assertEquals(addRedirect, "redirect:/products");
        Mockito.verify(productService, Mockito.times(1)).getById(2L);
    }


    @Test
    void addProductThatNotExistReturnExceptionAndRedirectToCart() {
        ModelMap modelMap = new ModelMap();
        Mockito.when(productService.getById(2L)).thenThrow(new NoSuchElementException("No such product"));
        String addRedirect = cartController.add(2L, modelMap);

        List<Product> products = cart.getProducts();

        assertTrue(products.isEmpty());
        assertEquals(addRedirect, "user_cart");
        Mockito.verify(productService, Mockito.times(1)).getById(2L);
    }

    @Test
    void removeFromCartReturnRemoveProductAndSendRedirectToCart() {
        cart.getProducts().add(testProduct);

        String remove = cartController.remove(1L);

        assertEquals(remove, "redirect:/cart");
        assertTrue(cart.getProducts().isEmpty());
    }

    @Test
    void removeFromCartProductThatNotExistDontThrowException() {
        assertTrue(cart.getProducts().isEmpty());

        String remove = cartController.remove(1L);

        assertEquals(remove, "redirect:/cart");
        assertTrue(cart.getProducts().isEmpty());
    }

}