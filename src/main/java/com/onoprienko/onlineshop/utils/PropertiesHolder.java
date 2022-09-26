package com.onoprienko.onlineshop.utils;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Getter
public class PropertiesHolder {
    private Map<String, Properties> propertiesMap = new ConcurrentHashMap<>();

    public Properties getProperties(String path) {
        if (!propertiesMap.containsKey(path)) {
            propertiesMap.put(path, readPropertiesFile(path));
        }
        return propertiesMap.get(path);
    }

    public static Properties readPropertiesFile(String path) {
        try (InputStream input = PropertiesHolder.class.getClassLoader().getResourceAsStream(path)) {
            Properties properties = new Properties();
            properties.load(input);
            return properties;
        } catch (Exception e) {
            log.error("Can not read properties from path {}, error {}", path, e);
            throw new RuntimeException(e);
        }
    }
}
