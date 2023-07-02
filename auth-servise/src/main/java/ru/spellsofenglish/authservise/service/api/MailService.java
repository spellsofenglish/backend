package ru.spellsofenglish.authservise.service.api;

public interface MailService {
    void sendEmail(String to, String subject, String text);
}
