package com.demo.constant;

import lombok.Getter;

/**
 * <h1>结果状态码</h1>
 *
 * <p>
 * createDate 2020/11/11 11:11:11
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Getter
public enum ResultCode {

    /* ==================== 0 成功 ==================== */
    /* -------------------- 0 成功 --------------------*/
    /**
     * 0 成功
     */
    OK(0, "成功"),

    /* ==================== 100-999 通用错误 ==================== */
    /* -------------------- 100-109 系统错误 --------------------*/
    /**
     * 100 系统内部错误
     */
    SYSTEM_INNER_ERROR(100, "系统内部错误"),

    /* -------------------- 110-119 未找到错误 --------------------*/
    /**
     * 110 页面未找到
     */
    PAGE_NOT_FOUND(110, "页面未找到"),

    /* -------------------- 120-129 不支持错误 --------------------*/
    /**
     * 120 请求方法不支持
     */
    REQUEST_METHOD_NOT_SUPPORTED(120, "请求方法不支持"),
    /**
     * 121 媒体类型不支持
     */
    MEDIA_TYPE_NOT_SUPPORTED(121, "媒体类型不支持"),

    /* -------------------- 130-139 参数错误 --------------------*/
    /**
     * 130 请求参数错误
     */
    PARAM_ERROR(130, "请求参数错误"),

    /* ==================== 1000-9999 自定义错误 ==================== */
    /* -------------------- 1000-1099 账号错误 --------------------*/
    /**
     * 1000 账号不存在或密码错误
     */
    LOGIN_ERROR(1000, "账号不存在或密码错误"),
    /**
     * 1001 账号未登录或登录已过期
     */
    NOT_LOGIN(1001, "账号未登录或登录已过期"),
    /**
     * 1002 账号没有权限访问
     */
    NOT_PERMISSION(1002, "账号没有权限访问"),
    /**
     * 1003 账号不可用
     */
    ACCOUNT_DISABLE(1003, "账号不可用"),
    /**
     * 1004 密码错误
     */
    PASSWORD_ERROR(1004, "密码错误"),
    /**
     * 1005 权限不足
     */
    INSUFFICIENT_PERMISSION(1005, "权限不足"),
    /**
     * 1006 账号已存在
     */
    ACCOUNT_EXIST(1006, "账号已存在"),
    /**
     * 1008 用户不存在
     */
    USER_NOT_EXIST(1008, "用户不存在"),
    /**
     * 1009 用户未登录
     */
    USER_NOT_LOGIN(1009, "用户未登录"),
    /**
     * 1010 角色不存在
     */
    ROLE_NOT_EXIST(1010, "角色不存在"),

    /* ==================== -1 未知错误 ==================== */
    /* -------------------- -1 未知错误 --------------------*/
    /**
     * -1 未知错误
     */
    ERROR(-1, "未知错误");

    /**
     * 状态码
     */
    private final int code;
    /**
     * 详细信息
     */
    private final String message;

    /**
     * 构造状态码
     *
     * @param code    状态码
     * @param message 详细信息
     */
    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

}