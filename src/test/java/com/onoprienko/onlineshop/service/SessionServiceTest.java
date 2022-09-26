package com.onoprienko.onlineshop.service;

import com.onoprienko.onlineshop.entity.Product;
import com.onoprienko.onlineshop.entity.Session;
import com.onoprienko.onlineshop.service.impl.SessionServiceImpl;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class SessionServiceTest {
    private final Map<String, Session> sessionMap = new HashMap<>();
    private final SessionService sessionService = new SessionServiceImpl(sessionMap);
    Product testProductOne = Product.builder().id(1L)
            .creationDate(Date.valueOf(LocalDate.now()))
            .name("test")
            .price(10.2).build();
    Product testProductTwo = Product.builder().id(2L)
            .creationDate(Date.valueOf(LocalDate.now()))
            .name("test 21")
            .price(1.2).build();
    Session session = Session.builder()
            .expire(LocalDateTime.now().plusMinutes(10))
            .role("ADMIN")
            .token("test")
            .cart(List.of(testProductOne, testProductTwo))
            .build();

    @Test
    void createSessionReturnSession() {
        Session session = sessionService.createSession("10", "USER");

        assertNotNull(session);
        assertNotNull(session.getToken());
        assertNotNull(session.getExpire());
        assertNotNull(session.getCart());
        assertEquals(session.getRole(), "USER");
    }

    @Test
    void removeSessionReturnsTrue() {
        sessionMap.put("test", session);

        boolean isDeleted = sessionService.removeSession("test");

        assertTrue(isDeleted);
    }

    @Test
    void removeSessionReturnsFalse() {
        sessionMap.put("test", session);

        boolean isDeleted = sessionService.removeSession("dsdssads");

        assertFalse(isDeleted);
    }

    @Test
    void getSession() {
        sessionMap.put("test", session);

        Session test = sessionService.getSession("test");

        assertNotNull(test);
        assertEquals(test.getRole(), session.getRole());
        assertEquals(test.getCart(), session.getCart());
        assertEquals(test.getExpire(), session.getExpire());
        assertEquals(test.getToken(), session.getToken());
    }

    @Test
    void getSessionReturnsNull() {
        SessionService sessionService = new SessionServiceImpl();

        assertNull(sessionService.getSession("test"));
    }
}