package com.onoprienko.onlineshop.web.servlet.products;

import com.onoprienko.onlineshop.entity.Product;
import com.onoprienko.onlineshop.ioc.context.OnlineShopApplicationContext;
import com.onoprienko.onlineshop.security.entity.Session;
import com.onoprienko.onlineshop.service.ProductService;
import com.onoprienko.onlineshop.service.impl.DefaultProductService;
import com.onoprienko.onlineshop.web.utils.PageGenerator;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class AddToCartServlet extends HttpServlet {
    private ProductService productService = (ProductService) OnlineShopApplicationContext.getService(DefaultProductService.class);
    private PageGenerator pageGenerator = (PageGenerator) OnlineShopApplicationContext.getService(PageGenerator.class);

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            long id = Long.parseLong(req.getParameter("id"));
            Session session = (Session) req.getAttribute("session");
            log.info("Get session from attribute {}", session);
            Product product = productService.getById(id);
            session.getCart().add(product);
            log.info("Add new product to session cart{}", product);
            resp.sendRedirect("/products");
        } catch (Exception e) {
            log.error("Error in cart servlet", e);
            String errorMessage = e.getMessage();
            resp.getWriter().write(pageGenerator.getPageWithMessage("user_cart.ftl", errorMessage));
        }
    }
}
