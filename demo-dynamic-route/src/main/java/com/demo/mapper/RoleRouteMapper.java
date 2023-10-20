package com.demo.mapper;

import com.demo.entity.vo.RoleRouteVo;

import java.util.List;

/**
 * <h1>角色-路由</h1>
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
     * 批量插入
     *
     * @param list id,roleId,routeId
     * @return 执行成功数量
     */
    int batchInsert(List<RoleRouteVo> list);

    /**
     * 删除，通过routeId
     *
     * @param routeId routeId
     * @return 执行成功数量
     */
    int deleteByRouteId(long routeId);

    /**
     * 删除，通过routeId列表
     *
     * @param routeIdList routeId
     * @return 执行成功数量
     */
    int deleteByRouteIdList(List<Long> routeIdList);

    /**
     * 删除，通过roleId
     *
     * @param roleId roleId
     * @return 执行成功数量
     */
    int deleteByRoleId(long roleId);

}
