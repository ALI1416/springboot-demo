package com.demo.ip2region;

import cn.z.ip2region.Ip2Region;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

/**
 * <h1>IP解析</h1>
 *
 * <p>
 * 文件路径为<br>
 * https://cdn.jsdelivr.net/gh/lionsoul2014/ip2region/v1.0/data/ip2region.db
 * 实际路径为<br>
 * https://gitee.com/lionsoul/ip2region/blob/master/v1.0/data/ip2region.db
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
            Ip2Region.init(Ip2Region.inputStream2bytes(new ClassPathResource("file/ip2region/data.db").getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info(Ip2Region.parse("157.122.178.42").toString());
        log.info(Ip2Region.parse("183.237.231.74").toString());
    }

}
