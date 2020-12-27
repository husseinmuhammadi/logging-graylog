package com.javastudio.tutorial.logger;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class GraylogConfigurationTest {

    private Logger logger = LoggerFactory.getLogger(GraylogConfigurationTest.class);

    @Test
    void whenResolveGraylogProperties_thenPrintGraylogProperties() {
        GraylogEndpoint endpoint = new GraylogEndpoint();
        logger.info(endpoint.getHost());
        logger.info("{}", endpoint.getPort());
    }
}