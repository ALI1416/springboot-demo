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

    /* ==================== 用户1000-1999 ==================== */
    /**
     * 1000 登录失败，账号不存在或密码错误
     */
    LOGIN_FAIL(1000, "登录失败，账号不存在或密码错误"),
    /**
     * 1001 账号未登录或登录已过期
     */
    NOT_LOGIN(1001, "账号未登录或登录已过期"),
    /**
     * 1002 账号没有权限访问
     */
    NOT_PERMISSION(1002, "账号没有权限访问"),
    /**
     * 1003 注册失败，账号已存在
     */
    REGISTER_FAIL(1003, "注册失败，账号已存在"),
    /**
     * 1004 密码错误
     */
    PASSWORD_ERROR(1004, "密码错误"),
    /**
     * 1005 权限不足
     */
    INSUFFICIENT_PERMISSION(1005, "权限不足"),
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