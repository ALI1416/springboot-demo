package com.demo.util.phone2region;

import cn.z.phone2region.Phone2Region;
import cn.z.phone2region.Region;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <h1>Phone2Region</h1>
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
public class Phone2RegionController {

    @GetMapping("/phone2region")
    public Region phone2region() {
        Region region = Phone2Region.parse(1875471);
        log.info(String.valueOf(region));
        return region;
    }

}
