package com.demo.property.me;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * <h1>读取me.properties的test3配置</h1>
 *
 * <p>
 * createDate 2022/02/21 15:40:15
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@PropertySource(value = {"classpath:config/me.properties"})
@ConfigurationProperties(prefix = "test3", ignoreInvalidFields = true)
@Component
@Getter
@Setter
public class Test3Properties {

    /**
     * 整数类型
     */
    private int intType;
    /**
     * 字符串类型
     */
    private String stringType;

}
