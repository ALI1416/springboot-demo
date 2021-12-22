package com.demo.dao.mysql;

import com.demo.base.DaoBase;
import com.demo.entity.vo.UserRoleVo;
import com.demo.mapper.UserRoleMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <h1>UserRoleDao</h1>
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
public class UserRoleDao extends DaoBase {

    private final UserRoleMapper userRoleMapper;

    /**
     * 插入多个
     *
     * @param userRoles userRoles
     * @return 是否成功
     */
    public boolean insertList(List<UserRoleVo> userRoles) {
        return tryif2(() -> userRoleMapper.insertList(userRoles) == userRoles.size());
    }

    /**
     * 删除，通过RoleId
     *
     * @param userId userId
     * @return 是否成功
     */
    public boolean deleteByUserId(Long userId) {
        return tryif3(() -> userRoleMapper.deleteByUserId(userId));
    }

    /**
     * 删除，通过RoleId
     *
     * @param roleId roleId
     * @return 是否成功
     */
    public boolean deleteByRoleId(Long roleId) {
        return tryif3(() -> userRoleMapper.deleteByRoleId(roleId));
    }

}
