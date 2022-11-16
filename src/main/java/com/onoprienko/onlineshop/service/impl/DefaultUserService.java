package com.onoprienko.onlineshop.service.impl;

import com.onoprienko.onlineshop.dao.UserDao;
import com.onoprienko.onlineshop.entity.User;
import com.onoprienko.onlineshop.security.PasswordEncoder;
import com.onoprienko.onlineshop.security.entity.Credentials;
import com.onoprienko.onlineshop.security.entity.EncodeInfo;
import com.onoprienko.onlineshop.security.entity.Role;
import com.onoprienko.onlineshop.service.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

import static com.onoprienko.onlineshop.security.PasswordEncoder.generateSoleAndPass;

@AllArgsConstructor
@Slf4j
@NoArgsConstructor
@Setter
public class DefaultUserService implements UserService {
    private UserDao userDao;

    @Override
    public void add(User user) {
        Optional<User> userByEmail = userDao.findByEmail(user.getEmail());
        if (userByEmail.isPresent()) {
            throw new RuntimeException("User already registered!");
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
        Optional<User> optionalUser = userDao.findByEmail(credentials.getEmail());
        if (optionalUser.isEmpty()) {
            log.error("User  not found");
            throw new RuntimeException("User not found");
        }
        User foundUser = optionalUser.get();
        boolean isVerified = PasswordEncoder.verifyUserPass(EncodeInfo.builder()
                .sole(foundUser.getSole())
                .encodedPassword(foundUser.getPassword())
                .decodedPassword(credentials.getPassword())
                .build());
        if (isVerified) {
            return foundUser;
        }
        log.error("Invalid password");
        throw new RuntimeException("Invalid password");
    }
}
