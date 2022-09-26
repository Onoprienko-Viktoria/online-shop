package com.onoprienko.onlineshop.security.filter;

import com.onoprienko.onlineshop.entity.Role;
import com.onoprienko.onlineshop.entity.Session;
import com.onoprienko.onlineshop.service.SessionService;
import com.onoprienko.onlineshop.utils.RequestExtractor;
import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;


@Slf4j
public class SecurityFilter implements Filter {
    private final SessionService sessionService;

    public SecurityFilter(SessionService sessionService) {
        this.sessionService = sessionService;
    }


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        log.info("Request on path: {}", ((HttpServletRequest) request).getRequestURI());

        Cookie cookie = RequestExtractor.getCookieFromRequest(httpServletRequest);
        String requestURI = httpServletRequest.getRequestURI();
        Session session = null;
        if (cookie != null) {
            session = sessionService.getSession(cookie.getValue());
        }
        if (session != null) {
            httpServletRequest.setAttribute("session", session);
            if (LocalDateTime.now().isAfter(session.getExpire())) {
                httpServletResponse.sendRedirect("/login");
                return;
            }
            for (String path : Role.valueOf(session.getRole()).getPaths()) {
                if (requestURI.startsWith(path)) {
                    chain.doFilter(request, response);
                    return;
                }
            }
        }
        List<String> paths = Role.GUEST.getPaths();
        for (String path : paths) {
            if (requestURI.startsWith(path)) {
                chain.doFilter(request, response);
                return;
            }
        }
        httpServletResponse.sendRedirect("/login");
    }
}
