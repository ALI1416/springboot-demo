package com.demo.entity.pojo;

import com.demo.base.ToStringBase;
import com.demo.constant.ResultCode;
import lombok.Getter;
import lombok.Setter;

/**
 * <h1>结果</h1>
 *
 * <p>
 * createDate 2020/11/11 11:11:11
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Getter
@Setter
public class Result<T> extends ToStringBase {

    /**
     * 是否成功(状态码为0时成功)
     */
    private boolean ok;
    /**
     * 状态码
     *
     * @see ResultCode
     */
    private int code;
    /**
     * 详细信息
     */
    private String message;
    /**
     * 数据
     */
    private T data;

    /**
     * 构造结果
     */
    public Result() {
    }

    /**
     * 构造结果
     *
     * @param code    状态码
     * @param message 详细信息
     * @param data    数据
     */
    public Result(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.ok = (this.code == ResultCode.OK.getCode());
    }

    /**
     * 构造结果
     *
     * @param resultCode 状态码
     * @param data       数据
     */
    public Result(ResultCode resultCode, T data) {
        this(resultCode.getCode(), resultCode.getMessage(), data);
    }

    /**
     * 成功
     */
    public static Result o() {
        return new Result(ResultCode.OK, null);
    }

    /**
     * 成功
     *
     * @param data 数据
     */
    public static <T> Result<T> o(T data) {
        return new Result<>(ResultCode.OK, data);
    }

    /**
     * 成功
     *
     * @param message 详细信息
     * @param data    数据
     */
    public static <T> Result<T> o(String message, T data) {
        return new Result<>(ResultCode.OK.getCode(), message, data);
    }

    /**
     * 错误
     */
    public static Result e() {
        return new Result(ResultCode.ERROR, null);
    }

    /**
     * 错误
     *
     * @param resultCode 状态码
     */
    public static Result e(ResultCode resultCode) {
        return new Result(resultCode, null);
    }

    /**
     * 错误
     *
     * @param resultCode 状态码
     * @param data       数据
     */
    public static <T> Result<T> e(ResultCode resultCode, T data) {
        return new Result<>(resultCode, data);
    }

    /**
     * 错误
     *
     * @param resultCode 状态码
     * @param message    详细信息
     * @param data       数据
     */
    public static <T> Result<T> e(ResultCode resultCode, String message, T data) {
        return new Result<>(resultCode.getCode(), message, data);
    }

}
