package com.demo.service;

import com.demo.base.ServiceBase;
import com.demo.dao.mysql.RouteDao;
import com.demo.entity.vo.RoleVo;
import com.demo.entity.vo.RouteNotInterceptVo;
import com.demo.entity.vo.RouteVo;
import com.demo.util.RouteUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
     * 插入
     *
     * @param route path,name,seq,parentId
     * @return ok:id,e:0
     */
    @Transactional
    public long insert(RouteVo route) {
        return routeDao.insert(route);
    }

    /**
     * 删除自己和子节点
     *
     * @param id id
     */
    @Transactional
    public boolean deleteWithChildren(Long id) {
        // 递归删除RoleRoute/Route的子节点
        try {
            deleteChildren(id);
        } catch (Exception ignore) {
            return false;
        }
        // 删除自己
        return (routeDao.deleteRoleRouteByRouteId(id) && routeDao.deleteById(id));
    }

    /**
     * 递归删除RoleRoute/Route的子节点
     *
     * @param parentId parentId
     */
    private void deleteChildren(Long parentId) {
        // 获取子节点
        List<RouteVo> children = routeDao.findByParentId(parentId);
        // 递归
        for (RouteVo child : children) {
            deleteChildren(child.getId());
        }
        // 查询子节点
        List<Long> ids = children.stream().map(RouteVo::getId).collect(Collectors.toList());
        // 没有子节点，提前退出这个递归
        if (ids.size() == 0) {
            return;
        }
        // 删除子节点
        if (!(routeDao.deleteRoleRouteByRouteIdList(ids) && routeDao.deleteByIdList(ids))) {
            throw new RuntimeException("删除失败！");
        }
    }

    /**
     * 删除自己，并且移动子节点到该节点的父节点
     *
     * @param id id
     */
    @Transactional
    public boolean deleteAndMoveChildren(Long id) {
        // 获取自己
        RouteVo route = routeDao.findById(id);
        // 获取子节点
        List<RouteVo> children = routeDao.findByParentId(id);
        // 更改子节点的父节点
        for (RouteVo child : children) {
            RouteVo routeChild = new RouteVo();
            routeChild.setId(child.getId());
            routeChild.setParentId(route.getParentId());
            if (!routeDao.update(routeChild)) {
                return false;
            }
        }
        // 删除自己
        return (routeDao.deleteRoleRouteByRouteId(id) && routeDao.deleteById(id));
    }

    /**
     * 更新(Id、parentId除外)
     *
     * @param route RouteVo
     */
    @Transactional
    public boolean update(RouteVo route) {
        route.setParentId(null);
        return routeDao.update(route);
    }

    /**
     * 移动节点成为parentId的子节点
     *
     * @param id       id
     * @param parentId parentId
     */
    public void move(Long id, Long parentId) {

    }

    /**
     * 查询通过id
     *
     * @param id id
     * @return RouteVo
     */
    public RouteVo findById(Long id) {
        return routeDao.findById(id);
    }

    /**
     * 查询通过父id
     *
     * @param parentId parentId
     * @return List&lt;RouteVo>
     */
    public List<RouteVo> findByParentId(Long parentId) {
        return routeDao.findByParentId(parentId);
    }

    /**
     * 查询该id下的所有子节点
     *
     * @param id id
     * @return List&lt;RouteVo>
     */
    public List<RouteVo> findChildrenById(Long id) {
        List<RouteVo> routeList = new ArrayList<>();
        findChildren(id, routeList);
        return routeList;
    }

    /**
     * 根据父id查询所有的子节点
     *
     * @param parentId  parentId
     * @param routeList 接收列表
     */
    private void findChildren(Long parentId, List<RouteVo> routeList) {
        List<RouteVo> children = routeDao.findByParentId(parentId);
        routeList.addAll(children);
        for (RouteVo child : children) {
            findChildren(child.getId(), routeList);
        }
    }

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
     * 查询全部，通过roleId
     *
     * @param roleId roleId
     * @return List&lt;RouteVo>
     */
    public List<RouteVo> findByRoleId(Long roleId) {
        return routeDao.findByRoleId(roleId);
    }

    /**
     * 查询全部，通过UserId
     *
     * @param userId userId
     * @return List&lt;RoleVo>->List&lt;RouteVo>
     */
    public List<RoleVo> findByUserId(Long userId) {
        List<RoleVo> roles = roleService.findByUserId(userId);
        for (RoleVo role : roles) {
            role.setRoutes(findByRoleId(role.getId()));
        }
        return roles;
    }

    /**
     * 查询所有路由不拦截
     *
     * @return List&lt;RouteNotInterceptVo>
     */
    public List<RouteNotInterceptVo> findAllRouteNotIntercept() {
        return routeDao.findAllRouteNotIntercept();
    }

}
