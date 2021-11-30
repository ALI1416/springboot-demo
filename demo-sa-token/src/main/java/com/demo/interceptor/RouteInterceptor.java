package com.demo.interceptor;

import cn.dev33.satoken.stp.StpUtil;
import com.demo.constant.RedisConstant;
import com.demo.entity.vo.RouteVo;
import com.demo.service.RoleService;
import com.demo.service.RouteService;
import com.demo.util.RedisUtils;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
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
@NoArgsConstructor
@AllArgsConstructor
public class RouteInterceptor implements HandlerInterceptor {

    private RoleService roleService;
    private RouteService routeService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 获取登录者id(没有登录会抛出异常)
        long id = StpUtil.getLoginIdAsLong();

        return true;
        // StpUtil.checkLogin();
        // String url = request.getRequestURI();
        // AntPathMatcher matcher = new AntPathMatcher();
        // List<String> permissionList = StpUtil.getPermissionList().stream().distinct().collect(Collectors.toList());
        // for (String permission : permissionList) {
        //     if (matcher.match(permission, url)) {
        //         return true;
        //     }
        // }
        // throw new NotPermissionException(url);
    }

    /**
     * 是匹配的路径
     *
     * @param id id
     * @return 是否匹配
     */
    private boolean isMatcher(long id) {
        List<String> matcherList = getMatcherList(id);
        return true;
    }

    /**
     * 在"不可匹配路径"中存在
     *
     * @param id id
     * @return 是否存在
     */
    private boolean existDirect(long id) {
        return true;
    }

    /**
     * 获取路由路径可匹配列表
     *
     * @param id id
     * @return 可匹配列表
     */
    @SuppressWarnings("unchecked")
    private List<String> getMatcherList(long id) {
        // 键名
        String key = RedisConstant.ROUTE + ":" + id + ":" + RedisConstant.MATCHER;
        // redis获取数据
        List<String> matcherList = (List<String>) RedisUtils.get(key);
        // 没有数据
        if (matcherList == null) {
            // 获取用户的所有拼接后的角色id
            List<String> roles =
                    roleService.findByUserId(id).stream().map(r -> RedisConstant.ROLE + ":" + r.getId()).collect(Collectors.toList());
            // 获取所有角色路由路径可匹配列表
            matcherList = getMatcherListByRoles(roles);
            // redis设置数据
            RedisUtils.set(key, matcherList, RedisConstant.ROUTE_EXPIRE);
        }
        return matcherList;
    }

    /**
     * 获取路由路径可匹配列表，通过所有角色
     *
     * @param roles 角色名(redis字段名)
     * @return 所有路由路径可匹配列表(已去重且排序)
     */
    private List<String> getMatcherListByRoles(List<String> roles) {
        List<String> list = new ArrayList<>();
        for (String role : roles) {
            list.addAll(getRoleRouteMatcherList(role));
        }
        sort(list);
        return list;
    }

    /**
     * 获取路由id,通过所有角色
     *
     * @param roles 所有角色
     * @return 路由id
     */
    private List<Long> getRouteIdByRoles(List<String> roles) {
        return null;
    }

    /**
     * 按照'/'字符出现的次数，从低到高排序
     *
     * @param list 列表
     */
    private static void sort(List<String> list) {
        Map<Integer, List<String>> map = new TreeMap<>();
        for (String s : list) {
            int count = charCount(s);
            if (!map.containsKey(count)) {
                map.put(count, new ArrayList<>());
            }
            map.get(count).add(s);
        }
        list.clear();
        for (List<String> value : map.values()) {
            list.addAll(value);
        }
    }

    /**
     * 计算字符串中出现'/'字符的次数
     *
     * @param str 字符串
     * @return 出现次数
     */
    private static int charCount(String str) {
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
     * 获取指定角色路由路径可匹配列表
     *
     * @param role 角色名(redis字段名)
     * @return 路由路径可匹配列表(已排序)
     */
    private List<String> getRoleRouteMatcherList(String role) {
        RouteVo routes = routeService.findExpandedList();
        RedisUtils.set(RedisConstant.ROUTE, routes, RedisConstant.ROUTE_EXPIRE);
        return null;
    }


}
