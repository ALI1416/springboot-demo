package com.demo.exception;

/**
 * <h1>无权限异常</h1>
 *
 * <p>
 * createDate 2023/08/10 10:51:33
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class NotPermissionException extends RuntimeException {

    /**
     * 无权限异常
     */
    public NotPermissionException() {
        super();
    }

    /**
     * 无权限异常
     *
     * @param message 信息
     */
    public NotPermissionException(String message) {
        super(message);
    }

}
