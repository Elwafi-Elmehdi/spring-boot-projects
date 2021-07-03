package com.example.demo.service;

import com.example.demo.bean.User;
import com.example.demo.consts.EmailConsts;
import com.example.demo.consts.EmailConsts.*;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

import static com.example.demo.consts.EmailConsts.*;

@Service
public class EmailService {


    private Message createEmail(User user,String password) throws MessagingException {
        Message message = new MimeMessage(getEmailSession());
        message.setFrom(new InternetAddress(FROM_EMAIL));
        message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(user.getEmail(),false));
        message.setRecipients(Message.RecipientType.CC,InternetAddress.parse(CC_EMAIL,false));
        message.setSubject(EMAIL_SUBJECT);
        message.setText("Hello "+user.getFirstName()+"\n\n"+"Your New Account Password Is : "+password+"\n\n Support Team.");
        message.setSentDate(new Date());
        message.saveChanges();
        return message;
    }

    private Session getEmailSession() {
        Properties properties = System.getProperties();
        properties.put(SMTP_HOST,GMAIL_SMTP_SERVER);
        properties.put(SMTP_AUTH,true);
        properties.put(SMTP_PORT,DEFAULT_PORT);
        properties.put(SMTP_STARTTLS_ENABLE,true);
        properties.put(SMTP_STARTTLS_REQUEST,true);
        return Session.getInstance(properties,null);
    }
}
