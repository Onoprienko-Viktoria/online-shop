package com.onoprienko.onlineshop.utils;


import com.onoprienko.onlineshop.entity.Product;
import com.onoprienko.onlineshop.entity.User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

public class RequestExtractor {
    public static Product getProductFromRequest(HttpServletRequest req) {
        return Product.builder()
                .name(req.getParameter("name"))
                .price(Double.parseDouble(req.getParameter("price")))
                .build();
    }

    public static String getUserTokenFromRequest(HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user-token")) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }


    public static Cookie getCookieFromRequest(HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user-token")) {
                    return cookie;
                }
            }
        }
        return null;
    }

    public static User getUserFromRequest(HttpServletRequest req) {
        return User.builder()
                .name(req.getParameter("name"))
                .role(req.getParameter("role"))
                .email(req.getParameter("email"))
                .password(req.getParameter("password"))
                .build();
    }
}

