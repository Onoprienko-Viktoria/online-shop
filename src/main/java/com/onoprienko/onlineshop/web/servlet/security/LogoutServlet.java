package com.onoprienko.onlineshop.web.servlet.security;

import com.onoprienko.onlineshop.ioc.context.OnlineShopApplicationContext;
import com.onoprienko.onlineshop.security.entity.Session;
import com.onoprienko.onlineshop.security.service.SecurityService;
import com.onoprienko.onlineshop.security.service.impl.DefaultSecurityService;
import com.onoprienko.onlineshop.web.utils.WebUtils;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class LogoutServlet extends HttpServlet {
    private SecurityService securityService = (SecurityService) OnlineShopApplicationContext.getService(DefaultSecurityService.class);

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = WebUtils.getUserTokenFromRequest(req);
        Cookie cookie = new Cookie("user-token", token);
        Session session = (Session) req.getAttribute("session");
        if (securityService.logout(session)) {
            cookie.setMaxAge(0);
            resp.addCookie(cookie);
            log.info("Logout user with token {}", token);
            resp.sendRedirect("/products");
        }
    }
}

