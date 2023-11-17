package com.demo.entity.po;

import cn.z.id.Id;
import cn.z.ip2region.Ip2Region;
import cn.z.ip2region.Region;
import cn.z.tool.useragent.UserAgent;
import com.demo.base.EntityBase;
import lombok.Getter;
import lombok.Setter;

import javax.servlet.http.HttpServletRequest;

/**
 * <h1>登录日志</h1>
 *
 * <p>
 * createDate 2021/09/29 14:18:44
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Getter
@Setter
public class LoginLog extends EntityBase {

    /**
     * IP地址
     */
    private String ip;
    /**
     * 浏览器标识
     */
    private String userAgent;
    /**
     * IP地址-国家
     */
    private String country;
    /**
     * IP地址-省份
     */
    private String province;
    /**
     * IP地址-城市
     */
    private String city;
    /**
     * IP地址-ISP
     */
    private String isp;
    /**
     * 浏览器标识-操作系统
     */
    private String os;
    /**
     * 浏览器标识-浏览器
     */
    private String browser;
    /**
     * 浏览器标识-是移动端
     */
    private Boolean isMobile;

    public LoginLog() {
    }

    public LoginLog(HttpServletRequest request) {
        setId(Id.next());
        setIpInfo(request);
        setUserAgentInfo(request);
    }

    /**
     * 设置IP信息-ip,country,province,city,isp
     *
     * @param request HttpServletRequest
     */
    public void setIpInfo(HttpServletRequest request) {
        String ipString = request.getHeader("X-Real-IP");
        if (ipString == null) {
            ipString = request.getRemoteAddr();
        }
        ip = ipString;
        try {
            Region region = Ip2Region.parse(ipString);
            country = region.getCountry();
            province = region.getProvince();
            city = region.getCity();
            isp = region.getIsp();
        } catch (Exception ignored) {
            country = "";
            province = "";
            city = "";
            isp = "";
        }
    }

    /**
     * 设置UserAgent信息-userAgent,os,browser,isMobile
     *
     * @param request HttpServletRequest
     */
    public void setUserAgentInfo(HttpServletRequest request) {
        String userAgentString = request.getHeader("User-Agent");
        if (userAgentString == null) {
            userAgent = "";
            os = "";
            browser = "";
            isMobile = false;
        } else {
            userAgent = userAgentString;
            UserAgent userAgentInfo = UserAgent.parse(userAgentString);
            // 浏览器标识-操作系统
            String userAgentInfoOs = userAgentInfo.getOs();
            if (userAgentInfoOs != null) {
                String userAgentInfoOsVersion = userAgentInfo.getOsVersion();
                if (userAgentInfoOsVersion != null) {
                    os = userAgentInfoOs + " " + userAgentInfoOsVersion;
                } else {
                    os = userAgentInfoOs;
                }
            } else {
                os = "";
            }
            // 浏览器标识-浏览器
            String userAgentInfoBrowser = userAgentInfo.getBrowser();
            if (userAgentInfoBrowser != null) {
                String userAgentInfoBrowserVersion = userAgentInfo.getBrowserVersion();
                if (userAgentInfoBrowserVersion != null) {
                    browser = userAgentInfoBrowser + " " + userAgentInfoBrowserVersion;
                } else {
                    browser = userAgentInfoBrowser;
                }
            } else {
                browser = "";
            }
            // 浏览器标识-是移动端
            isMobile = userAgentInfo.getMobile();
        }
    }

}
