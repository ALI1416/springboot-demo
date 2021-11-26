package com.demo.interceptor;

import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
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
public class RouteInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        StpUtil.checkLogin();
        String url = request.getRequestURI();
        AntPathMatcher matcher = new AntPathMatcher();
        List<String> permissionList = StpUtil.getPermissionList().stream().distinct().collect(Collectors.toList());
        for (String permission : permissionList) {
            if (matcher.match(permission, url)) {
                return true;
            }
        }
        throw new NotPermissionException(url);
    }

}
