package com.onoprienko.onlineshop.web.filter;

import com.onoprienko.onlineshop.security.entity.Role;
import com.onoprienko.onlineshop.security.entity.Session;
import com.onoprienko.onlineshop.security.service.SecurityService;
import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.util.List;
import java.util.Objects;


@Slf4j
public class SecurityFilter implements Filter {
    private final List<String> GUEST_PATHS = List.of("/products", "/registration", "/login", "/logout");
    private final List<String> USER_PATHS = List.of("/products", "/registration", "/login", "/cart");
    private final List<String> ADMIN_PATHS = List.of("/products", "/registration", "/login", "/product");

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        WebApplicationContext webApplicationContext =
                (WebApplicationContext) request.getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        SecurityService securityService = (SecurityService) webApplicationContext.getBean("securityService");
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        log.info("Request on path: {}", ((HttpServletRequest) request).getRequestURI());

        Cookie cookie = getCookieFromRequest(httpServletRequest);
        String requestURI = httpServletRequest.getRequestURI();
        Session session = null;
        if (cookie != null) {
            try {
                session = securityService.findSession(cookie.getValue());
            } catch (Exception e) {
                httpServletResponse.sendRedirect("/login");
                return;
            }

        }

        if (session != null) {
            httpServletRequest.setAttribute("session", session);
            if (isUserRoleAllowed(session, requestURI, Role.USER.name(), USER_PATHS) ||
                    isUserRoleAllowed(session, requestURI, Role.ADMIN.name(), ADMIN_PATHS)) {
                chain.doFilter(request, response);
                return;
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

    private boolean isUserRoleAllowed(Session session, String requestURI,
                                      String role, List<String> paths) {
        if (Objects.equals(session.getRole(), role)) {
            for (String path : paths) {
                if (requestURI.startsWith(path)) {
                    return true;
                }
            }
        }
        return false;
    }

    private Cookie getCookieFromRequest(HttpServletRequest req) {
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
}
