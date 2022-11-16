package com.onoprienko.onlineshop.ioc.factory;

import com.onoprienko.ioc.entity.BeanDefinition;
import com.onoprienko.ioc.processor.BeanFactoryPostProcessor;
import com.onoprienko.onlineshop.utils.PropertiesHolder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.Properties;

@Slf4j
public class PropertiesBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    private Properties properties;
    private final static String PROPERTY_PREFIX = "${";
    private final static String PROPERTY_ENDING = "}";

    @Override
    public void postProcessBeanFactory(List<BeanDefinition> beanDefinitions) {
        this.properties = getProperties();
        log.info("Injecting properties in bean definitions");
        for (BeanDefinition beanDefinition : beanDefinitions) {
            for (Map.Entry<String, String> entry : beanDefinition.getValueDependencies().entrySet()) {
                String value = entry.getValue();
                if (value.startsWith(PROPERTY_PREFIX) && value.endsWith(PROPERTY_ENDING)) {
                    String property = properties.getProperty(parseProperty(value));
                    entry.setValue(property);
                }
            }
        }
    }

    String parseProperty(String value) {
        return value.substring(2, value.length() - 1);
    }

    Properties getProperties() {
        String propertiesPath = "application.properties";
        PropertiesHolder propertiesHolder = new PropertiesHolder();
        Properties properties = propertiesHolder.getProperties(propertiesPath);
        log.info("Get properties");
        return properties;
    }
}
