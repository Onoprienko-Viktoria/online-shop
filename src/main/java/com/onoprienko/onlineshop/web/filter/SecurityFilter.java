package com.onoprienko.onlineshop.web.filter;

import com.onoprienko.onlineshop.ioc.context.OnlineShopApplicationContext;
import com.onoprienko.onlineshop.security.entity.Role;
import com.onoprienko.onlineshop.security.entity.Session;
import com.onoprienko.onlineshop.security.service.SecurityService;
import com.onoprienko.onlineshop.security.service.impl.DefaultSecurityService;
import com.onoprienko.onlineshop.web.utils.WebUtils;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;


@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class SecurityFilter implements Filter {
    private final List<String> GUEST_PATHS = List.of("/products", "/registration", "/login", "/logout");
    private final List<String> USER_PATHS = List.of("/products", "/registration", "/login", "/cart");
    private final List<String> ADMIN_PATHS = List.of("/products", "/registration", "/login", "/product");

    private SecurityService securityService = (SecurityService) OnlineShopApplicationContext.getService(DefaultSecurityService.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        log.info("Request on path: {}", ((HttpServletRequest) request).getRequestURI());

        Cookie cookie = WebUtils.getCookieFromRequest(httpServletRequest);
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
}
