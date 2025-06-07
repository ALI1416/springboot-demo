package com.demo.entity.pojo;

import com.demo.constant.ResultCode;
import lombok.Getter;

/**
 * <h1>全局异常</h1>
 *
 * <p>
 * createDate 2023/08/10 10:51:33
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Getter
public class GlobalException extends RuntimeException {

    /**
     * 统一返回状态枚举
     */
    private final ResultCode resultCode;

    /**
     * 全局异常
     */
    public GlobalException() {
        super();
        this.resultCode = ResultCode.ERROR;
    }

    /**
     * 全局异常
     *
     * @param message 详细信息
     */
    public GlobalException(String message) {
        super(message);
        this.resultCode = ResultCode.ERROR;
    }

    /**
     * 全局异常
     *
     * @param cause 原因
     */
    public GlobalException(Throwable cause) {
        super(cause);
        this.resultCode = ResultCode.ERROR;
    }

    /**
     * 全局异常
     *
     * @param resultCode 统一返回状态枚举
     */
    public GlobalException(ResultCode resultCode) {
        super(resultCode.getMessage());
        this.resultCode = resultCode;
    }

    /**
     * 全局异常
     *
     * @param message 详细信息
     * @param cause   原因
     */
    public GlobalException(String message, Throwable cause) {
        super(message, cause);
        this.resultCode = ResultCode.ERROR;
    }

    /**
     * 全局异常
     *
     * @param resultCode 统一返回状态枚举
     * @param message    详细信息
     */
    public GlobalException(ResultCode resultCode, String message) {
        super(message);
        this.resultCode = resultCode;
    }

    /**
     * 全局异常
     *
     * @param message            详细信息
     * @param cause              原因
     * @param enableSuppression  是否启用抑制
     * @param writableStackTrace 堆栈跟踪是否为可写的
     */
    protected GlobalException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.resultCode = ResultCode.ERROR;
    }

}
