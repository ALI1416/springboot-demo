package com.demo.service;

import com.demo.dao.mysql.RouteNotInterceptDao;
import com.demo.entity.vo.RouteNotInterceptVo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <h1>路由-不拦截Service</h1>
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
public class RouteNotInterceptService {

    private final RouteNotInterceptDao routeNotInterceptDao;

    /**
     * 查询所有
     *
     * @return List RouteNotInterceptVo
     */
    public List<RouteNotInterceptVo> findAll() {
        return routeNotInterceptDao.findAll();
    }

    /**
     * 插入
     *
     * @param routeNotIntercept path,name,seq
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
     * @param routeNotIntercept id(必须),path,name,seq(至少1个)
     * @return 是否成功
     */
    @Transactional
    public boolean update(RouteNotInterceptVo routeNotIntercept) {
        return routeNotInterceptDao.update(routeNotIntercept);
    }

}
