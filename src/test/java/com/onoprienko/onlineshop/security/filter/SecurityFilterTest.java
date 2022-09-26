package com.onoprienko.onlineshop.security.filter;

import com.onoprienko.onlineshop.entity.Session;
import com.onoprienko.onlineshop.service.SessionService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

class SecurityFilterTest {
    private final SessionService sessionService = Mockito.mock(SessionService.class);
    private final HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);
    private final HttpServletResponse httpServletResponse = Mockito.mock(HttpServletResponse.class);
    private final FilterChain filterChain = Mockito.mock(FilterChain.class);
    private final PrintWriter printWriter = Mockito.mock(PrintWriter.class);

    SecurityFilter securityFilter = new SecurityFilter(sessionService);

    @Test
    void doFilterTestWithExpiredSessionSendRedirect() throws ServletException, IOException {
        Cookie[] cookies = new Cookie[2];
        cookies[0] = new Cookie("test", "dsdfsd");
        cookies[1] = new Cookie("user-token", "dsdfsdf");
        Mockito.when(httpServletRequest.getCookies()).thenReturn(cookies);

        Mockito.when(httpServletRequest.getRequestURI()).thenReturn("/product/edit");

        Mockito.when(sessionService.getSession("dsdfsdf")).thenReturn(Session.builder()
                .token("dsdfsd")
                .role("ADMIN")
                .expire(LocalDateTime.now().minusMinutes(10))
                .build());

        securityFilter.doFilter(httpServletRequest, httpServletResponse, filterChain);

        Mockito.verify(httpServletRequest, Mockito.times(1)).getCookies();
        Mockito.verify(httpServletRequest, Mockito.times(2)).getRequestURI();
        Mockito.verify(sessionService, Mockito.times(1)).getSession("dsdfsdf");


        Mockito.verify(httpServletResponse, Mockito.times(1)).sendRedirect("/login");
    }

    @Test
    void doFilterTestWithRoleAdminOnPathForAdmin() throws ServletException, IOException {
        Cookie[] cookies = new Cookie[2];
        cookies[0] = new Cookie("test", "dsdfsd");
        cookies[1] = new Cookie("user-token", "dsdfsdf");
        Mockito.when(httpServletRequest.getCookies()).thenReturn(cookies);

        Mockito.when(httpServletRequest.getRequestURI()).thenReturn("/product/edit");

        Mockito.when(sessionService.getSession("dsdfsdf")).thenReturn(Session.builder()
                .token("dsdfsd")
                .role("ADMIN")
                .expire(LocalDateTime.now().plusMinutes(10))
                .build());

        securityFilter.doFilter(httpServletRequest, httpServletResponse, filterChain);

        Mockito.verify(httpServletRequest, Mockito.times(1)).getCookies();
        Mockito.verify(httpServletRequest, Mockito.times(2)).getRequestURI();
        Mockito.verify(sessionService, Mockito.times(1)).getSession("dsdfsdf");
    }

    @Test
    void doFilterTestWithRoleUserOnPathForAdminReturnRedirect() throws ServletException, IOException {
        Cookie[] cookies = new Cookie[2];
        cookies[0] = new Cookie("test", "dsdfsd");
        cookies[1] = new Cookie("user-token", "dsdfsdf");
        Mockito.when(httpServletRequest.getCookies()).thenReturn(cookies);

        Mockito.when(httpServletRequest.getRequestURI()).thenReturn("/product/edit");

        Mockito.when(sessionService.getSession("dsdfsdf")).thenReturn(Session.builder()
                .token("dsdfsd")
                .role("USER")
                .expire(LocalDateTime.now().plusMinutes(10))
                .build());

        securityFilter.doFilter(httpServletRequest, httpServletResponse, filterChain);

        Mockito.verify(httpServletRequest, Mockito.times(1)).getCookies();
        Mockito.verify(httpServletRequest, Mockito.times(2)).getRequestURI();
        Mockito.verify(sessionService, Mockito.times(1)).getSession("dsdfsdf");


        Mockito.verify(httpServletResponse, Mockito.times(1)).sendRedirect("/login");
    }

    @Test
    void doFilterTestWithRoleGuestOnPathForAdminReturnRedirect() throws ServletException, IOException {
        Cookie[] cookies = new Cookie[2];
        cookies[0] = new Cookie("test", "dsdfsd");
        cookies[1] = new Cookie("user-token", "dsdfsdf");
        Mockito.when(httpServletRequest.getCookies()).thenReturn(cookies);

        Mockito.when(httpServletRequest.getRequestURI()).thenReturn("/product/edit");

        Mockito.when(sessionService.getSession("dsdfsdf")).thenReturn(null);

        securityFilter.doFilter(httpServletRequest, httpServletResponse, filterChain);

        Mockito.verify(httpServletRequest, Mockito.times(1)).getCookies();
        Mockito.verify(httpServletRequest, Mockito.times(2)).getRequestURI();
        Mockito.verify(sessionService, Mockito.times(1)).getSession("dsdfsdf");


        Mockito.verify(httpServletResponse, Mockito.times(1)).sendRedirect("/login");
    }

    @Test
    void doFilterTestWithRoleAdminOnPathForUserReturnRedirect() throws ServletException, IOException {
        Cookie[] cookies = new Cookie[2];
        cookies[0] = new Cookie("test", "dsdfsd");
        cookies[1] = new Cookie("user-token", "dsdfsdf");
        Mockito.when(httpServletRequest.getCookies()).thenReturn(cookies);

        Mockito.when(httpServletRequest.getRequestURI()).thenReturn("/cart");

        Mockito.when(sessionService.getSession("dsdfsdf")).thenReturn(Session.builder()
                .token("dsdfsd")
                .role("ADMIN")
                .expire(LocalDateTime.now().plusMinutes(10))
                .build());

        securityFilter.doFilter(httpServletRequest, httpServletResponse, filterChain);

        Mockito.verify(httpServletRequest, Mockito.times(1)).getCookies();
        Mockito.verify(httpServletRequest, Mockito.times(2)).getRequestURI();
        Mockito.verify(sessionService, Mockito.times(1)).getSession("dsdfsdf");

        Mockito.verify(httpServletResponse, Mockito.times(1)).sendRedirect("/login");
    }

    @Test
    void doFilterTestWithRoleUserOnPathForUser() throws ServletException, IOException {
        Cookie[] cookies = new Cookie[2];
        cookies[0] = new Cookie("test", "dsdfsd");
        cookies[1] = new Cookie("user-token", "dsdfsdf");
        Mockito.when(httpServletRequest.getCookies()).thenReturn(cookies);

        Mockito.when(httpServletRequest.getRequestURI()).thenReturn("/cart");

        Mockito.when(sessionService.getSession("dsdfsdf")).thenReturn(Session.builder()
                .token("dsdfsd")
                .role("USER")
                .expire(LocalDateTime.now().plusMinutes(10))
                .build());

        securityFilter.doFilter(httpServletRequest, httpServletResponse, filterChain);

        Mockito.verify(httpServletRequest, Mockito.times(1)).getCookies();
        Mockito.verify(httpServletRequest, Mockito.times(2)).getRequestURI();
        Mockito.verify(sessionService, Mockito.times(1)).getSession("dsdfsdf");
    }

    @Test
    void doFilterTestWithRoleGuestOnPathForUserReturnRedirect() throws ServletException, IOException {
        Cookie[] cookies = new Cookie[2];
        cookies[0] = new Cookie("test", "dsdfsd");
        cookies[1] = new Cookie("user-token", "dsdfsdf");
        Mockito.when(httpServletRequest.getCookies()).thenReturn(cookies);

        Mockito.when(httpServletRequest.getRequestURI()).thenReturn("/cart");

        Mockito.when(sessionService.getSession("dsdfsdf")).thenReturn(null);

        securityFilter.doFilter(httpServletRequest, httpServletResponse, filterChain);

        Mockito.verify(httpServletRequest, Mockito.times(1)).getCookies();
        Mockito.verify(httpServletRequest, Mockito.times(2)).getRequestURI();
        Mockito.verify(sessionService, Mockito.times(1)).getSession("dsdfsdf");


        Mockito.verify(httpServletResponse, Mockito.times(1)).sendRedirect("/login");
    }

    @Test
    void doFilterTestWithRoleAdminOnPathForGuestReturnOk() throws ServletException, IOException {
        Cookie[] cookies = new Cookie[2];
        cookies[0] = new Cookie("test", "dsdfsd");
        cookies[1] = new Cookie("user-token", "dsdfsdf");
        Mockito.when(httpServletRequest.getCookies()).thenReturn(cookies);

        Mockito.when(httpServletRequest.getRequestURI()).thenReturn("/products");

        Mockito.when(sessionService.getSession("dsdfsdf")).thenReturn(Session.builder()
                .token("dsdfsd")
                .role("ADMIN")
                .expire(LocalDateTime.now().plusMinutes(10))
                .build());

        securityFilter.doFilter(httpServletRequest, httpServletResponse, filterChain);

        Mockito.verify(httpServletRequest, Mockito.times(1)).getCookies();
        Mockito.verify(httpServletRequest, Mockito.times(2)).getRequestURI();
        Mockito.verify(sessionService, Mockito.times(1)).getSession("dsdfsdf");
    }

    @Test
    void doFilterTestWithRoleUserOnPathForGuestReturnOk() throws ServletException, IOException {
        Cookie[] cookies = new Cookie[2];
        cookies[0] = new Cookie("test", "dsdfsd");
        cookies[1] = new Cookie("user-token", "dsdfsdf");
        Mockito.when(httpServletRequest.getCookies()).thenReturn(cookies);

        Mockito.when(httpServletRequest.getRequestURI()).thenReturn("/products");

        Mockito.when(sessionService.getSession("dsdfsdf")).thenReturn(Session.builder()
                .token("dsdfsd")
                .role("USER")
                .expire(LocalDateTime.now().plusMinutes(10))
                .build());

        securityFilter.doFilter(httpServletRequest, httpServletResponse, filterChain);

        Mockito.verify(httpServletRequest, Mockito.times(1)).getCookies();
        Mockito.verify(httpServletRequest, Mockito.times(2)).getRequestURI();
        Mockito.verify(sessionService, Mockito.times(1)).getSession("dsdfsdf");
    }

    @Test
    void doFilterTestWithRoleGuestOnPathForGuestReturnOk() throws ServletException, IOException {
        Cookie[] cookies = new Cookie[2];
        cookies[0] = new Cookie("test", "dsdfsd");
        cookies[1] = new Cookie("user-token", "dsdfsdf");
        Mockito.when(httpServletRequest.getCookies()).thenReturn(cookies);

        Mockito.when(httpServletRequest.getRequestURI()).thenReturn("/products");

        Mockito.when(sessionService.getSession("dsdfsdf")).thenReturn(null);

        securityFilter.doFilter(httpServletRequest, httpServletResponse, filterChain);

        Mockito.verify(httpServletRequest, Mockito.times(1)).getCookies();
        Mockito.verify(httpServletRequest, Mockito.times(2)).getRequestURI();
        Mockito.verify(sessionService, Mockito.times(1)).getSession("dsdfsdf");
    }
}