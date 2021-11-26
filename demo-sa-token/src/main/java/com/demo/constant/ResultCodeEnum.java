package com.demo.constant;

import lombok.Getter;

/**
 * <h1>返回结果状态枚举类</h1>
 *
 * <p>
 * createDate 2020/11/11 11:11:11
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Getter
public enum ResultCodeEnum {

    /* ==================== 成功0 ==================== */
    /**
     * 成功0
     */
    OK(0, "成功"),

    /* ==================== 通用100-999 ==================== */
    /**
     * 100 系统繁忙
     */
    SYSTEM_INNER_ERROR(100, "系统繁忙"),
    /**
     * 101 请求地址找不到
     */
    NOT_FOUND(101, "请求地址找不到"),
    /**
     * 102 请求方法不支持
     */
    NOT_SUPPORTED(102, "请求方法不支持"),
    /**
     * 103 请求参数错误
     */
    PARAM_IS_ERROR(103, "请求参数错误"),

    /* ==================== 未知错误-1 ==================== */
    /**
     * -1 未知错误
     */
    ERROR(-1, "未知错误");

    /* ==================== 枚举字段和构造器 ==================== */
    /**
     * 状态码
     */
    private final int code;
    /**
     * 状态信息
     */
    private final String msg;

    /**
     * 构造函数
     *
     * @param code 状态码
     * @param msg  信息
     */
    ResultCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}