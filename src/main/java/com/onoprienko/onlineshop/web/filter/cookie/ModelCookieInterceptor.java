package com.onoprienko.onlineshop.web.filter.cookie;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Slf4j
public class ModelCookieInterceptor implements HandlerInterceptor {

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws IOException {
        if (modelAndView != null) {
            for (Object value : modelAndView.getModel().values()) {
                if (value instanceof Cookie) {
                    response.addCookie((Cookie) value);
                    response.sendRedirect("/products");
                }
            }
        }
    }

}