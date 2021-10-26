package com.demo.entity.po;

import cn.z.id.Id;
import com.demo.base.EntityBase;
import lombok.Getter;
import lombok.Setter;

import javax.servlet.http.HttpServletRequest;

/**
 * <h1>登录日志测试实体类</h1>
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
public class LoginLogTest extends EntityBase {

    /**
     * IP地址
     */
    public String ip;
    /**
     * 浏览器标识
     */
    public String userAgent;
    /**
     * IP地址-国家
     */
    public String ipCountry;
    /**
     * IP地址-省份
     */
    public String ipProvince;
    /**
     * IP地址-城市
     */
    public String ipCity;
    /**
     * 浏览器标识-操作系统
     */
    public String uaOs;
    /**
     * 浏览器标识-浏览器
     */
    public String uaBrowser;
    /**
     * 浏览器标识-是手机
     */
    public Integer uaIsMobile;

    /**
     * 构造函数
     */
    public LoginLogTest(HttpServletRequest request) {
        setId(Id.next());
        setIpInfo(request);
        setUserAgentInfo(request);
    }

}
