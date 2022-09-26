package com.onoprienko.onlineshop.servlet.users;

import com.onoprienko.onlineshop.entity.Session;
import com.onoprienko.onlineshop.entity.User;
import com.onoprienko.onlineshop.service.SessionService;
import com.onoprienko.onlineshop.service.UserService;
import com.onoprienko.onlineshop.utils.PageGenerator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.io.PrintWriter;

class LoginServletTest {
    private final SessionService sessionService = Mockito.mock(SessionService.class);
    private final UserService userService = Mockito.mock(UserService.class);
    private final PageGenerator pageGenerator = Mockito.mock(PageGenerator.class);
    private final HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);
    private final HttpServletResponse httpServletResponse = Mockito.mock(HttpServletResponse.class);
    private final PrintWriter printWriter = Mockito.mock(PrintWriter.class);

    private final LoginServlet loginServlet = new LoginServlet(sessionService, userService, pageGenerator, "100");
    User user = User.builder()
            .password("pass")
            .role("USER")
            .name("name")
            .email("email").build();

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
        Mockito.when(httpServletRequest.getParameter("name")).thenReturn("name");
        Mockito.when(httpServletRequest.getParameter("role")).thenReturn("USER");
        Mockito.when(httpServletRequest.getParameter("email")).thenReturn("email");
        Mockito.when(httpServletRequest.getParameter("password")).thenReturn("pass");
        Mockito.when(userService.verifyUser(user)).thenReturn(user);
        Mockito.when(sessionService.createSession("100", "USER")).thenReturn(Session.builder()
                .token("qrq9r12e1j").build());

        loginServlet.doPost(httpServletRequest, httpServletResponse);


        Mockito.verify(httpServletRequest, Mockito.times(1)).getParameter("name");
        Mockito.verify(httpServletRequest, Mockito.times(1)).getParameter("password");
        Mockito.verify(httpServletRequest, Mockito.times(1)).getParameter("email");
        Mockito.verify(httpServletRequest, Mockito.times(1)).getParameter("role");
        Mockito.verify(userService, Mockito.times(1)).verifyUser(user);
        Mockito.verify(sessionService, Mockito.times(1)).createSession("100", "USER");
        Mockito.verify(httpServletResponse, Mockito.times(1)).sendRedirect("/products");
    }

    @Test
    void doPostTestThrowsException() throws IOException {
        Mockito.when(pageGenerator.getPageWithMessage("login.ftl", "exception")).thenReturn("test");
        Mockito.when(httpServletResponse.getWriter()).thenReturn(printWriter);
        Mockito.when(httpServletRequest.getParameter("name")).thenReturn("name");
        Mockito.when(httpServletRequest.getParameter("role")).thenReturn("USER");
        Mockito.when(httpServletRequest.getParameter("email")).thenReturn("email");
        Mockito.when(httpServletRequest.getParameter("password")).thenReturn("pass");
        Mockito.when(userService.verifyUser(user)).thenReturn(user);

        Mockito.doThrow(new RuntimeException("exception")).when(sessionService).createSession("100", "USER");
        loginServlet.doPost(httpServletRequest, httpServletResponse);


        Mockito.verify(httpServletRequest, Mockito.times(1)).getParameter("name");
        Mockito.verify(httpServletRequest, Mockito.times(1)).getParameter("password");
        Mockito.verify(httpServletRequest, Mockito.times(1)).getParameter("email");
        Mockito.verify(httpServletRequest, Mockito.times(1)).getParameter("role");
        Mockito.verify(userService, Mockito.times(1)).verifyUser(user);
        Mockito.verify(sessionService, Mockito.times(1)).createSession("100", "USER");
        Mockito.verify(pageGenerator, Mockito.times(1)).getPageWithMessage("login.ftl", "exception");
    }
}