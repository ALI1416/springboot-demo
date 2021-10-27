package com.demo.base;

import cn.z.ip2region.Ip2Region;
import cn.z.ip2region.Region;
import com.demo.util.ClientInfoUtils;
import com.demo.util.UserAgentUtils;
import com.demo.util.pojo.UserAgentInfo;
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

    /* ==================== vo ==================== */
    /* -------------------- 大多数表 -------------------- */
    /**
     * 创建时间-否定
     */
    private Timestamp createTimeNot;
    /**
     * 创建时间-结束
     */
    private Timestamp createTimeEnd;
    /**
     * 更新时间-否定
     */
    private Timestamp updateTimeNot;
    /**
     * 更新时间-结束
     */
    private Timestamp updateTimeEnd;
    /**
     * 版本-否定
     */
    private Integer versionNot;
    /**
     * 版本-结束
     */
    private Integer versionEnd;
    /* -------------------- 分页 -------------------- */
    /**
     * 分页-页码
     */
    private Integer pages;
    /**
     * 分页-每页条数
     */
    private Integer rows;
    /**
     * 分页-排序
     */
    private String orderBy;

    /* ++++++++++++++++++++ 方法 ++++++++++++++++++++ */
    /* ==================== 日志类 ==================== */

    /**
     * 设置ip信息<br>
     * 包括ip,ipCountry,ipProvince,ipCity
     *
     * @param request HttpServletRequest
     */
    public void setIpInfo(HttpServletRequest request) {
        String ipString = ClientInfoUtils.getIp(request);
        Region region = Ip2Region.parse(ClientInfoUtils.getIp(request));
        setIp(ipString);
        setIpCountry(region.getCountry());
        setIpProvince(region.getProvince());
        setIpCity(region.getCity());
    }

    /**
     * 设置userAgent信息<br>
     * 包括userAgent,uaOs,uaBrowser,uaIsMobile
     *
     * @param request HttpServletRequest
     */
    public void setUserAgentInfo(HttpServletRequest request) {
        String userAgentString = ClientInfoUtils.getUserAgent(request);
        UserAgentInfo userAgentInfo = UserAgentUtils.getUserAgentInfo(userAgentString);
        setUserAgent(userAgentString);
        setUaOs(userAgentInfo.getOs());
        setUaBrowser(userAgentInfo.getBrowser());
        setUaIsMobile(userAgentInfo.getIsMobile());
    }

}
