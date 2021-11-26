package com.demo.mapper;

import com.demo.entity.po.Role;

/**
 * <h1>登录日志测试Mapper</h1>
 *
 * <p>
 * createDate 2021/09/13 10:48:57
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public interface RoleMapper {

    /**
     * 插入
     *
     * @param role Role
     * @return 执行成功数量
     */
    int insert(Role role);

}
