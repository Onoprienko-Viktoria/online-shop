package com.onoprienko.onlineshop.web.servlet.products;

import com.onoprienko.onlineshop.entity.Product;
import com.onoprienko.onlineshop.ioc.context.OnlineShopApplicationContext;
import com.onoprienko.onlineshop.service.ProductService;
import com.onoprienko.onlineshop.service.impl.DefaultProductService;
import com.onoprienko.onlineshop.web.utils.PageGenerator;
import com.onoprienko.onlineshop.web.utils.WebUtils;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class EditProductServlet extends HttpServlet {
    private ProductService productService = (ProductService) OnlineShopApplicationContext.getService(DefaultProductService.class);
    private PageGenerator pageGenerator = (PageGenerator) OnlineShopApplicationContext.getService(PageGenerator.class);

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long idToEdit = Long.parseLong(req.getParameter("id"));
        Map<String, Object> params = new HashMap<>();
        params.put("id", String.valueOf(idToEdit));
        resp.getWriter().write(pageGenerator.getPage("edit_product.ftl", params));
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        long idToEdit = Long.parseLong(req.getParameter("id"));
        try {
            Product product = WebUtils.getProductFromRequest(req);
            product.setId(idToEdit);
            productService.edit(product);
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
