package com.demo.entity.vo;

import com.demo.entity.po.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * <h1>RoleVo</h1>
 *
 * <p>
 * createDate 2021/11/29 15:16:50
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Getter
@Setter
public class RoleVo extends Role {

    /**
     * 路由
     */
    private List<RouteVo> routes;

    /**
     * 路由id
     */
    private List<Long> routeIds;

}
