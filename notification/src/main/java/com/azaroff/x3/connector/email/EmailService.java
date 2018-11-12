package com.azaroff.x3.connector.email;

public interface EmailService {

    void sendSimpleMessage(String to, String subject, String text);
}
