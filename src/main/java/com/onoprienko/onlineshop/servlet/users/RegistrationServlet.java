package com.onoprienko.onlineshop.servlet.users;

import com.onoprienko.onlineshop.entity.User;
import com.onoprienko.onlineshop.service.UserService;
import com.onoprienko.onlineshop.utils.PageGenerator;
import com.onoprienko.onlineshop.utils.RequestExtractor;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@AllArgsConstructor
@Slf4j
public class RegistrationServlet extends HttpServlet {
    private UserService userService;
    private PageGenerator pageGenerator;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String page = pageGenerator.getPage("registration.ftl");
        resp.getWriter().write(page);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User user = RequestExtractor.getUserFromRequest(req);
        try {
            userService.addUser(user);
            log.info("Successfully add user {}", user);
            resp.sendRedirect("/login");
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            log.error("Error while registration", e);
            resp.getWriter().write(pageGenerator.getPageWithMessage("registration.ftl", errorMessage));
        }
    }
}
