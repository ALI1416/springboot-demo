package com.demo.util;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.AnalyzeRequest;
import org.elasticsearch.client.indices.AnalyzeResponse;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * <h1>ElasticSearch工具</h1>
 *
 * <p>
 * createDate 2020/12/29 16:23:19
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Component
@Slf4j
public class EsUtils {

    private static RestHighLevelClient client;

    @Autowired
    public EsUtils(RestHighLevelClient restHighLevelClient) {
        EsUtils.client = restHighLevelClient;
    }

    /**
     * 创建索引
     *
     * @param index   索引名
     * @param mapping 字段map(json格式)
     * @return 是否成功
     */
    public static boolean createIndex(String index, String mapping) {
        CreateIndexRequest request = new CreateIndexRequest(index);
        /* 设置类型字段类型 */
        request.mapping(mapping, XContentType.JSON);
        try {
            return client.indices().create(request, RequestOptions.DEFAULT).isAcknowledged();
        } catch (Exception e) {
            log.error("创建索引异常", e);
            return false;
        }
    }

    /**
     * 存在索引
     *
     * @param index 索引名
     * @return 是否存在
     */
    public static boolean existIndex(String index) {
        GetIndexRequest request = new GetIndexRequest(index);
        try {
            return client.indices().exists(request, RequestOptions.DEFAULT);
        } catch (Exception e) {
            log.error("查找索引异常", e);
            return false;
        }
    }

    /**
     * 删除索引
     *
     * @param index 索引名
     * @return 是否成功
     */
    public static boolean deleteIndex(String index) {
        DeleteIndexRequest request = new DeleteIndexRequest(index);
        try {
            return client.indices().delete(request, RequestOptions.DEFAULT).isAcknowledged();
        } catch (Exception e) {
            log.error("删除索引异常", e);
            return false;
        }
    }

    /**
     * 新增文档(随机文档id)
     *
     * @param index  索引名
     * @param object 文档名
     */
    public static <T> IndexResponse addDocument(String index, T object) {
        return addDocument(index, null, object);
    }

    /**
     * 新增文档
     *
     * @param index  索引名
     * @param id     文档id
     * @param object 对象
     */
    public static <T> IndexResponse addDocument(String index, String id, T object) {
        IndexRequest request = new IndexRequest(index);
        request.id(id);
        request.source(JSON.toJSONString(object), XContentType.JSON);
        try {
            return client.index(request, RequestOptions.DEFAULT);
        } catch (Exception e) {
            log.error("新增文档异常", e);
            return null;
        }
    }

    /**
     * 存在文档
     *
     * @param index 索引名
     * @param id    文档id
     * @return 是否存在
     */
    public static boolean existDocument(String index, String id) {
        GetRequest request = new GetRequest(index, id);
        // 去除_source
        request.fetchSourceContext(new FetchSourceContext(false));
        request.storedFields("_none_");
        try {
            return client.exists(request, RequestOptions.DEFAULT);
        } catch (Exception e) {
            log.error("查找文档异常", e);
            return false;
        }
    }

    /**
     * 查找文档
     *
     * @param index 索引名
     * @param id    文档id
     */
    public static GetResponse getDocument(String index, String id) {
        GetRequest request = new GetRequest(index, id);
        try {
            return client.get(request, RequestOptions.DEFAULT);
        } catch (Exception e) {
            log.error("查找文档异常", e);
            return null;
        }
    }

    /**
     * 更新文档
     *
     * @param index  索引名
     * @param id     文档id
     * @param object 对象
     */
    public static <T> UpdateResponse updateDocument(String index, String id, T object) {
        UpdateRequest request = new UpdateRequest(index, id);
        request.doc(JSON.toJSONString(object), XContentType.JSON);
        try {
            return client.update(request, RequestOptions.DEFAULT);
        } catch (Exception e) {
            log.error("更新文档异常", e);
            return null;
        }
    }

    /**
     * 删除文档
     *
     * @param index 索引名
     * @param id    文档id
     */
    public static DeleteResponse deleteDocument(String index, String id) {
        DeleteRequest request = new DeleteRequest(index, id);
        try {
            return client.delete(request, RequestOptions.DEFAULT);
        } catch (Exception e) {
            log.error("删除文档异常", e);
            return null;
        }
    }

    /**
     * 批量新增文档
     *
     * @param index   索引名
     * @param objects 带文档id对象Map
     */
    public static <T> boolean addDocumentBulk(String index, Map<String, T> objects) {
        BulkRequest request = new BulkRequest();
        for (Entry<String, T> object : objects.entrySet()) {
            request.add(//
                    new IndexRequest(index)//
                            .id(object.getKey())//
                            .source(JSON.toJSONString(object.getValue()), XContentType.JSON)//
            );
        }
        try {
            return !client.bulk(request, RequestOptions.DEFAULT).hasFailures();
        } catch (Exception e) {
            log.error("批量新增文档异常", e);
            return false;
        }
    }

    /**
     * 批量新增文档(随机文档id)
     *
     * @param index   索引名
     * @param objects 随机文档id对象List
     */
    public static <T> boolean addDocumentBulk(String index, List<T> objects) {
        BulkRequest request = new BulkRequest();
        for (T object : objects) {
            request.add(//
                    new IndexRequest(index)//
                            .source(JSON.toJSONString(object), XContentType.JSON)//
            );
        }
        try {
            return !client.bulk(request, RequestOptions.DEFAULT).hasFailures();
        } catch (Exception e) {
            log.error("批量新增文档异常", e);
            return false;
        }
    }

    /**
     * 查询文档
     *
     * @param index            索引名
     * @param queryBuilder     查询构造器
     * @param highlightBuilder 高亮构造器(默认关闭)
     * @param pages            页码(默认1)
     * @param rows             每页条数(默认10)
     * @param minScore         最小分数(默认0)
     */
    public static SearchResponse search(String index, QueryBuilder queryBuilder, HighlightBuilder highlightBuilder,
                                        Integer pages, Integer rows, Float minScore) {
        SearchRequest request = new SearchRequest(index);
        SearchSourceBuilder builder = new SearchSourceBuilder();
        /* 查询 */
        builder.query(queryBuilder);
        /* 高亮 */
        builder.highlighter(highlightBuilder);
        /* 分页查询 */
        if (pages != null && rows != null && pages > 0 && rows > 0) {
            // 从第几条开始查询，查询多少条
            builder.from((pages - 1) * rows).size(rows);
        }
        /* 最小分数 */
        if (minScore != null) {
            builder.minScore(minScore);
        }
        request.source(builder);
        try {
            return client.search(request, RequestOptions.DEFAULT);
        } catch (Exception e) {
            log.error("查询文档异常", e);
            return null;
        }
    }

    /**
     * 分析文本
     *
     * @param analyzer 分析器standard、ik_smart、ik_max_word
     * @param text     文本
     */
    public static AnalyzeResponse analyze(String analyzer, String text) {
        AnalyzeRequest request = AnalyzeRequest.withGlobalAnalyzer(analyzer, text);
        try {
            return client.indices().analyze(request, RequestOptions.DEFAULT);
        } catch (Exception e) {
            log.error("分析文本异常", e);
            return null;
        }
    }

    /**
     * 提取结果
     *
     * @param searchResponse searchResponse
     */
    public static List<Map<String, Object>> extractResult(SearchResponse searchResponse) {
        List<Map<String, Object>> result = new ArrayList<>();
        for (SearchHit documentFields : searchResponse.getHits().getHits()) {
            result.add(documentFields.getSourceAsMap());
        }
        return result;
    }

    /**
     * 提取高亮后的结果(启用去除相邻标签)
     *
     * @param searchResponse searchResponse
     */
    public static List<Map<String, Object>> extractHighlightResult(SearchResponse searchResponse) {
        return extractHighlightResult(searchResponse, null, null);
    }

    /**
     * 提取高亮后的结果(启用去除相邻标签)
     *
     * @param searchResponse searchResponse
     * @param regexTag       去除标签的正则表达式(默认&lt;/em>&lt;em>)
     */
    public static List<Map<String, Object>> extractHighlightResult(SearchResponse searchResponse, String regexTag) {
        return extractHighlightResult(searchResponse, null, regexTag);
    }

    /**
     * 提取高亮后的结果
     *
     * @param searchResponse          searchResponse
     * @param enableRemoveAdjacentTag 启用去除相邻标签(默认true)
     * @param regexTag                去除标签的正则表达式(默认&lt;/em>&lt;em>)
     */
    public static List<Map<String, Object>> extractHighlightResult(SearchResponse searchResponse,
                                                                   Boolean enableRemoveAdjacentTag, String regexTag) {
        /* 是否启用去除 */
        if (enableRemoveAdjacentTag == null || enableRemoveAdjacentTag) {
            // 启用去除
            if (regexTag == null) {
                // 标签默认
                regexTag = "</em><em>";
            }
            // else 自定义标签
        } else {
            // 不用去除
            regexTag = null;
        }
        List<Map<String, Object>> result = new ArrayList<>();
        /* 设置高亮 */
        for (SearchHit documentFields : searchResponse.getHits().getHits()) {
            // 原来的内容
            Map<String, Object> sourceAsMap = documentFields.getSourceAsMap();
            // 找到所有的高亮字段
            Map<String, HighlightField> highlightFieldMap = documentFields.getHighlightFields();
            // 遍历所有的高亮字段
            for (Entry<String, HighlightField> highlightFieldEntry : highlightFieldMap.entrySet()) {
                // 高亮字段的内容
                HighlightField highlightField = highlightFieldEntry.getValue();
                if (highlightField != null) {
                    // 高亮字段所有行内容
                    Text[] fragments = highlightField.getFragments();
                    StringBuilder texts = new StringBuilder();
                    for (Text text : fragments) {
                        texts.append(text);
                    }
                    String text = regexTag == null ? texts.toString() : removeAdjacentTag(texts, regexTag);
                    // 替换原来的内容(高亮字段,所有行内容)
                    sourceAsMap.put(highlightFieldEntry.getKey(), text);
                }
            }
            result.add(sourceAsMap);
        }
        return result;
    }

    /**
     * 去除相邻标签
     *
     * @param texts    文本
     * @param regexTag 去除标签的正则表达式
     */
    public static String removeAdjacentTag(StringBuilder texts, String regexTag) {
        return texts.toString().replaceAll(regexTag, "");
    }

}
