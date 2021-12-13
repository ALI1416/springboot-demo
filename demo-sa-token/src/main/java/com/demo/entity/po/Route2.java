package com.demo.entity.po;

import com.demo.base.EntityBase;
import lombok.Getter;
import lombok.Setter;

/**
 * <h1>前端路由</h1>
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
public class Route2 extends EntityBase {

    /**
     * 路径
     */
    private String path;
    /**
     * 名称
     */
    private String name;
    /**
     * 父id
     */
    private Long parentId;
    /**
     * 顺序
     */
    private Integer seq;
    /**
     * 组件名称或路径
     */
    private String component;
    /**
     * 重定向
     */
    private String redirect;
    /**
     * 自定义内容
     */
    private String meta;

}
