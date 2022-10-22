package com.onoprienko.onlineshop.service;

import com.onoprienko.onlineshop.entity.User;
import com.onoprienko.onlineshop.security.entity.Credentials;

public interface UserService {
    void add(User user);

    User verifyUser(Credentials credentials);
}
