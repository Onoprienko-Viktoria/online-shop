package com.onoprienko.onlineshop.security.entity;

import com.onoprienko.onlineshop.entity.Product;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@Component
@SessionScope
public class Cart {
    private List<Product> products;

    public Cart() {
        products = new ArrayList<>();
    }
}
