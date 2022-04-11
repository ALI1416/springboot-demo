package com.demo.service;

import com.demo.dao.mysql.RoleRoute2Dao;
import com.demo.dao.mysql.Route2Dao;
import com.demo.entity.vo.RoleVo;
import com.demo.entity.vo.Route2Vo;
import com.demo.util.Route2Utils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <h1>Route2Service</h1>
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
public class Route2Service {

    private final Route2Dao route2Dao;
    private final RoleRoute2Dao roleRoute2Dao;
    private final RoleService roleService;

    /**
     * 插入
     *
     * @param route2 path,name,seq,parentId
     * @return ok:id,e:0
     */
    @Transactional
    public long insert(Route2Vo route2) {
        return route2Dao.insert(route2);
    }

    /**
     * 删除自己和子节点
     *
     * @param id id
     */
    @Transactional
    public boolean deleteWithChildren(Long id) {
        // 递归删除RoleRoute2/Route2的子节点
        try {
            deleteChildren(id);
        } catch (Exception ignore) {
            return false;
        }
        // 删除自己
        return (roleRoute2Dao.deleteByRoute2Id(id) && route2Dao.deleteById(id));
    }

    /**
     * 递归删除RoleRoute2/Route2的子节点
     *
     * @param parentId parentId
     */
    private void deleteChildren(Long parentId) {
        // 获取子节点
        List<Route2Vo> children = route2Dao.findByParentId(parentId);
        // 递归
        for (Route2Vo child : children) {
            deleteChildren(child.getId());
        }
        // 查询子节点
        List<Long> ids = children.stream().map(Route2Vo::getId).collect(Collectors.toList());
        // 没有子节点，提前退出这个递归
        if (ids.isEmpty()) {
            return;
        }
        // 删除子节点
        if (!(roleRoute2Dao.deleteByRoute2IdList(ids) && route2Dao.deleteByIdList(ids))) {
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
        Route2Vo route2 = route2Dao.findById(id);
        // 获取子节点
        List<Route2Vo> children = route2Dao.findByParentId(id);
        // 更改子节点的父节点
        for (Route2Vo child : children) {
            Route2Vo route2Child = new Route2Vo();
            route2Child.setId(child.getId());
            route2Child.setParentId(route2.getParentId());
            if (!route2Dao.update(route2Child)) {
                return false;
            }
        }
        // 删除自己
        return (roleRoute2Dao.deleteByRoute2Id(id) && route2Dao.deleteById(id));
    }

    /**
     * 更新(Id、parentId除外)
     *
     * @param route2 Route2Vo
     */
    @Transactional
    public boolean update(Route2Vo route2) {
        route2.setParentId(null);
        return route2Dao.update(route2);
    }

    /**
     * 查询UserId拥有的路由
     *
     * @param id 用户id
     */
    public Route2Vo findByUserId(long id) {
        Route2Vo route2 = new Route2Vo();
        if (id == 0) {
            List<String> matcherPath = new ArrayList<>();
            matcherPath.add("/");
            route2.setMatcherPath(matcherPath);
            return route2;
        }
        // 获取用户拥有的角色id
        List<Long> roles = roleService.findByUserId(id).stream().map(RoleVo::getId).collect(Collectors.toList());
        if (roles.isEmpty()) {
            return route2;
        }
        // 获取该角色id的所有路由id
        List<Long> routes = findByRoleId(id).stream().map(Route2Vo::getId).collect(Collectors.toList());
        if (routes.isEmpty()) {
            return route2;
        }
        // 获取所有"匹配路径"列表和"不可匹配路径"列表
        Route2Vo routeAll = findExpandedList();
        // 设置用户的"匹配路径"列表和"不可匹配路径"列表
        route2.setMatcher(routeAll.getMatcher().stream().filter(s -> routes.contains(s.getId())).collect(Collectors.toList()));
        route2.setDirect(routeAll.getDirect().stream().filter(s -> routes.contains(s.getId())).collect(Collectors.toList()));
        return route2;
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
                ids.addAll(findChildrenById(routeId).stream().map(Route2Vo::getId).collect(Collectors.toList()));
            }
        }
        return ids;
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
     * 查询通过id
     *
     * @param id id
     * @return Route2Vo
     */
    public Route2Vo findById(Long id) {
        return route2Dao.findById(id);
    }

    /**
     * 查询通过父id
     *
     * @param parentId parentId
     * @return List&lt;Route2Vo>
     */
    public List<Route2Vo> findByParentId(Long parentId) {
        return route2Dao.findByParentId(parentId);
    }

    /**
     * 查询该id下的所有子节点
     *
     * @param id id
     * @return List&lt;Route2Vo>
     */
    public List<Route2Vo> findChildrenById(Long id) {
        List<Route2Vo> route2List = new ArrayList<>();
        findChildren(id, route2List);
        return route2List;
    }

    /**
     * 根据父id查询所有的子节点
     *
     * @param parentId   parentId
     * @param route2List 接收列表
     */
    private void findChildren(Long parentId, List<Route2Vo> route2List) {
        List<Route2Vo> children = route2Dao.findByParentId(parentId);
        route2List.addAll(children);
        for (Route2Vo child : children) {
            if (child.getId() != 0) {
                findChildren(child.getId(), route2List);
            }
        }
    }

    /**
     * 查询列表
     *
     * @return List&lt;Route2Vo>
     */
    public List<Route2Vo> findList() {
        return route2Dao.findAll();
    }

    /**
     * 查询树
     *
     * @return Route2Vo
     */
    public Route2Vo findTree() {
        return Route2Utils.list2Tree(route2Dao.findAll());
    }

    /**
     * 查询展开后的列表
     *
     * @return Route2Vo
     */
    public Route2Vo findExpandedList() {
        return Route2Utils.tree2ExpandedList(Route2Utils.list2Tree(route2Dao.findAll()));
    }

    /**
     * 查询全部，通过roleId
     *
     * @param roleId roleId
     * @return List&lt;Route2Vo>
     */
    public List<Route2Vo> findByRoleId(Long roleId) {
        return route2Dao.findByRoleId(roleId);
    }

    /**
     * 查询全部id，通过RoleId
     *
     * @param userId userId
     * @return List&lt;Long>
     */
    public List<Long> findIdByRoleId(Long userId) {
        return route2Dao.findIdByRoleId(userId);
    }

}
