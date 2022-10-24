package com.onoprienko.onlineshop.web.servlet.products;

import com.onoprienko.onlineshop.entity.Product;
import com.onoprienko.onlineshop.security.entity.Session;
import com.onoprienko.onlineshop.service.ProductService;
import com.onoprienko.onlineshop.web.utils.PageGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;


class CartServletTest {
    private final ProductService productService = Mockito.mock(ProductService.class);
    private final PageGenerator pageGenerator = Mockito.mock(PageGenerator.class);
    private final HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);
    private final HttpServletResponse httpServletResponse = Mockito.mock(HttpServletResponse.class);
    private final PrintWriter printWriter = Mockito.mock(PrintWriter.class);

    CartServlet cartServlet = new CartServlet(productService, pageGenerator);
    Product testProductOne = Product.builder().id(1L)
            .creationDate(LocalDateTime.now())
            .name("test")
            .price(10.2).build();
    Product testProductTwo = Product.builder().id(2L)
            .creationDate(LocalDateTime.now())
            .name("test 21")
            .price(1.2).build();
    Session session = Session.builder()
            .expire(LocalDateTime.now().plusMinutes(10))
            .role("ADMIN")
            .token("test")
            .cart(List.of(testProductOne, testProductTwo))
            .build();

    @Test
    void doGetTest() throws IOException {
        Mockito.when(httpServletRequest.getAttribute("session")).thenReturn(session);
        Mockito.when(pageGenerator.getPage("add_product.ftl")).thenReturn("test");
        Mockito.when(httpServletResponse.getWriter()).thenReturn(printWriter);

        cartServlet.doGet(httpServletRequest, httpServletResponse);
        HashMap<String, Object> result = new HashMap<>();
        result.put("products", session.getCart());

        Mockito.verify(pageGenerator, Mockito.times(1)).getPage("user_cart.ftl", result);
        Mockito.verify(httpServletResponse, Mockito.times(1)).getWriter();
        Mockito.verify(httpServletRequest, Mockito.times(1)).getAttribute("session");
    }

    @Test
    void doPostTestOnAddToCart() throws IOException {
        Mockito.when(httpServletRequest.getAttribute("session")).thenReturn(session);
        Mockito.when(httpServletRequest.getParameter("id")).thenReturn("1");
        Mockito.when(httpServletRequest.getRequestURI()).thenReturn("/cart/add");
        Mockito.when(pageGenerator.getPage("add_product.ftl")).thenReturn("test");
        Mockito.when(httpServletResponse.getWriter()).thenReturn(printWriter);

        cartServlet.doPost(httpServletRequest, httpServletResponse);

        Mockito.verify(productService, Mockito.times(1)).getById(1L);
        Mockito.verify(httpServletRequest, Mockito.times(1)).getAttribute("session");
        Mockito.verify(httpServletRequest, Mockito.times(1)).getParameter("id");
    }

    @Test
    void doPostTestOnDeleteFromCart() throws IOException {
        Mockito.when(httpServletRequest.getAttribute("session")).thenReturn(session);
        Mockito.when(httpServletRequest.getParameter("id")).thenReturn("1");
        Mockito.when(httpServletRequest.getRequestURI()).thenReturn("/cart/delete");
        Mockito.when(pageGenerator.getPage("add_product.ftl")).thenReturn("test");
        Mockito.when(httpServletResponse.getWriter()).thenReturn(printWriter);

        cartServlet.doPost(httpServletRequest, httpServletResponse);

        Mockito.verify(httpServletRequest, Mockito.times(1)).getAttribute("session");
        Mockito.verify(httpServletRequest, Mockito.times(1)).getParameter("id");
    }

    @Test
    void doPostTestThrowsException() throws IOException {
        Mockito.when(httpServletRequest.getAttribute("session")).thenReturn(session);
        Mockito.when(httpServletRequest.getParameter("id")).thenReturn("1");
        Mockito.when(httpServletRequest.getRequestURI()).thenReturn("/cart/add");
        Mockito.when(pageGenerator.getPage("add_product.ftl")).thenReturn("test");
        Mockito.when(httpServletResponse.getWriter()).thenReturn(printWriter);

        Mockito.doThrow(new RuntimeException("exception")).when(productService).getById(1L);

        cartServlet.doPost(httpServletRequest, httpServletResponse);

        Mockito.verify(pageGenerator, Mockito.times(1)).getPageWithMessage("user_cart.ftl", "exception");
        Mockito.verify(httpServletResponse, Mockito.times(1)).getWriter();
        Mockito.verify(productService, Mockito.times(1)).getById(1L);
        Mockito.verify(httpServletRequest, Mockito.times(1)).getAttribute("session");
        Mockito.verify(httpServletRequest, Mockito.times(1)).getParameter("id");
    }

}