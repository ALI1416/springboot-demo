package com.demo.autoconfigure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * <h1>Minio自动配置</h1>
 *
 * <p>
 * createDate 2023/08/31 14:28:48
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Configuration
@EnableConfigurationProperties(MinioProperties.class)
public class MinioAutoConfiguration {

    /**
     * 日志实例
     */
    private static final Logger log = LoggerFactory.getLogger(MinioAutoConfiguration.class);

    /**
     * URI默认值{@value}
     */
    private static final String DEFAULT_URI = "http://127.0.0.1:9000";
    /**
     * 用户名默认值{@value}
     */
    private static final String DEFAULT_USERNAME = "minioadmin";
    /**
     * 密码默认值{@value}
     */
    private static final String DEFAULT_PASSWORD = "minioadmin";

    /**
     * MinioProperties
     */
    private final MinioProperties minioProperties;

    /**
     * 构造函数(自动注入)
     *
     * @param minioProperties MinioProperties
     */
    public MinioAutoConfiguration(MinioProperties minioProperties) {
        this.minioProperties = minioProperties;
    }

    /**
     * 初始化
     */
    @PostConstruct
    public void init() {
        String msg = "Minio配置：URI ";
        if (minioProperties.getUri() == null) {
            minioProperties.setUri(DEFAULT_URI);
            msg += DEFAULT_URI + "(默认)";
        } else {
            msg += minioProperties.getUri() + " ";
        }
        if (minioProperties.getUsername() == null) {
            minioProperties.setUsername(DEFAULT_USERNAME);
        }
        if (minioProperties.getPassword() == null) {
            minioProperties.setPassword(DEFAULT_PASSWORD);
        }
        if (minioProperties.getRegion() != null) {
            msg += "区域REGION " + minioProperties.getRegion();
        }
        log.info(msg);
    }

}
