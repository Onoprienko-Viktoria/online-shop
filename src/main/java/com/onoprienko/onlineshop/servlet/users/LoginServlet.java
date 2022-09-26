package com.onoprienko.onlineshop.servlet.users;

import com.onoprienko.onlineshop.entity.User;
import com.onoprienko.onlineshop.service.SessionService;
import com.onoprienko.onlineshop.service.UserService;
import com.onoprienko.onlineshop.utils.PageGenerator;
import com.onoprienko.onlineshop.utils.RequestExtractor;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@AllArgsConstructor
public class LoginServlet extends HttpServlet {
    private SessionService sessionService;
    private UserService userService;
    private PageGenerator pageGenerator;
    private String timeToLive;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String page = pageGenerator.getPage("login.ftl");
        resp.getWriter().write(page);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            User user = RequestExtractor.getUserFromRequest(req);
            User verifyUser = userService.verifyUser(user);
            String token = sessionService.createSession(timeToLive, verifyUser.getRole()).getToken();

            Cookie cookie = new Cookie("user-token", token);
            cookie.setMaxAge(Integer.parseInt(timeToLive));
            resp.addCookie(cookie);

            log.info("Create token. Success login {}", user.getEmail());
            resp.sendRedirect("/products");
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            log.error("Exception while login", e);
            resp.getWriter().write(pageGenerator.getPageWithMessage("login.ftl", errorMessage));
        }
    }
}
