package com.pat.soe.service.api;

public interface MailService {
    void sendEmail(String to, String subject, String text);
}
