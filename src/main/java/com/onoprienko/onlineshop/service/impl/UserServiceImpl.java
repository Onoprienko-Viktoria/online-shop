package com.onoprienko.onlineshop.service.impl;

import com.onoprienko.onlineshop.dao.UserDao;
import com.onoprienko.onlineshop.entity.User;
import com.onoprienko.onlineshop.security.PasswordEncoder;
import com.onoprienko.onlineshop.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static com.onoprienko.onlineshop.security.PasswordEncoder.generateSoleAndPass;

@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    @Override
    public void addUser(User user) {
        User userByEmail = userDao.findUserByEmail(user.getEmail());
        if (userByEmail != null) {
            throw new RuntimeException("User with email " + user.getEmail() + " already registered!");
        }
        PasswordEncoder.EncodeInfo encodeInfo = generateSoleAndPass(user.getPassword());
        user.setSole(encodeInfo.getSole());
        user.setPassword(encodeInfo.getEncodedPassword());
        user.setRole(user.getRole());
        userDao.addUser(user);
        log.info("Add user with name: {}", user.getName());
    }


    @Override
    public User verifyUser(User user) {
        User foundUser = userDao.findUserByEmail(user.getEmail());
        if (foundUser == null) {
            log.error("User with email {} not found", user.getEmail());
            throw new RuntimeException("User with email " + user.getEmail() + " not found");
        }
        boolean isVerified = PasswordEncoder.verifyUserPass(PasswordEncoder.EncodeInfo.builder()
                .sole(foundUser.getSole())
                .encodedPassword(foundUser.getPassword())
                .decodedPassword(user.getPassword())
                .build());
        if (isVerified) {
            return foundUser;
        }
        log.error("Invalid password for user {}", user.getEmail());
        throw new RuntimeException("Invalid password");
    }
}
