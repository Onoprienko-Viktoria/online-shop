package com.onoprienko.onlineshop.service;

import com.onoprienko.onlineshop.entity.Session;

public interface SessionService {
    Session createSession(String timeToLive, String role);

    boolean removeSession(String token);

    Session getSession(String token);
}
