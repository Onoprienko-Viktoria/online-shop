package com.onoprienko.onlineshop.servlet.products;

import com.onoprienko.onlineshop.entity.Product;
import com.onoprienko.onlineshop.service.ProductService;
import com.onoprienko.onlineshop.utils.PageGenerator;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class AllProductsServlet extends HttpServlet {
    private final ProductService productService;
    private final PageGenerator pageGenerator;

    public AllProductsServlet(ProductService productService, PageGenerator pageGenerator) {
        this.productService = productService;
        this.pageGenerator = pageGenerator;
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String word = req.getParameter("word");
        List<Product> products;
        if (word != null) {
            products = productService.findAll(word);
        } else {
            products = productService.findAll();
        }
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("products", products);
        String page = pageGenerator.getPage("products_list.ftl", parameters);
        resp.getWriter().write(page);
    }
}
