package com.demo.base;

import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import cn.z.ip2region.Ip2Region;
import cn.z.ip2region.Region;
import lombok.Getter;
import lombok.Setter;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;

/**
 * <h1>实体层基类</h1>
 *
 * <p>
 * createDate 2020/11/11 11:11:11
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Getter
@Setter
public class EntityBase extends ToStringBase {

    /* ++++++++++++++++++++ 属性 ++++++++++++++++++++ */
    /* ==================== po ==================== */
    /* -------------------- 所有表 -------------------- */
    /**
     * id
     */
    private Long id;
    /* -------------------- 大多数表 -------------------- */
    /**
     * 已删除
     */
    private Integer isDelete;
    /**
     * 创建者id
     */
    private Long createId;
    /**
     * 创建时间
     */
    private Timestamp createTime;
    /**
     * 更新者id
     */
    private Long updateId;
    /**
     * 更新时间
     */
    private Timestamp updateTime;
    /**
     * 版本
     */
    private Integer version;
    /* -------------------- 备份、日志表 -------------------- */
    /**
     * 被备份的id/被登录的id
     */
    private Long refId;
    /* -------------------- 日志表 -------------------- */
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
    private String ipCountry;
    /**
     * IP地址-省份
     */
    private String ipProvince;
    /**
     * IP地址-城市
     */
    private String ipCity;
    /**
     * IP地址-ISP
     */
    private String ipIsp;
    /**
     * 浏览器标识-操作系统
     */
    private String uaOs;
    /**
     * 浏览器标识-浏览器
     */
    private String uaBrowser;
    /**
     * 浏览器标识-是手机
     */
    private Integer uaIsMobile;

    /* ++++++++++++++++++++ 方法 ++++++++++++++++++++ */
    /* ==================== 日志类 ==================== */

    /**
     * 设置ip信息<br>
     * 包括ip,ipCountry,ipProvince,ipCity,ipIsp
     *
     * @param request HttpServletRequest
     */
    public void setIpInfo(HttpServletRequest request) {
        String ipString = ServletUtil.getClientIP(request);
        setIp(ipString);
        try {
            Region region = Ip2Region.parse(ipString);
            setIpCountry(region.getCountry());
            setIpProvince(region.getProvince());
            setIpCity(region.getCity());
            setIpIsp(region.getIsp());
        } catch (Exception ignored) {
            setIpCountry("");
            setIpProvince("");
            setIpCity("");
            setIpIsp("");
        }
    }

    /**
     * 设置userAgent信息<br>
     * 包括userAgent,uaOs,uaBrowser,uaIsMobile
     *
     * @param request HttpServletRequest
     */
    public void setUserAgentInfo(HttpServletRequest request) {
        String userAgentString = request.getHeader("User-Agent");
        if (userAgentString == null) {
            setUserAgent("");
            setUaOs("");
            setUaBrowser("");
            setUaIsMobile(0);
        } else {
            setUserAgent(userAgentString);
            UserAgent userAgentInfo = UserAgentUtil.parse(userAgentString);
            if (userAgentInfo != null) {
                // 浏览器标识-操作系统
                String platformString = userAgentInfo.getPlatform().toString();
                if (!"Unknown".equals(platformString)) {
                    String osVersionString = userAgentInfo.getOsVersion();
                    if (osVersionString != null) {
                        setUaOs(platformString + " " + osVersionString);
                    } else {
                        setUaOs(platformString);
                    }
                } else {
                    setUaOs("");
                }
                // 浏览器标识-浏览器
                String browserString = userAgentInfo.getBrowser().toString();
                if (!"Unknown".equals(browserString)) {
                    String versionString = userAgentInfo.getVersion();
                    if (versionString != null) {
                        setUaBrowser(browserString + " " + versionString);
                    } else {
                        setUaBrowser(browserString);
                    }
                } else {
                    setUaBrowser("");
                }
                // 浏览器标识-是手机
                if (userAgentInfo.isMobile()) {
                    setUaIsMobile(1);
                } else {
                    setUaIsMobile(0);
                }
            }
        }
    }

}
