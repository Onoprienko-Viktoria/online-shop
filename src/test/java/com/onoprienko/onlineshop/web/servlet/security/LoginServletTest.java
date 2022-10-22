package com.onoprienko.onlineshop.web.servlet.security;

import com.onoprienko.onlineshop.security.entity.Credentials;
import com.onoprienko.onlineshop.security.service.SecurityService;
import com.onoprienko.onlineshop.web.utils.PageGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

class LoginServletTest {
    private final SecurityService securityService = Mockito.mock(SecurityService.class);
    private final PageGenerator pageGenerator = Mockito.mock(PageGenerator.class);
    private final HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);
    private final HttpServletResponse httpServletResponse = Mockito.mock(HttpServletResponse.class);
    private final PrintWriter printWriter = Mockito.mock(PrintWriter.class);

    private final LoginServlet loginServlet = new LoginServlet(securityService, pageGenerator, "100");
    private final Credentials credentials = Credentials.builder()
            .email("email").password("password").build();

    @Test
    void doGetTest() throws IOException {
        Mockito.when(pageGenerator.getPage("login.ftl")).thenReturn("test");
        Mockito.when(httpServletResponse.getWriter()).thenReturn(printWriter);

        loginServlet.doGet(httpServletRequest, httpServletResponse);

        Mockito.verify(pageGenerator, Mockito.times(1)).getPage("login.ftl");
        Mockito.verify(httpServletResponse, Mockito.times(1)).getWriter();
    }

    @Test
    void doPostTest() throws IOException {
        Mockito.when(httpServletResponse.getWriter()).thenReturn(printWriter);
        Mockito.when(httpServletRequest.getParameter("role")).thenReturn("USER");
        Mockito.when(httpServletRequest.getParameter("email")).thenReturn("email");
        Mockito.when(httpServletRequest.getParameter("password")).thenReturn("password");

        loginServlet.doPost(httpServletRequest, httpServletResponse);


        Mockito.verify(httpServletRequest, Mockito.times(1)).getParameter("password");
        Mockito.verify(httpServletRequest, Mockito.times(1)).getParameter("email");
    }

    @Test
    void doPostTestThrowsException() throws IOException {
        Mockito.when(pageGenerator.getPageWithMessage("login.ftl", "exception")).thenReturn("test");
        Mockito.when(httpServletResponse.getWriter()).thenReturn(printWriter);
        Mockito.when(httpServletRequest.getParameter("role")).thenReturn("USER");
        Mockito.when(httpServletRequest.getParameter("email")).thenReturn("email");
        Mockito.when(httpServletRequest.getParameter("password")).thenReturn("password");

        Mockito.doThrow(new RuntimeException("exception")).when(securityService).login("100", credentials);
        loginServlet.doPost(httpServletRequest, httpServletResponse);


        Mockito.verify(httpServletRequest, Mockito.times(1)).getParameter("password");
        Mockito.verify(httpServletRequest, Mockito.times(1)).getParameter("email");
    }
}