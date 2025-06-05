package com.demo.service;

import cn.z.redis.RedisTemp;
import cn.z.tool.DeepCopy;
import com.demo.base.ServiceBase;
import com.demo.constant.RedisConstant;
import com.demo.dao.mysql.RoleDao;
import com.demo.dao.mysql.RoleRouteDao;
import com.demo.dao.mysql.RouteDao;
import com.demo.dao.mysql.UserDao;
import com.demo.entity.vo.RouteVo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
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
public class RouteService extends ServiceBase {

    private final RedisTemp redisTemp;
    private final RouteDao routeDao;
    private final RoleRouteDao roleRouteDao;
    private final RoleDao roleDao;
    private final UserDao userDao;

    /**
     * 路由占位符
     */
    private static final String PLACEHOLDER = "placeholder";

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
     * 批量插入
     *
     * @param list id,path,name,seq,parentId
     * @return 是否成功
     */
    @Transactional
    public boolean batchInsert(List<RouteVo> list) {
        return routeDao.batchInsert(list);
    }

    /**
     * 删除自己和子节点
     *
     * @param id id
     * @return 是否成功
     */
    @Transactional
    public boolean deleteAndChild(long id) {
        // 递归删除RoleRoute/Route的子节点
        try {
            deleteChild(id);
        } catch (Exception e) {
            return false;
        }
        // 删除自己
        return execute(() -> roleRouteDao.deleteByRouteId(id), () -> routeDao.deleteById(id));
    }

    /**
     * 递归删除RoleRoute/Route的子节点
     *
     * @param parentId parentId
     */
    private void deleteChild(long parentId) throws Exception {
        // 获取子节点
        List<RouteVo> childList = routeDao.findByParentId(parentId);
        // 递归
        for (RouteVo child : childList) {
            deleteChild(child.getId());
        }
        // 查询子节点
        List<Long> idList = childList.stream().map(RouteVo::getId).toList();
        // 没有子节点，提前退出这个递归
        if (idList.isEmpty()) {
            return;
        }
        // 删除子节点
        if (!execute(() -> roleRouteDao.deleteByRouteIdList(idList), () -> routeDao.batchDelete(idList))) {
            throw new Exception();
        }
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
     * 查询，通过角色id
     *
     * @param roleId roleId
     * @return RouteVo
     */
    public RouteVo findByRoleId(long roleId) {
        RouteVo route = new RouteVo();
        // 从缓存中查询
        String key = RedisConstant.ROUTE_ROLE_PREFIX + roleId + RedisConstant.ROUTE_MATCH_SUFFIX;
        Set<Object> match = redisTemp.sMembers(key);
        // 不存在去创建
        if (match.isEmpty()) {
            setRouteByRoleId(roleId);
        }
        match = redisTemp.sMembers(key);
        route.setMatchPath(match.stream().map(String.class::cast).toList());
        route.setDirectPath(redisTemp.sMembers(RedisConstant.ROUTE_ROLE_PREFIX + roleId + RedisConstant.ROUTE_DIRECT_SUFFIX)
                .stream().map(String.class::cast).toList());
        return route;
    }

    /**
     * 查询，通过用户id
     *
     * @param userId userId
     * @return RouteVo
     */
    public RouteVo findByUserId(long userId) {
        RouteVo route = new RouteVo();
        // root用户
        if (userId == 0) {
            List<String> matchPath = new ArrayList<>(1);
            matchPath.add("/");
            route.setMatchPath(matchPath);
            List<String> directPath = new ArrayList<>(1);
            directPath.add(PLACEHOLDER);
            route.setDirectPath(directPath);
            return route;
        }
        // 从缓存中查询
        String key = RedisConstant.ROUTE_USER_PREFIX + userId + RedisConstant.ROUTE_MATCH_SUFFIX;
        Set<Object> match = redisTemp.sMembers(key);
        // 不存在去创建
        if (match.isEmpty()) {
            setUserRoute(userId);
        }
        match = redisTemp.sMembers(key);
        route.setMatchPath(match.stream().map(String.class::cast).toList());
        route.setDirectPath(redisTemp.sMembers(RedisConstant.ROUTE_USER_PREFIX + userId + RedisConstant.ROUTE_DIRECT_SUFFIX)
                .stream().map(String.class::cast).toList());
        return route;
    }

    /**
     * 查询路由和所有子路由id，通过用户id
     *
     * @param userId userId
     */
    public Set<Long> findIdAndChildIdByUserId(long userId) {
        Set<Long> routeIdList = new HashSet<>();
        // 获取用户拥有的角色id
        List<Long> roleIdList = roleDao.findIdByUserId(userId);
        // 获取该角色id的路由和子路由id
        for (Long roleId : roleIdList) {
            for (Long routeId : routeDao.findIdByRoleId(roleId)) {
                routeIdList.add(routeId);
                routeIdList.addAll(findChildByParentId(routeId).stream().map(RouteVo::getId).toList());
            }
        }
        return routeIdList;
    }

    /**
     * 查询所有子路由，通过父id
     *
     * @param parentId parentId
     * @return List RouteVo
     */
    public List<RouteVo> findChildByParentId(long parentId) {
        List<RouteVo> routeList = new ArrayList<>();
        findChild(parentId, routeList);
        return routeList;
    }

    /**
     * 查询所有子路由，通过父id
     *
     * @param parentId  parentId
     * @param routeList routeList
     */
    private void findChild(long parentId, List<RouteVo> routeList) {
        List<RouteVo> childList = routeDao.findByParentId(parentId);
        routeList.addAll(childList);
        for (RouteVo child : childList) {
            if (child.getId() != 0) {
                findChild(child.getId(), routeList);
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
     * 查询所有
     *
     * @return List RouteVo
     */
    public List<RouteVo> findAll() {
        return routeDao.findAll();
    }

    /**
     * 查询树
     *
     * @return RouteVo
     */
    public RouteVo findTree() {
        return list2Tree(routeDao.findAll());
    }

    /**
     * 查询展开后的列表
     *
     * @return RouteVo
     */
    public RouteVo findExpandList() {
        return tree2ExpandList(list2Tree(routeDao.findAll()));
    }

    /**
     * 删除Redis全部路由缓存
     */
    public void deleteRouteCache() {
        redisTemp.deleteMulti(redisTemp.scan(RedisConstant.ROUTE_PREFIX + "*"));
    }

    /**
     * 删除Redis角色和用户缓存，通过角色id
     *
     * @param roleId roleId
     */
    public void deleteRoleAndUserCacheByRoleId(long roleId) {
        // 角色
        redisTemp.deleteMulti(RedisConstant.ROUTE_ROLE_PREFIX + roleId + RedisConstant.ROUTE_DIRECT_SUFFIX,
                RedisConstant.ROUTE_ROLE_PREFIX + roleId + RedisConstant.ROUTE_MATCH_SUFFIX);
        // 用户
        List<Long> userIdList = userDao.findIdByRoleId(roleId);
        List<String> keys = new ArrayList<>();
        for (long userId : userIdList) {
            keys.add(RedisConstant.ROUTE_USER_PREFIX + userId + RedisConstant.ROUTE_DIRECT_SUFFIX);
            keys.add(RedisConstant.ROUTE_USER_PREFIX + userId + RedisConstant.ROUTE_MATCH_SUFFIX);
        }
        redisTemp.deleteMulti(keys);
    }

    /**
     * 删除Redis用户缓存，通过用户id
     *
     * @param userId userId
     */
    public void deleteUserCacheByUserId(long userId) {
        redisTemp.deleteMulti(RedisConstant.ROUTE_USER_PREFIX + userId + RedisConstant.ROUTE_DIRECT_SUFFIX,
                RedisConstant.ROUTE_USER_PREFIX + userId + RedisConstant.ROUTE_MATCH_SUFFIX);
    }

    /**
     * 是"匹配路径"
     *
     * @param userId  用户id
     * @param urlList URL列表
     * @return 是否
     */
    public boolean isMatch(long userId, List<String> urlList) {
        String key = RedisConstant.ROUTE_USER_PREFIX + userId + RedisConstant.ROUTE_MATCH_SUFFIX;
        // 判断值是否存在
        if (redisTemp.sIsMemberMulti(key, urlList).containsValue(true)) {
            // 存在
            return true;
        }
        // 判断key是否存在
        if (Boolean.TRUE.equals(redisTemp.exists(key))) {
            // 存在
            return false;
        } else {
            // 不存在，去添加
            setUserRoute(userId);
        }
        // 判断值是否存在
        return redisTemp.sIsMemberMulti(key, urlList).containsValue(true);
    }

    /**
     * 是"直接路径"
     *
     * @param userId 用户id
     * @param url    URL
     * @return 是否
     */
    public boolean isDirect(long userId, String url) {
        String key = RedisConstant.ROUTE_USER_PREFIX + userId + RedisConstant.ROUTE_DIRECT_SUFFIX;
        // 判断值是否存在(setUserRoute已创建)
        return redisTemp.sIsMember(key, url);
    }

    /**
     * 创建用户"匹配路径"和"直接路径"
     *
     * @param userId 用户id
     */
    public void setUserRoute(long userId) {
        Set<Object> matchList;
        Set<Object> directList;
        // 获取该用户的角色
        List<Long> roleIdList = roleDao.findIdByUserId(userId);
        // 判断是否有角色
        if (roleIdList.isEmpty()) {
            // 不存在，给一个占位符
            matchList = new HashSet<>();
            matchList.add(PLACEHOLDER);
            directList = new HashSet<>();
            directList.add(PLACEHOLDER);
        } else {
            // 创建角色的"匹配路径"和"直接路径"
            setRouteByRoleIdList(roleIdList);
            // 获取该用户的"匹配路径"和"直接路径"
            matchList = redisTemp.sUnionAll(
                    roleIdList.stream().map(roleId -> RedisConstant.ROUTE_ROLE_PREFIX + roleId + RedisConstant.ROUTE_MATCH_SUFFIX)
                            .toList()
            );
            directList = redisTemp.sUnionAll(
                    roleIdList.stream().map(roleId -> RedisConstant.ROUTE_ROLE_PREFIX + roleId + RedisConstant.ROUTE_DIRECT_SUFFIX)
                            .toList()
            );
        }
        // 创建该用户的"匹配路径"和"直接路径"
        String key = RedisConstant.ROUTE_USER_PREFIX + userId + RedisConstant.ROUTE_MATCH_SUFFIX;
        redisTemp.sAddMulti(key, matchList);
        redisTemp.expire(key, RedisConstant.ROUTE_EXPIRE);
        String key2 = RedisConstant.ROUTE_USER_PREFIX + userId + RedisConstant.ROUTE_DIRECT_SUFFIX;
        redisTemp.sAddMulti(key2, directList);
        redisTemp.expire(key2, RedisConstant.ROUTE_EXPIRE);
    }

    /**
     * 创建"匹配路径"和"直接路径"，通过角色id列表
     *
     * @param roleIdList 角色id列表
     */
    public void setRouteByRoleIdList(List<Long> roleIdList) {
        for (long roleId : roleIdList) {
            String key = RedisConstant.ROUTE_ROLE_PREFIX + roleId + RedisConstant.ROUTE_MATCH_SUFFIX;
            // 判断是否存在该角色的"匹配路径"和"直接路径"
            if (Boolean.FALSE.equals(redisTemp.exists(key))) {
                // 不存在，去创建
                setRouteByRoleId(roleId);
            }
        }
    }

    /**
     * 创建"匹配路径"和"直接路径"，通过角色id
     *
     * @param roleId 角色id
     */
    public void setRouteByRoleId(long roleId) {
        // 判断是否存在根"匹配路径"
        if (Boolean.FALSE.equals(redisTemp.exists(RedisConstant.ROUTE_MATCH))) {
            // 不存在，去创建
            setRoute();
        }
        // 获取该角色的路由
        List<String> routeIdList = routeDao.findIdByRoleId(roleId)
                .stream().map(Object::toString).toList();
        // 获取该角色的"匹配路径"(去除null)
        Collection<Object> routeMatch = redisTemp.hGetMulti(RedisConstant.ROUTE_MATCH, routeIdList)
                .stream().filter(Objects::nonNull).toList();
        // 判断是否有"匹配路径"
        if (routeMatch.isEmpty()) {
            // 不存在，给一个占位符
            routeMatch.add(PLACEHOLDER);
        }
        // 获取该角色的"直接路径"(setRoute已创建)(去除null)
        Collection<Object> routeDirect = redisTemp.hGetMulti(RedisConstant.ROUTE_DIRECT, routeIdList)
                .stream().filter(Objects::nonNull).toList();
        // 判断是否有"直接路径"
        if (routeDirect.isEmpty()) {
            // 不存在，给一个占位符
            routeDirect.add(PLACEHOLDER);
        }
        // 创建该角色的"匹配路径"
        String key = RedisConstant.ROUTE_ROLE_PREFIX + roleId + RedisConstant.ROUTE_MATCH_SUFFIX;
        redisTemp.sAddMulti(key, routeMatch);
        redisTemp.expire(key, RedisConstant.ROUTE_EXPIRE);
        // 创建该角色的"直接路径"
        String key2 = RedisConstant.ROUTE_ROLE_PREFIX + roleId + RedisConstant.ROUTE_DIRECT_SUFFIX;
        redisTemp.sAddMulti(key2, routeDirect);
        redisTemp.expire(key2, RedisConstant.ROUTE_EXPIRE);
    }

    /**
     * 创建根"匹配路径"和"直接路径"
     */
    public void setRoute() {
        // 获取根"匹配路径"和"直接路径"
        RouteVo route = findExpandList();
        // 创建根"匹配路径"和"直接路径"
        Map<String, String> match = route.getMatch()
                .stream().collect(Collectors.toMap(r -> r.getId().toString(), RouteVo::getPath));
        Map<String, String> direct = route.getDirect()
                .stream().collect(Collectors.toMap(r -> r.getId().toString(), RouteVo::getPath));
        redisTemp.hSetMulti(RedisConstant.ROUTE_MATCH, match);
        redisTemp.hSetMulti(RedisConstant.ROUTE_DIRECT, direct);
        redisTemp.expire(RedisConstant.ROUTE_MATCH, RedisConstant.ROUTE_EXPIRE);
        redisTemp.expire(RedisConstant.ROUTE_DIRECT, RedisConstant.ROUTE_EXPIRE);
    }

    /**
     * 列表转树
     *
     * @param routeList 路由表
     * @return 树<br>
     * 不存在id=0的根节点，返回空对象
     */
    private static RouteVo list2Tree(List<RouteVo> routeList) {
        // 拷贝原始数据
        List<RouteVo> list = DeepCopy.copy(routeList);
        // 找到并重置根节点
        RouteVo root;
        Optional<RouteVo> first = list.stream().filter(r -> r.getId() == 0).findFirst();
        if (first.isPresent()) {
            root = first.get();
            root.setParentId(-1L);
            root.setPath("/");
        } else {
            return new RouteVo();
        }
        // 按父id分组
        Map<Long, List<RouteVo>> map = list.stream().collect(Collectors.groupingBy(RouteVo::getParentId));
        // 生成树
        makeTree(root, map);
        return root;
    }

    /**
     * 生成树
     *
     * @param tree 接收的树
     * @param map  按父id分组的map
     */
    private static void makeTree(RouteVo tree, Map<Long, List<RouteVo>> map) {
        // 找到子节点
        List<RouteVo> childList = map.get(tree.getId());
        // 如果有子节点
        if (childList != null) {
            // 对子节点进行排序
            childList = childList.stream().sorted(Comparator.comparing(RouteVo::getSeq)).toList();
            // 子节点生成树
            for (RouteVo child : childList) {
                makeTree(child, map);
            }
            // 插入子节点
            tree.setChild(childList);
        }
    }

    /**
     * 树转展开列表
     *
     * @param root 树
     * @return 展开列表
     */
    private static RouteVo tree2ExpandList(RouteVo root) {
        // 创建新的路由表
        RouteVo route = new RouteVo();
        List<RouteVo> match = new ArrayList<>();
        List<RouteVo> direct = new ArrayList<>();
        route.setMatch(match);
        route.setDirect(direct);
        // 拷贝原始数据
        RouteVo tree = DeepCopy.copy(root);
        // 找到子节点
        List<RouteVo> childList = tree.getChild();
        tree.setChild(null);
        // 根节点加入路径
        match.add(tree);
        if (childList != null) {
            for (RouteVo child : childList) {
                // 子节点去展开
                makeExpandList(route, child, "");
            }
        }
        // 排序
        route.setMatch(route.getMatch().stream()
                .sorted(Comparator.comparing(RouteVo::getSeq))
                .sorted(Comparator.comparing(RouteVo::getParentId))
                .toList());
        route.setDirect(route.getDirect().stream()
                .sorted(Comparator.comparing(RouteVo::getSeq))
                .sorted(Comparator.comparing(RouteVo::getParentId))
                .toList());
        return route;
    }

    /**
     * 生成展开列表
     *
     * @param route  接收的列表
     * @param tree   树
     * @param prefix 前缀
     */
    private static void makeExpandList(RouteVo route, RouteVo tree, String prefix) {
        // 找到子节点
        List<RouteVo> childList = tree.getChild();
        tree.setChild(null);
        // 节点加入路径
        if (childList != null) {
            // 存在子节点
            prefix = prefix + "/" + tree.getPath();
            tree.setPath(prefix);
            route.getMatch().add(tree);
        } else {
            // 不存在子节点，且路径不为空
            if (!tree.getPath().isEmpty()) {
                prefix = prefix + "/" + tree.getPath();
            }
            tree.setPath(prefix);
            route.getDirect().add(tree);
        }
        if (childList != null) {
            for (RouteVo child : childList) {
                // 子节点去展开
                makeExpandList(route, child, prefix);
            }
        }
    }

}
