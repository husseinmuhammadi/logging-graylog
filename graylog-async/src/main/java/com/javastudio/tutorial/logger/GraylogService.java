package com.javastudio.tutorial.logger;

import org.graylog2.gelfclient.*;
import org.graylog2.gelfclient.transport.GelfTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

public enum GraylogService {
    TCP(GelfTransports.TCP),
    UDP(GelfTransports.UDP);

    private final Logger logger = LoggerFactory.getLogger(GraylogService.class);

    private final BlockingQueue<GraylogMessage> blockingQueue = new LinkedBlockingDeque<>();
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    private final GelfTransport transport;
    private final String serverHostName;
    private final String facility = "TEST";

    GraylogService(GelfTransports transports) {
        GraylogEndpoint endpoint = new GraylogEndpoint();
        final GelfConfiguration cfg = new GelfConfiguration(
                endpoint.getHost(),
                endpoint.getPort()
        )
                .transport(transports)
                .queueSize(512)
                .connectTimeout(5000)
                .reconnectDelay(1000)
                .tcpNoDelay(true)
                .sendBufferSize(32768);

        transport = GelfTransports.create(cfg);
        this.serverHostName = resolveServerHostName();
    }

    public void init() {
        executorService.submit(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    GraylogMessage graylogMessage = getBlockingQueue().take();
                    final GelfMessage message = new GelfMessageBuilder(graylogMessage.getShortMessage(), getServerHostName())
                            .fullMessage(graylogMessage.getFullMessage())
                            .level(graylogMessage.getLevel())
                            .timestamp(System.currentTimeMillis())
                            .additionalField("facility", facility)
                            .build();
                    logger.info("Sending message to graylog {}", graylogMessage.getShortMessage());
                    transport.send(message);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void stop() {
        try {
            if (transport != null) {
                transport.stop();
            }
            executorService.shutdownNow();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addLogToQueue(GraylogMessage graylogMessage) {
        getBlockingQueue().add(graylogMessage);
    }

    public void info(String heading, String message) {
        getBlockingQueue().add(new GraylogMessage.Builder()
                .level(GelfMessageLevel.INFO)
                .shortMessage(heading)
                .fullMessage(message)
                .build());
    }

    public void warn(String heading, String message) {
        getBlockingQueue().add(new GraylogMessage.Builder()
                .level(GelfMessageLevel.WARNING)
                .shortMessage(heading)
                .fullMessage(message)
                .build());
    }

    public void error(String heading, String message) {
        getBlockingQueue().add(new GraylogMessage.Builder()
                .level(GelfMessageLevel.ERROR)
                .shortMessage(heading)
                .fullMessage(message)
                .build());
    }

    private String resolveServerHostName() {
        StringBuilder builder = new StringBuilder();
        try {
            Process hostname = Runtime.getRuntime().exec("hostname");
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(hostname.getInputStream()));
            String s;
            while ((s = stdInput.readLine()) != null) {
                builder.append(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    public String getServerHostName() {
        return serverHostName;
    }

    public BlockingQueue<GraylogMessage> getBlockingQueue() {
        return blockingQueue;
    }
}
