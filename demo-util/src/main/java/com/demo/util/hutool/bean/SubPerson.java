package com.demo.util.hutool.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <h1>SubPerson</h1>
 *
 * <p>
 * createDate 2022/03/09 13:51:12
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Getter
@Setter
@ToString
public class SubPerson extends Person {

    public static final String SUB_NAME = "TEST";

    private String openId;
    private String subName;
    private Boolean isSlow;

}
