package com.demo.entity.po;

import com.demo.base.ToStringBase;
import lombok.Getter;
import lombok.Setter;

/**
 * <h1>用户表持久化对象</h1>
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
public class User extends ToStringBase {

    /**
     * id
     */
    private Long id;
    /**
     * 账号
     */
    private String account;
    /**
     * 密码
     */
    private String pwd;
    /**
     * 用户名
     */
    private String name;
    /**
     * 性别
     */
    private Integer gender;
    /**
     * 出生年
     */
    private Integer year;
    /**
     * 个人简介
     */
    private String profile;
    /**
     * 备注
     */
    private String comment;

}
