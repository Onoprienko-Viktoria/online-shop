package com.onoprienko.onlineshop.security.service;

import com.onoprienko.onlineshop.security.entity.Credentials;
import com.onoprienko.onlineshop.security.entity.Session;

public interface SecurityService {
    Session login(String timeToLive, Credentials credentials);

    boolean logout(String token);

    Session findSession(String token);
}
