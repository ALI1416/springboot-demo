package com.demo.interceptor;

import cn.z.tinytoken.T4s;
import com.demo.constant.RedisConstant;
import com.demo.constant.ResultEnum;
import com.demo.entity.po.RouteNotIntercept;
import com.demo.entity.pojo.GlobalException;
import com.demo.entity.vo.RouteNotInterceptVo;
import com.demo.entity.vo.RouteVo;
import com.demo.service.RoleService;
import com.demo.service.RouteNotInterceptService;
import com.demo.service.RouteService;
import com.demo.tool.RedisTemp;
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
        // 是"不拦截路径"
        if (isNotIntercept(url)) {
            return true;
        }
        // 获取"登录者id"
        Long id = t4s.getId();
        // 抛出"未登录异常"
        if (id == null) {
            throw new GlobalException(ResultEnum.NOT_LOGIN);
        }
        // 是"root"用户
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
        throw new GlobalException(ResultEnum.NOT_PERMISSION, "ID[" + id + "],URL[" + url + "]");
    }

    /**
     * 是"不拦截路径"
     *
     * @param url URL
     * @return 是否
     */
    private boolean isNotIntercept(String url) {
        String key = RedisConstant.ROUTE_NOT_INTERCEPT;
        // 判断值是否存在
        if (Boolean.TRUE.equals(redisTemp.sIsMember(key, url))) {
            // 存在
            return true;
        }
        // 判断key是否存在
        if (Boolean.TRUE.equals(redisTemp.exists(key))) {
            // 存在
            return false;
        } else {
            // 不存在，去添加
            setNotIntercept();
        }
        // 判断值是否存在
        return redisTemp.sIsMember(key, url);
    }

    /**
     * 是"匹配路径"
     *
     * @param id  用户id
     * @param url URL
     * @return 是否
     */
    private boolean isMatcher(Long id, String url) {
        String key = RedisConstant.ROUTE_USER_PREFIX + id + RedisConstant.ROUTE_MATCHER_SUFFIX;
        List<String> urlList = urlList(url);
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
            setUserRoute(id);
        }
        // 判断值是否存在
        return redisTemp.sIsMemberMulti(key, urlList).containsValue(true);
    }

    /**
     * 是"直接路径"
     *
     * @param id  用户id
     * @param url URL
     * @return 是否
     */
    private boolean isDirect(Long id, String url) {
        String key = RedisConstant.ROUTE_USER_PREFIX + id + RedisConstant.ROUTE_DIRECT_SUFFIX;
        // 判断值是否存在(setUserRoute已创建)
        return redisTemp.sIsMember(key, url);
    }

    /**
     * 创建"不拦截路径"
     */
    private void setNotIntercept() {
        String key = RedisConstant.ROUTE_NOT_INTERCEPT;
        // 获取"不拦截路径"
        List<RouteNotInterceptVo> notIntercept = routeNotInterceptService.findAll();
        List<String> notInterceptPath = new ArrayList<>();
        // 判断是否有"不拦截路径"
        if (notIntercept.isEmpty()) {
            // 不存在，给一个占位符
            notInterceptPath.add(PLACEHOLDER);
        } else {
            // 存在，提取路径
            notInterceptPath = notIntercept.stream().map(RouteNotIntercept::getPath).collect(Collectors.toList());
        }
        // 创建"不拦截路径"
        redisTemp.sAddMulti(key, notInterceptPath);
        redisTemp.expire(key, RedisConstant.ROUTE_EXPIRE);
    }

    /**
     * 创建用户"匹配路径"和"直接路径"
     *
     * @param id 用户id
     */
    private void setUserRoute(Long id) {
        Set<Object> matcherList = new HashSet<>();
        Set<Object> directList = new HashSet<>();
        // 获取该用户的角色
        List<Long> roleIdList = roleService.findIdByUserId(id);
        // 判断是否有角色
        if (roleIdList.isEmpty()) {
            // 不存在，给一个占位符
            matcherList.add(PLACEHOLDER);
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
        String key = RedisConstant.ROUTE_USER_PREFIX + id + RedisConstant.ROUTE_MATCHER_SUFFIX;
        redisTemp.sAddMulti(key, matcherList);
        redisTemp.expire(key, RedisConstant.ROUTE_EXPIRE);
        String key2 = RedisConstant.ROUTE_USER_PREFIX + id + RedisConstant.ROUTE_DIRECT_SUFFIX;
        redisTemp.sAddMulti(key2, directList);
        redisTemp.expire(key2, RedisConstant.ROUTE_EXPIRE);
    }

    /**
     * 创建"匹配路径"和"直接路径"，通过角色id
     *
     * @param ids 角色id
     */
    private void setRouteByRoleIdList(List<Long> ids) {
        for (Long id : ids) {
            String key = RedisConstant.ROUTE_ROLE_PREFIX + id + RedisConstant.ROUTE_MATCHER_SUFFIX;
            // 判断是否存在该角色的"匹配路径"和"直接路径"
            if (Boolean.FALSE.equals(redisTemp.exists(key))) {
                // 不存在，去创建
                setRouteByRoleId(id);
            }
        }
    }

    /**
     * 创建"匹配路径"和"直接路径"，通过角色id
     *
     * @param id 角色id
     */
    private void setRouteByRoleId(Long id) {
        // 判断是否存在根"匹配路径"
        if (Boolean.FALSE.equals(redisTemp.exists(RedisConstant.ROUTE_MATCHER))) {
            // 不存在，去创建
            setRoute();
        }
        // 获取该角色的路由
        List<String> routeIdList = routeService.findByRoleId(id) //
                .stream().map(r -> r.getId().toString()).collect(Collectors.toList());
        // 获取该角色的"匹配路径"(必须去除null)
        Collection<Object> routeMatcher = redisTemp.hGetMulti(RedisConstant.ROUTE_MATCHER, routeIdList) //
                .stream().filter(Objects::nonNull).collect(Collectors.toList());
        // 判断是否有"匹配路径"
        if (routeMatcher.isEmpty()) {
            // 不存在，给一个占位符
            routeMatcher.add(PLACEHOLDER);
        }
        // 获取该角色的"直接路径"(setRoute已创建)(必须去除null)
        Collection<Object> routeDirect = redisTemp.hGetMulti(RedisConstant.ROUTE_DIRECT, routeIdList) //
                .stream().filter(Objects::nonNull).collect(Collectors.toList());
        // 判断是否有"直接路径"
        if (routeDirect.isEmpty()) {
            // 不存在，给一个占位符
            routeDirect.add(PLACEHOLDER);
        }
        // 创建该角色的"匹配路径"
        String key = RedisConstant.ROUTE_ROLE_PREFIX + id + RedisConstant.ROUTE_MATCHER_SUFFIX;
        redisTemp.sAddMulti(key, routeMatcher);
        redisTemp.expire(key, RedisConstant.ROUTE_EXPIRE);
        // 创建该角色的"直接路径"
        String key2 = RedisConstant.ROUTE_ROLE_PREFIX + id + RedisConstant.ROUTE_DIRECT_SUFFIX;
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

    /**
     * 删除Redis全部不拦截路由
     *
     * @return 是否成功
     */
    public Boolean deleteRouteNotIntercept() {
        return redisTemp.delete(RedisConstant.ROUTE_NOT_INTERCEPT);
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
     * @param id 角色id
     * @return 成功条数
     * @see #deleteRouteUser(List)
     */
    public Long deleteRouteRole(Long id) {
        return redisTemp.deleteMulti(RedisConstant.ROUTE_ROLE_PREFIX + id + RedisConstant.ROUTE_DIRECT_SUFFIX, //
                RedisConstant.ROUTE_ROLE_PREFIX + id + RedisConstant.ROUTE_MATCHER_SUFFIX);
    }

    /**
     * 删除Redis用户路由，通过用户id列表
     *
     * @param ids 用户id列表
     */
    public Long deleteRouteUser(List<Long> ids) {
        List<String> keys = new ArrayList<>();
        for (Long id : ids) {
            keys.add(RedisConstant.ROUTE_USER_PREFIX + id + RedisConstant.ROUTE_DIRECT_SUFFIX);
            keys.add(RedisConstant.ROUTE_USER_PREFIX + id + RedisConstant.ROUTE_MATCHER_SUFFIX);
        }
        return redisTemp.deleteMulti(keys);
    }

    /**
     * 删除Redis用户路由，通过用户id
     *
     * @param id 用户id
     */
    public Long deleteRouteUser(Long id) {
        return redisTemp.deleteMulti(RedisConstant.ROUTE_USER_PREFIX + id + RedisConstant.ROUTE_DIRECT_SUFFIX, //
                RedisConstant.ROUTE_USER_PREFIX + id + RedisConstant.ROUTE_MATCHER_SUFFIX);
    }

}
