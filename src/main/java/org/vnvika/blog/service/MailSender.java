package org.vnvika.blog.service;

public interface MailSender {
    public void send(String emailTo, String subject, String message);
}
