package com.demo.mapper;

import com.demo.entity.vo.Route2NotInterceptVo;

import java.util.List;

/**
 * <h1>Route2NotInterceptMapper</h1>
 *
 * <p>
 * createDate 2021/12/08 09:57:34
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public interface Route2NotInterceptMapper {

    /**
     * 查询所有
     *
     * @return List&lt;Route2NotInterceptVo>
     */
    List<Route2NotInterceptVo> findAll();

    /**
     * 插入
     *
     * @param route2NotIntercept Route2NotInterceptVo
     * @return 执行成功数量
     */
    int insert(Route2NotInterceptVo route2NotIntercept);

    /**
     * 删除
     *
     * @param id id
     * @return 执行成功数量
     */
    int delete(Long id);

    /**
     * 更新
     *
     * @param route2NotIntercept Route2NotInterceptVo
     * @return 执行成功数量
     */
    int update(Route2NotInterceptVo route2NotIntercept);

}
