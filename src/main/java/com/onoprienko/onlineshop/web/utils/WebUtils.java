package com.onoprienko.onlineshop.web.utils;


import com.onoprienko.onlineshop.entity.Product;
import com.onoprienko.onlineshop.entity.User;
import com.onoprienko.onlineshop.security.entity.Credentials;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class WebUtils {
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
                .email(req.getParameter("email"))
                .password(req.getParameter("password"))
                .build();
    }

    public static Credentials getCredentialsFromRequest(HttpServletRequest req) {
        return Credentials.builder()
                .email(req.getParameter("email"))
                .password(req.getParameter("password"))
                .build();
    }
}

