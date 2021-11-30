package com.demo.entity.po;

import com.demo.base.ToStringBase;
import lombok.Getter;
import lombok.Setter;

/**
 * <h1>Car</h1>
 *
 * <p>
 * createDate 2021/11/23 17:53:42
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Getter
@Setter
public class Car extends ToStringBase {

    /**
     * 名字
     */
    private String name;

    /**
     * 颜色
     */
    private String color;

}
