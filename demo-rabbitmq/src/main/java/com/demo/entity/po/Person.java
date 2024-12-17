package com.demo.entity.po;

import com.demo.base.ToStringBase;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

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
    private String name;
    /**
     * 出生年
     */
    private int year;
    /**
     * 性别
     */
    private Boolean gender;
    /**
     * 日期
     */
    private Long date;
    /**
     * 地址
     */
    private String address;
    /**
     * 车
     */
    private List<Car> cars;
    /**
     * 其他信息
     */
    private Map<String, String> other;

}
