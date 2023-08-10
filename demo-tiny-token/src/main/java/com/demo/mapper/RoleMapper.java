package com.demo.mapper;

import com.demo.entity.vo.RoleVo;

import java.util.List;

/**
 * <h1>角色Mapper</h1>
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
    int delete(Long id);

    /**
     * 查询所有
     *
     * @return List RoleVo
     */
    List<RoleVo> findAll();

    /**
     * 查询，通过createId
     *
     * @param createId createId
     * @return List RoleVo
     */
    List<RoleVo> findByCreateId(Long createId);

    /**
     * 查询，通过userId
     *
     * @param userId userId
     * @return List RoleVo
     */
    List<RoleVo> findByUserId(Long userId);

    /**
     * 查询id，通过userId
     *
     * @param userId userId
     * @return List Long
     */
    List<Long> findIdByUserId(Long userId);

}
