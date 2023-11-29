package cn.z.elasticsearch.autoconfigure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

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
@EnableConfigurationProperties(ElasticSearchProperties.class)
public class ElasticSearchAutoConfiguration {

    /**
     * 日志实例
     */
    private static final Logger log = LoggerFactory.getLogger(ElasticSearchAutoConfiguration.class);

    /**
     * 构造函数(自动注入)
     *
     * @param elasticSearchProperties ElasticSearchProperties
     */
    public ElasticSearchAutoConfiguration(ElasticSearchProperties elasticSearchProperties) {
        String msg = "ElasticSearch配置：URI " + elasticSearchProperties.getUri() +
                " ，使用账号密码访问 " + (elasticSearchProperties.getUsername() != null && !elasticSearchProperties.getUsername().isEmpty() && elasticSearchProperties.getPassword() != null && !elasticSearchProperties.getPassword().isEmpty());
        log.info(msg);
    }

}
