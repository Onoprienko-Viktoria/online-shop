package com.onoprienko.onlineshop.security.service;

import com.onoprienko.onlineshop.entity.Product;
import com.onoprienko.onlineshop.entity.User;
import com.onoprienko.onlineshop.security.entity.Credentials;
import com.onoprienko.onlineshop.security.entity.Session;
import com.onoprienko.onlineshop.security.service.impl.DefaultSecurityService;
import com.onoprienko.onlineshop.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SecurityServiceTest {
    private final List<Session> sessionList = new ArrayList<>();
    private final UserService userService = Mockito.mock(UserService.class);
    private final SecurityService securityService = new DefaultSecurityService(userService, 480, sessionList);

    User user = User.builder()
            .name("test")
            .sole("mvodoflsfgdfsd")
            .email("email")
            .role("USER")
            .build();
    Credentials credentials = Credentials.builder()
            .password("pass")
            .email("email")
            .build();
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
    void createSessionReturnSession() {
        Mockito.when(userService.verifyUser(credentials)).thenReturn(user);
        Session session = securityService.login(credentials);

        assertNotNull(session);
        assertNotNull(session.getToken());
        assertNotNull(session.getExpire());
        assertNotNull(session.getCart());
        assertEquals(session.getRole(), "USER");

        Mockito.verify(userService, Mockito.times(1)).verifyUser(credentials);
    }

    @Test
    void removeSessionReturnsTrue() {
        sessionList.add(session);

        boolean isDeleted = securityService.logout(session);

        assertTrue(isDeleted);
    }

    @Test
    void removeSessionReturnsTrueEvenIfSessionNotExist() {
        sessionList.add(session);

        boolean isDeleted = securityService.logout(session);

        assertTrue(isDeleted);
    }

    @Test
    void getSession() {
        sessionList.add(session);

        Session test = securityService.findSession("test");

        assertNotNull(test);
        assertEquals(test.getRole(), session.getRole());
        assertEquals(test.getCart(), session.getCart());
        assertEquals(test.getExpire(), session.getExpire());
        assertEquals(test.getToken(), session.getToken());
    }

    @Test
    void getSessionReturnsNull() {
        SecurityService securityService = new DefaultSecurityService();

        assertNull(securityService.findSession("test"));
    }
}