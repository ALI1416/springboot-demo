package com.demo.autoconfigure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * <h1>ElasticSearch自动配置</h1>
 *
 * <p>
 * createDate 2023/09/04 14:28:48
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Configuration
@EnableConfigurationProperties(ElasticSearchProperties.class)
public class ElasticSearchAutoConfiguration {

    /**
     * 日志实例
     */
    private static final Logger log = LoggerFactory.getLogger(ElasticSearchAutoConfiguration.class);

    /**
     * URI默认值{@value}
     */
    private static final String DEFAULT_URI = "http://127.0.0.1:9200";

    /**
     * ElasticSearchProperties
     */
    private final ElasticSearchProperties elasticSearchProperties;

    /**
     * 构造函数(自动注入)
     *
     * @param elasticSearchProperties ElasticSearchProperties
     */
    public ElasticSearchAutoConfiguration(ElasticSearchProperties elasticSearchProperties) {
        this.elasticSearchProperties = elasticSearchProperties;
    }

    /**
     * 初始化
     */
    @PostConstruct
    public void init() {
        String msg = "ElasticSearch配置：URI ";
        if (elasticSearchProperties.getUri() == null) {
            elasticSearchProperties.setUri(DEFAULT_URI);
            msg += DEFAULT_URI + "(默认)";
        } else {
            msg += elasticSearchProperties.getUri();
        }
        log.info(msg);
    }

}
