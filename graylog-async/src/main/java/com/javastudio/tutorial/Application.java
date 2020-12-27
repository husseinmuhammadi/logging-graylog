package com.javastudio.tutorial;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Application {

    private static Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        logger.info("This is an logging demo to graylog in asynchronous");
    }
}
