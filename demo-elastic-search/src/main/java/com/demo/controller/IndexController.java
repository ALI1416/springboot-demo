package com.demo.controller;

import cn.z.elasticsearch.ElasticSearchTemp;
import cn.z.elasticsearch.entity.*;
import cn.z.elasticsearch.entity.analyze.AnalyzeResponse;
import cn.z.elasticsearch.entity.search.SearchResponse;
import co.elastic.clients.elasticsearch._types.mapping.Property;
import co.elastic.clients.elasticsearch._types.mapping.TextProperty;
import co.elastic.clients.elasticsearch._types.mapping.TypeMapping;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.search.Highlight;
import com.demo.entity.po.Article;
import com.demo.entity.pojo.Result;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * <h1>首页</h1>
 *
 * <p>
 * createDate 2021/09/09 10:35:04
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@RestController
@AllArgsConstructor
public class IndexController {

    private final ElasticSearchTemp elasticSearchTemp;

    /**
     * 创建索引<br>
     * http://localhost:8080/indexCreate?index=a
     */
    @GetMapping("/indexCreate")
    public Result<CreateIndexResponse> indexCreate(String index) {
        return Result.o(elasticSearchTemp.indexCreate(index));
    }

    /**
     * 创建特殊索引<br>
     * http://localhost:8080/indexCreate?index=a
     */
    @GetMapping("/indexCreate2")
    public Result<CreateIndexResponse> indexCreate2(String index) {
        // {
        //  "properties": {
        //    "content": {
        //      "analyzer": "ik_max_word",
        //      "search_analyzer": "ik_max_word",
        //      "type": "text"
        //    }
        //  }
        // }
        TextProperty textProperty = new TextProperty.Builder().analyzer("ik_max_word").searchAnalyzer("ik_max_word").build();
        TypeMapping mapping = new TypeMapping.Builder().properties("content", new Property(textProperty)).build();
        return Result.o(elasticSearchTemp.indexCreate(index, mapping));
    }

    /**
     * 存在索引<br>
     * http://localhost:8080/indexExist?index=a
     */
    @GetMapping("/indexExist")
    public Result<BooleanResponse> indexExist(String index) {
        return Result.o(elasticSearchTemp.indexExist(index));
    }

    /**
     * 删除索引<br>
     * http://localhost:8080/indexDelete?index=a
     */
    @GetMapping("/indexDelete")
    public Result<DeleteIndexResponse> indexDelete(String index) {
        return Result.o(elasticSearchTemp.indexDelete(index));
    }

    /**
     * 创建或更新文档(随机ID)<br>
     * http://localhost:8080/documentUpdate?index=a&content=abc
     */
    @GetMapping("/documentUpdate")
    public Result<IndexResponse> documentUpdate(String index, String content) {
        Article article = new Article();
        article.setContent(content);
        return Result.o(elasticSearchTemp.documentUpdate(index, article));
    }

    /**
     * 创建或更新文档<br>
     * http://localhost:8080/documentUpdate2?index=a&id=123&content=abc
     */
    @GetMapping("/documentUpdate2")
    public Result<IndexResponse> documentUpdate2(String index, String id, String content) {
        Article article = new Article();
        article.setContent(content);
        return Result.o(elasticSearchTemp.documentUpdate(index, id, article));
    }

    /**
     * 删除文档<br>
     * http://localhost:8080/documentDelete?index=a&id=123
     */
    @GetMapping("/documentDelete")
    public Result<DeleteResponse> documentDelete(String index, String id) {
        return Result.o(elasticSearchTemp.documentDelete(index, id));
    }

    /**
     * 存在文档<br>
     * http://localhost:8080/documentExist?index=a&id=123
     */
    @GetMapping("/documentExist")
    public Result<BooleanResponse> documentExist(String index, String id) {
        return Result.o(elasticSearchTemp.documentExist(index, id));
    }

    /**
     * 获取文档<br>
     * http://localhost:8080/documentGet?index=a&id=123
     */
    @GetMapping("/documentGet")
    public Result<GetResponse<Article>> documentGet(String index, String id) {
        return Result.o(elasticSearchTemp.documentGet(index, id, Article.class));
    }

    /**
     * 批量创建或更新文档(随机ID)<br>
     * http://localhost:8080/bulkDocumentUpdate?index=a&contentList=abc&contentList=def
     */
    @GetMapping("/bulkDocumentUpdate")
    public Result<BulkResponse> bulkDocumentUpdate(String index, String[] contentList) {
        List<Article> list = new ArrayList<>();
        for (String content : contentList) {
            Article article = new Article();
            article.setContent(content);
            list.add(article);
        }
        return Result.o(elasticSearchTemp.bulkDocumentUpdate(index, list));
    }

    /**
     * 批量创建或更新文档<br>
     * http://localhost:8080/bulkDocumentUpdate2?index=a&idList=1&idList=2&contentList=abc&contentList=def
     */
    @GetMapping("/bulkDocumentUpdate2")
    public Result<BulkResponse> bulkDocumentUpdate2(String index, String[] idList, String[] contentList) {
        Map<String, Article> list = new HashMap<>();
        for (int i = 0; i < idList.length; i++) {
            Article article = new Article();
            article.setContent(contentList[i]);
            list.put(idList[i], article);
        }
        return Result.o(elasticSearchTemp.bulkDocumentUpdate(index, list));
    }

    /**
     * 批量删除文档<br>
     * http://localhost:8080/bulkDocumentDelete?index=a&idList=1&idList=2
     */
    @GetMapping("/bulkDocumentDelete")
    public Result<BulkResponse> bulkDocumentDelete(String index, String[] idList) {
        return Result.o(elasticSearchTemp.bulkDocumentDelete(index, Arrays.asList(idList)));
    }

    /**
     * 搜索<br>
     * http://localhost:8080/search?index=a&field=content&value=ali <br>
     * http://localhost:8080/search?index=a&field=content&value=ali&pages=1&rows=10 <br>
     * http://localhost:8080/search?index=a&field=content&value=ali <br>
     * http://localhost:8080/search?index=a&field=content&value=ali&preTag=<span style='color:red'>&postTag=</span>
     */
    @GetMapping("/search")
    public Result<SearchResponse<Article>> search(String index, String field, String value, String preTag, String postTag, Integer pages, Integer rows) {
        Query query = new Query.Builder().match(m -> m.field(field).query(value)).build();
        Highlight highlight = null;
        if (preTag != null && postTag != null) {
            highlight = new Highlight.Builder().fields(field, v -> v.requireFieldMatch(false).preTags(preTag).postTags(postTag)).build();
        }
        return Result.o(elasticSearchTemp.search(index, Article.class, query, 0D, highlight, pages, rows));
    }

    /**
     * 分析<br>
     * http://localhost:8080/analyze?index=a&analyzer=standard&text=ali abc <br>
     * http://localhost:8080/analyze?index=a&analyzer=ik_smart&text=山东省济宁市任城区石桥镇栗河涯村平安街十六号 <br>
     * http://localhost:8080/analyze?index=a&analyzer=ik_max_word&text=山东省济宁市任城区石桥镇栗河涯村平安街十六号
     */
    @GetMapping("/analyze")
    public Result<AnalyzeResponse> analyze(String analyzer, String text) {
        return Result.o(elasticSearchTemp.analyze(analyzer, text));
    }

}
