package com.pat.soe.mail;

public interface MailService {
    void sendEmail(String to, String subject, String text);
}
