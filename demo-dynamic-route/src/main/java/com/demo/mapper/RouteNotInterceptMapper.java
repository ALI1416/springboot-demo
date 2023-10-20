package com.demo.mapper;

import com.demo.entity.vo.RouteNotInterceptVo;

import java.util.List;

/**
 * <h1>路由-不拦截</h1>
 *
 * <p>
 * createDate 2021/12/08 09:57:34
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public interface RouteNotInterceptMapper {

    /**
     * 插入
     *
     * @param routeNotIntercept id,path,name,isMatch,needLogin,seq
     * @return 执行成功数量
     */
    int insert(RouteNotInterceptVo routeNotIntercept);

    /**
     * 删除
     *
     * @param id id
     * @return 执行成功数量
     */
    int delete(long id);

    /**
     * 更新
     *
     * @param routeNotIntercept id(必须),path,name,isMatch,needLogin,seq(至少1个)
     * @return 执行成功数量
     */
    int update(RouteNotInterceptVo routeNotIntercept);

    /**
     * 查询所有
     *
     * @return List RouteNotInterceptVo
     */
    List<RouteNotInterceptVo> findAll();

}
