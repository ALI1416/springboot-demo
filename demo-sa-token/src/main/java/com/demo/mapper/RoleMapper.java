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
     * 插入
     *
     * @param role id,name,seq,createId
     * @return 执行成功数量
     */
    int insert(RoleVo role);

    /**
     * 更新
     *
     * @param role 必须id,至少一个name,seq
     * @return 执行成功数量
     */
    int update(RoleVo role);

    /**
     * 删除
     *
     * @param id id
     * @return 执行成功数量
     */
    int delete(Long id);

    /**
     * 删除RoleRoute表，通过RoleId
     *
     * @param id RoleId
     * @return 执行成功数量
     */
    int deleteRoleRouteByRoleId(Long id);

    /**
     * 查询所有
     *
     * @return List&lt;RoleVo>
     */
    List<RoleVo> findAll();

    /**
     * 查询所有通过CreateId
     *
     * @param createId CreateId
     * @return List&lt;RoleVo>
     */
    List<RoleVo> findByCreateId(Long createId);

    /**
     * 查询UserId拥有的角色
     *
     * @param userId userId
     * @return List&lt;RoleVo>
     */
    List<RoleVo> findByUserId(Long userId);

    /**
     * 查询UserId拥有的角色id
     *
     * @param userId userId
     * @return List&lt;RoleVo>
     */
    List<Long> findIdByUserId(Long userId);

}
