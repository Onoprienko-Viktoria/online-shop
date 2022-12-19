package com.onoprienko.onlineshop.web.controller;

import com.onoprienko.onlineshop.entity.User;
import com.onoprienko.onlineshop.security.entity.Credentials;
import com.onoprienko.onlineshop.security.entity.Session;
import com.onoprienko.onlineshop.security.service.SecurityService;
import com.onoprienko.onlineshop.service.UserService;
import jakarta.servlet.http.Cookie;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("")
@Slf4j
public class SecurityController {
    private final SecurityService securityService;
    private final UserService userService;

    private final static String TOKEN_NAME = "user-token";

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute Credentials credentials,
                        ModelMap modelMap) {
        try {
            Session session = securityService.login(credentials);

            Cookie cookie = new Cookie(TOKEN_NAME, session.getToken());
            cookie.setMaxAge(session.getTimeToLive());
            modelMap.addAttribute(TOKEN_NAME, cookie);
            log.info("Create token. Success login");
            return ("login");
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            modelMap.addAttribute("errorMessage", errorMessage);
            log.error("Exception while login", e);
            return "login";
        }
    }

    @GetMapping("/registration")
    public String getRegistrationPage() {
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute User user,
                               ModelMap modelMap) {
        try {
            userService.add(user);
            log.info("Successfully add user {}", user);
            return "redirect:/login";
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            log.error("Error while registration", e);
            modelMap.addAttribute("errorMessage", errorMessage);
            return "registration";
        }
    }


    @PostMapping("/logout")
    public String logout(@ModelAttribute Session session,
                         ModelMap modelMap) {
        securityService.logout(session);
        log.info("Logout user");
        Cookie cookie = new Cookie(TOKEN_NAME, session.getToken());
        cookie.setMaxAge(0);
        modelMap.addAttribute(TOKEN_NAME, cookie);
        return "logout";
    }
}