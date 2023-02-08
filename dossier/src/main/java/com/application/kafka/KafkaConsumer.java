package com.application.kafka;

import com.application.service.EmailMessageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class KafkaConsumer {
    private final EmailMessageService emailMessageService;

    @KafkaListener(id = "topic1", topics = "finish-registration")
    public void listen1(String in) {
        emailMessageService.sendEmail(in, "finish-registration");
    }

    @KafkaListener(id = "topic2", topics = "create-documents")
    public void listen2(String in) {
        emailMessageService.sendEmail(in, "create-documents");
    }

    @KafkaListener(id = "topic3", topics = "send-documents")
    public void listen3(String in) {
        emailMessageService.sendEmail(in, "send-documents");
    }

    @KafkaListener(id = "topic4", topics = "send-ses")
    public void listen4(String in) {
        emailMessageService.sendEmail(in, "send-ses");
    }

    @KafkaListener(id = "topic5", topics = "credit-issued")
    public void listen5(String in) {
        emailMessageService.sendEmail(in, "credit-issued");
    }

    @KafkaListener(id = "topic6", topics = "application-denied")
    public void listen6(String in) {
        emailMessageService.sendEmail(in, "application-denied");
    }


}
