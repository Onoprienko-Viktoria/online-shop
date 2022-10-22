package com.onoprienko.onlineshop.service;

import com.onoprienko.onlineshop.dao.UserDao;
import com.onoprienko.onlineshop.entity.User;
import com.onoprienko.onlineshop.security.entity.Credentials;
import com.onoprienko.onlineshop.security.entity.Role;
import com.onoprienko.onlineshop.service.impl.DefaultUserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    private final UserDao userDao = Mockito.mock(UserDao.class);
    private final UserService userService = new DefaultUserService(userDao);


    User testUserOne = User.builder()
            .role(Role.USER.name())
            .email("a@gmail.com")
            .name("Tom")
            .password("70bdeae2a2b72c7991d36ee1b6f792a1")
            .sole("21675f33-8b4c-45b5-83a0-0e5f32ec364b")
            .build();
    Credentials userOneCreds = Credentials.builder()
            .email("a@gmail.com")
            .password("1234")
            .build();
    User testUserTwo = User.builder()
            .role(Role.ADMIN.name())
            .email("b@gmail.com")
            .name("John")
            .password("asdasdad")
            .sole("cxvfrvwvsdccadcva")
            .build();
    Credentials testUserTwoCreds = Credentials.builder()
            .email(testUserTwo.getEmail())
            .password("1234")
            .build();

    @Test
    void addUserWorkCorrect() {
        Mockito.when(userDao.findByEmail("b@gmail.com")).thenReturn(null);

        userService.add(testUserTwo);

        Mockito.verify(userDao, Mockito.times(1)).findByEmail("b@gmail.com");
        Mockito.verify(userDao, Mockito.times(1)).add(testUserTwo);
    }

    @Test
    void addUserReturnsExceptionIfUserAlreadyExist() {
        Mockito.when(userDao.findByEmail("b@gmail.com")).thenReturn(testUserTwo);

        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> userService.add(testUserTwo));

        String errorMes = "User with email " + testUserTwo.getEmail() + " already registered!";
        assertEquals(errorMes, runtimeException.getMessage());

        Mockito.verify(userDao, Mockito.times(1)).findByEmail("b@gmail.com");
    }

    @Test
    void verifyUserReturnsExceptionIfUserNotFound() {
        Mockito.when(userDao.findByEmail("a@gmail.com")).thenReturn(null);

        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> userService.verifyUser(userOneCreds));

        String errorMes = "User with email " + testUserOne.getEmail() + " not found";
        assertEquals(errorMes, runtimeException.getMessage());

        Mockito.verify(userDao, Mockito.times(1)).findByEmail(testUserOne.getEmail());

    }

    @Test
    void verifyUserReturnsUser() {
        Mockito.when(userDao.findByEmail(testUserOne.getEmail())).thenReturn(testUserOne);

        User user = userService.verifyUser(userOneCreds);

        assertNotNull(user);
        assertEquals(user.getEmail(), testUserOne.getEmail());
        assertEquals(user.getPassword(), testUserOne.getPassword());
        assertEquals(user.getRole(), testUserOne.getRole());
        assertEquals(user.getName(), testUserOne.getName());
        assertEquals(user.getSole(), testUserOne.getSole());

        Mockito.verify(userDao, Mockito.times(1)).findByEmail(testUserOne.getEmail());
    }

    @Test
    void verifyUserReturnsExceptionIfPasswordIncorrect() {
        Mockito.when(userDao.findByEmail(testUserTwo.getEmail())).thenReturn(testUserTwo);

        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> userService.verifyUser(testUserTwoCreds));

        String errorMes = "Invalid password";
        assertEquals(errorMes, runtimeException.getMessage());

        Mockito.verify(userDao, Mockito.times(1)).findByEmail(testUserTwo.getEmail());

    }
}