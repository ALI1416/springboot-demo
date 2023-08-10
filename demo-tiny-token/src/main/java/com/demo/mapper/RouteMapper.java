package com.demo.mapper;

import com.demo.entity.vo.RouteVo;

import java.util.List;

/**
 * <h1>路由Mapper</h1>
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
     * 删除
     *
     * @param list id
     * @return 执行成功数量
     */
    int deleteByIdList(List<Long> list);

    /**
     * 查询，通过id
     *
     * @param id id
     * @return RouteVo
     */
    RouteVo findById(Long id);

    /**
     * 查询，通过parentId
     *
     * @param parentId parentId
     * @return List RouteVo
     */
    List<RouteVo> findByParentId(Long parentId);

    /**
     * 查询所有
     *
     * @return List RouteVo
     */
    List<RouteVo> findAll();

    /**
     * 查询，通过roleId
     *
     * @param roleId roleId
     * @return List RouteVo
     */
    List<RouteVo> findByRoleId(Long roleId);

    /**
     * 查询id，通过roleId
     *
     * @param roleId roleId
     * @return List Long
     */
    List<Long> findIdByRoleId(Long roleId);

}
