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
    private Map<String, Session> sessionMap = Collections.synchronizedMap(new HashMap<>());

    public DefaultSecurityService(UserService userService) {
        this.userService = userService;
    }


    @Override
    public Session login(String timeToLive, Credentials credentials) {
        User user = userService.verifyUser(credentials);

        String userToken = UUID.randomUUID().toString();
        List<Product> productsCart = Collections.synchronizedList(new ArrayList<>());
        sessionMap.put(userToken, Session.builder()
                .cart(productsCart)
                .token(userToken)
                .role(Role.valueOf(user.getRole()).name())
                .expire(LocalDateTime.now().plusMinutes(Long.parseLong(timeToLive)))
                .build());
        log.info("Login user. Create new Session {}", sessionMap.get(userToken));
        return sessionMap.get(userToken);
    }

    @Override
    public boolean logout(String token) {
        sessionMap.remove(token);
        log.info("Logout user. Remove session with token-key {}", token);
        return true;
    }

    @Override
    public Session findSession(String token) {
        Session session = sessionMap.get(token);
        log.info("Find session by token {}, session: {}", token, session);
        return session;
    }

}