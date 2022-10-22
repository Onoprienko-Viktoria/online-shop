package com.onoprienko.onlineshop.web.servlet.products;

import com.onoprienko.onlineshop.entity.Product;
import com.onoprienko.onlineshop.service.ProductService;
import com.onoprienko.onlineshop.web.utils.PageGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

class AddProductServletTest {
    private final ProductService productService = Mockito.mock(ProductService.class);
    private final PageGenerator pageGenerator = Mockito.mock(PageGenerator.class);
    private final HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);
    private final HttpServletResponse httpServletResponse = Mockito.mock(HttpServletResponse.class);
    private final PrintWriter printWriter = Mockito.mock(PrintWriter.class);
    private final AddProductServlet addProductServlet = new AddProductServlet(productService, pageGenerator);

    @Test
    void doGetTest() throws IOException {
        Mockito.when(pageGenerator.getPage("add_product.ftl")).thenReturn("test");
        Mockito.when(httpServletResponse.getWriter()).thenReturn(printWriter);

        addProductServlet.doGet(httpServletRequest, httpServletResponse);

        Mockito.verify(pageGenerator, Mockito.times(1)).getPage("add_product.ftl");
        Mockito.verify(httpServletResponse, Mockito.times(1)).getWriter();
    }

    @Test
    void doPostTest() throws IOException {
        Mockito.when(httpServletRequest.getParameter("name")).thenReturn("test");
        Mockito.when(httpServletRequest.getParameter("price")).thenReturn("10");

        addProductServlet.doPost(httpServletRequest, httpServletResponse);

        Mockito.verify(httpServletRequest, Mockito.times(1)).getParameter("name");
        Mockito.verify(httpServletRequest, Mockito.times(1)).getParameter("price");
        Mockito.verify(productService, Mockito.times(1)).add(Product.builder()
                .id(0)
                .name("test")
                .price(10.0)
                .creationDate(null)
                .build());
    }

    @Test
    void doPostTestReturnsException() throws IOException {
        Product test = Product.builder()
                .name("test").price(10.0).build();
        Mockito.when(httpServletRequest.getParameter("name")).thenReturn("test");
        Mockito.when(httpServletRequest.getParameter("price")).thenReturn("10");
        Mockito.when(pageGenerator.getPage("add_product.ftl")).thenReturn("test");
        Mockito.when(httpServletResponse.getWriter()).thenReturn(printWriter);

        Mockito.doThrow(new RuntimeException("exception")).when(productService).add(test);
        addProductServlet.doPost(httpServletRequest, httpServletResponse);


        Mockito.verify(httpServletRequest, Mockito.times(1)).getParameter("name");
        Mockito.verify(httpServletRequest, Mockito.times(1)).getParameter("price");
        Mockito.verify(productService, Mockito.times(1)).add(Product.builder()
                .id(0)
                .name("test")
                .price(10.0)
                .creationDate(null)
                .build());
    }
}