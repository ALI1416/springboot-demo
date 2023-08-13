package com.demo.entity.pojo;

import com.demo.constant.ResultEnum;
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
    private final ResultEnum resultEnum;
    /**
     * 错误信息
     */
    private final String msg;

    /**
     * 构造函数
     */
    public GlobalException() {
        super();
        this.resultEnum = ResultEnum.ERROR;
        this.msg = null;
    }

    /**
     * 构造函数
     *
     * @param msg 错误信息
     */
    public GlobalException(String msg) {
        super(msg);
        this.resultEnum = ResultEnum.ERROR;
        this.msg = msg;
    }

    /**
     * 构造函数
     *
     * @param resultEnum 统一返回状态枚举
     */
    public GlobalException(ResultEnum resultEnum) {
        super(resultEnum.name() + "[" + resultEnum.getMsg() + "]");
        this.resultEnum = resultEnum;
        this.msg = null;
    }

    /**
     * 构造函数
     *
     * @param resultEnum 统一返回状态枚举
     * @param msg        错误信息
     */
    public GlobalException(ResultEnum resultEnum, String msg) {
        super(resultEnum.name() + "[" + resultEnum.getMsg() + "]:" + msg);
        this.resultEnum = resultEnum;
        this.msg = msg;
    }

}
