package org.ansoya.drugs.drugs.controller;

import org.ansoya.drugs.drugs.entity.Result;
import org.ansoya.drugs.drugs.entity.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.internet.MimeMessage;

/**
 * Created by Administrator on 2016/11/7.
 */
@RestController
@RequestMapping("mail")
public class MailController {
    @Autowired
    JavaMailSender mailSender;

    @RequestMapping("sendemail")
    public Result<String> sendEmail(String mail) {
        try {
            final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
            final MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
            message.setFrom("ansoya@126.com");
            message.setTo("33432796@qq.com");
            message.setSubject("测试邮件主题");
            message.setText("测试邮件内容");
            this.mailSender.send(mimeMessage);
            return Results.newSuccessResult("发送成功");
        } catch (Exception ex) {
            return Results.newFailedResult("发送失败");
        }
    }
}
