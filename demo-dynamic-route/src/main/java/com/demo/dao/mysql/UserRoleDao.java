package com.demo.dao.mysql;

import com.demo.base.DaoBase;
import com.demo.entity.vo.UserRoleVo;
import com.demo.mapper.UserRoleMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <h1>用户-角色</h1>
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
     * 批量插入
     *
     * @param list id,userId,roleId
     * @return 是否成功
     */
    public boolean insertList(List<UserRoleVo> list) {
        return tryAny(() -> userRoleMapper.batchInsert(list));
    }

    /**
     * 删除，通过角色id
     *
     * @param roleId roleId
     * @return 是否成功
     */
    public boolean deleteByRoleId(long roleId) {
        return tryAny(() -> userRoleMapper.deleteByRoleId(roleId));
    }

    /**
     * 删除，通过用户id
     *
     * @param userId userId
     * @return 是否成功
     */
    public boolean deleteByUserId(long userId) {
        return tryAny(() -> userRoleMapper.deleteByUserId(userId));
    }

}
