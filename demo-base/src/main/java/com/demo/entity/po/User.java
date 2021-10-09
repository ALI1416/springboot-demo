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

    public String account;

    public String name;

    public int year;

    public Boolean gender;

    public Date date;

}
