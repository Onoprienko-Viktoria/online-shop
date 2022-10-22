package com.onoprienko.onlineshop.web.servlet.security;

import com.onoprienko.onlineshop.security.service.SecurityService;
import com.onoprienko.onlineshop.service.locator.ServiceLocator;
import com.onoprienko.onlineshop.web.utils.WebUtils;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class LogoutServlet extends HttpServlet {
    private final SecurityService securityService;

    public LogoutServlet() {
        this.securityService = ServiceLocator.getService(SecurityService.class);
    }

    public LogoutServlet(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = WebUtils.getUserTokenFromRequest(req);
        Cookie cookie = new Cookie("user-token", token);
        if (securityService.logout(token)) {
            cookie.setMaxAge(0);
            resp.addCookie(cookie);
            log.info("Logout user with token {}", token);
            resp.sendRedirect("/products");
        }
    }
}

