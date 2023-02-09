package com.application.service;

import com.application.dto.MsgDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EmailMessageServiceTest {

    @InjectMocks
    private EmailMessageService emailMessageService;

    @ParameterizedTest
    @ValueSource(strings = {"finish-registration", "create-documents",
            "send-documents", "send-ses", "credit-issued", "application-denied"})
    void createEmail(String theme) {
        String address = "name@mail.ru";
        MsgDTO email = emailMessageService.createEmail(address, theme);
        assertEquals(address, email.getAddress());
        assertEquals(theme, email.getTitle());
        assertEquals("Text for " + theme, email.getBody());
    }
}