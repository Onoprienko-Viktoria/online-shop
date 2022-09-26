package com.onoprienko.onlineshop.utils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Collections;
import java.util.Map;

public class PageGenerator {
    private final Configuration configuration;


    public PageGenerator(String templatesDir) {
        try {
            this.configuration = new Configuration(Configuration.VERSION_2_3_31);
            configuration.setDirectoryForTemplateLoading(new File(templatesDir));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getPage(String filename, Map<String, Object> data) {
        Writer stream = new StringWriter();
        try {
            Template template = configuration.getTemplate(filename);
            template.process(data, stream);
        } catch (IOException | TemplateException e) {
            throw new RuntimeException(e);
        }
        return stream.toString();

    }

    public String getPage(String filename) {
        return getPage(filename, Collections.emptyMap());
    }

    public String getPageWithMessage(String fileName, String message) {
        Map<String, Object> parameters = Map.of("errorMessage", message);
        return getPage(fileName, parameters);
    }
}
