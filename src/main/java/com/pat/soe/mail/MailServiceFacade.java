package com.pat.soe.mail;

public interface MailServiceFacade {
    void sendEmail(String to, String subject, String text);
}
