package com.demo.dao.mysql;

import com.demo.base.DaoBase;
import com.demo.entity.vo.RoleRouteVo;
import com.demo.mapper.RoleRouteMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
     * 插入多个
     *
     * @param list id,roleId,routeId
     * @return 是否成功
     */
    public boolean insertList(List<RoleRouteVo> list) {
        return tryif2(() -> roleRouteMapper.insertList(list) == list.size());
    }

    /**
     * 删除，通过routeId
     *
     * @param routeIdList routeId
     * @return 是否成功
     */
    public boolean deleteByRouteIdList(List<Long> routeIdList) {
        return tryif3(() -> roleRouteMapper.deleteByRouteIdList(routeIdList));
    }

    /**
     * 删除，通过roleId
     *
     * @param roleId roleId
     * @return 是否成功
     */
    public boolean deleteByRoleId(long roleId) {
        return tryif3(() -> roleRouteMapper.deleteByRoleId(roleId));
    }

    /**
     * 删除，通过routeId
     *
     * @param routeId routeId
     * @return 是否成功
     */
    public boolean deleteByRouteId(long routeId) {
        List<Long> list = new ArrayList<>(1);
        list.add(routeId);
        return deleteByRouteIdList(list);
    }

}
