package com.demo.entity.po;

import com.demo.base.ToStringBase;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * <h1>用户</h1>
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
public class User extends ToStringBase {

    /**
     * 账号
     */
    public String account;

    /**
     * 用户名
     */
    public String name;

    /**
     * 出生年
     */
    public int year;

    /**
     * 性别
     */
    public Boolean gender;

    /**
     * 日期
     */
    public Date date;

}
