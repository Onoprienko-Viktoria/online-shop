package com.onoprienko.onlineshop.servlet.products;

import com.onoprienko.onlineshop.entity.Product;
import com.onoprienko.onlineshop.service.ProductService;
import com.onoprienko.onlineshop.utils.PageGenerator;
import com.onoprienko.onlineshop.utils.RequestExtractor;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;


@Slf4j
@AllArgsConstructor
public class AddProductServlet extends HttpServlet {
    private final ProductService productService;
    private final PageGenerator pageGenerator;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String page = pageGenerator.getPage("add_product.ftl");
        resp.getWriter().write(page);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            Product product = RequestExtractor.getProductFromRequest(req);
            productService.addProduct(product);
            resp.sendRedirect("/products");
        } catch (Exception e) {
            log.error("Error in add product servlet", e);
            String errorMessage = "Your product has not been added! " + e.getMessage();
            resp.getWriter().write(pageGenerator.getPageWithMessage("add_product.ftl", errorMessage));
        }
    }
}





