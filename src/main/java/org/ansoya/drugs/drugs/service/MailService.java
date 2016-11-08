package org.ansoya.drugs.drugs.service;

import org.ansoya.drugs.drugs.entity.Result;
import org.ansoya.drugs.drugs.entity.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

/**
 * Created by Administrator on 2016/11/7.
 */
@Service
public class MailService {
    @Autowired
    JavaMailSender mailSender;

    public void sendEmail(String from, String to, String subject, String content) {
        try {
            final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
            final MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
            message.setFrom(from);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(content);
            this.mailSender.send(mimeMessage);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
