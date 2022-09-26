package com.onoprienko.onlineshop.servlet.products;

import com.onoprienko.onlineshop.entity.Product;
import com.onoprienko.onlineshop.entity.Session;
import com.onoprienko.onlineshop.service.ProductService;
import com.onoprienko.onlineshop.utils.PageGenerator;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@AllArgsConstructor
@Slf4j
public class CartServlet extends HttpServlet {
    private ProductService productService;
    private PageGenerator pageGenerator;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Session session = (Session) req.getAttribute("session");
        log.info("Get session from attribute {}", session);
        List<Product> products = session.getCart();
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("products", products);
        String page = pageGenerator.getPage("user_cart.ftl", parameters);
        resp.getWriter().write(page);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            long id = Long.parseLong(req.getParameter("id"));
            Session session = (Session) req.getAttribute("session");
            log.info("Get session from attribute {}", session);
            if (req.getRequestURI().contains("/add")) {
                Product product = productService.getProduct(id);
                session.getCart().add(product);
                log.info("Add new product to session cart{}", product);
                resp.sendRedirect("/products");
            } else {
                List<Product> products = session.getCart();
                products.removeIf(product -> id == product.getId());
                session.setCart(products);
                log.info("Remove product from session cart");
                resp.sendRedirect("/cart");
            }
        } catch (Exception e) {
            log.error("Error in cart servlet", e);
            String errorMessage = e.getMessage();
            resp.getWriter().write(pageGenerator.getPageWithMessage("user_cart.ftl", errorMessage));
        }
    }
}
