package com.demo.util.phone2region;

import cn.z.phone2region.Phone2Region;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;

/**
 * <h1>手机号码转区域Controller</h1>
 *
 * <p>
 * 文件地址为：https://www.404z.cn/files/phone2region/v2.0.0/data/phone2region.zdb
 * </p>

 * <p>
 * createDate 2023/04/06 15:37:22
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Slf4j
public class Main {

    public static void main(String[] args) {
        try {
            Phone2Region.init(new ClassPathResource("file/phone2region/phone2region.zdb").getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info(String.valueOf(Phone2Region.parse(1875471)));
    }

}
