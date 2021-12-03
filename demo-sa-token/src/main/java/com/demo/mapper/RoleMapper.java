package com.demo.mapper;

import com.demo.entity.vo.RoleVo;

import java.util.List;

/**
 * <h1>RoleMapper</h1>
 *
 * <p>
 * createDate 2021/11/29 15:00:09
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public interface RoleMapper {

    /**
     * 查询所有
     *
     * @return List&lt;RoleVo>
     */
    List<RoleVo> findAll();

    /**
     * 查询所有通过UserId
     *
     * @param userId userId
     * @return List&lt;RoleVo>
     */
    List<RoleVo> findByUserId(Long userId);

}
