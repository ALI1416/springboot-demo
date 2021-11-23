package com.demo.entity.po;

import com.demo.base.ToStringBase;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

/**
 * <h1>Person</h1>
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
public class Person extends ToStringBase {

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

    /**
     * 地址
     */
    public String address;

    /**
     * 车
     */
    public List<Car> cars = new ArrayList<Car>();

    /**
     * 其他信息
     */
    public Map<String, String> other = new HashMap<String, String>();

}
