package com.demo.base;

import cn.z.ip2region.Ip2Region;
import cn.z.ip2region.Region;
import cn.z.tool.useragent.UserAgent;
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
     * 浏览器标识-是移动端
     */
    private Integer uaIsMobile;

    /* ++++++++++++++++++++ 方法 ++++++++++++++++++++ */
    /* ==================== 日志类 ==================== */

    /**
     * 设置IP信息<br>
     * 包括ip,ipCountry,ipProvince,ipCity,ipIsp
     *
     * @param request HttpServletRequest
     */
    public void setIpInfo(HttpServletRequest request) {
        String ipString = request.getRemoteAddr();
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
     * 设置UserAgent信息<br>
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
            UserAgent userAgentInfo = UserAgent.parse(userAgentString);
            // 浏览器标识-操作系统
            String os = userAgentInfo.getOs();
            if (os != null) {
                String osVersion = userAgentInfo.getOsVersion();
                if (osVersion != null) {
                    setUaOs(os + " " + osVersion);
                } else {
                    setUaOs(os);
                }
            } else {
                setUaOs("");
            }
            // 浏览器标识-浏览器
            String browser = userAgentInfo.getBrowser();
            if (browser != null) {
                String browserVersion = userAgentInfo.getBrowserVersion();
                if (browserVersion != null) {
                    setUaBrowser(browser + " " + browserVersion);
                } else {
                    setUaBrowser(browser);
                }
            } else {
                setUaBrowser("");
            }
            // 浏览器标识-是移动端
            if (userAgentInfo.isMobile()) {
                setUaIsMobile(1);
            } else {
                setUaIsMobile(0);
            }
        }
    }

}
