package com.demo.service;

import cn.z.redis.RedisTemp;
import com.demo.constant.RedisConstant;
import com.demo.dao.mysql.RoleRouteDao;
import com.demo.dao.mysql.RouteDao;
import com.demo.entity.vo.RouteVo;
import com.demo.util.RouteUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <h1>路由</h1>
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
@Slf4j
public class RouteService {

    private final RedisTemp redisTemp;
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
     * 插入多个
     *
     * @param list id,path,name,seq,parentId
     * @return 是否成功
     */
    @Transactional
    public boolean insertList(List<RouteVo> list) {
        return true;
    }

    /**
     * 删除自己和子节点
     *
     * @param id id
     * @return 是否成功
     */
    @Transactional
    public boolean deleteAndChildren(long id) {
        // 递归删除RoleRoute/Route的子节点
        try {
            deleteChildren(id);
        } catch (Exception e) {
            log.error("删除自己和子节点失败！id为{}", id);
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
    private void deleteChildren(long parentId) throws Exception {
        // 获取子节点
        List<RouteVo> children = routeDao.findByParentId(parentId);
        // 递归
        for (RouteVo child : children) {
            deleteChildren(child.getId());
        }
        // 查询子节点
        List<Long> ids = children.stream().map(RouteVo::getId).collect(Collectors.toList());
        // 没有子节点，提前退出这个递归
        if (ids.isEmpty()) {
            return;
        }
        // 删除子节点
        if (!(roleRouteDao.deleteByRouteIdList(ids) && routeDao.deleteByIdList(ids))) {
            throw new Exception();
        }
    }

    /**
     * 删除自己，并且移动子节点到该节点的父节点
     *
     * @param id id
     * @return 是否成功
     */
    @Transactional
    public boolean deleteAndMoveChildren(long id) {
        // 获取自己
        RouteVo route = routeDao.findById(id);
        // 获取子节点
        List<RouteVo> children = routeDao.findByParentId(id);
        Long parentId = route.getParentId();
        // 更改子节点的父节点
        for (RouteVo child : children) {
            RouteVo routeChild = new RouteVo();
            routeChild.setId(child.getId());
            routeChild.setParentId(parentId);
            if (!routeDao.update(routeChild)) {
                return false;
            }
        }
        // 删除自己
        return (roleRouteDao.deleteByRouteId(id) && routeDao.deleteById(id));
    }

    /**
     * 更新
     *
     * @param route id(必须),path,name,seq(至少1个)
     * @return 是否成功
     */
    @Transactional
    public boolean update(RouteVo route) {
        route.setParentId(null);
        return routeDao.update(route);
    }

    /**
     * 查询，通过用户id
     *
     * @param userId userId
     * @return RouteVo
     */
    public RouteVo findByUserId(long userId) {
        RouteVo route = new RouteVo();
        if (userId == 0) {
            List<String> matcherPath = new ArrayList<>();
            matcherPath.add("/");
            route.setMatcherPath(matcherPath);
            return route;
        }
        route.setMatcherPath(redisTemp.sMembers(RedisConstant.ROUTE_USER_PREFIX + userId + RedisConstant.ROUTE_MATCHER_SUFFIX).stream().map(String.class::cast).collect(Collectors.toList()));
        route.setDirectPath(redisTemp.sMembers(RedisConstant.ROUTE_USER_PREFIX + userId + RedisConstant.ROUTE_DIRECT_SUFFIX).stream().map(String.class::cast).collect(Collectors.toList()));
        return route;
    }

    /**
     * 查询路由id(不包含子路由)，通过用户id
     *
     * @param userId userId
     */
    public Set<Long> findIdByUserId(long userId) {
        Set<Long> ids = new HashSet<>();
        // 获取用户拥有的角色id
        List<Long> roles = roleService.findIdByUserId(userId);
        // 获取该角色id的路由id
        for (Long role : roles) {
            ids.addAll(findIdByRoleId(role));
        }
        return ids;
    }

    /**
     * 查询路由和子路由id，通过用户id
     *
     * @param userId userId
     */
    public Set<Long> findIdAndChildrenIdByUserId(long userId) {
        Set<Long> routeIdList = new HashSet<>();
        // 获取用户拥有的角色id
        List<Long> roleList = roleService.findIdByUserId(userId);
        // 获取该角色id的路由和子路由id
        for (Long role : roleList) {
            for (Long routeId : findIdByRoleId(role)) {
                routeIdList.add(routeId);
                routeIdList.addAll(findChildrenById(routeId).stream().map(RouteVo::getId).collect(Collectors.toList()));
            }
        }
        return routeIdList;
    }

    /**
     * 查询子节点，通过父id
     *
     * @param parentId parentId
     * @return List RouteVo
     */
    private List<RouteVo> findChildrenById(long parentId) {
        List<RouteVo> routeList = new ArrayList<>();
        findChildren(parentId, routeList);
        return routeList;
    }

    /**
     * 查询子节点，通过父id
     *
     * @param parentId  parentId
     * @param routeList routeList
     */
    private void findChildren(long parentId, List<RouteVo> routeList) {
        List<RouteVo> children = routeDao.findIdAndParentIdByParentId(parentId);
        routeList.addAll(children);
        for (RouteVo child : children) {
            if (child.getId() != 0) {
                findChildren(child.getId(), routeList);
            }
        }
    }

    /**
     * 查询，通过id
     *
     * @param id id
     * @return RouteVo
     */
    public RouteVo findById(long id) {
        return routeDao.findById(id);
    }

    /**
     * 查询，通过父id
     *
     * @param parentId parentId
     * @return List RouteVo
     */
    public List<RouteVo> findByParentId(long parentId) {
        return routeDao.findByParentId(parentId);
    }

    /**
     * 查询列表
     *
     * @return List RouteVo
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
     * 查询，通过角色id
     *
     * @param roleId roleId
     * @return List RouteVo
     */
    public List<RouteVo> findByRoleId(long roleId) {
        return routeDao.findByRoleId(roleId);
    }

    /**
     * 查询id，通过角色id
     *
     * @param roleId roleId
     * @return List Long
     */
    public List<Long> findIdByRoleId(long roleId) {
        return routeDao.findIdByRoleId(roleId);
    }

    /**
     * 删除Redis全部路由
     *
     * @return 成功条数
     */
    public Long deleteRoute() {
        return redisTemp.deleteMulti(redisTemp.scan(RedisConstant.ROUTE_PREFIX + "*"));
    }

    /**
     * 删除Redis角色路由，通过角色id<br>
     * 请手动查询该角色下的所有用户并删除用户路由
     *
     * @param roleId roleId
     * @return 成功条数
     * @see #deleteRouteUser(List)
     */
    public Long deleteRouteRole(long roleId) {
        return redisTemp.deleteMulti(RedisConstant.ROUTE_ROLE_PREFIX + roleId + RedisConstant.ROUTE_DIRECT_SUFFIX, //
                RedisConstant.ROUTE_ROLE_PREFIX + roleId + RedisConstant.ROUTE_MATCHER_SUFFIX);
    }

    /**
     * 删除Redis用户路由，通过用户id列表
     *
     * @param userIdList userId
     */
    public Long deleteRouteUser(List<Long> userIdList) {
        List<String> keys = new ArrayList<>();
        for (Long id : userIdList) {
            keys.add(RedisConstant.ROUTE_USER_PREFIX + id + RedisConstant.ROUTE_DIRECT_SUFFIX);
            keys.add(RedisConstant.ROUTE_USER_PREFIX + id + RedisConstant.ROUTE_MATCHER_SUFFIX);
        }
        return redisTemp.deleteMulti(keys);
    }

    /**
     * 删除Redis用户路由，通过用户id
     *
     * @param userId userId
     */
    public Long deleteRouteUser(long userId) {
        return redisTemp.deleteMulti(RedisConstant.ROUTE_USER_PREFIX + userId + RedisConstant.ROUTE_DIRECT_SUFFIX, //
                RedisConstant.ROUTE_USER_PREFIX + userId + RedisConstant.ROUTE_MATCHER_SUFFIX);
    }

}