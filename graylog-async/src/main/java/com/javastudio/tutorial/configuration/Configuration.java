package com.javastudio.tutorial.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configuration {

    private final Properties properties = new Properties();

    public Configuration(String resource) {
        InputStream resourceAsStream = Configuration.class.getClassLoader().getResourceAsStream(resource);
        try {
            properties.load(resourceAsStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getProperty(String property) {
        return properties.getProperty(property);
    }
}
