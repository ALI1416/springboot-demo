package cn.z.influx.autoconfigure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * <h1>InfluxDB自动配置</h1>
 *
 * <p>
 * createDate 2024/09/03 15:17:12
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@EnableConfigurationProperties(InfluxProperties.class)
public class InfluxAutoConfiguration {

    /**
     * 日志实例
     */
    private static final Logger log = LoggerFactory.getLogger(InfluxAutoConfiguration.class);

    /**
     * 构造函数(自动注入)
     *
     * @param influxProperties InfluxProperties
     */
    public InfluxAutoConfiguration(InfluxProperties influxProperties) {
        String msg = "InfluxDB配置：URL " + influxProperties.getUrl();
        if (influxProperties.getOrg() != null && !influxProperties.getOrg().isEmpty()) {
            msg += " ，组织ORG " + influxProperties.getOrg();
        }
        if (influxProperties.getBucket() != null && !influxProperties.getBucket().isEmpty()) {
            msg += " ，储存桶BUCKET " + influxProperties.getBucket();
        }
        msg += " ，日志级别LOG_LEVEL " + influxProperties.getLogLevel()
                + " ，读超时READ_TIMEOUT " + influxProperties.getReadTimeout()
                + " ，写超时WRITE_TIMEOUT " + influxProperties.getWriteTimeout()
                + " ，连接超时CONNECT_TIMEOUT " + influxProperties.getConnectTimeout();
        log.info(msg);
    }

}
