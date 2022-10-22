package com.onoprienko.onlineshop.web.filter;

import com.onoprienko.onlineshop.security.entity.Role;
import com.onoprienko.onlineshop.security.entity.Session;
import com.onoprienko.onlineshop.security.service.SecurityService;
import com.onoprienko.onlineshop.service.locator.ServiceLocator;
import com.onoprienko.onlineshop.web.utils.WebUtils;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;


@Slf4j
public class SecurityFilter implements Filter {
    private final List<String> GUEST_PATHS = List.of("/products", "/registration", "/login", "/logout");
    private final List<String> USER_PATHS = List.of("/products", "/registration", "/login", "/cart");
    private final List<String> ADMIN_PATHS = List.of("/products", "/registration", "/login", "/product");

    private final SecurityService securityService;

    public SecurityFilter(SecurityService securityService) {
        this.securityService = securityService;
    }

    public SecurityFilter() {
        this.securityService = ServiceLocator.getService(SecurityService.class);
    }


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        log.info("Request on path: {}", ((HttpServletRequest) request).getRequestURI());

        Cookie cookie = WebUtils.getCookieFromRequest(httpServletRequest);
        String requestURI = httpServletRequest.getRequestURI();
        Session session = null;
        if (cookie != null) {
            session = securityService.findSession(cookie.getValue());
        }

        if (session != null) {
            httpServletRequest.setAttribute("session", session);
            if (LocalDateTime.now().isAfter(session.getExpire())) {
                httpServletResponse.sendRedirect("/login");
                return;
            }
            if (Objects.equals(session.getRole(), Role.USER.name())) {
                for (String path : USER_PATHS) {
                    if (requestURI.startsWith(path)) {
                        chain.doFilter(request, response);
                        return;
                    }
                }
            }

            if (Objects.equals(session.getRole(), Role.ADMIN.name())) {
                for (String path : ADMIN_PATHS) {
                    if (requestURI.startsWith(path)) {
                        chain.doFilter(request, response);
                        return;
                    }
                }
            }
        }
        for (String path : GUEST_PATHS) {
            if (requestURI.startsWith(path)) {
                chain.doFilter(request, response);
                return;
            }
        }

        httpServletResponse.sendRedirect("/login");
    }
}
