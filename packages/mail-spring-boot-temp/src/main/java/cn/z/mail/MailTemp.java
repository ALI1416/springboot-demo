package cn.z.mail;

import cn.z.tool.ThreadPool;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

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
public class MailTemp {

    /**
     * 邮件发送服务
     */
    private final JavaMailSender javaMailSender;
    /**
     * 用户名
     */
    private final String username;

    /**
     * 构造函数(自动注入)
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
        ThreadPool.execute(() -> sendMailHtml(to, subject, text));
    }

    /**
     * 发送HTML邮件
     *
     * @param to      收件人
     * @param subject 主题
     * @param text    内容
     */
    public void sendMailHtml(String to, String subject, String text) {
        MimeMessageHelper message = new MimeMessageHelper(javaMailSender.createMimeMessage());
        try {
            message.setFrom(username);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text, true);
        } catch (Exception e) {
            throw new MailException(e);
        }
        javaMailSender.send(message.getMimeMessage());
    }

}
