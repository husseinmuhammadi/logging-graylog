package com.javastudio.tutorial.configuration;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

class ConfigurationTest {

    private Logger logger = LoggerFactory.getLogger(ConfigurationTest.class);

    @Test
    void whenResolveGraylogProperties_thenPrintGraylogProperties() {
        Configuration cfg = new Configuration("graylog.properties");
        String host = cfg.getProperty("graylog.gelf.host");
        logger.info(host);
        String port = cfg.getProperty("graylog.gelf.port");
        logger.info(port);
        String protocol = cfg.getProperty("graylog.gelf.protocol");
        logger.info(protocol);
    }
}