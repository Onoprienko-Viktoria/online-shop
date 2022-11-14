package com.onoprienko.onlineshop.web.servlet.security;

import com.onoprienko.onlineshop.security.entity.Credentials;
import com.onoprienko.onlineshop.security.service.SecurityService;
import com.onoprienko.onlineshop.service.locator.ServiceLocator;
import com.onoprienko.onlineshop.web.utils.PageGenerator;
import com.onoprienko.onlineshop.web.utils.WebUtils;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class LoginServlet extends HttpServlet {
    private final SecurityService securityService;
    private final PageGenerator pageGenerator;
    private final String timeToLive;

    public LoginServlet(SecurityService securityService, PageGenerator pageGenerator, String timeToLive) {
        this.securityService = securityService;
        this.pageGenerator = pageGenerator;
        this.timeToLive = timeToLive;
    }

    public LoginServlet() {
        this.securityService = ServiceLocator.getService(SecurityService.class);
        this.pageGenerator = ServiceLocator.getService(PageGenerator.class);
        this.timeToLive = "480";
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String page = pageGenerator.getPage("login.ftl");
        resp.getWriter().write(page);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            Credentials credentials = WebUtils.getCredentialsFromRequest(req);

            String token = securityService.login(timeToLive, credentials).getToken();

            Cookie cookie = new Cookie("user-token", token);
            cookie.setMaxAge(Integer.parseInt(timeToLive));
            resp.addCookie(cookie);

            log.info("Create token. Success login");
            resp.sendRedirect("/products");
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            log.error("Exception while login", e);
            resp.getWriter().write(pageGenerator.getPageWithMessage("login.ftl", errorMessage));
        }
    }
}
