package com.demo.service;

import com.demo.base.ServiceBase;
import com.demo.constant.RedisConstant;
import com.demo.dao.mysql.RoleRouteDao;
import com.demo.dao.mysql.RouteDao;
import com.demo.entity.vo.RouteVo;
import com.demo.util.RedisUtils;
import com.demo.util.RouteUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
    private final RoleRouteDao roleRouteDao;
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
        return (roleRouteDao.deleteByRouteId(id) && routeDao.deleteById(id));
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
        if (!(roleRouteDao.deleteByRouteIdList(ids) && routeDao.deleteByIdList(ids))) {
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
        return (roleRouteDao.deleteByRouteId(id) && routeDao.deleteById(id));
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
     * 查询UserId拥有的路由
     */
    @SuppressWarnings("unchecked")
    public RouteVo findByUserId(long id) {
        RouteVo route = new RouteVo();
        if (id == 0) {
            List<String> matcherPath = new ArrayList<>();
            matcherPath.add("/");
            route.setMatcherPath(matcherPath);
            return route;
        }
        route.setMatcherPath((List<String>) RedisUtils.get(RedisConstant.ROUTE_USER_PREFIX + id + RedisConstant.ROUTE_MATCHER_SUFFIX));
        route.setDirectPath(RedisUtils.sMembers(RedisConstant.ROUTE_USER_PREFIX + id + RedisConstant.ROUTE_DIRECT_SUFFIX).stream().map(obj -> (String) obj).collect(Collectors.toList()));
        return route;
    }

    /**
     * 查询UserId拥有的路由id
     *
     * @param id 用户id
     */
    public Set<Long> findIdByUserId(long id) {
        Set<Long> ids = new HashSet<>();
        // 获取用户拥有的角色id
        List<Long> roles = roleService.findIdByUserId(id);
        // 获取该角色id的所有路由id
        for (Long role : roles) {
            ids.addAll(findIdByRoleId(role));
        }
        return ids;
    }

    /**
     * 查询UserId拥有的路由和子节点id
     *
     * @param id 用户id
     */
    public Set<Long> findChildrenIdByUserId(long id) {
        Set<Long> ids = new HashSet<>();
        // 获取用户拥有的角色id
        List<Long> roles = roleService.findIdByUserId(id);
        // 获取该角色id的所有路由以及子节点id
        for (Long role : roles) {
            for (Long routeId : findIdByRoleId(role)) {
                ids.add(routeId);
                ids.addAll(findChildrenById(routeId).stream().map(RouteVo::getId).collect(Collectors.toList()));
            }
        }
        return ids;
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
            if (child.getId() != 0) {
                findChildren(child.getId(), routeList);
            }
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
     * 查询全部id，通过RoleId
     *
     * @param roleId roleId
     * @return List&lt;Long>
     */
    public List<Long> findIdByRoleId(Long roleId) {
        return routeDao.findIdByRoleId(roleId);
    }

}
