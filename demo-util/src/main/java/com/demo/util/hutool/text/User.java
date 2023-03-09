package com.demo.util.hutool.text;

import cn.hutool.core.annotation.Alias;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <h1>用户</h1>
 *
 * <p>
 * createDate 2022/03/10 10:31:36
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Getter
@Setter
@ToString
public class User {

    // 字段别名
    @Alias("姓名")
    private String name;
    private String gender;
    private Integer year;

}
