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
import java.util.HashMap;
import java.util.Map;


@Slf4j
@AllArgsConstructor
public class EditProductServlet extends HttpServlet {
    private final ProductService productService;
    private final PageGenerator pageGenerator;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long idToEdit = Long.parseLong(req.getParameter("id"));
        Map<String, Object> params = new HashMap<>();
        params.put("id", String.valueOf(idToEdit));
        resp.getWriter().write(pageGenerator.getPage("edit_product.ftl", params));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        long idToEdit = Long.parseLong(req.getParameter("id"));
        try {
            Product product = RequestExtractor.getProductFromRequest(req);
            product.setId(idToEdit);
            productService.editProduct(product);
            resp.sendRedirect("/products");
        } catch (Exception e) {
            String errorMessage = "Your product has not been edited! " + e.getMessage();
            Map<String, Object> params = new HashMap<>();
            params.put("id", String.valueOf(idToEdit));
            params.put("errorMessage", errorMessage);
            log.error("Error while editing product ", e);
            resp.getWriter().write(pageGenerator.getPage("edit_product.ftl", params));
        }
    }
}
