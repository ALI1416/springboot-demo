package com.demo.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <h1>读取application.yml的test配置</h1>
 *
 * <p>
 * createDate 2022/02/21 14:55:01
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@ConfigurationProperties(prefix = "test", ignoreInvalidFields = true)
@Component
@Getter
@Setter
public class TestYml {

    /**
     * 整数类型
     */
    private int intType;
    /**
     * 字符串类型
     */
    private String stringType;

}
