package com.onoprienko.onlineshop.web.controller;

import com.onoprienko.onlineshop.entity.User;
import com.onoprienko.onlineshop.security.entity.Credentials;
import com.onoprienko.onlineshop.security.entity.Session;
import com.onoprienko.onlineshop.security.service.SecurityService;
import com.onoprienko.onlineshop.service.UserService;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.ui.ModelMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class SecurityControllerTest {
    private final UserService userService = Mockito.mock(UserService.class);
    private final SecurityService securityService = Mockito.mock(SecurityService.class);
    private final SecurityController securityController = new SecurityController(securityService, userService);
    private final Credentials credentials = Credentials.builder()
            .email("email").password("password").build();

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
    void loginReturnLoginPageNameAndAddToModelCookie() {
        ModelMap modelMap = new ModelMap();
        Mockito.when(securityService.login(credentials)).thenReturn(Session.builder().token("fafdagggfh").timeToLive(480).build());
        String loginPage = securityController.login(credentials, modelMap);

        assertEquals(loginPage, "login");

        assertTrue(modelMap.containsAttribute("user-token"));
        assertEquals(modelMap.getAttribute("user-token").getClass(), Cookie.class);
    }

    @Test
    void loginReturnExceptionIfSessionNull() {
        ModelMap modelMap = new ModelMap();
        String loginPage = securityController.login(credentials, modelMap);
        Mockito.when(securityService.login(credentials)).thenThrow(new RuntimeException("Cannot invoke \"com.onoprienko.onlineshop.security.entity.Session.getToken()\" because \"session\" is null"));
        assertEquals(loginPage, "login");

        assertTrue(modelMap.containsAttribute("errorMessage"));
        assertEquals(modelMap.getAttribute("errorMessage"), "Cannot invoke \"com.onoprienko.onlineshop.security.entity.Session.getToken()\" because \"session\" is null");
    }

    @Test
    void registrationReturnRedirectToLogin() {
        ModelMap modelMap = new ModelMap();
        String loginPage = securityController.registration(User.builder()
                .name("name").build(), modelMap);

        assertEquals(loginPage, "redirect:/login");
    }

    @Test
    void logoutReturnLogoutPageName() {
        ModelMap modelMap = new ModelMap();
        String loginPage = securityController.logout(Session.builder().build(), modelMap);

        assertEquals(loginPage, "logout");
    }
}