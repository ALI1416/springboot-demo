package com.demo.util.hutool.http;

import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * <h1>HTTP</h1>
 *
 * <p>
 * createDate 2023/04/06 14:18:28
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Slf4j
public class Main {

    public static void main(String[] args) {
        userAgent();
    }

    private static void userAgent() {
        UserAgent userAgent = UserAgentUtil.parse("Mozilla/5.0 (Windows NT 10.0; Win64; x64) " + //
                "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/95.0.4638.69 Safari/537.36");
        log.info("移动平台 {}", userAgent.isMobile());
        log.info("浏览器类型 {}", userAgent.getBrowser());
        log.info("浏览器版本 {}", userAgent.getVersion());
        log.info("平台类型 {}", userAgent.getPlatform());
        log.info("系统类型 {}", userAgent.getOs());
        log.info("系统版本 {}", userAgent.getOsVersion());
        log.info("引擎类型 {}", userAgent.getEngine());
        log.info("引擎版本 {}", userAgent.getEngineVersion());
    }

}
