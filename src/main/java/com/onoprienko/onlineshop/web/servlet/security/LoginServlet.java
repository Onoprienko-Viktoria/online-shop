package com.onoprienko.onlineshop.web.servlet.security;

import com.onoprienko.onlineshop.ioc.context.OnlineShopApplicationContext;
import com.onoprienko.onlineshop.security.entity.Credentials;
import com.onoprienko.onlineshop.security.entity.Session;
import com.onoprienko.onlineshop.security.service.SecurityService;
import com.onoprienko.onlineshop.security.service.impl.DefaultSecurityService;
import com.onoprienko.onlineshop.web.utils.PageGenerator;
import com.onoprienko.onlineshop.web.utils.WebUtils;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class LoginServlet extends HttpServlet {
    private SecurityService securityService = (SecurityService) OnlineShopApplicationContext.getService(DefaultSecurityService.class);
    private PageGenerator pageGenerator = (PageGenerator) OnlineShopApplicationContext.getService(PageGenerator.class);


    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String page = pageGenerator.getPage("login.ftl");
        resp.getWriter().write(page);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            Credentials credentials = WebUtils.getCredentialsFromRequest(req);

            Session session = securityService.login(credentials);

            Cookie cookie = new Cookie("user-token", session.getToken());
            cookie.setMaxAge(session.getTimeToLive());
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
