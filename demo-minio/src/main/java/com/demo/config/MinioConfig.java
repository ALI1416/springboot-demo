package com.demo.config;

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

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder() //
                .endpoint("http://127.0.0.1:9000/") // 地址
                .credentials("minioadmin", "minioadmin") // 账号和密码
                .build();
    }

}
