package com.demo.hutool.bean;

import cn.hutool.core.annotation.Alias;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <h1>SubPersonWithAlias</h1>
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
public class SubPersonWithAlias {

    @Alias("aliasSubName")
    private String subName;
    private Boolean slow;

}
