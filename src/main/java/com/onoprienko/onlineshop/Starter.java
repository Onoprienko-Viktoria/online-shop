package com.onoprienko.onlineshop;

import com.onoprienko.onlineshop.dao.ProductDao;
import com.onoprienko.onlineshop.dao.UserDao;
import com.onoprienko.onlineshop.dao.jdbc.JdbcProductDao;
import com.onoprienko.onlineshop.dao.jdbc.JdbcUserDao;
import com.onoprienko.onlineshop.security.filter.SecurityFilter;
import com.onoprienko.onlineshop.service.ProductService;
import com.onoprienko.onlineshop.service.SessionService;
import com.onoprienko.onlineshop.service.impl.ProductServiceImpl;
import com.onoprienko.onlineshop.service.impl.SessionServiceImpl;
import com.onoprienko.onlineshop.service.impl.UserServiceImpl;
import com.onoprienko.onlineshop.servlet.products.*;
import com.onoprienko.onlineshop.servlet.users.LoginServlet;
import com.onoprienko.onlineshop.servlet.users.LogoutServlet;
import com.onoprienko.onlineshop.servlet.users.RegistrationServlet;
import com.onoprienko.onlineshop.utils.DataSourceFactory;
import com.onoprienko.onlineshop.utils.PageGenerator;
import com.onoprienko.onlineshop.utils.PropertiesHolder;
import jakarta.servlet.DispatcherType;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.util.EnumSet;
import java.util.Properties;

@Slf4j
public class Starter {

    public static void main(String[] args) throws Exception {
        //Properties
        String propertiesPath = "application.properties";
        PropertiesHolder propertiesHolder = new PropertiesHolder();
        Properties properties = propertiesHolder.getProperties(propertiesPath);
        log.info("Get properties");

        PageGenerator pageGenerator = new PageGenerator(properties.getProperty("html.templates.path"));

        //DAO and Services
        DataSourceFactory dataSourceFactory = new DataSourceFactory(
                properties.getProperty("jdbc.url"));
        log.info("Configure datasource");

        ProductDao productDao = new JdbcProductDao(dataSourceFactory.create());
        UserDao userDao = new JdbcUserDao(dataSourceFactory.create());
        UserServiceImpl userService = new UserServiceImpl(userDao);
        SessionService sessionService = new SessionServiceImpl();
        ProductService productService = new ProductServiceImpl(productDao);

        //WEB

        //Servlets
        AllProductsServlet allProductsServlet = new AllProductsServlet(productService, pageGenerator);
        AddProductServlet addProductServlet = new AddProductServlet(productService, pageGenerator);
        EditProductServlet editProductServlet = new EditProductServlet(productService, pageGenerator);
        RemoveProductServlet removeProductServlet = new RemoveProductServlet(productService);
        RegistrationServlet registrationServlet = new RegistrationServlet(userService, pageGenerator);
        LoginServlet loginServlet = new LoginServlet(sessionService, userService, pageGenerator, properties.getProperty("session.time-to-live"));
        LogoutServlet logoutServlet = new LogoutServlet(sessionService);
        CartServlet cartServlet = new CartServlet(productService, pageGenerator);


        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        contextHandler.addFilter(new FilterHolder(new SecurityFilter(sessionService)), "/*", EnumSet.of(DispatcherType.REQUEST));

        //Paths
        contextHandler.addServlet(new ServletHolder(allProductsServlet), "/products");
        contextHandler.addServlet(new ServletHolder(addProductServlet), "/product/add");
        contextHandler.addServlet(new ServletHolder(removeProductServlet), "/product/remove");
        contextHandler.addServlet(new ServletHolder(editProductServlet), "/product/edit");
        contextHandler.addServlet(new ServletHolder(registrationServlet), "/registration");
        contextHandler.addServlet(new ServletHolder(loginServlet), "/login");
        contextHandler.addServlet(new ServletHolder(logoutServlet), "/logout");
        contextHandler.addServlet(new ServletHolder(cartServlet), "/cart/*");
        log.info("Create servlets");
        //Server
        String port = properties.getProperty("server.port");
        Server server = new Server(Integer.parseInt(port));
        server.setHandler(contextHandler);
        server.start();
        log.info("Server start work on port {}", port);
    }
}
