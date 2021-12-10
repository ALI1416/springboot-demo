package com.demo.interceptor;

import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.stp.StpUtil;
import com.demo.constant.RedisConstant;
import com.demo.entity.po.RouteNotIntercept;
import com.demo.entity.vo.RoleVo;
import com.demo.entity.vo.RouteNotInterceptVo;
import com.demo.entity.vo.RouteVo;
import com.demo.service.RoleService;
import com.demo.service.RouteNotInterceptService;
import com.demo.service.RouteService;
import com.demo.util.RedisUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <h1>RouteInterceptor</h1>
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
     * 路由占位符：当路由为空时，使用{@value}占位<br>
     * 由于URL都是由'/'开头，所以该占位符不能以'/'开头
     */
    private static final String PLACEHOLDER = "placeholder";
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
     * @throws cn.dev33.satoken.exception.NotLoginException      用户没有登录时
     * @throws cn.dev33.satoken.exception.NotPermissionException 用户没有权限访问该URL
     */
    @Override
    @SuppressWarnings("all")
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 前端预检查不拦截
        if ("OPTIONS".equals(request.getMethod())) {
            return true;
        }
        // 去除项目路径context-path的干扰
        String url = request.getServletPath();
        // 是不拦截的路径
        if (isNotIntercept(url)) {
            return true;
        }
        // 获取登录者id(没有登录会抛出未登录异常)
        long id = StpUtil.getLoginIdAsLong();
        // root用户
        if (id == 0L) {
            return true;
        }
        // 是"匹配路径"
        if (isMatcher(id, url)) {
            return true;
        }
        // 在"不可匹配路径"中存在
        if (isDirect(id, url)) {
            return true;
        }
        // 抛出无权限异常
        throw new NotPermissionException(url);
    }

    /**
     * 是不拦截的路径
     *
     * @param url url
     * @return 是否不拦截
     */
    private boolean isNotIntercept(String url) {
        String key = RedisConstant.ROUTE_NOT_INTERCEPT;
        // 查询是否存在key
        // 不存在去添加
        if (!RedisUtils.exists(key)) {
            setNotIntercept();
        }
        // 存在，直接判断值是否存在
        return RedisUtils.sIsMember(key, url);
    }

    /**
     * 设置不拦截路径
     */
    private void setNotIntercept() {
        String key = RedisConstant.ROUTE_NOT_INTERCEPT;
        // 数据库查询
        List<RouteNotInterceptVo> notIntercept = routeNotInterceptService.findAll();
        List<String> notInterceptPath = new ArrayList<>();
        // 不存在，给一个占位符
        if (notIntercept.size() == 0) {
            notInterceptPath.add(PLACEHOLDER);
        } else {
            notInterceptPath = notIntercept.stream().map(RouteNotIntercept::getPath).collect(Collectors.toList());
        }
        // 添加key，并设置过期时间
        RedisUtils.sAddMulti(key, notInterceptPath);
        RedisUtils.expire(key, RedisConstant.ROUTE_EXPIRE);
    }

    /**
     * 是"匹配路径"
     *
     * @param id  用户id
     * @param url url
     * @return 是否匹配
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
     * 在"不可匹配路径"中存在
     *
     * @param id  用户id
     * @param url url
     * @return 是否存在
     */
    private boolean isDirect(long id, String url) {
        String key = RedisConstant.ROUTE_USER_PREFIX + id + RedisConstant.ROUTE_DIRECT_SUFFIX;
        // 判断值是否存在(setAndGetUserMatcherList已创建)
        return RedisUtils.sIsMember(key, url);
    }

    /**
     * 设置并获取用户"匹配路径"列表<br>
     * 也设置用户"不可匹配路径"列表
     *
     * @param id 用户id
     * @return "匹配路径"列表
     */
    @SuppressWarnings("unchecked")
    private List<String> setAndGetUserMatcherList(long id) {
        String key = RedisConstant.ROUTE_USER_PREFIX + id + RedisConstant.ROUTE_MATCHER_SUFFIX;
        // 获取用户"匹配路径"列表
        List<String> matcherList = (List<String>) RedisUtils.get(key);
        Set<Object> directList;
        // 没有数据
        if (matcherList == null) {
            // 获取用户拥有的角色id
            List<Long> roles = roleService.findOwnByUserId(id).stream().map(RoleVo::getId).collect(Collectors.toList());
            // 用户没有角色，给一个占位符
            if (roles.size() == 0) {
                matcherList = new ArrayList<>();
                matcherList.add(PLACEHOLDER);
                directList = new HashSet<>();
                directList.add(PLACEHOLDER);
            } else {
                // 设置并获取所有角色路由路径可匹配列表
                matcherList = setAndGetMatcherListByRoles(roles);
                // 读取用户"不可匹配路径"列表(setRouteByRoleId已创建)
                directList = RedisUtils.sUnionAll(roles.stream()//
                        .map(r -> RedisConstant.ROUTE_ROLE_PREFIX + r + RedisConstant.ROUTE_DIRECT_SUFFIX)//
                        .collect(Collectors.toList()));
            }
            // 设置用户"匹配路径"列表
            RedisUtils.set(key, matcherList, RedisConstant.ROUTE_EXPIRE);
            RedisUtils.expire(key, RedisConstant.ROUTE_EXPIRE);
            // 设置用户"不可匹配路径"列表
            String key2 = RedisConstant.ROUTE_USER_PREFIX + id + RedisConstant.ROUTE_DIRECT_SUFFIX;
            RedisUtils.sAddMulti(key2, directList);
            RedisUtils.expire(key2, RedisConstant.ROUTE_EXPIRE);
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
            if (!RedisUtils.exists(roleString)) {
                setRouteByRoleId(role);
            }
        }
        return RedisUtils.sUnionAll(rolesList) // 先取并集
                .stream().map(obj -> (String) obj) // 转成字符串
                .sorted(Comparator.comparing(this::charCount)) // 排序
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
        Collection<Object> routeMatcher = RedisUtils.hGetMulti(RedisConstant.ROUTE_MATCHER, routes) // 必须去除null
                .stream().filter(Objects::nonNull).collect(Collectors.toList());
        // 不存在去创建
        if (routeMatcher.size() == 0) {
            setRouteMatcher();
            // 再次获取"匹配路径"列表
            routeMatcher = RedisUtils.hGetMulti(RedisConstant.ROUTE_MATCHER, routes) // 必须去除null
                    .stream().filter(Objects::nonNull).collect(Collectors.toList());
        }
        // 还是不存在，给一个占位符
        if (routeMatcher.size() == 0) {
            routeMatcher.add(PLACEHOLDER);
        }
        // 设置该角色的"匹配路径"列表
        String key = RedisConstant.ROUTE_ROLE_PREFIX + id + RedisConstant.ROUTE_MATCHER_SUFFIX;
        RedisUtils.sAddMulti(key, routeMatcher);
        RedisUtils.expire(key, RedisConstant.ROUTE_EXPIRE);
        // 获取"不可匹配路径"列表(setAndGetRouteMatcher已创建)
        Collection<Object> routeDirect = RedisUtils.hGetMulti(RedisConstant.ROUTE_DIRECT, routes) // 必须去除null
                .stream().filter(Objects::nonNull).collect(Collectors.toList());
        // 不存在，给一个占位符
        if (routeDirect.size() == 0) {
            routeDirect.add(PLACEHOLDER);
        }
        // 设置该角色的"不可匹配路径"列表
        String key2 = RedisConstant.ROUTE_ROLE_PREFIX + id + RedisConstant.ROUTE_DIRECT_SUFFIX;
        RedisUtils.sAddMulti(key2, routeDirect);
        RedisUtils.expire(key2, RedisConstant.ROUTE_EXPIRE);
    }

    /**
     * 设置"匹配路径"列表<br>
     * "不可匹配路径"列表也被设置
     */
    private void setRouteMatcher() {
        // 获取"匹配路径"列表和"不可匹配路径"列表
        RouteVo route = routeService.findExpandedList();
        // 创建"匹配路径"列表和"不可匹配路径"列表
        Map<String, String> matcherMap = route.getMatcher().stream() //
                .collect(Collectors.toMap(r -> r.getId().toString(), RouteVo::getPath));
        Map<String, String> directMap = route.getDirect().stream() //
                .collect(Collectors.toMap(r -> r.getId().toString(), RouteVo::getPath));
        RedisUtils.hSetMulti(RedisConstant.ROUTE_MATCHER, matcherMap);
        RedisUtils.hSetMulti(RedisConstant.ROUTE_DIRECT, directMap);
        RedisUtils.expire(RedisConstant.ROUTE_MATCHER, RedisConstant.ROUTE_EXPIRE);
        RedisUtils.expire(RedisConstant.ROUTE_DIRECT, RedisConstant.ROUTE_EXPIRE);
    }

    /**
     * 计算字符串中出现'/'字符的次数
     *
     * @param str 字符串
     * @return 出现次数
     */
    private int charCount(String str) {
        int count = 0;
        char[] chars = str.toCharArray();
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
        RedisUtils.delete(RedisConstant.ROUTE_NOT_INTERCEPT);
    }

    /**
     * 删除ROUTE_MATCHER、ROUTE_DIRECT
     */
    public void deleteRoute() {
        RedisUtils.delete(RedisConstant.ROUTE_MATCHER);
        RedisUtils.delete(RedisConstant.ROUTE_DIRECT);
    }

    /**
     * 删除全部ROUTE_ROLE
     */
    public void deleteRouteRole() {
        Set<String> keys = RedisUtils.scan(RedisConstant.ROUTE_ROLE_PREFIX + "*");
        RedisUtils.deleteMulti(keys);
    }

    /**
     * 删除指定id的ROUTE_ROLE
     *
     * @param id 角色id
     */
    public void deleteRouteRole(long id) {
        Set<String> keys = new HashSet<>();
        keys.add(RedisConstant.ROUTE_ROLE_PREFIX + id + RedisConstant.ROUTE_DIRECT_SUFFIX);
        keys.add(RedisConstant.ROUTE_ROLE_PREFIX + id + RedisConstant.ROUTE_MATCHER_SUFFIX);
        RedisUtils.deleteMulti(keys);
    }

    /**
     * 删除全部ROUTE_USER
     */
    public void deleteRouteUser() {
        Set<String> keys = RedisUtils.scan(RedisConstant.ROUTE_USER_PREFIX + "*");
        RedisUtils.deleteMulti(keys);
    }

    /**
     * 删除指定id的ROUTE_USER
     *
     * @param ids 用户id列表
     */
    public void deleteRouteUser(List<Long> ids) {
        Set<String> keys = new HashSet<>();
        for (Long id : ids) {
            keys.add(RedisConstant.ROUTE_USER_PREFIX + id + RedisConstant.ROUTE_DIRECT_SUFFIX);
            keys.add(RedisConstant.ROUTE_USER_PREFIX + id + RedisConstant.ROUTE_MATCHER_SUFFIX);
        }
        RedisUtils.deleteMulti(keys);
    }

}
