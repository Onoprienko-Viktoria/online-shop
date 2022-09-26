package com.onoprienko.onlineshop.utils;

import com.onoprienko.onlineshop.entity.Product;
import com.onoprienko.onlineshop.entity.User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class RequestExtractorTest {
    private final HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

    @Test
    void getProductFromRequestReturnProduct() {
        Mockito.when(request.getParameter("name")).thenReturn("test");
        Mockito.when(request.getParameter("price")).thenReturn("10");

        Product productFromRequest = RequestExtractor.getProductFromRequest(request);

        assertNotNull(productFromRequest);

        assertEquals("test", productFromRequest.getName());
        assertEquals(10, productFromRequest.getPrice());
    }

    @Test
    void getProductFromRequestReturnExceptionIfRequestDoesntContainParams() {
        assertThrows(NullPointerException.class, () -> RequestExtractor.getProductFromRequest(request));
    }

    @Test
    void getUserFromRequestReturnUser() {
        Mockito.when(request.getParameter("name")).thenReturn("name");
        Mockito.when(request.getParameter("role")).thenReturn("role");
        Mockito.when(request.getParameter("email")).thenReturn("email");
        Mockito.when(request.getParameter("password")).thenReturn("pass");

        User userFromRequest = RequestExtractor.getUserFromRequest(request);

        assertNotNull(userFromRequest);

        assertEquals(userFromRequest.getPassword(), "pass");
        assertEquals(userFromRequest.getRole(), "role");
        assertEquals(userFromRequest.getEmail(), "email");
        assertEquals(userFromRequest.getName(), "name");
    }

    @Test
    void getTokenFromRequest() {
        Cookie[] cookies = new Cookie[2];
        cookies[0] = new Cookie("test", "dsdfsd");
        cookies[1] = new Cookie("user-token", "dsdfsdf");
        Mockito.when(request.getCookies()).thenReturn(cookies);

        String userTokenFromRequest = RequestExtractor.getUserTokenFromRequest(request);

        assertNotNull(userTokenFromRequest);

        assertEquals("dsdfsdf", userTokenFromRequest);
    }

    @Test
    void getTokenFromRequestReturnNullIfCookiesNull() {
        Mockito.when(request.getCookies()).thenReturn(null);

        assertNull(RequestExtractor.getUserTokenFromRequest(request));
    }

    @Test
    void getTokenFromRequestReturnNullIfCookieNonFound() {
        Cookie[] cookies = new Cookie[2];
        cookies[0] = new Cookie("test", "dsdfsd");
        cookies[1] = new Cookie("user", "dsdfsdf");

        Mockito.when(request.getCookies()).thenReturn(cookies);

        assertNull(RequestExtractor.getUserTokenFromRequest(request));
    }
}