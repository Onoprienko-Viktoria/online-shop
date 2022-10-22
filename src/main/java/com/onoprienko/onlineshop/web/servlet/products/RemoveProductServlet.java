package com.onoprienko.onlineshop.web.servlet.products;

import com.onoprienko.onlineshop.service.ProductService;
import com.onoprienko.onlineshop.service.locator.ServiceLocator;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j

public class RemoveProductServlet extends HttpServlet {
    private ProductService productService;

    public RemoveProductServlet(ProductService productService) {
        this.productService = productService;
    }

    public RemoveProductServlet() {
        this.productService = ServiceLocator.getService(ProductService.class);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long id = Long.parseLong(req.getParameter("id"));
        productService.remove(id);
        log.info("Delete product with id {}", id);
        resp.sendRedirect("/products");
    }
}
