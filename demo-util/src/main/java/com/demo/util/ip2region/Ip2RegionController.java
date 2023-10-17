package com.demo.util.ip2region;

import cn.z.ip2region.Ip2Region;
import cn.z.ip2region.Region;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <h1>IP地址转区域Controller</h1>
 *
 * <p>
 * createDate 2022/03/25 09:46:21
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@RestController
@Slf4j
public class Ip2RegionController {

    /**
     * 解析<br>
     * http://localhost:8080/ip2region?ip=123.132.0.0
     */
    @GetMapping("ip2region")
    public Region ip2region(String ip) {
        Region region = Ip2Region.parse(ip);
        log.info(String.valueOf(region));
        return region;
    }

}
