package com.demo.tool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;

/**
 * <h1>邮件模板</h1>
 *
 * <p>
 * createDate 2020/11/11 11:11:11
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Component
public class MailTemp {

    /**
     * 日志实例
     */
    private static final Logger log = LoggerFactory.getLogger(MailTemp.class);

    /**
     * 邮件发送服务
     */
    private final JavaMailSender javaMailSender;
    /**
     * 用户名
     */
    private final String username;

    /**
     * 静态注入(自动注入)
     *
     * @param javaMailSender JavaMailSender
     * @param mailProperties MailProperties
     */
    public MailTemp(JavaMailSender javaMailSender, MailProperties mailProperties) {
        this.javaMailSender = javaMailSender;
        this.username = mailProperties.getUsername();
    }

    /**
     * 异步发送普通邮件
     *
     * @param to      收件人
     * @param subject 主题
     * @param text    内容
     */
    public void sendMailSync(String to, String subject, String text) {
        ThreadPool.execute(() -> sendMail(to, subject, text));
    }

    /**
     * 发送普通邮件
     *
     * @param to      收件人
     * @param subject 主题
     * @param text    内容
     */
    public void sendMail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(username);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
    }

    /**
     * 异步发送HTML邮件
     *
     * @param to      收件人
     * @param subject 主题
     * @param text    内容
     */
    public void sendMailHtmlSync(String to, String subject, String text) {
        ThreadPool.execute(() -> {
            try {
                sendMailHtml(to, subject, text);
            } catch (Exception e) {
                log.error("异步发送HTML邮件错误！", e);
            }
        });
    }

    /**
     * 发送HTML邮件
     *
     * @param to      收件人
     * @param subject 主题
     * @param text    内容
     */
    public void sendMailHtml(String to, String subject, String text) throws MessagingException {
        MimeMessageHelper message = new MimeMessageHelper(javaMailSender.createMimeMessage());
        message.setFrom(username);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text, true);
        javaMailSender.send(message.getMimeMessage());
    }

}
