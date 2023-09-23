package cn.z.elasticsearch;

import cn.z.elasticsearch.autoconfigure.ElasticSearchProperties;
import cn.z.elasticsearch.entity.*;
import cn.z.elasticsearch.entity.analyze.AnalyzeResponse;
import cn.z.elasticsearch.entity.search.SearchResponse;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.mapping.TypeMapping;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.search.Highlight;
import co.elastic.clients.elasticsearch.indices.AnalyzeRequest;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Map;

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

    /**
     * 日志实例
     */
    private static final Logger log = LoggerFactory.getLogger(ElasticSearchTemp.class);
    /**
     * ElasticSearch客户端
     */
    private final ElasticsearchClient elasticSearchClient;

    /**
     * 构造函数(自动注入)
     *
     * @param elasticSearchProperties ElasticSearchProperties
     */
    public ElasticSearchTemp(ElasticSearchProperties elasticSearchProperties) {
        RestClientBuilder builder = RestClient.builder(HttpHost.create(elasticSearchProperties.getUri()));
        if (elasticSearchProperties.getUsername() != null && elasticSearchProperties.getPassword() != null) {
            builder.setDefaultHeaders(new Header[]{new BasicHeader("Authorization", "Basic " + new String(Base64.getEncoder().encode((elasticSearchProperties.getUsername() + ":" + elasticSearchProperties.getPassword()).getBytes(StandardCharsets.UTF_8))))});
        }
        this.elasticSearchClient = new ElasticsearchClient(new RestClientTransport(builder.build(), new JacksonJsonpMapper()));
    }

    /* ==================== 索引 ==================== */

    /**
     * 创建索引
     *
     * @param index 索引
     * @return CreateIndexResponse
     */
    public CreateIndexResponse indexCreate(String index) {
        try {
            return new CreateIndexResponse(elasticSearchClient.indices().create(c -> c.index(index)));
        } catch (Exception e) {
            log.error("[创建索引]异常！", e);
            return null;
        }
    }

    /**
     * 创建特殊索引
     *
     * @param index   索引
     * @param mapping TypeMapping
     * @return CreateIndexResponse
     */
    public CreateIndexResponse indexCreate(String index, TypeMapping mapping) {
        try {
            return new CreateIndexResponse(elasticSearchClient.indices().create(c -> c.index(index).mappings(mapping)));
        } catch (Exception e) {
            log.error("[创建特殊索引]异常！", e);
            return null;
        }
    }

    /**
     * 存在索引
     *
     * @param index 索引
     * @return BooleanResponse
     */
    public BooleanResponse indexExist(String index) {
        try {
            return new BooleanResponse(elasticSearchClient.indices().exists(c -> c.index(index)));
        } catch (Exception e) {
            log.error("[存在索引]异常！", e);
            return null;
        }
    }

    /**
     * 删除索引
     *
     * @param index 索引
     * @return DeleteIndexResponse
     */
    public DeleteIndexResponse indexDelete(String index) {
        try {
            return new DeleteIndexResponse(elasticSearchClient.indices().delete(c -> c.index(index)));
        } catch (Exception e) {
            log.error("[删除索引]异常！", e);
            return null;
        }
    }

    /* ==================== 文档 ==================== */

    /**
     * 创建或更新文档(随机ID)
     *
     * @param <T>   数据类型
     * @param index 索引(不存在将创建)
     * @param data  数据
     * @return IndexResponse
     */
    public <T> IndexResponse documentUpdate(String index, T data) {
        try {
            return new IndexResponse(elasticSearchClient.index(c -> c.index(index).document(data)));
        } catch (Exception e) {
            log.error("[创建或更新文档(随机ID)]异常！", e);
            return null;
        }
    }

    /**
     * 创建或更新文档
     *
     * @param index 索引(不存在将创建)
     * @param id    ID
     * @param data  数据
     * @return IndexResponse
     */
    public <T> IndexResponse documentUpdate(String index, String id, T data) {
        try {
            return new IndexResponse(elasticSearchClient.index(c -> c.index(index).id(id).document(data)));
        } catch (Exception e) {
            log.error("[创建或更新文档]异常！", e);
            return null;
        }
    }

    /**
     * 删除文档
     *
     * @param index 索引
     * @param id    ID
     * @return DeleteResponse
     */
    public DeleteResponse documentDelete(String index, String id) {
        try {
            return new DeleteResponse(elasticSearchClient.delete(c -> c.index(index).id(id)));
        } catch (Exception e) {
            log.error("[删除文档]异常！", e);
            return null;
        }
    }

    /**
     * 存在文档
     *
     * @param index 索引
     * @param id    ID
     * @return BooleanResponse
     */
    public BooleanResponse documentExist(String index, String id) {
        try {
            return new BooleanResponse(elasticSearchClient.exists(c -> c.index(index).id(id)));
        } catch (Exception e) {
            log.error("[存在文档]异常！", e);
            return null;
        }
    }

    /**
     * 获取文档
     *
     * @param <T>   数据类型
     * @param index 索引
     * @param id    ID
     * @param clazz T.class
     * @return GetResponse T
     */
    public <T> GetResponse<T> documentGet(String index, String id, Class<T> clazz) {
        try {
            return new GetResponse<>(elasticSearchClient.get(c -> c.index(index).id(id), clazz));
        } catch (Exception e) {
            log.error("[获取文档]异常！", e);
            return null;
        }
    }

    /* ==================== 批量 ==================== */

    /**
     * 批量创建或更新文档(随机ID)
     *
     * @param <T>      数据类型
     * @param index    索引(不存在将创建)
     * @param dataList 数据列表
     * @return BulkResponse
     */
    public <T> BulkResponse bulkDocumentUpdate(String index, List<T> dataList) {
        BulkRequest.Builder builder = new BulkRequest.Builder();
        for (T data : dataList) {
            builder.index(index).operations(o -> o.index(i -> i.document(data)));
        }
        try {
            return new BulkResponse(elasticSearchClient.bulk(builder.build()));
        } catch (Exception e) {
            log.error("[批量创建或更新文档(随机ID)]异常！", e);
            return null;
        }
    }

    /**
     * 批量创建或更新文档
     *
     * @param <T>     数据类型
     * @param index   索引(不存在将创建)
     * @param dataMap 数据Map ID,数据
     * @return BulkResponse
     */
    public <T> BulkResponse bulkDocumentUpdate(String index, Map<String, T> dataMap) {
        BulkRequest.Builder builder = new BulkRequest.Builder();
        dataMap.forEach((id, data) -> builder.index(index).operations(o -> o.index(i -> i.id(id).document(data))));
        try {
            return new BulkResponse(elasticSearchClient.bulk(builder.build()));
        } catch (Exception e) {
            log.error("[批量创建或更新文档]异常！", e);
            return null;
        }
    }

    /**
     * 批量删除文档
     *
     * @param index  索引
     * @param idList ID列表
     * @return BulkResponse
     */
    public BulkResponse bulkDocumentDelete(String index, List<String> idList) {
        BulkRequest.Builder builder = new BulkRequest.Builder();
        for (String id : idList) {
            builder.index(index).operations(o -> o.delete(i -> i.id(id)));
        }
        try {
            return new BulkResponse(elasticSearchClient.bulk(builder.build()));
        } catch (Exception e) {
            log.error("[批量删除文档]异常！", e);
            return null;
        }
    }

    /* ==================== 搜索 ==================== */

    /**
     * 搜索
     *
     * @param <T>       数据类型
     * @param index     索引
     * @param clazz     T.class
     * @param query     查询
     * @param minScore  最小分数
     * @param highlight 高亮
     * @param pages     页码(默认1)
     * @param rows      每页条数(默认10)
     * @return SearchResponse T
     */
    public <T> SearchResponse<T> search(String index, Class<T> clazz, Query query, Double minScore, Highlight highlight, Integer pages, Integer rows) {
        SearchRequest.Builder builder = new SearchRequest.Builder();
        builder.index(index).query(query).highlight(highlight).minScore(minScore);
        if (pages != null && rows != null && pages > 0 && rows > 0) {
            builder.from((pages - 1) * rows).size(rows);
        }
        try {
            return new SearchResponse<>(elasticSearchClient.search(builder.build(), clazz));
        } catch (Exception e) {
            log.error("[搜索]异常！", e);
            return null;
        }
    }

    /* ==================== 分析 ==================== */

    /**
     * 分析
     *
     * @param analyzer 分析器
     * @param text     文本
     * @return AnalyzeResponse
     */
    public AnalyzeResponse analyze(String analyzer, String text) {
        AnalyzeRequest.Builder builder = new AnalyzeRequest.Builder();
        builder.analyzer(analyzer).text(text);
        try {
            return new AnalyzeResponse(elasticSearchClient.indices().analyze(builder.build()));
        } catch (Exception e) {
            log.error("[分析]异常！", e);
            return null;
        }
    }

}
