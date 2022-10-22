package com.onoprienko.onlineshop.web.filter;

import com.onoprienko.onlineshop.security.entity.Session;
import com.onoprienko.onlineshop.security.service.SecurityService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

class SecurityFilterTest {
    private final SecurityService securityService = Mockito.mock(SecurityService.class);
    private final HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);
    private final HttpServletResponse httpServletResponse = Mockito.mock(HttpServletResponse.class);
    private final FilterChain filterChain = Mockito.mock(FilterChain.class);

    SecurityFilter securityFilter = new SecurityFilter(securityService);

    @Test
    void doFilterTestWithExpiredSessionSendRedirect() throws ServletException, IOException {
        Cookie[] cookies = new Cookie[2];
        cookies[0] = new Cookie("test", "dsdfsd");
        cookies[1] = new Cookie("user-token", "dsdfsdf");
        Mockito.when(httpServletRequest.getCookies()).thenReturn(cookies);

        Mockito.when(httpServletRequest.getRequestURI()).thenReturn("/product/edit");

        Mockito.when(securityService.findSession("dsdfsdf")).thenReturn(Session.builder()
                .token("dsdfsd")
                .role("ADMIN")
                .expire(LocalDateTime.now().minusMinutes(10))
                .build());

        securityFilter.doFilter(httpServletRequest, httpServletResponse, filterChain);

        Mockito.verify(httpServletRequest, Mockito.times(1)).getCookies();
        Mockito.verify(httpServletRequest, Mockito.times(2)).getRequestURI();
        Mockito.verify(securityService, Mockito.times(1)).findSession("dsdfsdf");


        Mockito.verify(httpServletResponse, Mockito.times(1)).sendRedirect("/login");
    }

    @Test
    void doFilterTestWithRoleAdminOnPathForAdmin() throws ServletException, IOException {
        Cookie[] cookies = new Cookie[2];
        cookies[0] = new Cookie("test", "dsdfsd");
        cookies[1] = new Cookie("user-token", "dsdfsdf");
        Mockito.when(httpServletRequest.getCookies()).thenReturn(cookies);

        Mockito.when(httpServletRequest.getRequestURI()).thenReturn("/product/edit");

        Mockito.when(securityService.findSession("dsdfsdf")).thenReturn(Session.builder()
                .token("dsdfsd")
                .role("ADMIN")
                .expire(LocalDateTime.now().plusMinutes(10))
                .build());

        securityFilter.doFilter(httpServletRequest, httpServletResponse, filterChain);

        Mockito.verify(httpServletRequest, Mockito.times(1)).getCookies();
        Mockito.verify(httpServletRequest, Mockito.times(2)).getRequestURI();
        Mockito.verify(securityService, Mockito.times(1)).findSession("dsdfsdf");
    }

    @Test
    void doFilterTestWithRoleUserOnPathForAdminReturnRedirect() throws ServletException, IOException {
        Cookie[] cookies = new Cookie[2];
        cookies[0] = new Cookie("test", "dsdfsd");
        cookies[1] = new Cookie("user-token", "dsdfsdf");
        Mockito.when(httpServletRequest.getCookies()).thenReturn(cookies);

        Mockito.when(httpServletRequest.getRequestURI()).thenReturn("/product/edit");

        Mockito.when(securityService.findSession("dsdfsdf")).thenReturn(Session.builder()
                .token("dsdfsd")
                .role("USER")
                .expire(LocalDateTime.now().plusMinutes(10))
                .build());

        securityFilter.doFilter(httpServletRequest, httpServletResponse, filterChain);

        Mockito.verify(httpServletRequest, Mockito.times(1)).getCookies();
        Mockito.verify(httpServletRequest, Mockito.times(2)).getRequestURI();
        Mockito.verify(securityService, Mockito.times(1)).findSession("dsdfsdf");


        Mockito.verify(httpServletResponse, Mockito.times(1)).sendRedirect("/login");
    }

    @Test
    void doFilterTestWithRoleGuestOnPathForAdminReturnRedirect() throws ServletException, IOException {
        Cookie[] cookies = new Cookie[2];
        cookies[0] = new Cookie("test", "dsdfsd");
        cookies[1] = new Cookie("user-token", "dsdfsdf");
        Mockito.when(httpServletRequest.getCookies()).thenReturn(cookies);

        Mockito.when(httpServletRequest.getRequestURI()).thenReturn("/product/edit");

        Mockito.when(securityService.findSession("dsdfsdf")).thenReturn(null);

        securityFilter.doFilter(httpServletRequest, httpServletResponse, filterChain);

        Mockito.verify(httpServletRequest, Mockito.times(1)).getCookies();
        Mockito.verify(httpServletRequest, Mockito.times(2)).getRequestURI();
        Mockito.verify(securityService, Mockito.times(1)).findSession("dsdfsdf");


        Mockito.verify(httpServletResponse, Mockito.times(1)).sendRedirect("/login");
    }

    @Test
    void doFilterTestWithRoleAdminOnPathForUserReturnRedirect() throws ServletException, IOException {
        Cookie[] cookies = new Cookie[2];
        cookies[0] = new Cookie("test", "dsdfsd");
        cookies[1] = new Cookie("user-token", "dsdfsdf");
        Mockito.when(httpServletRequest.getCookies()).thenReturn(cookies);

        Mockito.when(httpServletRequest.getRequestURI()).thenReturn("/cart");

        Mockito.when(securityService.findSession("dsdfsdf")).thenReturn(Session.builder()
                .token("dsdfsd")
                .role("ADMIN")
                .expire(LocalDateTime.now().plusMinutes(10))
                .build());

        securityFilter.doFilter(httpServletRequest, httpServletResponse, filterChain);

        Mockito.verify(httpServletRequest, Mockito.times(1)).getCookies();
        Mockito.verify(httpServletRequest, Mockito.times(2)).getRequestURI();
        Mockito.verify(securityService, Mockito.times(1)).findSession("dsdfsdf");

        Mockito.verify(httpServletResponse, Mockito.times(1)).sendRedirect("/login");
    }

    @Test
    void doFilterTestWithRoleUserOnPathForUser() throws ServletException, IOException {
        Cookie[] cookies = new Cookie[2];
        cookies[0] = new Cookie("test", "dsdfsd");
        cookies[1] = new Cookie("user-token", "dsdfsdf");
        Mockito.when(httpServletRequest.getCookies()).thenReturn(cookies);

        Mockito.when(httpServletRequest.getRequestURI()).thenReturn("/cart");

        Mockito.when(securityService.findSession("dsdfsdf")).thenReturn(Session.builder()
                .token("dsdfsd")
                .role("USER")
                .expire(LocalDateTime.now().plusMinutes(10))
                .build());

        securityFilter.doFilter(httpServletRequest, httpServletResponse, filterChain);

        Mockito.verify(httpServletRequest, Mockito.times(1)).getCookies();
        Mockito.verify(httpServletRequest, Mockito.times(2)).getRequestURI();
        Mockito.verify(securityService, Mockito.times(1)).findSession("dsdfsdf");
    }

    @Test
    void doFilterTestWithRoleGuestOnPathForUserReturnRedirect() throws ServletException, IOException {
        Cookie[] cookies = new Cookie[2];
        cookies[0] = new Cookie("test", "dsdfsd");
        cookies[1] = new Cookie("user-token", "dsdfsdf");
        Mockito.when(httpServletRequest.getCookies()).thenReturn(cookies);

        Mockito.when(httpServletRequest.getRequestURI()).thenReturn("/cart");

        Mockito.when(securityService.findSession("dsdfsdf")).thenReturn(null);

        securityFilter.doFilter(httpServletRequest, httpServletResponse, filterChain);

        Mockito.verify(httpServletRequest, Mockito.times(1)).getCookies();
        Mockito.verify(httpServletRequest, Mockito.times(2)).getRequestURI();
        Mockito.verify(securityService, Mockito.times(1)).findSession("dsdfsdf");


        Mockito.verify(httpServletResponse, Mockito.times(1)).sendRedirect("/login");
    }

    @Test
    void doFilterTestWithRoleAdminOnPathForGuestReturnOk() throws ServletException, IOException {
        Cookie[] cookies = new Cookie[2];
        cookies[0] = new Cookie("test", "dsdfsd");
        cookies[1] = new Cookie("user-token", "dsdfsdf");
        Mockito.when(httpServletRequest.getCookies()).thenReturn(cookies);

        Mockito.when(httpServletRequest.getRequestURI()).thenReturn("/products");

        Mockito.when(securityService.findSession("dsdfsdf")).thenReturn(Session.builder()
                .token("dsdfsd")
                .role("ADMIN")
                .expire(LocalDateTime.now().plusMinutes(10))
                .build());

        securityFilter.doFilter(httpServletRequest, httpServletResponse, filterChain);

        Mockito.verify(httpServletRequest, Mockito.times(1)).getCookies();
        Mockito.verify(httpServletRequest, Mockito.times(2)).getRequestURI();
        Mockito.verify(securityService, Mockito.times(1)).findSession("dsdfsdf");
    }

    @Test
    void doFilterTestWithRoleUserOnPathForGuestReturnOk() throws ServletException, IOException {
        Cookie[] cookies = new Cookie[2];
        cookies[0] = new Cookie("test", "dsdfsd");
        cookies[1] = new Cookie("user-token", "dsdfsdf");
        Mockito.when(httpServletRequest.getCookies()).thenReturn(cookies);

        Mockito.when(httpServletRequest.getRequestURI()).thenReturn("/products");

        Mockito.when(securityService.findSession("dsdfsdf")).thenReturn(Session.builder()
                .token("dsdfsd")
                .role("USER")
                .expire(LocalDateTime.now().plusMinutes(10))
                .build());

        securityFilter.doFilter(httpServletRequest, httpServletResponse, filterChain);

        Mockito.verify(httpServletRequest, Mockito.times(1)).getCookies();
        Mockito.verify(httpServletRequest, Mockito.times(2)).getRequestURI();
        Mockito.verify(securityService, Mockito.times(1)).findSession("dsdfsdf");
    }

    @Test
    void doFilterTestWithRoleGuestOnPathForGuestReturnOk() throws ServletException, IOException {
        Cookie[] cookies = new Cookie[2];
        cookies[0] = new Cookie("test", "dsdfsd");
        cookies[1] = new Cookie("user-token", "dsdfsdf");
        Mockito.when(httpServletRequest.getCookies()).thenReturn(cookies);

        Mockito.when(httpServletRequest.getRequestURI()).thenReturn("/products");

        Mockito.when(securityService.findSession("dsdfsdf")).thenReturn(null);

        securityFilter.doFilter(httpServletRequest, httpServletResponse, filterChain);

        Mockito.verify(httpServletRequest, Mockito.times(1)).getCookies();
        Mockito.verify(httpServletRequest, Mockito.times(2)).getRequestURI();
        Mockito.verify(securityService, Mockito.times(1)).findSession("dsdfsdf");
    }
}