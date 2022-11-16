package com.onoprienko.onlineshop.web.utils;

import com.onoprienko.ioc.annotation.PostConstruct;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Collections;
import java.util.Map;

@NoArgsConstructor
@Setter
public class PageGenerator {
    private Configuration configuration;
    private String templatesDir;

    @PostConstruct
    public void init() {
        if (configuration == null) {
            try {
                this.configuration = new Configuration(Configuration.VERSION_2_3_31);
                configuration.setClassForTemplateLoading(this.getClass(), templatesDir);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public PageGenerator(String templatesDir) {
        this.templatesDir = templatesDir;
        try {
            this.configuration = new Configuration(Configuration.VERSION_2_3_31);
            configuration.setClassForTemplateLoading(this.getClass(), templatesDir);
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
