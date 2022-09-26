package com.onoprienko.onlineshop.service;

import com.onoprienko.onlineshop.dao.UserDao;
import com.onoprienko.onlineshop.entity.Role;
import com.onoprienko.onlineshop.entity.User;
import com.onoprienko.onlineshop.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    private final UserDao userDao = Mockito.mock(UserDao.class);
    private final UserService userService = new UserServiceImpl(userDao);

    User testUserOne = User.builder()
            .role(Role.USER.name())
            .email("a@gmail.com")
            .name("Tom")
            .password("70bdeae2a2b72c7991d36ee1b6f792a1")
            .sole("21675f33-8b4c-45b5-83a0-0e5f32ec364b")
            .build();
    User testUserOneCreds = User.builder()
            .email(testUserOne.getEmail())
            .password("1234")
            .build();
    User testUserTwo = User.builder()
            .role(Role.ADMIN.name())
            .email("b@gmail.com")
            .name("John")
            .password("asdasdad")
            .sole("cxvfrvwvsdccadcva")
            .build();
    User testUserTwoCreds = User.builder()
            .email(testUserTwo.getEmail())
            .password("1234")
            .build();

    @Test
    void addUserWorkCorrect() {
        Mockito.when(userDao.findUserByEmail("b@gmail.com")).thenReturn(null);

        userService.addUser(testUserTwo);

        Mockito.verify(userDao, Mockito.times(1)).findUserByEmail("b@gmail.com");
        Mockito.verify(userDao, Mockito.times(1)).addUser(testUserTwo);
    }

    @Test
    void addUserReturnsExceptionIfUserAlreadyExist() {
        Mockito.when(userDao.findUserByEmail("b@gmail.com")).thenReturn(testUserTwo);

        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> userService.addUser(testUserTwo));

        String errorMes = "User with email " + testUserTwo.getEmail() + " already registered!";
        assertEquals(errorMes, runtimeException.getMessage());

        Mockito.verify(userDao, Mockito.times(1)).findUserByEmail("b@gmail.com");
    }

    @Test
    void verifyUserReturnsExceptionIfUserNotFound() {
        Mockito.when(userDao.findUserByEmail("a@gmail.com")).thenReturn(null);

        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> userService.verifyUser(testUserOne));

        String errorMes = "User with email " + testUserOne.getEmail() + " not found";
        assertEquals(errorMes, runtimeException.getMessage());

        Mockito.verify(userDao, Mockito.times(1)).findUserByEmail(testUserOne.getEmail());

    }

    @Test
    void verifyUserReturnsUser() {
        Mockito.when(userDao.findUserByEmail(testUserOne.getEmail())).thenReturn(testUserOne);

        User user = userService.verifyUser(testUserOneCreds);

        assertNotNull(user);
        assertEquals(user.getEmail(), testUserOne.getEmail());
        assertEquals(user.getPassword(), testUserOne.getPassword());
        assertEquals(user.getRole(), testUserOne.getRole());
        assertEquals(user.getName(), testUserOne.getName());
        assertEquals(user.getSole(), testUserOne.getSole());

        Mockito.verify(userDao, Mockito.times(1)).findUserByEmail(testUserOne.getEmail());
    }

    @Test
    void verifyUserReturnsExceptionIfPasswordIncorrect() {
        Mockito.when(userDao.findUserByEmail(testUserTwo.getEmail())).thenReturn(testUserTwo);

        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> userService.verifyUser(testUserTwo));

        String errorMes = "Invalid password";
        assertEquals(errorMes, runtimeException.getMessage());

        Mockito.verify(userDao, Mockito.times(1)).findUserByEmail(testUserTwo.getEmail());

    }
}