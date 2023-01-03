package com.onoprienko.onlineshop.service;

import com.onoprienko.onlineshop.dao.UserDao;
import com.onoprienko.onlineshop.entity.User;
import com.onoprienko.onlineshop.security.entity.Credentials;
import com.onoprienko.onlineshop.security.entity.Role;
import com.onoprienko.onlineshop.service.impl.DefaultUserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    private final UserDao userDao = Mockito.mock(UserDao.class);
    private final PasswordEncoder passwordEncoder = Mockito.mock(PasswordEncoder.class);
    private final DefaultUserService userService = new DefaultUserService(userDao, passwordEncoder);


    User testUserOne = User.builder()
            .role(Role.USER.name())
            .email("a@gmail.com")
            .name("Tom")
            .password("70bdeae2a2b72c7991d36ee1b6f792a1")
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
            .build();
    Credentials testUserTwoCreds = Credentials.builder()
            .email(testUserTwo.getEmail())
            .password("1234")
            .build();

    @Test
    void addUserWorkCorrect() {
        Mockito.when(userDao.findByEmail("b@gmail.com")).thenReturn(Optional.empty());

        User user = userService.add(testUserTwo);

        assertEquals(user.getEmail(), "b@gmail.com");
        assertEquals(user.getName(), "John");

        Mockito.verify(userDao, Mockito.times(1)).findByEmail("b@gmail.com");
        Mockito.verify(userDao, Mockito.times(1)).add(testUserTwo);
    }

    @Test
    void addUserNullReturnNullPointerException() {
        Mockito.when(userDao.findByEmail("b@gmail.com")).thenReturn(Optional.empty());

        NullPointerException nullPointerException = assertThrows(NullPointerException.class, () -> userService.add(null));

        assertEquals(nullPointerException.getMessage(), "Cannot invoke \"com.onoprienko.onlineshop.entity.User.getEmail()\" because \"user\" is null");
    }

    @Test
    void addUserWithNullRoleSaveUserWithRoleUSER() {
        Mockito.when(userDao.findByEmail("b@gmail.com")).thenReturn(Optional.empty());

        User user = userService.add(User.builder()
                .email("email")
                .name("name")
                .password("pass")
                .role(null)
                .build());

        assertEquals(user.getEmail(), "email");
        assertEquals(user.getName(), "name");
        assertEquals(user.getRole(), "USER");

        Mockito.verify(userDao, Mockito.times(1)).findByEmail("email");
        Mockito.verify(userDao, Mockito.times(1)).add(user);
    }


    @Test
    void addUserReturnsExceptionIfUserAlreadyExist() {
        Mockito.when(userDao.findByEmail("b@gmail.com")).thenReturn(Optional.of(testUserTwo));

        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> userService.add(testUserTwo));

        String errorMes = "User already registered!";
        assertEquals(errorMes, runtimeException.getMessage());

        Mockito.verify(userDao, Mockito.times(1)).findByEmail("b@gmail.com");
    }

    @Test
    void loadUserByUsernameReturnsUserDetails() {
        Mockito.when(userDao.findByEmail(testUserOne.getEmail())).thenReturn(Optional.of(testUserOne));

        UserDetails user = userService.loadUserByUsername(userOneCreds.getEmail());

        assertNotNull(user);
        assertEquals(user.getPassword(), testUserOne.getPassword());
        assertEquals(user.getUsername(), testUserOne.getEmail());
        user.getAuthorities().forEach(grantedAuthority -> assertEquals(grantedAuthority.getAuthority(), testUserOne.getRole()));

        Mockito.verify(userDao, Mockito.times(1)).findByEmail(testUserOne.getEmail());
    }

    @Test
    void loadUserByUsernameReturnsExceptionIfUserNotFound() {
        Mockito.when(userDao.findByEmail(testUserOne.getEmail())).thenThrow(new UsernameNotFoundException("Not found"));

        UsernameNotFoundException usernameNotFoundException = assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername(userOneCreds.getEmail()));

        assertEquals(usernameNotFoundException.getMessage(), "Not found");

        Mockito.verify(userDao, Mockito.times(1)).findByEmail(testUserOne.getEmail());
    }
}