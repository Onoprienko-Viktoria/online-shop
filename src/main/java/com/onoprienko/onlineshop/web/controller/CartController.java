package com.onoprienko.onlineshop.web.controller;

import com.onoprienko.onlineshop.entity.Product;
import com.onoprienko.onlineshop.security.entity.Cart;
import com.onoprienko.onlineshop.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Controller
@AllArgsConstructor
@RequestMapping("/cart")
@Slf4j
public class CartController {
    private final ProductService productService;
    private final Cart cart;


    @GetMapping
    public String getCart(ModelMap model) {
        log.info("Get cart {}", cart);
        List<Product> products = cart.getProducts();
        model.addAttribute("products", products);
        return "user_cart";
    }

    @PostMapping("/add")
    public String add(@RequestParam Long id,
                      ModelMap model) {
        try {
            Product product = productService.getById(id);
            cart.getProducts().add(product);
            log.info("Add new product to session cart {}", product);
            return "redirect:/products";
        } catch (Exception e) {
            log.error("Error while add to cart", e);
            String errorMessage = e.getMessage();
            model.addAttribute("errorMessage", errorMessage);
            return "user_cart";
        }
    }

    @PostMapping("/remove")
    public String remove(@RequestParam Long id) {
        List<Product> products = cart.getProducts();
        Iterator<Product> iterator = products.listIterator();
        while (iterator.hasNext()) {
            if (Objects.equals(iterator.next().getId(), id)) {
                iterator.remove();
                break;
            }
        }
        log.info("Remove product from session cart");
        return ("redirect:/cart");
    }
}
