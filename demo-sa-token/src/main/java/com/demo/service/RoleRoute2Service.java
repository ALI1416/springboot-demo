package com.demo.service;

import com.demo.base.ServiceBase;
import com.demo.dao.mysql.RoleRoute2Dao;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <h1>RoleRoute2Service</h1>
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
public class RoleRoute2Service extends ServiceBase {

    private final RoleRoute2Dao roleRoute2Dao;

    /**
     * 删除，通过RoleId
     *
     * @param roleId roleId
     * @return 是否成功
     */
    @Transactional
    public boolean deleteByRoleId(Long roleId) {
        return roleRoute2Dao.deleteByRoleId(roleId);
    }

}
