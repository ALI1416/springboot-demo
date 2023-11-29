package cn.z.minio.autoconfigure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

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
@EnableConfigurationProperties(MinioProperties.class)
public class MinioAutoConfiguration {

    /**
     * 日志实例
     */
    private static final Logger log = LoggerFactory.getLogger(MinioAutoConfiguration.class);

    /**
     * 构造函数(自动注入)
     *
     * @param minioProperties MinioProperties
     */
    public MinioAutoConfiguration(MinioProperties minioProperties) {
        String msg = "Minio配置：URI " + minioProperties.getUri();
        if (minioProperties.getRegion() != null && !minioProperties.getRegion().isEmpty()) {
            msg += " ，区域REGION " + minioProperties.getRegion();
        }
        log.info(msg);
    }

}
