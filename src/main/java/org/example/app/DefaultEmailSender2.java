package org.example.app;

import org.example.infrastructure.annotation.Component;
import org.example.infrastructure.annotation.Env;
import org.example.infrastructure.annotation.Log;
import org.example.infrastructure.annotation.Property;

@Component
public class DefaultEmailSender2 implements EmailSender {

    @Property
    private String senderEmail;

    @Env("USER")
    public static String user;

    @Log
    @Override
    public void send(String to, String subject, String body) {
        System.out.printf(
                "Sending email from " + user + " to %s. Subject: %s. Body: %s\n",
                to,
                subject,
                body
        );
    }
}
