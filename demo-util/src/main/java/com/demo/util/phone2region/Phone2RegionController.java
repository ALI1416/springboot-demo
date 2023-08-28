package com.demo.util.phone2region;

import cn.z.phone2region.Phone2Region;
import cn.z.phone2region.Region;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <h1>手机号码转区域Controller</h1>
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
@RequestMapping("phone2region")
public class Phone2RegionController {

    /**
     * 解析<br>
     * http://localhost:8080/phone2region/parse?phone=1875471
     */
    @GetMapping("/parse")
    public Region parse(int phone) {
        Region region = Phone2Region.parse(phone);
        log.info(String.valueOf(region));
        return region;
    }

}
