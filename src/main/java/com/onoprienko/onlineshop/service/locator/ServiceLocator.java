package com.onoprienko.onlineshop.service.locator;

import com.onoprienko.onlineshop.dao.ProductDao;
import com.onoprienko.onlineshop.dao.UserDao;
import com.onoprienko.onlineshop.dao.jdbc.JdbcProductDao;
import com.onoprienko.onlineshop.dao.jdbc.JdbcUserDao;
import com.onoprienko.onlineshop.security.service.SecurityService;
import com.onoprienko.onlineshop.security.service.impl.DefaultSecurityService;
import com.onoprienko.onlineshop.service.ProductService;
import com.onoprienko.onlineshop.service.UserService;
import com.onoprienko.onlineshop.service.impl.DefaultProductService;
import com.onoprienko.onlineshop.service.impl.DefaultUserService;
import com.onoprienko.onlineshop.utils.Parser;
import com.onoprienko.onlineshop.utils.database.DataSourceFactory;
import com.onoprienko.onlineshop.utils.PropertiesHolder;
import com.onoprienko.onlineshop.web.utils.PageGenerator;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Slf4j
public class ServiceLocator {

    private static final Map<Class<?>, Object> SERVICES = new HashMap<>();

    static {
        //Properties
        String propertiesPath = "application.properties";
        PropertiesHolder propertiesHolder = new PropertiesHolder();
        Properties properties = propertiesHolder.getProperties(propertiesPath);
        log.info("Get properties");

        PageGenerator pageGenerator = new PageGenerator(properties.getProperty("html.templates.path"));

        //DAO and Services
        String herokuDataBaseUrl = System.getenv("DATABASE_URL");
        DataSourceFactory dataSourceFactory;
        if(herokuDataBaseUrl!= null) {
            dataSourceFactory = new DataSourceFactory(Parser.parseDatabaseUrl(herokuDataBaseUrl));
        } else {
            dataSourceFactory = new DataSourceFactory(
                    properties.getProperty("jdbc.url"),
                    properties.getProperty("jdbc.pass"),
                    properties.getProperty("jdbc.user"));
        }
        log.info("Configure datasource");

        ProductDao productDao = new JdbcProductDao(dataSourceFactory.create());
        UserDao userDao = new JdbcUserDao(dataSourceFactory.create());


        UserService userService = new DefaultUserService(userDao);
        SecurityService securityService = new DefaultSecurityService(userService);
        ProductService productService = new DefaultProductService(productDao);


        addService(DataSourceFactory.class, dataSourceFactory);
        addService(UserService.class, userService);
        addService(ProductService.class, productService);
        addService(SecurityService.class, securityService);
        addService(PageGenerator.class, pageGenerator);
        log.info("Add services");
    }

    public static <T> T getService(Class<T> serviceType) {
        return serviceType.cast(SERVICES.get(serviceType));
    }

    public static void addService(Class<?> serviceName, Object service) {
        SERVICES.put(serviceName, service);
    }
}