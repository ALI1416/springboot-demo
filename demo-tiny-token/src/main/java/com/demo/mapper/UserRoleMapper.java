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
     * 删除，通过userId
     *
     * @param id userId
     * @return 删除成功个数
     */
    int deleteByUserId(long id);

    /**
     * 删除，通过roleId
     *
     * @param id roleId
     * @return 删除成功个数
     */
    int deleteByRoleId(long id);

    /**
     * 插入多个
     *
     * @param list id,userId,roleId
     * @return 删除成功个数
     */
    int insertList(List<UserRoleVo> list);

}
