package com.onoprienko.onlineshop.servlet.users;

import com.onoprienko.onlineshop.service.SessionService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

class LogoutServletTest {
    private final HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);
    private final HttpServletResponse httpServletResponse = Mockito.mock(HttpServletResponse.class);
    private final SessionService sessionService = Mockito.mock(SessionService.class);

    private final LogoutServlet logoutServlet = new LogoutServlet(sessionService);

    @Test
    void doGetTest() throws IOException {
        Cookie[] cookies = new Cookie[2];
        String token = "dsdfsdf";
        cookies[0] = new Cookie("test", token);
        cookies[1] = new Cookie("user-token", token);
        Mockito.when(httpServletRequest.getCookies()).thenReturn(cookies);
        Mockito.when(sessionService.removeSession(token)).thenReturn(true);

        logoutServlet.doGet(httpServletRequest, httpServletResponse);

        Mockito.verify(sessionService, Mockito.times(1)).removeSession(token);
        Mockito.verify(httpServletRequest, Mockito.times(1)).getCookies();
    }
}