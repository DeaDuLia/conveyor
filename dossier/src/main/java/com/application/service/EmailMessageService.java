package com.application.service;

import com.application.dto.MsgDTO;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.mail.*;
import javax.mail.internet.*;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailMessageService {
    @Value("${mail.user}")
    private String user;
    @Value("${mail.pass}")
    private String password;
    @Value("${mail.smtp.host}")
    private String hostSMTP;
    @Value("${mail.smtp.port}")
    private Integer port;
    @Value("${mail.smtp.ssl.enable}")
    private String ssl_enable;
    @Value("${mail.smtp.auth}")
    private String auth;

    public void sendEmail(String email, String theme) {
        log.info("Create Msg");
        MsgDTO msgDTO = createEmail(email, theme);
        try {
            log.info("send '" + theme + "' msg to " + email);
            sendmail(msgDTO);
            log.info("sending completed");
        } catch (Exception e) {
            log.warn("Can't send message: \n" + e.getMessage());
        }
    }

    public MsgDTO createEmail (String address, String theme) {
        String body = "Default body text";
        //Для простого сообщения можно было бы использовать String body = "Text for " + theme;
        //Но оператор switch используется на случай, если текст сообщения будет более осоззанным и сложным
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

    private void sendmail(MsgDTO msgDTO) throws MessagingException, IOException {
        String to = msgDTO.getAddress();

        Properties props = new Properties();
        props.put("mail.smtp.host", hostSMTP);
        props.put("mail.smtp.ssl.enable", ssl_enable);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.auth", auth);
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        });
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(user));
        InternetAddress[] addresses = {new InternetAddress(to)};
        msg.setRecipients(Message.RecipientType.TO, addresses);
        msg.setSubject(msgDTO.getTitle());
        msg.setSentDate(new Date());
        msg.setText(msgDTO.getBody());
        Transport.send(msg);
    }
}
