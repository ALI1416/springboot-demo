package com.demo.mapper;

import com.demo.entity.vo.RoleVo;

import java.util.List;

/**
 * <h1>角色</h1>
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
     * @param role id(必须),name,seq(至少1个)
     * @return 执行成功数量
     */
    int update(RoleVo role);

    /**
     * 删除
     *
     * @param id id
     * @return 执行成功数量
     */
    int delete(long id);

    /**
     * 查询
     *
     * @param role RoleVo
     * @return List RoleVo
     */
    List<RoleVo> find(RoleVo role);

    /**
     * 是否存在
     *
     * @param role RoleVo
     * @return 是否存在
     */
    boolean exist(RoleVo role);

    /**
     * 查询，通过id
     *
     * @param id id
     * @return RoleVo
     */
    RoleVo findById(long id);

    /**
     * 查询id，通过userId
     *
     * @param userId userId
     * @return List Long
     */
    List<Long> findIdByUserId(long userId);

    /**
     * 查询，通过userId
     *
     * @param userId userId
     * @return List RoleVo
     */
    List<RoleVo> findByUserId(long userId);

}
