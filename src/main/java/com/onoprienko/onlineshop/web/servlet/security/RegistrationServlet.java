package com.onoprienko.onlineshop.web.servlet.security;

import com.onoprienko.onlineshop.entity.User;
import com.onoprienko.onlineshop.ioc.context.OnlineShopApplicationContext;
import com.onoprienko.onlineshop.service.UserService;
import com.onoprienko.onlineshop.service.impl.DefaultUserService;
import com.onoprienko.onlineshop.web.utils.PageGenerator;
import com.onoprienko.onlineshop.web.utils.WebUtils;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationServlet extends HttpServlet {
    private UserService userService = (UserService) OnlineShopApplicationContext.getService(DefaultUserService.class);
    private PageGenerator pageGenerator = (PageGenerator) OnlineShopApplicationContext.getService(PageGenerator.class);

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String page = pageGenerator.getPage("registration.ftl");
        resp.getWriter().write(page);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User user = WebUtils.getUserFromRequest(req);
        try {
            userService.add(user);
            log.info("Successfully add user {}", user);
            resp.sendRedirect("/login");
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            log.error("Error while registration", e);
            resp.getWriter().write(pageGenerator.getPageWithMessage("registration.ftl", errorMessage));
        }
    }
}
