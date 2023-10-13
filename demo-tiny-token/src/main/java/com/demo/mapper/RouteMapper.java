package com.demo.mapper;

import com.demo.entity.vo.RouteVo;

import java.util.List;

/**
 * <h1>路由</h1>
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
     * 更新
     *
     * @param route id(必须),path,name,seq,parentId(至少1个)
     * @return 执行成功数量
     */
    int update(RouteVo route);

    /**
     * 删除多个通过id
     *
     * @param idList id
     * @return 执行成功数量
     */
    int deleteByIdList(List<Long> idList);

    /**
     * 查询，通过id
     *
     * @param id id
     * @return RouteVo
     */
    RouteVo findById(long id);

    /**
     * 查询
     *
     * @param route RouteVo
     * @return List RouteVo
     */
    List<RouteVo> find(RouteVo route);

    /**
     * 查询id
     *
     * @param route RouteVo
     * @return List Long
     */
    List<Long> findId(RouteVo route);

    /**
     * 查询id和parentId
     *
     * @param route RouteVo
     * @return List RouteVo
     */
    List<RouteVo> findIdAndParentId(RouteVo route);

    /**
     * 查询，通过roleId
     *
     * @param roleId roleId
     * @return List RouteVo
     */
    List<RouteVo> findByRoleId(long roleId);

    /**
     * 查询id，通过roleId
     *
     * @param roleId roleId
     * @return List Long
     */
    List<Long> findIdByRoleId(long roleId);

}
