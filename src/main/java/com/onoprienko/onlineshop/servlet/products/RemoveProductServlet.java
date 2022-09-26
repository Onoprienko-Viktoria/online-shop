package com.onoprienko.onlineshop.servlet.products;

import com.onoprienko.onlineshop.service.ProductService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class RemoveProductServlet extends HttpServlet {
    private final ProductService productService;

    public RemoveProductServlet(ProductService productService) {
        this.productService = productService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        long id = Long.parseLong(req.getParameter("id"));
        productService.removeProduct(id);
        log.info("Delete product with id {}", id);
        resp.sendRedirect("/products");
    }
}
