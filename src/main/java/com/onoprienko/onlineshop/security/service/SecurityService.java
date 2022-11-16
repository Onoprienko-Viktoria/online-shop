package com.onoprienko.onlineshop.security.service;

import com.onoprienko.onlineshop.security.entity.Credentials;
import com.onoprienko.onlineshop.security.entity.Session;

public interface SecurityService {
    Session login(Credentials credentials);

    boolean logout(Session session);

    Session findSession(String token);
}
