package com.demo.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.demo.autoconfigure.ElasticSearchProperties;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

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
public class ElasticSearchConfig {

    /**
     * ElasticSearch配置属性
     */
    private final ElasticSearchProperties elasticSearchProperties;

    /**
     * 构造函数(自动注入)
     *
     * @param elasticSearchProperties ElasticSearchProperties
     */
    public ElasticSearchConfig(ElasticSearchProperties elasticSearchProperties) {
        this.elasticSearchProperties = elasticSearchProperties;
    }

    /**
     * ElasticsearchClient
     */
    @Bean
    public ElasticsearchClient elasticSearchClient() {
        RestClientBuilder builder = RestClient.builder(HttpHost.create(elasticSearchProperties.getUri()));
        if (elasticSearchProperties.getUsername() != null && elasticSearchProperties.getPassword() != null) {
            builder.setDefaultHeaders(new Header[]{new BasicHeader("Authorization", "Basic " + new String(Base64.getEncoder().encode((elasticSearchProperties.getUsername() + ":" + elasticSearchProperties.getPassword()).getBytes(StandardCharsets.UTF_8))))});
        }
        return new ElasticsearchClient(new RestClientTransport(builder.build(), new JacksonJsonpMapper()));
    }

}
