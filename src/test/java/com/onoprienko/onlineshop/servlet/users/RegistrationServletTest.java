package com.onoprienko.onlineshop.servlet.users;

import com.onoprienko.onlineshop.entity.User;
import com.onoprienko.onlineshop.service.UserService;
import com.onoprienko.onlineshop.utils.PageGenerator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.io.PrintWriter;

class RegistrationServletTest {
    private final UserService userService = Mockito.mock(UserService.class);
    private final PageGenerator pageGenerator = Mockito.mock(PageGenerator.class);
    private final HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);
    private final HttpServletResponse httpServletResponse = Mockito.mock(HttpServletResponse.class);
    private final PrintWriter printWriter = Mockito.mock(PrintWriter.class);

    private final RegistrationServlet registrationServlet = new RegistrationServlet(userService, pageGenerator);

    @Test
    void doGetTest() throws IOException {
        Mockito.when(pageGenerator.getPage("registration.ftl")).thenReturn("test");
        Mockito.when(httpServletResponse.getWriter()).thenReturn(printWriter);

        registrationServlet.doGet(httpServletRequest, httpServletResponse);

        Mockito.verify(pageGenerator, Mockito.times(1)).getPage("registration.ftl");
        Mockito.verify(httpServletResponse, Mockito.times(1)).getWriter();

    }

    @Test
    void doPostTest() throws IOException {
        Mockito.when(httpServletRequest.getParameter("name")).thenReturn("name");
        Mockito.when(httpServletRequest.getParameter("role")).thenReturn("USER");
        Mockito.when(httpServletRequest.getParameter("email")).thenReturn("email");
        Mockito.when(httpServletRequest.getParameter("password")).thenReturn("pass");

        registrationServlet.doPost(httpServletRequest, httpServletResponse);

        Mockito.verify(httpServletRequest, Mockito.times(1)).getParameter("name");
        Mockito.verify(httpServletRequest, Mockito.times(1)).getParameter("password");
        Mockito.verify(httpServletRequest, Mockito.times(1)).getParameter("email");
        Mockito.verify(httpServletRequest, Mockito.times(1)).getParameter("role");
        Mockito.verify(httpServletResponse, Mockito.times(1)).sendRedirect("/login");

    }

    @Test
    void doPostTestReturnsException() throws IOException {
        Mockito.when(httpServletRequest.getParameter("name")).thenReturn("name");
        Mockito.when(httpServletRequest.getParameter("role")).thenReturn("USER");
        Mockito.when(httpServletRequest.getParameter("email")).thenReturn("email");
        Mockito.when(httpServletRequest.getParameter("password")).thenReturn("pass");
        Mockito.when(pageGenerator.getPage("add_product.ftl")).thenReturn("test");
        Mockito.when(httpServletResponse.getWriter()).thenReturn(printWriter);
        User userFromRequest = User.builder()
                .sole("sole")
                .password("pass")
                .name("name")
                .email("email").build();
        Mockito.doThrow(new RuntimeException("exception")).when(userService).addUser(userFromRequest);
        registrationServlet.doPost(httpServletRequest, httpServletResponse);

        Mockito.verify(httpServletRequest, Mockito.times(1)).getParameter("name");
        Mockito.verify(httpServletRequest, Mockito.times(1)).getParameter("password");
        Mockito.verify(httpServletRequest, Mockito.times(1)).getParameter("email");
        Mockito.verify(httpServletRequest, Mockito.times(1)).getParameter("role");
    }
}