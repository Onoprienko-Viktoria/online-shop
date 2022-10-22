package com.onoprienko.onlineshop.dao;

import com.onoprienko.onlineshop.entity.User;

public interface UserDao {

    void add(User user);

    User findByEmail(String email);

}
