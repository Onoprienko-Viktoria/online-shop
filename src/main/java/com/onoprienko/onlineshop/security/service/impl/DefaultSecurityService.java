package com.onoprienko.onlineshop.security.service.impl;


import com.onoprienko.onlineshop.entity.Product;
import com.onoprienko.onlineshop.entity.User;
import com.onoprienko.onlineshop.security.entity.Credentials;
import com.onoprienko.onlineshop.security.entity.Role;
import com.onoprienko.onlineshop.security.entity.Session;
import com.onoprienko.onlineshop.security.service.SecurityService;
import com.onoprienko.onlineshop.service.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class DefaultSecurityService implements SecurityService {
    private UserService userService;
    private List<Session> sessionList = Collections.synchronizedList(new ArrayList<>());

    public DefaultSecurityService(UserService userService) {
        this.userService = userService;
    }


    @Override
    public Session login(String timeToLive, Credentials credentials) {
        User user = userService.verifyUser(credentials);

        String userToken = UUID.randomUUID().toString();
        List<Product> productsCart = Collections.synchronizedList(new ArrayList<>());
        Session session = Session.builder()
                .cart(productsCart)
                .token(userToken)
                .role(Role.valueOf(user.getRole()).name())
                .expire(LocalDateTime.now().plusMinutes(Long.parseLong(timeToLive)))
                .build();
        sessionList.add(session);
        log.info("Login user. Create new Session {}", session);
        return session;
    }

    @Override
    public boolean logout(Session session) {
        sessionList.remove(session);
        log.info("Logout user. Remove session with token-key {}", session.getToken());
        return true;
    }

    @Override
    public Session findSession(String token) {
        Optional<Session> sessionOptional = sessionList.stream().filter(session -> session.getToken().equals(token)).findFirst();
        return sessionOptional.orElse(null);
    }
}