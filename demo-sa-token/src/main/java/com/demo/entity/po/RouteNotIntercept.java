package com.demo.entity.po;

import com.demo.base.EntityBase;
import lombok.Getter;
import lombok.Setter;

/**
 * <h1>路由-不拦截</h1>
 *
 * <p>
 * createDate 2021/11/26 09:36:03
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Getter
@Setter
public class RouteNotIntercept extends EntityBase {

    /**
     * 路径
     */
    private String path;
    /**
     * 名称
     */
    private String name;
    /**
     * 顺序
     */
    private Integer seq;

}
