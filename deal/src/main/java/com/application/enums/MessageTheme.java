package com.application.enums;

public enum MessageTheme {
    FINISH_REGISTRATION("finish-registration"),
    CREATE_DOCUMENTS("create-documents"),
    SEND_DOCUMENTS("send-documents"),
    SEND_SES("send-ses"),
    CREDIT_ISSUED("credit-issued"),
    APPLICATION_DENIED("application-denied");

    public final String topic;
    MessageTheme(String topic) {
        this.topic = topic;
    }
}
