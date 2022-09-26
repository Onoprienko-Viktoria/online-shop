package com.onoprienko.onlineshop.servlet.products;

import com.onoprienko.onlineshop.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

class RemoveProductServletTest {
    private final ProductService productService = Mockito.mock(ProductService.class);
    private final HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);
    private final HttpServletResponse httpServletResponse = Mockito.mock(HttpServletResponse.class);
    private final RemoveProductServlet removeProductServlet = new RemoveProductServlet(productService);

    @Test
    void doGetTest() throws IOException {
        Mockito.when(httpServletRequest.getParameter("id")).thenReturn("2");

        removeProductServlet.doGet(httpServletRequest, httpServletResponse);

        Mockito.verify(httpServletRequest, Mockito.times(1)).getParameter("id");
        Mockito.verify(productService, Mockito.times(1)).removeProduct(2L);
        Mockito.verify(httpServletResponse, Mockito.times(1)).sendRedirect("/products");
    }
}