package com.demo.tool;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * <h1>ElasticSearch模板</h1>
 *
 * <p>
 * createDate 2023/09/04 14:55:40
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Component
public class ElasticSearchTemp {

    private static final Logger log = LoggerFactory.getLogger(ElasticSearchTemp.class);
    private final ElasticsearchClient elasticSearchClient;

    /**
     * 静态注入(自动注入)
     *
     * @param elasticSearchClient ElasticsearchClient
     */
    public ElasticSearchTemp(ElasticsearchClient elasticSearchClient) {
        this.elasticSearchClient = elasticSearchClient;
    }

    /**
     * 创建索引
     *
     * @param index 索引
     * @return 是否成功
     */
    public boolean createIndex(String index) {
        try {
            elasticSearchClient.indices().create(c -> c.index(index));
            return true;
        } catch (Exception e) {
            log.error("[创建索引]异常！", e);
            return false;
        }
    }

}
