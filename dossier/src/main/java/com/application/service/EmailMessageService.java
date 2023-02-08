package com.application.service;

import com.application.dto.MsgDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class EmailMessageService {
    private final JavaMailSender javaMailSender;

    public void sendEmail(String email, String theme) {
        log.info("Create Msg");
        MsgDTO msgDTO = createEmail(email, theme);
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(msgDTO.getAddress());
        msg.setSubject(msgDTO.getTitle());
        msg.setText(msgDTO.getBody());
        try {
            log.info("send" + theme + " msg to " + email);
            javaMailSender.send(msg);
            log.info("sending completed");
        } catch (Exception e) {
            log.warn("\n" +
                    "Email should be sent here, but Gmail is bad :s" +
                    "\nBut the method itself works, honestly!" +
                    "\nhere is the error message from gmail.com service: \n" + e.getMessage());
        }
    }

    public MsgDTO createEmail (String address, String theme) {
        String body = "Default body text";
        switch (theme) {
            case "finish-registration":
                body = "Text for finish-registration";
                break;
            case "create-documents":
                body = "Text for create-documents";
                break;
            case "send-documents":
                body = "Text for send-documents";
                break;
            case "send-ses":
                body = "Text for send-ses";
                break;
            case "credit-issued":
                body = "Text for credit-issued";
                break;
            case "application-denied":
                body = "Text for application-denied";
                break;
        }
        return new MsgDTO(address, theme, body);
    }
}
