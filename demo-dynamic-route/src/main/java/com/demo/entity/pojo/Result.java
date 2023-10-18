package com.demo.entity.pojo;

import com.demo.base.ToStringBase;
import com.demo.constant.ResultEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * <h1>统一返回</h1>
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
@Schema(description = "统一返回")
public class Result<T> extends ToStringBase {

    /**
     * 是否成功(状态码为0时成功)
     */
    @Schema(description = "是否成功(状态码为0时成功)")
    private boolean ok;
    /**
     * 状态码
     *
     * @see ResultEnum
     */
    @Schema(description = "状态码")
    private int code;
    /**
     * 状态信息
     */
    @Schema(description = "状态信息")
    private String msg;
    /**
     * 数据
     */
    @Schema(description = "数据")
    private T data;

    /**
     * 构造函数
     */
    public Result() {
    }

    /**
     * 构造函数
     *
     * @param resultEnum 统一返回状态枚举
     * @param data       数据
     */
    public Result(ResultEnum resultEnum, T data) {
        this.code = resultEnum.getCode();
        this.msg = resultEnum.getMsg();
        this.data = data;
        this.ok = (this.code == ResultEnum.OK.getCode());
    }

    /**
     * 构造函数
     *
     * @param code 状态码
     * @param msg  状态信息
     * @param data 数据
     */
    public Result(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.ok = (this.code == ResultEnum.OK.getCode());
    }

    /**
     * 成功
     */
    public static Result o() {
        return new Result(ResultEnum.OK, null);
    }

    /**
     * 成功
     *
     * @param data 数据
     */
    public static <T> Result<T> o(T data) {
        return new Result<>(ResultEnum.OK, data);
    }

    /**
     * 成功
     *
     * @param msg  状态信息
     * @param data 数据
     */
    public static <T> Result<T> o(String msg, T data) {
        return new Result<>(ResultEnum.OK.getCode(), msg, data);
    }

    /**
     * 未知错误
     */
    public static Result e() {
        return new Result(ResultEnum.ERROR, null);
    }

    /**
     * 错误
     *
     * @param resultEnum 统一返回状态枚举
     */
    public static Result e(ResultEnum resultEnum) {
        return new Result(resultEnum, null);
    }

    /**
     * 错误
     *
     * @param resultEnum 统一返回状态枚举
     * @param data       数据
     */
    public static <T> Result<T> e(ResultEnum resultEnum, T data) {
        return new Result<>(resultEnum, data);
    }

    /**
     * 错误
     *
     * @param resultEnum 统一返回状态枚举
     * @param msg        状态信息
     * @param data       数据
     */
    public static <T> Result<T> e(ResultEnum resultEnum, String msg, T data) {
        return new Result<>(resultEnum.getCode(), msg, data);
    }

}
