package com.onoprienko.onlineshop.dao;

import com.onoprienko.onlineshop.entity.User;

import java.util.Optional;

public interface UserDao {

    void add(User user);

    Optional<User> findByEmail(String email);

}
