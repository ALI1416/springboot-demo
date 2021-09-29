package com.demo.util.pojo;

import cn.hutool.http.useragent.UserAgent;
import com.demo.base.ToStringBase;
import lombok.Getter;
import lombok.Setter;

/**
 * <h1>UserAgentInfo实体</h1>
 *
 * <p>
 * createDate 2021/01/15 11:42:21
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Getter
@Setter
public class UserAgentInfo extends ToStringBase {

    /**
     * 操作系统
     */
    private String os = "";
    /**
     * 浏览器
     */
    private String browser = "";
    /**
     * 是手机
     */
    private Integer isMobile = 0;

    /**
     * 构造函数
     */
    public UserAgentInfo() {

    }

    /**
     * 构造函数
     *
     * @param userAgent userAgent
     */
    public UserAgentInfo(UserAgent userAgent) {
        // null
        if (userAgent == null) {
            return;
        }
        // os
        if (!"Unknown".equals(userAgent.getBrowser().toString())) {
            os = userAgent.getBrowser().toString();
            if (userAgent.getVersion() != null) {
                os += " " + userAgent.getVersion();
            }
        }
        // browser
        if (!"Unknown".equals(userAgent.getPlatform().toString())) {
            browser = userAgent.getPlatform().toString();
            if (userAgent.getOsVersion() != null) {
                browser += " " + userAgent.getOsVersion();
            }
        }
        // isMobile
        if (userAgent.isMobile()) {
            isMobile = 1;
        }
    }

}
