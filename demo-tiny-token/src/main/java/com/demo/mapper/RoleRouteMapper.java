package com.demo.mapper;

import com.demo.entity.vo.RoleRouteVo;

import java.util.List;

/**
 * <h1>角色-路由Mapper</h1>
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
     * @param list id,roleId,routeId
     * @return 执行成功数量
     */
    int insertList(List<RoleRouteVo> list);

    /**
     * 删除，通过routeId
     *
     * @param list routeId
     * @return 执行成功数量
     */
    int deleteByRouteIdList(List<Long> list);

    /**
     * 删除，通过roleId
     *
     * @param roleId roleId
     * @return 执行成功数量
     */
    int deleteByRoleId(Long roleId);

}
