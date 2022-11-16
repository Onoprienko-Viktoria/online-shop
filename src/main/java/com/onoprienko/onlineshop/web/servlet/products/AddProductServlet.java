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


@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class AddProductServlet extends HttpServlet {
    private ProductService productService = (ProductService) OnlineShopApplicationContext.getService(DefaultProductService.class);
    private PageGenerator pageGenerator = (PageGenerator) OnlineShopApplicationContext.getService(PageGenerator.class);

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String page = pageGenerator.getPage("add_product.ftl");
        resp.getWriter().write(page);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            Product product = WebUtils.getProductFromRequest(req);
            productService.add(product);
            resp.sendRedirect("/products");
        } catch (Exception e) {
            log.error("Error in add product servlet", e);
            String errorMessage = "Your product has not been added! " + e.getMessage();
            resp.getWriter().write(pageGenerator.getPageWithMessage("add_product.ftl", errorMessage));
        }
    }
}





