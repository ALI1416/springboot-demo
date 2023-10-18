package com.demo.interceptor;

import cn.z.tinytoken.T4s;
import com.demo.constant.ResultEnum;
import com.demo.entity.pojo.GlobalException;
import com.demo.service.RouteNotInterceptService;
import com.demo.service.RouteService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

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

    private final T4s t4s;
    private final RouteService routeService;
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
        if (routeNotInterceptService.isMatch(urlList)) {
            return true;
        }
        // 是"不拦截-直接路径"
        if (routeNotInterceptService.isDirect(url)) {
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
        if (routeNotInterceptService.isLoginMatch(urlList)) {
            return true;
        }
        // 是"不拦截-直接路径(需要登录)"
        if (routeNotInterceptService.isLoginDirect(url)) {
            return true;
        }
        // 是"匹配路径"
        if (routeService.isMatch(userId, urlList)) {
            return true;
        }
        // 是"直接路径"
        if (routeService.isDirect(userId, url)) {
            return true;
        }
        // 抛出"无权限异常"
        throw new GlobalException(ResultEnum.NOT_PERMISSION, "ID[" + userId + "],URL[" + url + "]");
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
