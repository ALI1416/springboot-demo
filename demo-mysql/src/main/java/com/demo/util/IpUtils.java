package com.demo.util;

import com.demo.property.Ip2RegionProperty;
import com.demo.util.pojo.IpInfo;
import lombok.extern.slf4j.Slf4j;
import org.lionsoul.ip2region.DataBlock;
import org.lionsoul.ip2region.DbConfig;
import org.lionsoul.ip2region.DbSearcher;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

/**
 * <h1>IP工具</h1>
 *
 * <p>
 * createDate 2020/11/18 10:27:10
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Component
@Slf4j
public class IpUtils {

    /**
     * IP地址正则表达式
     */
    private final static String IP_PATTERN = "^((?:(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(?:25[0-5]" +
            "|2[0-4]\\d|((1\\d{2})|([1-9]?\\d))))$";
    /**
     * ip2region搜索实例
     */
    private static DbSearcher ip2regionSearcher = null;

    public static void main(String[] args) {
        String ip = "202.108.22.5";
        // String ip = "255.255.255.255";
        // String ip = "0.0.0.0";
        System.out.println(ip);

        boolean isIp = isIp(ip);
        System.out.println(isIp);

        long longIp = ip2Long(ip);
        System.out.println(longIp);

        boolean isIp2 = isIp(longIp);
        System.out.println(isIp2);

        String strIp = long2Ip(longIp);
        System.out.println(strIp);

        ip2RegionInitial();
        System.out.println(getIpInfo("157.122.178.42"));
    }

    /**
     * 初始化DbSearcher实例
     *
     * @see org.lionsoul.ip2region.DbConfig#DbConfig()
     * @see org.lionsoul.ip2region.DbSearcher#DbSearcher(DbConfig dbConfig, String dbFile)
     */
    public static void ip2RegionInitial() {
        if (ip2regionSearcher == null) {
            synchronized (IpUtils.class) {
                if (ip2regionSearcher == null) {
                    try {
                        ip2regionSearcher = new DbSearcher(new DbConfig(), Ip2RegionProperty.REFERENCE_PATH);
                        // ip2regionSearcher = new DbSearcher(new DbConfig(), "/file/ip2region/data.db");
                    } catch (Exception e) {
                        log.error("初始化DbSearcher实例异常", e);
                    }
                }
            }
        }
    }

    /**
     * 获取IP信息
     *
     * @param ipString IP地址
     * @see org.lionsoul.ip2region.DbSearcher#memorySearch(String ip)
     */
    public static IpInfo getIpInfo(String ipString) {
        DataBlock block = null;
        try {
            block = ip2regionSearcher.memorySearch(ipString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (block != null) {
            return new IpInfo(block.getRegion());
        } else {
            return new IpInfo();
        }
    }

    /**
     * 是字符串IP地址
     *
     * @param ipString 字符串IP地址
     * @see Pattern#matches(String regex, CharSequence input)
     * @see #IP_PATTERN
     */
    public static boolean isIp(String ipString) {
        return Pattern.matches(IP_PATTERN, ipString);
    }

    /**
     * 是数字IP地址
     *
     * @param ipLong 数字IP地址
     */
    public static boolean isIp(long ipLong) {
        return (ipLong > -1) && (ipLong < (1L << 32));
    }

    /**
     * IP地址字符串转数字
     *
     * @param ipString 字符串IP地址
     */
    public static long ip2Long(String ipString) {
        String[] s = ipString.split("\\.");
        return (Long.parseLong(s[0]) << 24) | (Integer.parseInt(s[1]) << 16) | (Integer.parseInt(s[2]) << 8) | Integer.parseInt(s[3]);
    }

    /**
     * IP地址数字转字符串
     *
     * @param ipLong 数字IP地址
     */
    public static String long2Ip(long ipLong) {
        return (ipLong >> 24) + "." + (ipLong >> 16 & 0xFF) + "." + (ipLong >> 8 & 0xFF) + "." + (ipLong & 0xFF);
    }
}
