package com.demo.dao.mysql;

import com.demo.base.DaoBase;
import com.demo.entity.vo.RoleRouteVo;
import com.demo.mapper.RoleRouteMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>RoleRouteDao</h1>
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
     * @param roleRoutes RoleRouteVo
     * @return 是否成功
     */
    public boolean insertList(List<RoleRouteVo> roleRoutes) {
        return tryif2(() -> roleRouteMapper.insertList(roleRoutes) == roleRoutes.size());
    }

    /**
     * 删除，通过RouteId
     *
     * @param ids RouteId
     * @return 是否成功
     */
    public boolean deleteByRouteIdList(List<Long> ids) {
        return tryif3(() -> roleRouteMapper.deleteByRouteIdList(ids));
    }

    /**
     * 删除，通过RoleId
     *
     * @param roleId   roleId
     * @return 是否成功
     */
    public boolean deleteByRoleId(Long roleId) {
        return tryif3(() -> roleRouteMapper.deleteByRoleId(roleId));
    }

    /**
     * 删除，通过RouteId
     *
     * @param id RouteId
     * @return 是否成功
     */
    public boolean deleteByRouteId(Long id) {
        List<Long> ids = new ArrayList<>();
        ids.add(id);
        return deleteByRouteIdList(ids);
    }

}
