package com.demo.ip2region;

import lombok.extern.slf4j.Slf4j;

/**
 * <h1>IP解析</h1>
 *
 * <p>
 * createDate 2022/02/22 10:18:51
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Slf4j
public class Main {

    public static void main(String[] args) {
        String filePath = "E:/file/ip2region/data.db";
        Ip2regionUtils.ip2RegionInit(filePath);
        log.info(Ip2regionUtils.getIpInfo("157.122.178.42").toString());
        log.info(Ip2regionUtils.getIpInfo("183.237.231.74").toString());
    }

}
