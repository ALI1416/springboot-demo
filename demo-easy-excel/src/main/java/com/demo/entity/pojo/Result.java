package com.demo.entity.pojo;

import com.demo.base.ToStringBase;
import com.demo.constant.ResultCodeEnum;
import lombok.Getter;

/**
 * <h1>返回结果实体类</h1>
 *
 * <p>
 * createDate 2020/11/11 11:11:11
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Getter
public class Result extends ToStringBase {

    /**
     * 成功(状态码为0)
     */
    private final boolean ok;
    /**
     * 状态码
     */
    private final int code;
    /**
     * 状态信息
     */
    private final String msg;
    /**
     * 数据
     */
    private final Object data;

    /**
     * 构造函数
     */
    private Result(ResultCodeEnum resultCodeEnum, Object data) {
        this.code = resultCodeEnum.getCode();
        this.msg = resultCodeEnum.getMsg();
        this.data = data;
        this.ok = (this.code == ResultCodeEnum.OK.getCode());
    }

    /**
     * 构造函数
     */
    private Result(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.ok = (this.code == ResultCodeEnum.OK.getCode());
    }

    /**
     * 成功
     */
    public static Result o() {
        return new Result(ResultCodeEnum.OK, null);
    }

    /**
     * 成功
     */
    public static Result o(Object data) {
        return new Result(ResultCodeEnum.OK, data);
    }

    /**
     * 成功
     */
    public static Result o(String msg) {
        return new Result(ResultCodeEnum.OK.getCode(), msg, null);
    }

    /**
     * 成功
     */
    public static Result o(String msg, Object data) {
        return new Result(ResultCodeEnum.OK.getCode(), msg, data);
    }

    /**
     * 错误
     */
    public static Result e() {
        return new Result(ResultCodeEnum.ERROR, null);
    }

    /**
     * 错误
     */
    public static Result e(ResultCodeEnum resultCodeEnum) {
        return new Result(resultCodeEnum, null);
    }

    /**
     * 错误
     */
    public static Result e(ResultCodeEnum resultCodeEnum, Object data) {
        return new Result(resultCodeEnum, data);
    }

    /**
     * 错误
     */
    public static Result e(ResultCodeEnum resultCodeEnum, String msg) {
        return new Result(resultCodeEnum.getCode(), msg, null);
    }

    /**
     * 错误
     */
    public static Result e(ResultCodeEnum resultCodeEnum, String msg, Object data) {
        return new Result(resultCodeEnum.getCode(), msg, data);
    }

}
