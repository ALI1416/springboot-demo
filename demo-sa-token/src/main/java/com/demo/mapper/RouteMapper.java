package com.demo.mapper;

import com.demo.entity.vo.RouteVo;

import java.util.List;

/**
 * <h1>RouteMapper</h1>
 *
 * <p>
 * createDate 2021/09/13 10:48:57
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public interface RouteMapper {

    /**
     * 插入
     *
     * @param route id,path,name,seq,parentId
     * @return 执行成功数量
     */
    int insert(RouteVo route);

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
     * @param route RouteVo
     * @return 执行成功数量
     */
    int update(RouteVo route);

    /**
     * 查询通过id
     *
     * @param id id
     * @return RouteVo
     */
    RouteVo findById(Long id);

    /**
     * 查询通过父id
     *
     * @param parentId parentId
     * @return List&lt;RouteVo>
     */
    List<RouteVo> findByParentId(Long parentId);

    /**
     * 查询所有
     *
     * @return List&lt;RouteVo>
     */
    List<RouteVo> findAll();

    /**
     * 查询所有通过roleId
     *
     * @param roleId roleId
     * @return List&lt;RouteVo>
     */
    List<RouteVo> findByRoleId(Long roleId);

    /**
     * 查询id，通过roleId
     *
     * @param roleId roleId
     * @return List&lt;Long>
     */
    List<Long> findIdByRoleId(Long roleId);

}
