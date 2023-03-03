package com.pat.soe.service;

public interface MailService {
    void sendEmail(String to, String subject, String text);
}
