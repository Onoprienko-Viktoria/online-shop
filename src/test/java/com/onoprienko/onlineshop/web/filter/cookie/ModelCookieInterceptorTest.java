package com.onoprienko.onlineshop.web.filter.cookie;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

class ModelCookieInterceptorTest {
    private HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);
    private HttpServletResponse httpServletResponse = Mockito.mock(HttpServletResponse.class);

    @Test
    void postHandleAddCookieInResponseAndSendRedirect() throws IOException {
        ModelAndView modelAndView = new ModelAndView();
        Cookie cookie = new Cookie("user-token", "fkndsifnds");
        modelAndView.addObject(cookie);

        ModelCookieInterceptor modelCookieInterceptor = new ModelCookieInterceptor();
        modelCookieInterceptor.postHandle(httpServletRequest, httpServletResponse,
                null, modelAndView);

        Mockito.verify(httpServletResponse, Mockito.times(1)).addCookie(cookie);
        Mockito.verify(httpServletResponse, Mockito.times(1)).sendRedirect("/products");
    }

    @Test
    void postHandleDoNothingIfThereNoCookie() throws IOException {
        Cookie cookie = new Cookie("name", "ffdsgsdg");
        ModelCookieInterceptor modelCookieInterceptor = new ModelCookieInterceptor();
        modelCookieInterceptor.postHandle(httpServletRequest, httpServletResponse,
                null, new ModelAndView());

        Mockito.verify(httpServletResponse, Mockito.times(0)).addCookie(cookie);
        Mockito.verify(httpServletResponse, Mockito.times(0)).sendRedirect("/products");
    }
}