package com.onoprienko.onlineshop.service.impl;

import com.onoprienko.onlineshop.dao.UserDao;
import com.onoprienko.onlineshop.entity.User;
import com.onoprienko.onlineshop.security.PasswordEncoder;
import com.onoprienko.onlineshop.security.entity.Credentials;
import com.onoprienko.onlineshop.security.entity.EncodeInfo;
import com.onoprienko.onlineshop.security.entity.Role;
import com.onoprienko.onlineshop.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static com.onoprienko.onlineshop.security.PasswordEncoder.generateSoleAndPass;

@AllArgsConstructor
@Slf4j
public class DefaultUserService implements UserService {
    private final UserDao userDao;

    @Override
    public void add(User user) {
        User userByEmail = userDao.findByEmail(user.getEmail());
        if (userByEmail != null) {
            throw new RuntimeException("User with email " + user.getEmail() + " already registered!");
        }
        EncodeInfo encodeInfo = generateSoleAndPass(user.getPassword());
        user.setSole(encodeInfo.getSole());
        user.setPassword(encodeInfo.getEncodedPassword());
        user.setRole(Role.USER.name());
        userDao.add(user);
        log.info("Add user with name: {}", user.getName());
    }


    @Override
    public User verifyUser(Credentials credentials) {
        User foundUser = userDao.findByEmail(credentials.getEmail());
        if (foundUser == null) {
            log.error("User with email {} not found", credentials.getEmail());
            throw new RuntimeException("User with email " + credentials.getEmail() + " not found");
        }
        boolean isVerified = PasswordEncoder.verifyUserPass(EncodeInfo.builder()
                .sole(foundUser.getSole())
                .encodedPassword(foundUser.getPassword())
                .decodedPassword(credentials.getPassword())
                .build());
        if (isVerified) {
            return foundUser;
        }
        log.error("Invalid password for user {}", credentials.getEmail());
        throw new RuntimeException("Invalid password");
    }
}
