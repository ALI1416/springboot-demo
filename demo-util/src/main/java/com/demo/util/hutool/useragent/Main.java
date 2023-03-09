package com.demo.util.hutool.useragent;

import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * <h1>UserAgent</h1>
 *
 * <p>
 * createDate 2022/02/22 14:55:04
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Slf4j
public class Main {

    public static void main(String[] args) {
        String uaStr = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/14.0.835.163" +
                " Safari/535.1";
        UserAgent ua = UserAgentUtil.parse(uaStr);
        log.info("浏览器名称:" + ua.getBrowser().toString());
        log.info("浏览器版本号:" + ua.getVersion());
        log.info("浏览器引擎名称:" + ua.getEngine().toString());
        log.info("浏览器引擎版本号:" + ua.getEngineVersion());
        log.info("操作系统名称:" + ua.getOs().toString());
        log.info("平台:" + ua.getPlatform().toString());
        log.info("是否为移动端:" + ua.isMobile());
    }

}
