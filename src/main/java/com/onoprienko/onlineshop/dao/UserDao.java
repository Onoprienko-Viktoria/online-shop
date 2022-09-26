package com.onoprienko.onlineshop.dao;

import com.onoprienko.onlineshop.entity.User;

public interface UserDao {

    void addUser(User user);

    User findUserByEmail(String email);

}
