package com.demo.dao.mysql;

import com.demo.base.DaoBase;
import com.demo.entity.vo.RoleRouteVo;
import com.demo.mapper.RoleRouteMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <h1>角色-路由</h1>
 *
 * <p>
 * createDate 2021/12/08 09:53:08
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Service
@AllArgsConstructor
public class RoleRouteDao extends DaoBase {

    private final RoleRouteMapper roleRouteMapper;

    /**
     * 批量插入
     *
     * @param list id,roleId,routeId
     * @return 是否成功
     */
    public boolean batchInsert(List<RoleRouteVo> list) {
        return tryAny(() -> roleRouteMapper.batchInsert(list));
    }

    /**
     * 删除，通过路由id
     *
     * @param routeId routeId
     * @return 是否成功
     */
    public boolean deleteByRouteId(long routeId) {
        return tryAny(() -> roleRouteMapper.deleteByRouteId(routeId));
    }

    /**
     * 删除，通过路由id列表
     *
     * @param routeIdList routeId
     * @return 是否成功
     */
    public boolean deleteByRouteIdList(List<Long> routeIdList) {
        return tryAny(() -> roleRouteMapper.deleteByRouteIdList(routeIdList));
    }

    /**
     * 删除，通过角色id
     *
     * @param roleId roleId
     * @return 是否成功
     */
    public boolean deleteByRoleId(long roleId) {
        return tryAny(() -> roleRouteMapper.deleteByRoleId(roleId));
    }

}
