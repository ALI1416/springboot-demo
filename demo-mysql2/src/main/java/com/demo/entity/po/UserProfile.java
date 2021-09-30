package com.demo.entity.po;

import com.demo.base.EntityBase;
import lombok.Getter;
import lombok.Setter;

/**
 * <h1>UserProfile</h1>
 *
 * <p>
 * createDate 2021/09/09 15:41:33
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Getter
@Setter
public class UserProfile extends EntityBase {

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
     * 性别：0女1男，默认0
     */
    private Integer gender;
    /**
     * 出生年：默认2000
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
