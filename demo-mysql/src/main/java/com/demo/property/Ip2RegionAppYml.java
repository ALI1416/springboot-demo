package com.demo.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <h1>ip2region数据文件配置</h1>
 *
 * <p>
 * createDate 2020/11/21 10:24:53
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 */
@ConfigurationProperties(prefix = "ip2region", ignoreInvalidFields = true)
@Component
@Getter
@Setter
public class Ip2RegionAppYml {

    /**
     * 数据文件在本项目中resources文件夹下的路径
     */
    private String resourcePath = "/file/ip2region/data.db";
    /**
     * 数据文件引用路径(本机绝对地址)
     */
    private String referencePath = "/file/ip2region/data.db";
}
