package com.demo.entity.pojo;

import com.demo.base.ToStringBase;
import com.demo.constant.ResultCodeEnum;
import lombok.Getter;
import lombok.Setter;

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
@Setter
public class Result extends ToStringBase {

    /**
     * 是否成功(状态码为0时成功)
     */
    private boolean ok;
    /**
     * 状态码
     */
    private int code;
    /**
     * 状态信息
     */
    private String msg;
    /**
     * 数据
     */
    private Object data;

    public Result() {

    }

    public Result(ResultCodeEnum resultCodeEnum, Object data) {
        this.code = resultCodeEnum.getCode();
        this.msg = resultCodeEnum.getMsg();
        this.data = data;
        this.ok = (this.code == ResultCodeEnum.OK.getCode());
    }

    public Result(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.ok = (this.code == ResultCodeEnum.OK.getCode());
    }

    /**
     * <h1>成功</h1>
     * <p>不携带数据</p>
     */
    public static Result o() {
        return new Result(ResultCodeEnum.OK, null);
    }

    /**
     * <h1>成功</h1>
     *
     * @param data 数据
     */
    public static Result o(Object data) {
        return new Result(ResultCodeEnum.OK, data);
    }

    /**
     * <h1>成功</h1>
     *
     * @param msg  指定状态信息
     * @param data 数据
     */
    public static Result o(String msg, Object data) {
        return new Result(ResultCodeEnum.OK.getCode(), msg, data);
    }

    /**
     * <h1>错误</h1>
     * <p>未知错误，不携带数据</p>
     */
    public static Result e() {
        return new Result(ResultCodeEnum.ERROR, null);
    }

    /**
     * <h1>错误</h1>
     * <p>不携带数据</p>
     *
     * @param resultCodeEnum 返回结果状态枚举类
     * @see ResultCodeEnum
     */
    public static Result e(ResultCodeEnum resultCodeEnum) {
        return new Result(resultCodeEnum, null);
    }

    /**
     * <h1>错误</h1>
     *
     * @param resultCodeEnum 返回结果状态枚举类
     * @param data           数据
     * @see ResultCodeEnum
     */
    public static Result e(ResultCodeEnum resultCodeEnum, Object data) {
        return new Result(resultCodeEnum, data);
    }

    /**
     * <h1>错误</h1>
     *
     * @param resultCodeEnum 返回结果状态枚举类
     * @param msg            指定状态信息
     * @param data           数据
     * @see ResultCodeEnum
     */
    public static Result e(ResultCodeEnum resultCodeEnum, String msg, Object data) {
        return new Result(resultCodeEnum.getCode(), msg, data);
    }

}
