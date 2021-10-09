package com.demo.util;

import cn.hutool.extra.servlet.ServletUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * <h1>客户端信息工具类</h1>
 *
 * <p>
 * createDate 2021/01/12 21:29:25
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class ClientInfoUtils {

    /**
     * 获取IP
     *
     * @param request HttpServletRequest
     */
    public static String getIp(HttpServletRequest request) {
        return ServletUtil.getClientIP(request);
    }

    /**
     * 获取UserAgent
     *
     * @param request HttpServletRequest
     */
    public static String getUserAgent(HttpServletRequest request) {
        String userAgentString = request.getHeader("User-Agent");
        return userAgentString == null ? "" : userAgentString;
    }

}
