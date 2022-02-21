package com.demo.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <h1>读取application.yml的logging.file配置</h1>
 *
 * <p>
 * createDate 2022/02/21 14:55:01
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@ConfigurationProperties(prefix = "logging.file", ignoreInvalidFields = true)
@Component
@Getter
@Setter
public class LoggingFileYml {

    /**
     * 日志文件名
     */
    private String name;

}
