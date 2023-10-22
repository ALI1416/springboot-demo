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
     * 批量插入
     *
     * @param list id,path,name,seq,parentId
     * @return 执行成功数量
     */
    int batchInsert(List<RouteVo> list);

    /**
     * 删除
     *
     * @param id id
     * @return 执行成功数量
     */
    int delete(long id);

    /**
     * 批量删除
     *
     * @param idList id
     * @return 执行成功数量
     */
    int batchDelete(List<Long> idList);

    /**
     * 更新
     *
     * @param route id(必须),path,name,seq,parentId(至少1个)
     * @return 执行成功数量
     */
    int update(RouteVo route);

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
     * 查询id，通过roleId
     *
     * @param roleId roleId
     * @return List Long
     */
    List<Long> findIdByRoleId(long roleId);

}
