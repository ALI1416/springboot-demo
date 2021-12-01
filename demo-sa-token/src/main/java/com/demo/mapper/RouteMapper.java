package com.demo.mapper;

import com.demo.entity.vo.RouteNotInterceptVo;
import com.demo.entity.vo.RouteVo;

import java.util.List;

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
public interface RouteMapper {

    /**
     * 插入
     *
     * @param route RouteVo
     * @return 执行成功数量
     */
    int insert(RouteVo route);

    /**
     * 查询所有
     *
     * @return List<RouteVo>
     */
    List<RouteVo> findAll();

    /**
     * 查询所有通过RoleId
     *
     * @param id RoleId
     * @return List<RouteVo>
     */
    List<RouteVo> findByRoleId(Long id);

    /**
     * 查询所有路由不拦截
     *
     * @return List<RouteNotInterceptVo>
     */
    List<RouteNotInterceptVo> findAllRouteNotIntercept();

}
