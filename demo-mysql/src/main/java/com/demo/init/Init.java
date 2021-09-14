package com.demo.init;

import lombok.extern.slf4j.Slf4j;

/**
 * <h1>总初始化</h1>
 *
 * <p>
 * createDate 2020/11/21 09:28:54
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Slf4j
public class Init {

    public static void init() {
        log.info("初始化开始...");
        Ip2RegionInit.init();
        log.info("初始化结束。");
    }

}
