package com.demo.ip2region;

import lombok.extern.slf4j.Slf4j;
import org.lionsoul.ip2region.DataBlock;
import org.lionsoul.ip2region.DbConfig;
import org.lionsoul.ip2region.DbSearcher;

/**
 * <h1>IP解析工具</h1>
 *
 * <p>
 * createDate 2022/02/22 10:23:06
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Slf4j
public class Ip2regionUtils {

    /**
     * ip2region搜索实例
     */
    private static volatile DbSearcher ip2regionSearcher = null;

    /**
     * 初始化DbSearcher实例
     *
     * @param filePath 数据文件路径
     * @see org.lionsoul.ip2region.DbConfig#DbConfig()
     * @see org.lionsoul.ip2region.DbSearcher#DbSearcher(DbConfig dbConfig, String dbFile)
     */
    public static void ip2RegionInit(String filePath) {
        if (ip2regionSearcher == null) {
            synchronized (Ip2regionUtils.class) {
                if (ip2regionSearcher == null) {
                    try {
                        ip2regionSearcher = new DbSearcher(new DbConfig(), filePath);
                    } catch (Exception e) {
                        log.error("初始化DbSearcher实例失败", e);
                    }
                }
            }
        }
    }

    /**
     * 获取IP信息
     *
     * @param ipStr IP地址
     * @return 错误：属性全为null
     * @see org.lionsoul.ip2region.DbSearcher#memorySearch(String ip)
     */
    public static IpInfo getIpInfo(String ipStr) {
        DataBlock block = null;
        try {
            block = ip2regionSearcher.memorySearch(ipStr);
        } catch (Exception e) {
            log.error("获取IP信息失败", e);
        }
        if (block != null) {
            return new IpInfo(block.getRegion());
        } else {
            return new IpInfo();
        }
    }

}
