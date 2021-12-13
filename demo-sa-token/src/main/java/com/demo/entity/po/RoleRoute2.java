package com.demo.entity.po;

import com.demo.base.EntityBase;
import lombok.Getter;
import lombok.Setter;

/**
 * <h1>角色-前端路由</h1>
 *
 * <p>
 * createDate 2021/11/26 09:37:54
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Getter
@Setter
public class RoleRoute2 extends EntityBase {

    /**
     * 角色id
     */
    private Long roleId;
    /**
     * 前端路由id
     */
    private Long routeId;

}
