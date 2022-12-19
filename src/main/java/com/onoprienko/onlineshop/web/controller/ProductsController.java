package com.onoprienko.onlineshop.web.controller;

import com.onoprienko.onlineshop.entity.Product;
import com.onoprienko.onlineshop.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@AllArgsConstructor
@Slf4j
public class ProductsController {
    private final ProductService productService;

    @GetMapping("/products")
    public String findAll(@RequestParam(required = false) String word, ModelMap model) {
        List<Product> allProducts = productService.findAll(word);
        model.addAttribute("products", allProducts);
        return "products_list";
    }

    @GetMapping("/product/add")
    public String getAddPage() {
        return "add_product";
    }

    @PostMapping("/product/add")
    public String add(@ModelAttribute Product product, ModelMap model) {
        try {
            productService.add(product);
            return "redirect:/products";
        } catch (Exception e) {
            log.error("Error in add product servlet", e);
            String errorMessage = "Your product has not been added! " + e.getMessage();
            model.addAttribute("errorMessage", errorMessage);
            return "add_product";
        }
    }

    @GetMapping("/product/edit")
    public String getEditPage(@RequestParam String id, ModelMap modelMap) {
        modelMap.addAttribute("id", id);
        return "edit_product";
    }

    @PostMapping("/product/edit")
    public String edit(@ModelAttribute Product product, ModelMap model) {
        try {
            productService.edit(product);
            return "redirect:/products";
        } catch (Exception e) {
            String errorMessage = "Your product has not been edited! " + e.getMessage();
            model.addAttribute("id", product.getId());
            model.addAttribute("errorMessage", errorMessage);
            log.error("Error while editing product ", e);
            return "edit_product";
        }
    }

    @PostMapping("/product/remove")
    public String remove(@RequestParam Long id) {
        productService.remove(id);
        log.info("Delete product with id {}", id);
        return "redirect:/products";
    }
}
