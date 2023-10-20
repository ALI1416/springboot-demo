package com.demo.mapper;


import com.demo.entity.vo.UserRoleVo;

import java.util.List;

/**
 * <h1>用户-角色</h1>
 *
 * <p>
 * createDate 2021/12/16 10:41:17
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public interface UserRoleMapper {

    /**
     * 批量插入
     *
     * @param list id,userId,roleId
     * @return 执行成功数量
     */
    int batchInsert(List<UserRoleVo> list);

    /**
     * 删除，通过userId
     *
     * @param userId userId
     * @return 执行成功数量
     */
    int deleteByUserId(long userId);

    /**
     * 删除，通过roleId
     *
     * @param roleId roleId
     * @return 执行成功数量
     */
    int deleteByRoleId(long roleId);

}
