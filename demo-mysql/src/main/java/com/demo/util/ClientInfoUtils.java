package com.demo.util;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;

/**
 * <h1>客户端信息工具</h1>
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
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if ("127.0.0.1".equals(ip) || "0:0:0:0:0:0:0:1".equals(ip)) {
                // 根据网卡获取本机配置的IP
                try {
                    ip = InetAddress.getLocalHost().getHostAddress();
                } catch (Exception ignored) {
                }
            }
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = "0.0.0.0";
        }
        // 对于通过多个代理的情况，第一个IP为客户端真实IP，多个IP按照','分割
        if (ip.indexOf(",") > 0) {
            ip = ip.substring(0, ip.indexOf(","));
        }
        return ip;
    }

    /**
     * 获取UserAgent
     *
     * @param request HttpServletRequest
     */
    public static String getUserAgent(HttpServletRequest request) {
        String userAgentString = request.getHeader("User-Agent");
        if (userAgentString == null) {
            return "";
        }
        return userAgentString;
    }

}
