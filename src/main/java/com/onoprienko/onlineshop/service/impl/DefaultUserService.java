package com.onoprienko.onlineshop.service.impl;

import com.onoprienko.onlineshop.dao.UserDao;
import com.onoprienko.onlineshop.entity.User;
import com.onoprienko.onlineshop.security.entity.Role;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
@Setter
public class DefaultUserService implements UserDetailsService {
    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;


    public User add(User user) {
        Optional<User> userByEmail = userDao.findByEmail(user.getEmail());
        if (userByEmail.isPresent()) {
            throw new RuntimeException("User already registered!");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER.name());
        userDao.add(user);
        log.info("Add user with name: {}", user.getName());
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDao.findByEmail(username).orElseThrow();
    }
}
