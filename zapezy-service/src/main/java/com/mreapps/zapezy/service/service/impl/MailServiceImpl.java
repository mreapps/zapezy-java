package com.mreapps.zapezy.service.service.impl;

import com.mreapps.zapezy.service.service.MailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailServiceImpl implements MailService
{
    @Value("${mail.smtphost}")
    private String host;

    @Value("${mail.port}")
    private int port;

    @Value("${mail.username}")
    private String username;

    @Value("${mail.password}")
    private String password;

    @Override
    public void sendMail(String recepient, String subject, String message)
    {
        try
        {
            JavaMailSender mailSender = createMailSender();
            mailSender.send(createMessage(recepient, subject, message, mailSender));

            // TODO log mails sent
        }
        catch (Exception e)
        {
            // TODO store in batch to enable resend later
        }
    }

    private MimeMessage createMessage(String recepient, String subject, String messageBody, JavaMailSender mailSender) throws MessagingException
    {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setFrom("do_not_reply@zapezy.com");
        helper.setSubject(subject);
        helper.setTo(recepient);
        helper.setText(messageBody);

        return mimeMessage;
    }

    private JavaMailSender createMailSender()
    {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(username);
        mailSender.setPassword(password);

        return mailSender;
    }
}
