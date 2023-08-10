package com.demo.interceptor;

import cn.z.tinytoken.T4s;
import com.demo.constant.RedisConstant;
import com.demo.entity.po.RouteNotIntercept;
import com.demo.entity.vo.RouteNotInterceptVo;
import com.demo.entity.vo.RouteVo;
import com.demo.exception.NotLoginException;
import com.demo.exception.NotPermissionException;
import com.demo.service.RoleService;
import com.demo.service.RouteNotInterceptService;
import com.demo.service.RouteService;
import com.demo.template.RedisTemp;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <h1>路由拦截器</h1>
 *
 * <p>
 * createDate 2021/11/25 15:55:30
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Service
@AllArgsConstructor
public class RouteInterceptor implements HandlerInterceptor {

    /**
     * 路由占位符：当路由为空时，使用{@value}占位
     */
    private static final String PLACEHOLDER = "placeholder";

    private final T4s t4s;
    private final RedisTemp redisTemp;
    private final RouteService routeService;
    private final RoleService roleService;
    private final RouteNotInterceptService routeNotInterceptService;

    /**
     * preHandle
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @param handler  Object
     * @return 是否通过
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 前端"预检查"不拦截
        if ("OPTIONS".equals(request.getMethod())) {
            return true;
        }
        // 获取"URL"
        String url = request.getServletPath();
        // 是"不拦截路径"
        if (isNotIntercept(url)) {
            return true;
        }
        // 获取"登录者id"
        Long id = t4s.getId();
        // 抛出"未登录异常"
        if (id == null) {
            throw new NotLoginException();
        }
        // 是"root用户"
        if (id == 0L) {
            return true;
        }
        // 是"匹配路径"
        if (isMatcher(id, url)) {
            return true;
        }
        // 是"直接路径"
        if (isDirect(id, url)) {
            return true;
        }
        // 抛出"无权限异常"
        throw new NotPermissionException(url);
    }

    /**
     * 是"不拦截路径"
     *
     * @param url url
     * @return 是否不拦截
     */
    private boolean isNotIntercept(String url) {
        String key = RedisConstant.ROUTE_NOT_INTERCEPT;
        // 查询是否存在key
        if (Boolean.FALSE.equals(redisTemp.exists(key))) {
            // 不存在去添加
            setNotIntercept();
        }
        // 存在，判断值是否存在
        return redisTemp.sIsMember(key, url);
    }

    /**
     * 设置"不拦截路径"
     */
    private void setNotIntercept() {
        String key = RedisConstant.ROUTE_NOT_INTERCEPT;
        // 数据库查询
        List<RouteNotInterceptVo> notIntercept = routeNotInterceptService.findAll();
        List<String> notInterceptPath = new ArrayList<>();
        // 不存在，给一个占位符
        if (notIntercept.isEmpty()) {
            notInterceptPath.add(PLACEHOLDER);
        } else {
            notInterceptPath = notIntercept.stream().map(RouteNotIntercept::getPath).collect(Collectors.toList());
        }
        // 添加key，并设置过期时间
        redisTemp.sAddMulti(key, notInterceptPath);
        redisTemp.expire(key, RedisConstant.ROUTE_EXPIRE);
    }

    /**
     * 是"匹配路径"
     *
     * @param id  用户id
     * @param url url
     * @return 是否
     */
    private boolean isMatcher(long id, String url) {
        List<String> list = setAndGetUserMatcherList(id);
        // 比对每一个路径
        for (String s : list) {
            // 路径与url前部分相同
            if ((url.length() > s.length()) && s.equals(url.substring(0, s.length()))) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是"直接路径"
     *
     * @param id  用户id
     * @param url url
     * @return 是否
     */
    private boolean isDirect(long id, String url) {
        String key = RedisConstant.ROUTE_USER_PREFIX + id + RedisConstant.ROUTE_DIRECT_SUFFIX;
        // 判断值是否存在(setAndGetUserMatcherList已创建)
        return redisTemp.sIsMember(key, url);
    }

    /**
     * 设置并获取用户"匹配路径"列表<br>
     * 也设置用户"直接路径"列表
     *
     * @param id 用户id
     * @return "匹配路径"列表
     */
    private List<String> setAndGetUserMatcherList(long id) {
        String key = RedisConstant.ROUTE_USER_PREFIX + id + RedisConstant.ROUTE_MATCHER_SUFFIX;
        // 获取用户"匹配路径"列表
        List<String> matcherList = (List<String>) redisTemp.get(key);
        Set<Object> directList;
        // 没有数据
        if (matcherList == null) {
            // 获取用户拥有的角色id
            List<Long> roles = roleService.findIdByUserId(id);
            // 用户没有角色，给一个占位符
            if (roles.isEmpty()) {
                matcherList = new ArrayList<>();
                matcherList.add(PLACEHOLDER);
                directList = new HashSet<>();
                directList.add(PLACEHOLDER);
            } else {
                // 设置并获取所有角色路由路径可匹配列表
                matcherList = setAndGetMatcherListByRoles(roles);
                // 读取用户"不可匹配路径"列表(setRouteByRoleId已创建)
                directList = redisTemp.sUnionAll(roles.stream()//
                        .map(r -> RedisConstant.ROUTE_ROLE_PREFIX + r + RedisConstant.ROUTE_DIRECT_SUFFIX)//
                        .collect(Collectors.toList()));
            }
            // 设置用户"匹配路径"列表
            redisTemp.set(key, matcherList, RedisConstant.ROUTE_EXPIRE);
            redisTemp.expire(key, RedisConstant.ROUTE_EXPIRE);
            // 设置用户"不可匹配路径"列表
            String key2 = RedisConstant.ROUTE_USER_PREFIX + id + RedisConstant.ROUTE_DIRECT_SUFFIX;
            redisTemp.sAddMulti(key2, directList);
            redisTemp.expire(key2, RedisConstant.ROUTE_EXPIRE);
        }
        return matcherList;
    }

    /**
     * 设置并获取路由"匹配路径"列表，通过所有角色
     *
     * @param roles 角色id
     * @return 所有路由"匹配路径"列表(已去重且排序)
     */
    private List<String> setAndGetMatcherListByRoles(List<Long> roles) {
        List<String> rolesList = new ArrayList<>();
        for (Long role : roles) {
            String roleString = RedisConstant.ROUTE_ROLE_PREFIX + role + RedisConstant.ROUTE_MATCHER_SUFFIX;
            rolesList.add(roleString);
            // 不存在角色的路由表，去创建
            if (Boolean.FALSE.equals(redisTemp.exists(roleString))) {
                setRouteByRoleId(role);
            }
        }
        return redisTemp.sUnionAll(rolesList) // 先取并集
                .stream().map(String.class::cast) // 转成字符串
                .sorted(Comparator.comparing(RouteInterceptor::urlCount)) // 排序
                .collect(Collectors.toList()); // 打包
    }

    /**
     * 创建路由表，通过角色id
     *
     * @param id 角色id
     */
    private void setRouteByRoleId(Long id) {
        // 获取该角色id的所有路由id
        List<String> routes =
                routeService.findByRoleId(id).stream().map(r -> r.getId().toString()).collect(Collectors.toList());
        // 获取"匹配路径"列表
        Collection<Object> routeMatcher = redisTemp.hGetMulti(RedisConstant.ROUTE_MATCHER, routes) // 必须去除null
                .stream().filter(Objects::nonNull).collect(Collectors.toList());
        // 不存在去创建
        if (routeMatcher.isEmpty()) {
            setRouteMatcher();
            // 再次获取"匹配路径"列表
            routeMatcher = redisTemp.hGetMulti(RedisConstant.ROUTE_MATCHER, routes) // 必须去除null
                    .stream().filter(Objects::nonNull).collect(Collectors.toList());
        }
        // 还是不存在，给一个占位符
        if (routeMatcher.isEmpty()) {
            routeMatcher.add(PLACEHOLDER);
        }
        // 设置该角色的"匹配路径"列表
        String key = RedisConstant.ROUTE_ROLE_PREFIX + id + RedisConstant.ROUTE_MATCHER_SUFFIX;
        redisTemp.sAddMulti(key, routeMatcher);
        redisTemp.expire(key, RedisConstant.ROUTE_EXPIRE);
        // 获取"不可匹配路径"列表(setAndGetRouteMatcher已创建)
        Collection<Object> routeDirect = redisTemp.hGetMulti(RedisConstant.ROUTE_DIRECT, routes) // 必须去除null
                .stream().filter(Objects::nonNull).collect(Collectors.toList());
        // 不存在，给一个占位符
        if (routeDirect.isEmpty()) {
            routeDirect.add(PLACEHOLDER);
        }
        // 设置该角色的"不可匹配路径"列表
        String key2 = RedisConstant.ROUTE_ROLE_PREFIX + id + RedisConstant.ROUTE_DIRECT_SUFFIX;
        redisTemp.sAddMulti(key2, routeDirect);
        redisTemp.expire(key2, RedisConstant.ROUTE_EXPIRE);
    }

    /**
     * 设置"匹配路径"列表<br>
     * "直接路径"列表也被设置
     */
    private void setRouteMatcher() {
        // 获取"匹配路径"列表和"不可匹配路径"列表
        RouteVo route = routeService.findExpandedList();
        // 创建"匹配路径"列表和"不可匹配路径"列表
        Map<String, String> matcherMap = route.getMatcher().stream() //
                .collect(Collectors.toMap(r -> r.getId().toString(), RouteVo::getPath));
        Map<String, String> directMap = route.getDirect().stream() //
                .collect(Collectors.toMap(r -> r.getId().toString(), RouteVo::getPath));
        redisTemp.hSetMulti(RedisConstant.ROUTE_MATCHER, matcherMap);
        redisTemp.hSetMulti(RedisConstant.ROUTE_DIRECT, directMap);
        redisTemp.expire(RedisConstant.ROUTE_MATCHER, RedisConstant.ROUTE_EXPIRE);
        redisTemp.expire(RedisConstant.ROUTE_DIRECT, RedisConstant.ROUTE_EXPIRE);
    }

    /**
     * URL按"/"分割并组合后的列表
     *
     * @param url URL
     * @return 列表
     */
    private static List<String> urlList(String url) {
        String[] split = url.split("/", -1);
        List<String> list = new ArrayList<>(split.length - 1);
        for (int i = 1; i < split.length; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 1; j < i + 1; j++) {
                sb.append("/");
                sb.append(split[j]);
            }
            list.add(sb.toString());
        }
        return list;
    }

    /**
     * URL中出现"/"的次数
     *
     * @param url URL
     * @return 次数
     */
    private static int urlCount(String url) {
        int count = 0;
        char[] chars = url.toCharArray();
        for (char a : chars) {
            if (a == '/') {
                count++;
            }
        }
        return count;
    }

    /**
     * 删除ROUTE_NOT_INTERCEPT
     */
    public void deleteRouteNotIntercept() {
        redisTemp.delete(RedisConstant.ROUTE_NOT_INTERCEPT);
    }

    /**
     * 删除ROUTE_MATCHER、ROUTE_DIRECT
     */
    public void deleteRoute() {
        redisTemp.delete(RedisConstant.ROUTE_MATCHER);
        redisTemp.delete(RedisConstant.ROUTE_DIRECT);
    }

    /**
     * 删除全部ROUTE_ROLE
     */
    public void deleteRouteRole() {
        redisTemp.deleteMulti(redisTemp.scan(RedisConstant.ROUTE_ROLE_PREFIX + "*"));
    }

    /**
     * 删除指定id的ROUTE_ROLE
     *
     * @param id 角色id
     */
    public void deleteRouteRole(long id) {
        List<String> keys = new ArrayList<>();
        keys.add(RedisConstant.ROUTE_ROLE_PREFIX + id + RedisConstant.ROUTE_DIRECT_SUFFIX);
        keys.add(RedisConstant.ROUTE_ROLE_PREFIX + id + RedisConstant.ROUTE_MATCHER_SUFFIX);
        redisTemp.deleteMulti(keys);
    }

    /**
     * 删除全部ROUTE_USER
     */
    public void deleteRouteUser() {
        redisTemp.deleteMulti(redisTemp.scan(RedisConstant.ROUTE_USER_PREFIX + "*"));
    }

    /**
     * 删除指定id的ROUTE_USER
     *
     * @param ids 用户id列表
     */
    public void deleteRouteUser(List<Long> ids) {
        List<String> keys = new ArrayList<>();
        for (Long id : ids) {
            keys.add(RedisConstant.ROUTE_USER_PREFIX + id + RedisConstant.ROUTE_DIRECT_SUFFIX);
            keys.add(RedisConstant.ROUTE_USER_PREFIX + id + RedisConstant.ROUTE_MATCHER_SUFFIX);
        }
        redisTemp.deleteMulti(keys);
    }

    /**
     * 删除指定id的ROUTE_USER
     *
     * @param id 用户id
     */
    public void deleteRouteUser(Long id) {
        List<String> keys = new ArrayList<>();
        keys.add(RedisConstant.ROUTE_USER_PREFIX + id + RedisConstant.ROUTE_DIRECT_SUFFIX);
        keys.add(RedisConstant.ROUTE_USER_PREFIX + id + RedisConstant.ROUTE_MATCHER_SUFFIX);
        redisTemp.deleteMulti(keys);
    }

}
