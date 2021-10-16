package com.yutao.cron.service;

import com.yutao.cron.mail.GreetMailTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.UnsupportedEncodingException;

/**
 * @author :RETURN
 * @date :2021/10/12 23:34
 */
@Slf4j
@Component
public class MainService {

    @Resource
    private JavaMailSender mailSender;
    @Resource
    private GreetMailTemplate mailTemplate;

    @Scheduled(cron = "0 30 8 * * ?")
    public void sendMail(){
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        log.info("发送邮件中----------->");
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true);
            String template = mailTemplate.create();
            String nickName = MimeUtility.encodeText("亲爱的");
            mimeMessageHelper.setText(template,true);
            mimeMessageHelper.setFrom(nickName+" <18398858464@163.com>");

            mimeMessageHelper.setTo("1627850021@qq.com");
            mimeMessageHelper.setSubject("元气提醒");
            mailSender.send(mimeMessage);
            log.info("<-----------邮件发送成功");
        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }
}
