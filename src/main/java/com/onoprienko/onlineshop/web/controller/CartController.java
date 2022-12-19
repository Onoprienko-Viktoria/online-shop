package com.onoprienko.onlineshop.web.controller;

import com.onoprienko.onlineshop.entity.Product;
import com.onoprienko.onlineshop.security.entity.Session;
import com.onoprienko.onlineshop.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Controller
@AllArgsConstructor
@RequestMapping("/cart")
@Slf4j
public class CartController {
    private final ProductService productService;


    @GetMapping
    public String getCart(@ModelAttribute Session session, ModelMap model) {
        log.info("Get session from attribute {}", session);
        List<Product> products = session.getCart();
        model.addAttribute("products", products);
        return "user_cart";
    }

    @PostMapping("/add")
    public String add(@RequestParam Long id,
                      @ModelAttribute Session session,
                      ModelMap model) {
        try {
            log.info("Get session from attribute {}", session);
            Product product = productService.getById(id);
            session.getCart().add(product);
            log.info("Add new product to session cart{}", product);
            return "redirect:/products";
        } catch (Exception e) {
            log.error("Error in cart servlet", e);
            String errorMessage = e.getMessage();
            model.addAttribute("errorMessage", errorMessage);
            return "user_cart";
        }
    }

    @PostMapping("/remove")
    public String remove(@RequestParam Long id,
                         @ModelAttribute Session session) {
        log.info("Get session from attribute {}", session);
        List<Product> products = session.getCart();
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
