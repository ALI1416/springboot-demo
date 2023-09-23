package com.demo.util.ip;

import cn.z.util.IpUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * <h1>IP工具</h1>
 *
 * <p>
 * createDate 2023/08/31 15:06:02
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Slf4j
public class Main {

    public static void main(String[] args) {

        /* ==================== IP ==================== */
        log.warn("IP");
        String ip = "123.45.67.90";
        long ipLong = IpUtils.ip2long(ip);
        log.info("ip2long(ip):" + ipLong);
        log.info("long2ip(ipLong):" + IpUtils.long2ip(ipLong));
        log.info("isValidIp(ip):" + IpUtils.isValidIp(ip));
        log.info("isValidIp(ipLong):" + IpUtils.isValidIp(ipLong));
        log.info("isValidIp(-1):" + IpUtils.isValidIp(-1));
        log.info("isValidIp(\"1.2.3.456\"):" + IpUtils.isValidIp("1.2.3.456"));

        /* ==================== 端口 ==================== */
        log.warn("端口");
        log.info("isValidPort(-1):" + IpUtils.isValidPort(-1));
        log.info("isValidPort(123):" + IpUtils.isValidPort(123));

        /* ==================== 子网掩码 ==================== */
        log.warn("子网掩码");
        String mask = "255.255.0.0";
        long maskLong = IpUtils.mask2long(mask);
        int maskBit = IpUtils.mask2maskBit(mask);
        log.info("mask2long(mask):" + maskLong);
        log.info("mask2maskBit(mask):" + maskBit);
        log.info("long2mask(maskLong):" + IpUtils.long2mask(maskLong));
        log.info("long2maskBit(maskLong):" + IpUtils.long2maskBit(maskLong));
        log.info("maskBit2mask(maskBit):" + IpUtils.maskBit2mask(maskBit));
        log.info("maskBit2long(maskBit):" + IpUtils.maskBit2long(maskBit));
        log.info("isValidMask(\"1.2.3.4\"):" + IpUtils.isValidMask("1.2.3.4"));
        log.info("isValidMask(mask):" + IpUtils.isValidMask(mask));
        log.info("isValidMask(-1L):" + IpUtils.isValidMask(-1L));
        log.info("isValidMask(maskLong):" + IpUtils.isValidMask(maskLong));
        log.info("isValidMask(-1):" + IpUtils.isValidMask(-1));
        log.info("isValidMask(maskBit):" + IpUtils.isValidMask(maskBit));
    }

}
