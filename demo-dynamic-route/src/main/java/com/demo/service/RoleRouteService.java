package com.demo.service;

import com.demo.base.ServiceBase;
import com.demo.dao.mysql.RoleRouteDao;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <h1>角色-路由</h1>
 *
 * <p>
 * createDate 2021/12/13 17:39:59
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Service
@AllArgsConstructor
public class RoleRouteService extends ServiceBase {

    private final RoleRouteDao roleRouteDao;

    /**
     * 删除，通过角色id
     *
     * @param roleId roleId
     * @return 是否成功
     */
    @Transactional
    public boolean deleteByRoleId(long roleId) {
        return roleRouteDao.deleteByRoleId(roleId);
    }

}
