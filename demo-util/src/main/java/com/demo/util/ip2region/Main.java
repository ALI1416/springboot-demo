package com.demo.util.ip2region;

import cn.z.ip2region.Ip2Region;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

/**
 * <h1>IP解析</h1>
 *
 * <p>
 * 文件地址为：https://cdn.jsdelivr.net/gh/ali1416/ip2region-test/data/ip2region.zxdb
 * </p>
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
        try {
            Ip2Region.init(new ClassPathResource("file/ip2region/ip2region.zxdb").getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info(Ip2Region.parse("157.122.178.42").toString());
        log.info(Ip2Region.parse("183.237.231.74").toString());
    }

}
