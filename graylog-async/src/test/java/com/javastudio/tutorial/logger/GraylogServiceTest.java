package com.javastudio.tutorial.logger;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;

class GraylogServiceTest {

    private Logger logger = LoggerFactory.getLogger(GraylogServiceTest.class);

    @Test
    void whenInstantiateGrayLogService_thenServerHostNameIsComputerName() {
        GraylogService graylogService = GraylogService.TCP;
        logger.info(graylogService.getServerHostName());
    }

    @Test
    void when() {
        GraylogService graylogService = GraylogService.TCP;
        graylogService.init();
        for (int i = 0; i < 1000000; i++) {
            graylogService.info(MessageFormat.format("Message #{0}", i), MessageFormat.format("This is a long message {0}", i));
        }
        sleep(5000);
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}