package com.demo.config;

import com.demo.autoconfigure.MinioProperties;
import io.minio.MinioClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <h1>Minio配置</h1>
 *
 * <p>
 * createDate 2022/03/28 10:01:48
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Configuration
public class MinioConfig {

    /**
     * Minio配置属性
     */
    private final MinioProperties minioProperties;

    /**
     * 构造函数(自动注入)
     *
     * @param minioProperties MinioProperties
     */
    public MinioConfig(MinioProperties minioProperties) {
        this.minioProperties = minioProperties;
    }

    /**
     * MinioClient
     */
    @Bean
    public MinioClient minioClient() {
        MinioClient.Builder builder = MinioClient.builder()
                .endpoint(minioProperties.getUri())
                .credentials(minioProperties.getUsername(), minioProperties.getPassword());
        if (minioProperties.getRegion() != null) {
            builder.region(minioProperties.getRegion());
        }
        return builder.build();
    }

}
