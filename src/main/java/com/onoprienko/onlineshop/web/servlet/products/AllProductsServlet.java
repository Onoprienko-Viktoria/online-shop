package com.onoprienko.onlineshop.web.servlet.products;

import com.onoprienko.onlineshop.entity.Product;
import com.onoprienko.onlineshop.service.ProductService;
import com.onoprienko.onlineshop.service.locator.ServiceLocator;
import com.onoprienko.onlineshop.web.utils.PageGenerator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    public AllProductsServlet() {
        this.productService = ServiceLocator.getService(ProductService.class);
        this.pageGenerator = ServiceLocator.getService(PageGenerator.class);
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String word = req.getParameter("word");
        List<Product> products;
        products = productService.findAll(word);
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("products", products);
        String page = pageGenerator.getPage("products_list.ftl", parameters);
        resp.getWriter().write(page);
    }
}
