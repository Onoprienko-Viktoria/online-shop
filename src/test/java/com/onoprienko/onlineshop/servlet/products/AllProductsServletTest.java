package com.onoprienko.onlineshop.servlet.products;

import com.onoprienko.onlineshop.entity.Product;
import com.onoprienko.onlineshop.service.ProductService;
import com.onoprienko.onlineshop.utils.PageGenerator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

class AllProductsServletTest {
    private final ProductService productService = Mockito.mock(ProductService.class);
    private final PageGenerator pageGenerator = Mockito.mock(PageGenerator.class);
    private final HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);
    private final HttpServletResponse httpServletResponse = Mockito.mock(HttpServletResponse.class);
    private final PrintWriter printWriter = Mockito.mock(PrintWriter.class);
    private final AllProductsServlet allProductsServlet = new AllProductsServlet(productService, pageGenerator);

    Product testProductOne = Product.builder().id(1L)
            .creationDate(Date.valueOf(LocalDate.now()))
            .name("test")
            .price(10.2).build();
    Product testProductTwo = Product.builder().id(2L)
            .creationDate(Date.valueOf(LocalDate.now()))
            .name("test 21")
            .price(1.2).build();
    Product testProductThree = Product.builder().id(3L)
            .creationDate(null)
            .name("t")
            .price(0).build();

    @Test
    void doGetTest() throws IOException {
        List<Product> products = List.of(this.testProductOne, testProductTwo, testProductThree);
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("products", products);

        Mockito.when(productService.findAll()).thenReturn(products);
        Mockito.when(pageGenerator.getPage("product_list.html", parameters)).thenReturn(parameters.toString());
        Mockito.when(httpServletResponse.getWriter()).thenReturn(printWriter);
        allProductsServlet.doGet(httpServletRequest, httpServletResponse);

        Mockito.verify(productService, Mockito.times(1)).findAll();
    }

    @Test
    void doGetTestWithWorldToFind() throws IOException {
        Mockito.when(httpServletRequest.getParameter("word")).thenReturn("a");
        List<Product> products = List.of(this.testProductOne, testProductTwo, testProductThree);
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("products", products);

        Mockito.when(productService.findAll()).thenReturn(products);
        Mockito.when(pageGenerator.getPage("product_list.html", parameters)).thenReturn(parameters.toString());
        Mockito.when(httpServletResponse.getWriter()).thenReturn(printWriter);
        allProductsServlet.doGet(httpServletRequest, httpServletResponse);

        Mockito.verify(productService, Mockito.times(1)).findAll("a");
    }

}