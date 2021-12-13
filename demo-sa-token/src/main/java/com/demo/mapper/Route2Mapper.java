package com.demo.mapper;

import com.demo.entity.vo.Route2Vo;

import java.util.List;

/**
 * <h1>Route2Mapper</h1>
 *
 * <p>
 * createDate 2021/09/13 10:48:57
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public interface Route2Mapper {

    /**
     * 插入
     *
     * @param route2 id,path,name,seq,parentId
     * @return 执行成功数量
     */
    int insert(Route2Vo route2);

    /**
     * 删除
     *
     * @param ids ids
     * @return 执行成功数量
     */
    int deleteByIdList(List<Long> ids);

    /**
     * 更新
     *
     * @param route2 Route2Vo
     * @return 执行成功数量
     */
    int update(Route2Vo route2);

    /**
     * 查询通过id
     *
     * @param id id
     * @return Route2Vo
     */
    Route2Vo findById(Long id);

    /**
     * 查询通过父id
     *
     * @param parentId parentId
     * @return List&lt;Route2Vo>
     */
    List<Route2Vo> findByParentId(Long parentId);

    /**
     * 查询所有
     *
     * @return List&lt;Route2Vo>
     */
    List<Route2Vo> findAll();

    /**
     * 查询所有通过roleId
     *
     * @param roleId roleId
     * @return List&lt;Route2Vo>
     */
    List<Route2Vo> findByRoleId(Long roleId);

    /**
     * 查询所有id通过roleId
     *
     * @param roleId roleId
     * @return List&lt;Long>
     */
    List<Long> findIdByRoleId(Long roleId);

}
