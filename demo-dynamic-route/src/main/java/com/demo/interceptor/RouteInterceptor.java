package com.demo.interceptor;

import cn.z.redis.RedisTemp;
import cn.z.tinytoken.T4s;
import com.demo.constant.RedisConstant;
import com.demo.constant.ResultEnum;
import com.demo.entity.pojo.GlobalException;
import com.demo.entity.vo.RouteVo;
import com.demo.service.RoleService;
import com.demo.service.RouteNotInterceptService;
import com.demo.service.RouteService;
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
     * @return 是否放行
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 请求方法"预检查"放行
        if ("OPTIONS".equals(request.getMethod())) {
            return true;
        }
        // 获取"URL"
        String url = request.getServletPath();
        // URL按"/"分割并组合后的列表
        List<String> urlList = urlList(url);
        // 是"不拦截-匹配路径"
        if (routeNotInterceptService.isNotInterceptMatch(urlList)) {
            return true;
        }
        // 是"不拦截-直接路径"
        if (routeNotInterceptService.isNotInterceptDirect(url)) {
            return true;
        }
        // 获取"用户id"
        Long userId = t4s.getId();
        // 抛出"未登录异常"
        if (userId == null) {
            throw new GlobalException(ResultEnum.NOT_LOGIN);
        }
        // 是"root"用户
        if (userId == 0L) {
            return true;
        }
        // 是"不拦截-匹配路径(需要登录)"
        if (routeNotInterceptService.isNotInterceptLoginMatch(urlList)) {
            return true;
        }
        // 是"不拦截-直接路径(需要登录)"
        if (routeNotInterceptService.isNotInterceptLoginDirect(url)) {
            return true;
        }
        // 是"匹配路径"
        if (isMatcher(userId, urlList)) {
            return true;
        }
        // 是"直接路径"
        if (isDirect(userId, url)) {
            return true;
        }
        // 抛出"无权限异常"
        throw new GlobalException(ResultEnum.NOT_PERMISSION, "ID[" + userId + "],URL[" + url + "]");
    }

    /**
     * 是"匹配路径"
     *
     * @param userId  用户id
     * @param urlList URL列表
     * @return 是否
     */
    private boolean isMatcher(long userId, List<String> urlList) {
        String key = RedisConstant.ROUTE_USER_PREFIX + userId + RedisConstant.ROUTE_MATCHER_SUFFIX;
        // 判断值是否存在
        if (Boolean.TRUE.equals(redisTemp.sIsMemberMulti(key, urlList).containsValue(true))) {
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
    private boolean isDirect(long userId, String url) {
        String key = RedisConstant.ROUTE_USER_PREFIX + userId + RedisConstant.ROUTE_DIRECT_SUFFIX;
        // 判断值是否存在(setUserRoute已创建)
        return redisTemp.sIsMember(key, url);
    }

    /**
     * 创建用户"匹配路径"和"直接路径"
     *
     * @param userId 用户id
     */
    private void setUserRoute(long userId) {
        Set<Object> matcherList;
        Set<Object> directList;
        // 获取该用户的角色
        List<Long> roleIdList = roleService.findIdByUserId(userId);
        // 判断是否有角色
        if (roleIdList.isEmpty()) {
            // 不存在，给一个占位符
            matcherList = new HashSet<>();
            matcherList.add(PLACEHOLDER);
            directList = new HashSet<>();
            directList.add(PLACEHOLDER);
        } else {
            // 创建角色的"匹配路径"和"直接路径"
            setRouteByRoleIdList(roleIdList);
            // 获取该用户的"匹配路径"和"直接路径"
            matcherList = redisTemp.sUnionAll(roleIdList.stream() //
                    .map(r -> RedisConstant.ROUTE_ROLE_PREFIX + r + RedisConstant.ROUTE_MATCHER_SUFFIX) //
                    .collect(Collectors.toList()));
            directList = redisTemp.sUnionAll(roleIdList.stream() //
                    .map(r -> RedisConstant.ROUTE_ROLE_PREFIX + r + RedisConstant.ROUTE_DIRECT_SUFFIX) //
                    .collect(Collectors.toList()));
        }
        // 创建该用户的"匹配路径"和"直接路径"
        String key = RedisConstant.ROUTE_USER_PREFIX + userId + RedisConstant.ROUTE_MATCHER_SUFFIX;
        redisTemp.sAddMulti(key, matcherList);
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
    private void setRouteByRoleIdList(List<Long> roleIdList) {
        for (Long roleId : roleIdList) {
            String key = RedisConstant.ROUTE_ROLE_PREFIX + roleId + RedisConstant.ROUTE_MATCHER_SUFFIX;
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
    private void setRouteByRoleId(long roleId) {
        // 判断是否存在根"匹配路径"
        if (Boolean.FALSE.equals(redisTemp.exists(RedisConstant.ROUTE_MATCHER))) {
            // 不存在，去创建
            setRoute();
        }
        // 获取该角色的路由
        List<String> routeIdList = routeService.findByRoleId(roleId) //
                .stream().map(r -> r.getId().toString()).collect(Collectors.toList());
        // 获取该角色的"匹配路径"(去除null)
        Collection<Object> routeMatcher = redisTemp.hGetMulti(RedisConstant.ROUTE_MATCHER, routeIdList) //
                .stream().filter(Objects::nonNull).collect(Collectors.toList());
        // 判断是否有"匹配路径"
        if (routeMatcher.isEmpty()) {
            // 不存在，给一个占位符
            routeMatcher.add(PLACEHOLDER);
        }
        // 获取该角色的"直接路径"(setRoute已创建)(去除null)
        Collection<Object> routeDirect = redisTemp.hGetMulti(RedisConstant.ROUTE_DIRECT, routeIdList) //
                .stream().filter(Objects::nonNull).collect(Collectors.toList());
        // 判断是否有"直接路径"
        if (routeDirect.isEmpty()) {
            // 不存在，给一个占位符
            routeDirect.add(PLACEHOLDER);
        }
        // 创建该角色的"匹配路径"
        String key = RedisConstant.ROUTE_ROLE_PREFIX + roleId + RedisConstant.ROUTE_MATCHER_SUFFIX;
        redisTemp.sAddMulti(key, routeMatcher);
        redisTemp.expire(key, RedisConstant.ROUTE_EXPIRE);
        // 创建该角色的"直接路径"
        String key2 = RedisConstant.ROUTE_ROLE_PREFIX + roleId + RedisConstant.ROUTE_DIRECT_SUFFIX;
        redisTemp.sAddMulti(key2, routeDirect);
        redisTemp.expire(key2, RedisConstant.ROUTE_EXPIRE);
    }

    /**
     * 创建根"匹配路径"和"直接路径"
     */
    private void setRoute() {
        // 获取根"匹配路径"和"直接路径"
        RouteVo route = routeService.findExpandedList();
        // 创建根"匹配路径"和"直接路径"
        Map<String, String> matcher = route.getMatcher() //
                .stream().collect(Collectors.toMap(r -> r.getId().toString(), RouteVo::getPath));
        Map<String, String> direct = route.getDirect() //
                .stream().collect(Collectors.toMap(r -> r.getId().toString(), RouteVo::getPath));
        redisTemp.hSetMulti(RedisConstant.ROUTE_MATCHER, matcher);
        redisTemp.hSetMulti(RedisConstant.ROUTE_DIRECT, direct);
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
        List<String> list = new ArrayList<>();
        list.add("/");
        if (url.length() == 1) {
            return list;
        }
        String[] split = url.split("/", -1);
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

}
