package com.onoprienko.onlineshop.web.servlet.products;

import com.onoprienko.onlineshop.entity.Product;
import com.onoprienko.onlineshop.security.entity.Session;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Slf4j
public class RemoveFromCartServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long id = Long.parseLong(req.getParameter("id"));
        Session session = (Session) req.getAttribute("session");
        log.info("Get session from attribute {}", session);
        List<Product> products = session.getCart();
        Iterator<Product> iterator = products.listIterator();
        while (iterator.hasNext()) {
            if (Objects.equals(iterator.next().getId(), id)) {
                iterator.remove();
                break;
            }
        }
        log.info("Remove product from session cart");
        resp.sendRedirect("/cart");
    }
}
