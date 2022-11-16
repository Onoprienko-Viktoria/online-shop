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
import java.util.HashMap;
import java.util.List;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class GetCartServlet extends HttpServlet {
    private ProductService productService = (ProductService) OnlineShopApplicationContext.getService(DefaultProductService.class);
    private PageGenerator pageGenerator = (PageGenerator) OnlineShopApplicationContext.getService(PageGenerator.class);


    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Session session = (Session) req.getAttribute("session");
        log.info("Get session from attribute {}", session);
        List<Product> products = session.getCart();
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("products", products);
        String page = pageGenerator.getPage("user_cart.ftl", parameters);
        resp.getWriter().write(page);
    }
}
