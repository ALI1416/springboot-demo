package com.demo.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <h1>ElasticSearch配置</h1>
 *
 * <p>
 * createDate 2020/12/29 16:00:05
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Configuration
public class EsConfig {

    /**
     * rest风格高级客户端
     */
    @Bean
    public RestHighLevelClient restHighLevelClient() {
        return new RestHighLevelClient(//
                RestClient.builder(//
                        new HttpHost(//
                                "localhost", // 主机
                                9200, // 端口
                                "http" // 协议
                        )));
    }

}
