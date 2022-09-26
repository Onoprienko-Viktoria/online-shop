package com.onoprienko.onlineshop.service;

import com.onoprienko.onlineshop.entity.User;

public interface UserService {
    void addUser(User user);

    User verifyUser(User user);
}
