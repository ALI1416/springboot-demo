package cn.z.mail;

/**
 * <h1>邮件异常</h1>
 *
 * <p>
 * createDate 2023/09/15 15:17:12
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class MailException extends RuntimeException {

    /**
     * 邮件异常
     */
    public MailException() {
        super();
    }

    /**
     * 邮件异常
     *
     * @param message 信息
     */
    public MailException(String message) {
        super(message);
    }

    /**
     * 邮件异常
     *
     * @param message 信息
     * @param cause   异常
     */
    public MailException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 邮件异常
     *
     * @param cause 异常
     */
    public MailException(Throwable cause) {
        super(cause);
    }

}
