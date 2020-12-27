package com.javastudio.tutorial.logger;

import org.graylog2.gelfclient.GelfMessageLevel;

public class GraylogMessage {
    private final GelfMessageLevel level;
    private final String shortMessage;
    private final String fullMessage;

    private GraylogMessage(Builder builder) {
        this.level = builder.level;
        this.shortMessage=builder.shortMessage;
        this.fullMessage=builder.fullMessage;
    }

    public GelfMessageLevel getLevel() {
        return level;
    }

    public String getShortMessage() {
        return shortMessage;
    }

    public String getFullMessage() {
        return fullMessage;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        GelfMessageLevel level;
        String shortMessage;
        String fullMessage;

        public Builder level(GelfMessageLevel level) {
            this.level = level;
            return this;
        }

        public Builder shortMessage(String shortMessage) {
            this.shortMessage = shortMessage;
            return this;
        }

        public Builder fullMessage(String fullMessage) {
            this.fullMessage = fullMessage;
            return this;
        }

        public GraylogMessage build(){
            GraylogMessage graylogMessage = new GraylogMessage(this);
            validateGraylogMessage();
            return graylogMessage;
        }

        private void validateGraylogMessage() {

        }
    }
}
