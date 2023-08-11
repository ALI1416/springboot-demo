package com.demo.entity.pojo;

import com.demo.constant.ResultCodeEnum;
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
     * ResultCodeEnum
     */
    private final ResultCodeEnum resultCodeEnum;
    /**
     * 信息
     */
    private final String msg;

    /**
     * 全局异常
     */
    public GlobalException() {
        super();
        this.resultCodeEnum = ResultCodeEnum.ERROR;
        this.msg = null;
    }

    /**
     * 全局异常
     *
     * @param msg 信息
     */
    public GlobalException(String msg) {
        super(msg);
        this.resultCodeEnum = ResultCodeEnum.ERROR;
        this.msg = msg;
    }

    /**
     * 全局异常
     *
     * @param resultCodeEnum ResultCodeEnum
     */
    public GlobalException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.name() + "[" + resultCodeEnum.getMsg() + "]");
        this.resultCodeEnum = resultCodeEnum;
        this.msg = null;
    }

    /**
     * 全局异常
     *
     * @param resultCodeEnum ResultCodeEnum
     * @param msg            信息
     */
    public GlobalException(ResultCodeEnum resultCodeEnum, String msg) {
        super(resultCodeEnum.name() + "[" + resultCodeEnum.getMsg() + "]:" + msg);
        this.resultCodeEnum = resultCodeEnum;
        this.msg = msg;
    }

}
