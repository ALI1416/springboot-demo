package com.demo.service;

import com.demo.base.ServiceBase;
import com.demo.dao.mysql.RouteDao;
import com.demo.entity.vo.RoleVo;
import com.demo.entity.vo.RouteNotInterceptVo;
import com.demo.entity.vo.RouteVo;
import com.demo.util.RouteUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <h1>RouteService</h1>
 *
 * <p>
 * createDate 2021/11/29 14:07:58
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Service
@AllArgsConstructor
public class RouteService extends ServiceBase {

    private final RouteDao routeDao;
    private final RoleService roleService;

    /**
     * 查询列表
     *
     * @return List&lt;RouteVo>
     */
    public List<RouteVo> findList() {
        return routeDao.findAll();
    }

    /**
     * 查询树
     *
     * @return RouteVo
     */
    public RouteVo findTree() {
        return RouteUtils.list2Tree(routeDao.findAll());
    }

    /**
     * 查询展开后的列表
     *
     * @return RouteVo
     */
    public RouteVo findExpandedList() {
        return RouteUtils.tree2ExpandedList(RouteUtils.list2Tree(routeDao.findAll()));
    }

    /**
     * 查询全部，通过RoleId
     *
     * @param id RoleId
     * @return List&lt;RouteVo>
     */
    public List<RouteVo> findByRoleId(Long id) {
        return routeDao.findByRoleId(id);
    }

    /**
     * 查询全部，通过UserId
     *
     * @param id UserId
     * @return List&lt;RoleVo>->List&lt;RouteVo>
     */
    public List<RoleVo> findByUserId(Long id) {
        List<RoleVo> roles = roleService.findByUserId(id);
        for (RoleVo role : roles) {
            role.setRoutes(findByRoleId(role.getId()));
        }
        return roles;
    }

    /**
     * 查询所有路由不拦截
     *
     * @return List<RouteNotInterceptVo>
     */
    public List<RouteNotInterceptVo> findAllRouteNotIntercept() {
        return routeDao.findAllRouteNotIntercept();
    }

}
