package com.onoprienko.onlineshop.web.servlet.products;

import com.onoprienko.onlineshop.ioc.context.OnlineShopApplicationContext;
import com.onoprienko.onlineshop.service.ProductService;
import com.onoprienko.onlineshop.service.impl.DefaultProductService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class RemoveProductServlet extends HttpServlet {
    private ProductService productService = (ProductService) OnlineShopApplicationContext.getService(DefaultProductService.class);

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long id = Long.parseLong(req.getParameter("id"));
        productService.remove(id);
        log.info("Delete product with id {}", id);
        resp.sendRedirect("/products");
    }
}
