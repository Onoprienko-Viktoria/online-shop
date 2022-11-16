package com.onoprienko.onlineshop.ioc.context;

import com.onoprienko.ioc.context.ApplicationContext;
import com.onoprienko.ioc.context.impl.GenericApplicationContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OnlineShopApplicationContext {
    private static final ApplicationContext applicationContext;

    static {
        applicationContext = new GenericApplicationContext("context/context.xml");
    }

    public static Object getService(Class<?> clazz) {
        log.info("Get service from Application context: {}", clazz.getName());
        return applicationContext.getBean(clazz);
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }
}
