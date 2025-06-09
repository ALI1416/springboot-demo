package com.demo.entity.po;

import lombok.Getter;
import lombok.Setter;

/**
 * <h1>用户</h1>
 *
 * <p>
 * createDate 2021/10/09 15:20:48
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Getter
@Setter
public class User2 extends User {

    /**
     * 账号：唯一
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
     * 备注
     */
    private String comment;

}
