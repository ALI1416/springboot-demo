package com.demo.util;

import com.demo.tool.ThreadPool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;

/**
 * <h1>邮件工具</h1>
 *
 * <p>
 * createDate 2020/11/11 11:11:11
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Component
@Slf4j
public class MailUtils {

    private static MailProperties mailProperties;
    private static JavaMailSender javaMailSender;

    /**
     * 静态注入
     */
    @Autowired
    public MailUtils(MailProperties mailProperties, JavaMailSender javaMailSender) {
        MailUtils.mailProperties = mailProperties;
        MailUtils.javaMailSender = javaMailSender;
    }

    /**
     * 发送普通邮件
     *
     * @param to      发送到
     * @param subject 主题
     * @param text    内容
     * @see SimpleMailMessage
     * @see ThreadPool#execute(Runnable command)
     */
    public static void sendMail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mailProperties.getUsername());
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        // 使用线程池(邮件发送速度比较慢)
        ThreadPool.execute(() -> javaMailSender.send(message));
    }

    /**
     * 发送HTML邮件
     *
     * @param to      发送到
     * @param subject 主题
     * @param text    内容
     * @see MimeMessage
     * @see MimeMessageHelper
     * @see ThreadPool#execute(Runnable command)
     */
    public static void sendMailHtml(String to, String subject, String text) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
        try {
            messageHelper.setFrom(mailProperties.getUsername());
            messageHelper.setTo(to);
            messageHelper.setSubject(subject);
            messageHelper.setText(text, true);
        } catch (Exception e) {
            log.error("发送HTML邮件异常", e);
        }
        // 使用线程池(邮件发送速度比较慢)
        ThreadPool.execute(() -> javaMailSender.send(messageHelper.getMimeMessage()));
    }

}
