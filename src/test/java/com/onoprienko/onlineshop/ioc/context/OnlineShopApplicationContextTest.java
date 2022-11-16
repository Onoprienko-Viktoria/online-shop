package com.onoprienko.onlineshop.ioc.context;

import com.onoprienko.ioc.context.ApplicationContext;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class OnlineShopApplicationContextTest {

    @Test
    void getApplicationContext() {
        ApplicationContext applicationContext = OnlineShopApplicationContext.getApplicationContext();
        assertNotNull(applicationContext);
    }
}