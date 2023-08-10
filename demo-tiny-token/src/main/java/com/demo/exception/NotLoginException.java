package com.demo.exception;

/**
 * <h1>未登录异常</h1>
 *
 * <p>
 * createDate 2023/08/10 10:51:33
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class NotLoginException extends RuntimeException {

    /**
     * 未登录异常
     */
    public NotLoginException() {
        super();
    }

    /**
     * 未登录异常
     *
     * @param message 信息
     */
    public NotLoginException(String message) {
        super(message);
    }

}
