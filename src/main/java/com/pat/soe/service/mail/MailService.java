package com.pat.soe.service.mail;

public interface MailService {
    void sendEmail(String to, String subject, String text);
}
