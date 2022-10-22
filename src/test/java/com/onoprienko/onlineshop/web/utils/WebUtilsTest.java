package com.onoprienko.onlineshop.web.utils;

import com.onoprienko.onlineshop.entity.Product;
import com.onoprienko.onlineshop.entity.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.*;

class WebUtilsTest {
    private final HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

    @Test
    void getProductFromRequestReturnProduct() {
        Mockito.when(request.getParameter("name")).thenReturn("test");
        Mockito.when(request.getParameter("price")).thenReturn("10");

        Product productFromRequest = WebUtils.getProductFromRequest(request);

        assertNotNull(productFromRequest);

        assertEquals("test", productFromRequest.getName());
        assertEquals(10, productFromRequest.getPrice());
    }

    @Test
    void getProductFromRequestReturnExceptionIfRequestDoesntContainParams() {
        assertThrows(NullPointerException.class, () -> WebUtils.getProductFromRequest(request));
    }

    @Test
    void getUserFromRequestReturnUser() {
        Mockito.when(request.getParameter("name")).thenReturn("name");
        Mockito.when(request.getParameter("role")).thenReturn("role");
        Mockito.when(request.getParameter("email")).thenReturn("email");
        Mockito.when(request.getParameter("password")).thenReturn("pass");

        User userFromRequest = WebUtils.getUserFromRequest(request);

        assertNotNull(userFromRequest);

        assertEquals(userFromRequest.getPassword(), "pass");
        assertEquals(userFromRequest.getEmail(), "email");
        assertEquals(userFromRequest.getName(), "name");
    }

    @Test
    void getTokenFromRequest() {
        Cookie[] cookies = new Cookie[2];
        cookies[0] = new Cookie("test", "dsdfsd");
        cookies[1] = new Cookie("user-token", "dsdfsdf");
        Mockito.when(request.getCookies()).thenReturn(cookies);

        String userTokenFromRequest = WebUtils.getUserTokenFromRequest(request);

        assertNotNull(userTokenFromRequest);

        assertEquals("dsdfsdf", userTokenFromRequest);
    }

    @Test
    void getTokenFromRequestReturnNullIfCookiesNull() {
        Mockito.when(request.getCookies()).thenReturn(null);

        assertNull(WebUtils.getUserTokenFromRequest(request));
    }

    @Test
    void getTokenFromRequestReturnNullIfCookieNonFound() {
        Cookie[] cookies = new Cookie[2];
        cookies[0] = new Cookie("test", "dsdfsd");
        cookies[1] = new Cookie("user", "dsdfsdf");

        Mockito.when(request.getCookies()).thenReturn(cookies);

        assertNull(WebUtils.getUserTokenFromRequest(request));
    }
}