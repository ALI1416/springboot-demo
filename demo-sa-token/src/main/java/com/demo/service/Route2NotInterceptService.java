package com.demo.service;

import com.demo.base.ServiceBase;
import com.demo.dao.mysql.Route2NotInterceptDao;
import com.demo.entity.vo.Route2NotInterceptVo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <h1>Route2NotInterceptService</h1>
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
public class Route2NotInterceptService extends ServiceBase {

    private final Route2NotInterceptDao route2NotInterceptDao;

    /**
     * 查询所有路由不拦截
     *
     * @return List&lt;Route2NotInterceptVo>
     */
    public List<Route2NotInterceptVo> findAll() {
        return route2NotInterceptDao.findAll();
    }

    /**
     * 插入
     *
     * @param route2NotIntercept Route2NotInterceptVo
     * @return ok:id,e:0
     */
    @Transactional
    public Long insert(Route2NotInterceptVo route2NotIntercept) {
        return route2NotInterceptDao.insert(route2NotIntercept);
    }

    /**
     * 删除
     *
     * @param id id
     * @return 是否成功
     */
    @Transactional
    public boolean delete(Long id) {
        return route2NotInterceptDao.delete(id);
    }

    /**
     * 更新
     *
     * @param route2NotIntercept Route2NotInterceptVo
     * @return 是否成功
     */
    @Transactional
    public boolean update(Route2NotInterceptVo route2NotIntercept) {
        return route2NotInterceptDao.update(route2NotIntercept);
    }

}
