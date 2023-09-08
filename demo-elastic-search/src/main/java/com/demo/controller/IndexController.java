package com.demo.controller;

import co.elastic.clients.elasticsearch._types.mapping.Property;
import co.elastic.clients.elasticsearch._types.mapping.TextProperty;
import co.elastic.clients.elasticsearch._types.mapping.TypeMapping;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import com.demo.entity.po.Article;
import com.demo.entity.pojo.Result;
import com.demo.tool.ElasticSearchTemp;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
     * http://localhost:8080/createIndex?index=a
     */
    @GetMapping("/createIndex")
    public Result<Boolean> createIndex(String index) {
        return Result.o(elasticSearchTemp.createIndex(index));
    }

    /**
     * 创建特殊索引<br>
     * http://localhost:8080/createIndex2?index=a2
     */
    @GetMapping("/createIndex2")
    public Result<Boolean> createIndex2(String index) {
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
        return Result.o(elasticSearchTemp.createIndex(index, mapping));
    }

    /**
     * 存在索引<br>
     * http://localhost:8080/existIndex?index=a
     */
    @GetMapping("/existIndex")
    public Result<Boolean> existIndex(String index) {
        return Result.o(elasticSearchTemp.existIndex(index));
    }

    /**
     * 删除索引<br>
     * http://localhost:8080/deleteIndex?index=a
     */
    @GetMapping("/deleteIndex")
    public Result<Boolean> deleteIndex(String index) {
        return Result.o(elasticSearchTemp.deleteIndex(index));
    }

    /**
     * 新增文档(随机ID)<br>
     * http://localhost:8080/addDocument?index=a2&content=山东省济宁市任城区石桥镇栗河涯村平安街
     */
    @GetMapping("/addDocument")
    public Result<IndexResponse> addDocument(String index, String id, String content) {
        Article article = new Article();
        article.setContent(content);
        return Result.o(elasticSearchTemp.addDocument(index, id, article));
    }

    /**
     * 新增文档<br>
     * http://localhost:8080/addDocument2?index=a2&id=123&content=山东省济宁市任城区石桥镇栗河涯村平安街
     */
    @GetMapping("/addDocument2")
    public Result<IndexResponse> addDocument2(String index, String id, String content) {
        Article article = new Article();
        article.setContent(content);
        return Result.o(elasticSearchTemp.addDocument(index, id, article));
    }

}
