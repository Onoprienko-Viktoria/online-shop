package com.onoprienko.onlineshop.service.impl;


import com.onoprienko.onlineshop.entity.Product;
import com.onoprienko.onlineshop.entity.Session;
import com.onoprienko.onlineshop.service.SessionService;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@NoArgsConstructor
public class SessionServiceImpl implements SessionService {
    private Map<String, Session> sessionMap = Collections.synchronizedMap(new HashMap<>());

    public SessionServiceImpl(Map<String, Session> sessionMap) {
        this.sessionMap = sessionMap;
    }

    @Override
    public Session createSession(String timeToLive, String role) {
        String userToken = UUID.randomUUID().toString();
        List<Product> productsCart = Collections.synchronizedList(new ArrayList<>());
        sessionMap.put(userToken, Session.builder()
                .cart(productsCart)
                .token(userToken)
                .role(role)
                .expire(LocalDateTime.now().plusMinutes(Long.parseLong(timeToLive)))
                .build());
        log.info("Create new Session {}", sessionMap.get(userToken));
        return sessionMap.get(userToken);
    }

    @Override
    public boolean removeSession(String token) {
        if (sessionMap.containsKey(token)) {
            sessionMap.remove(token);
            log.info("Remove session with token-key {}", token);
            return true;
        }
        return false;
    }

    @Override
    public Session getSession(String token) {
        Session session = sessionMap.get(token);
        log.info("Find session by token {}, session: {}", token, session);
        return session;
    }

}