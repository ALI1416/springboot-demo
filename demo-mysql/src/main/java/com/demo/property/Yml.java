package com.demo.property;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <h1>Yml总配置</h1>
 *
 * <p>
 * createDate 2020/11/11 11:11:11
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 */
@Component
public class Yml {

    public static Ip2RegionAppYml ip2RegionAppYml;

    @Autowired
    private Yml(Ip2RegionAppYml ip2RegionAppYml) {
        Yml.ip2RegionAppYml = ip2RegionAppYml;
    }
}
