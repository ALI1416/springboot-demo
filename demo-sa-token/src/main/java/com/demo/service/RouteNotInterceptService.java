package com.demo.service;

import com.demo.base.ServiceBase;
import com.demo.dao.mysql.RouteNotInterceptDao;
import com.demo.entity.vo.RouteNotInterceptVo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <h1>RouteNotInterceptService</h1>
 *
 * <p>
 * createDate 2021/12/08 10:28:26
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Service
@AllArgsConstructor
public class RouteNotInterceptService extends ServiceBase {

    private final RouteNotInterceptDao routeNotInterceptDao;

    /**
     * 查询所有路由不拦截
     *
     * @return List&lt;RouteNotInterceptVo>
     */
    public List<RouteNotInterceptVo> findAll() {
        return routeNotInterceptDao.findAll();
    }

    /**
     * 插入
     *
     * @param routeNotIntercept RouteNotInterceptVo
     * @return ok:id,e:0
     */
    @Transactional
    public Long insert(RouteNotInterceptVo routeNotIntercept) {
        return routeNotInterceptDao.insert(routeNotIntercept);
    }

    /**
     * 删除
     *
     * @param id id
     * @return 是否成功
     */
    @Transactional
    public boolean delete(Long id) {
        return routeNotInterceptDao.delete(id);
    }

    /**
     * 更新
     *
     * @param routeNotIntercept RouteNotInterceptVo
     * @return 是否成功
     */
    @Transactional
    public boolean update(RouteNotInterceptVo routeNotIntercept) {
        return routeNotInterceptDao.update(routeNotIntercept);
    }

}
