package com.javastudio.tutorial.logger;

import com.javastudio.tutorial.configuration.Configuration;


public class GraylogEndpoint {

    private Configuration cfg = new Configuration("graylog.properties");

    private final String host;
    private final int port;

    public GraylogEndpoint() {
        this.host = cfg.getProperty("graylog.gelf.host");
        this.port = Integer.parseInt(cfg.getProperty("graylog.gelf.port"));
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }
}
