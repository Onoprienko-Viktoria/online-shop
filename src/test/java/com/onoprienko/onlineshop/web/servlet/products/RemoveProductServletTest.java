package com.onoprienko.onlineshop.web.servlet.products;

import com.onoprienko.onlineshop.service.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

class RemoveProductServletTest {
    private final ProductService productService = Mockito.mock(ProductService.class);
    private final HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);
    private final HttpServletResponse httpServletResponse = Mockito.mock(HttpServletResponse.class);
    private final RemoveProductServlet removeProductServlet = new RemoveProductServlet(productService);

    @Test
    void doGetTest() throws IOException, ServletException {
        Mockito.when(httpServletRequest.getParameter("id")).thenReturn("2");

        removeProductServlet.doPost(httpServletRequest, httpServletResponse);

        Mockito.verify(httpServletRequest, Mockito.times(1)).getParameter("id");
        Mockito.verify(productService, Mockito.times(1)).remove(2L);
        Mockito.verify(httpServletResponse, Mockito.times(1)).sendRedirect("/products");
    }
}