package com.onoprienko.onlineshop.ioc.factory;

import com.onoprienko.ioc.entity.BeanDefinition;
import com.onoprienko.onlineshop.ioc.entity.Worker;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PropertiesBeanFactoryPostProcessorTest {

    @Test
    void postProcessBeanFactory() {
        PropertiesBeanFactoryPostProcessor postProcessor = new PropertiesBeanFactoryPostProcessor();
        BeanDefinition worker = new BeanDefinition("worker_1", Worker.class.getCanonicalName());
        worker.setValueDependencies(Map.of("name", "${session.timetolive}"));
        postProcessor.postProcessBeanFactory(List.of(worker));

        assertEquals(worker.getValueDependencies().get("name"), "480");
    }

    @Test
    void parseProperty() {
        PropertiesBeanFactoryPostProcessor postProcessor = new PropertiesBeanFactoryPostProcessor();
        String value = "${test.value}";

        String result = postProcessor.parseProperty(value);

        assertEquals(result, "test.value");
    }

    @Test
    void getProperties() {
        PropertiesBeanFactoryPostProcessor postProcessor = new PropertiesBeanFactoryPostProcessor();
        Properties properties = postProcessor.getProperties();

        assertNotNull(properties);

        assertEquals(properties.getProperty("session.timetolive"), "480");
    }
}