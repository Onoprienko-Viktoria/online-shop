package com.onoprienko.onlineshop.web.controller;

import com.onoprienko.onlineshop.entity.User;
import com.onoprienko.onlineshop.service.impl.DefaultUserService;
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
    private final DefaultUserService userService;

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
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
            log.info("Successfully add user");
            return "redirect:/login";
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            log.error("Error while registration", e);
            modelMap.addAttribute("errorMessage", errorMessage);
            return "registration";
        }
    }
}