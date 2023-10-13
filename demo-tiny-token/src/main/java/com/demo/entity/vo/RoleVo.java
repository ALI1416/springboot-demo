package com.demo.entity.vo;

import com.demo.entity.po.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * <h1>角色</h1>
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
     * 路由列表
     */
    private List<RouteVo> routeList;
    /**
     * 路由id列表
     */
    private List<Long> routeIdList;

}
