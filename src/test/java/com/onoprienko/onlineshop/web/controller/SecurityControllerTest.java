package com.onoprienko.onlineshop.web.controller;

import com.onoprienko.onlineshop.entity.User;
import com.onoprienko.onlineshop.service.impl.DefaultUserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.ui.ModelMap;

import static org.junit.jupiter.api.Assertions.assertEquals;


class SecurityControllerTest {
    private final DefaultUserService userService = Mockito.mock(DefaultUserService.class);
    private final SecurityController securityController = new SecurityController(userService);


    @Test
    void getLoginPageReturnLoginPageName() {
        String loginPage = securityController.getLoginPage();

        assertEquals(loginPage, "login");
    }

    @Test
    void getRegistrationPageReturnLoginPageName() {
        String registrationPage = securityController.getRegistrationPage();

        assertEquals(registrationPage, "registration");
    }

    @Test
    void registrationReturnRedirectToLogin() {
        ModelMap modelMap = new ModelMap();
        String loginPage = securityController.registration(User.builder()
                .name("name").build(), modelMap);

        assertEquals(loginPage, "redirect:/login");
    }

    @Test
    void registrationReturnExceptionAndRedirectToRegistration() {
        User user = new User("name", "email", "pass", "role");
        Mockito.when(userService.add(user)).thenThrow(new RuntimeException("Exception"));
        ModelMap modelMap = new ModelMap();
        String response = securityController.registration(user, modelMap);

        assertEquals(modelMap.getAttribute("errorMessage"), "Exception");
        assertEquals(response, "registration");
    }
}