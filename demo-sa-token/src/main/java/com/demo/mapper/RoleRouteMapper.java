package com.demo.mapper;

import com.demo.entity.vo.RoleRouteVo;

import java.util.List;

/**
 * <h1>RoleRouteMapper</h1>
 *
 * <p>
 * createDate 2021/12/08 09:49:54
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public interface RoleRouteMapper {

    /**
     * 插入多个
     *
     * @param roleRoutes RoleRouteVo
     * @return 执行成功数量
     */
    int insertList(List<RoleRouteVo> roleRoutes);

    /**
     * 删除，通过RouteId
     *
     * @param ids RouteId
     * @return 执行成功数量
     */
    int deleteByRouteIdList(List<Long> ids);

}
