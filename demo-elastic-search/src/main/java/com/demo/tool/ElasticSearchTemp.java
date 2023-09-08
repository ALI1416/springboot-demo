package com.demo.tool;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.mapping.TypeMapping;
import co.elastic.clients.elasticsearch.core.IndexResponse;
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

    /**
     * 创建特殊索引
     *
     * @param index   索引
     * @param mapping TypeMapping
     * @return 是否成功
     */
    public boolean createIndex(String index, TypeMapping mapping) {
        try {
            elasticSearchClient.indices().create(c -> c.index(index).mappings(mapping));
            return true;
        } catch (Exception e) {
            log.error("[创建特殊索引]异常！", e);
            return false;
        }
    }

    /**
     * 存在索引
     *
     * @param index 索引
     * @return 是否成功
     */
    public boolean existIndex(String index) {
        try {
            return elasticSearchClient.indices().exists(c -> c.index(index)).value();
        } catch (Exception e) {
            log.error("[存在索引]异常！", e);
            return false;
        }
    }

    /**
     * 删除索引
     *
     * @param index 索引
     * @return 是否成功
     */
    public boolean deleteIndex(String index) {
        try {
            elasticSearchClient.indices().delete(c -> c.index(index));
            return true;
        } catch (Exception e) {
            log.error("[删除索引]异常！", e);
            return false;
        }
    }

    /**
     * 新增文档(随机ID)
     *
     * @param <T>   数据类型
     * @param index 索引
     * @param data  数据
     * @param clazz T.class
     * @return 是否成功
     */
    public <T> IndexResponse addDocument(String index, T data, Class<T> clazz) {
        try {
            return elasticSearchClient.index(c -> c.index(index).document(data));
        } catch (Exception e) {
            log.error("[新增文档(随机ID)]异常！", e);
            return null;
        }
    }

    /**
     * 新增文档
     *
     * @param index 索引
     * @param id    ID
     * @param data  数据
     * @return 是否成功
     */
    public <T> IndexResponse addDocument(String index, String id, T data) {
        try {
            return elasticSearchClient.index(c -> c.index(index).id(id).document(data));
        } catch (Exception e) {
            log.error("[新增文档]异常！", e);
            return null;
        }
    }

}
