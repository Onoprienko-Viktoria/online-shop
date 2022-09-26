package com.onoprienko.onlineshop.servlet.users;

import com.onoprienko.onlineshop.service.SessionService;
import com.onoprienko.onlineshop.utils.RequestExtractor;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@AllArgsConstructor
@Slf4j
public class LogoutServlet extends HttpServlet {
    private SessionService sessionService;

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String token = RequestExtractor.getUserTokenFromRequest(req);
        Cookie cookie = new Cookie("user-token", token);
        if (sessionService.removeSession(token)) {
            cookie.setMaxAge(0);
            resp.addCookie(cookie);
            log.info("Logout user with token {}", token);
            resp.sendRedirect("/products");
        }
    }
}

