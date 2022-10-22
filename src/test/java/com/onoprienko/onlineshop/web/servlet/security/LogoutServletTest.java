package com.onoprienko.onlineshop.web.servlet.security;

import com.onoprienko.onlineshop.security.service.SecurityService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

class LogoutServletTest {
    private final HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);
    private final HttpServletResponse httpServletResponse = Mockito.mock(HttpServletResponse.class);
    private final SecurityService securityService = Mockito.mock(SecurityService.class);

    private final LogoutServlet logoutServlet = new LogoutServlet(securityService);

    @Test
    void doGetTest() throws IOException, ServletException {
        Cookie[] cookies = new Cookie[2];
        String token = "dsdfsdf";
        cookies[0] = new Cookie("test", token);
        cookies[1] = new Cookie("user-token", token);
        Mockito.when(httpServletRequest.getCookies()).thenReturn(cookies);
        Mockito.when(securityService.logout(token)).thenReturn(true);

        logoutServlet.doPost(httpServletRequest, httpServletResponse);

        Mockito.verify(securityService, Mockito.times(1)).logout(token);
        Mockito.verify(httpServletRequest, Mockito.times(1)).getCookies();
    }
}