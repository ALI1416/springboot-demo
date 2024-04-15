package com.demo.controller;

import cn.z.mongo.MongoTemp;
import com.demo.entity.pojo.Result;
import com.demo.entity.vo.UserMongoVo;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import lombok.AllArgsConstructor;
import org.bson.Document;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.index.IndexDefinition;
import org.springframework.data.mongodb.core.index.IndexInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

/**
 * <h1>首页</h1>
 *
 * <p>
 * createDate 2023/11/04 16:42:19
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@RestController
@AllArgsConstructor
public class IndexController {

    private final MongoTemp mongoTemp;
    private final MongoTemplate mongoTemplate;

    /**
     * 获取数据库名<br>
     * http://localhost:8080/getDbName
     */
    @GetMapping("getDbName")
    public Result<String> getDbName() {
        return Result.o(mongoTemp.getDb().getName());
    }

    /**
     * 获取集合名<br>
     * http://localhost:8080/getCollectionName
     */
    @GetMapping("getCollectionName")
    public Result<Set<String>> getCollectionName() {
        return Result.o(mongoTemp.getCollectionName());
    }

    /**
     * 获取集合名<br>
     * http://localhost:8080/getCollectionName2
     */
    @GetMapping("getCollectionName2")
    public Result<String> getCollectionName2() {
        return Result.o(mongoTemp.getCollectionName(UserMongoVo.class));
    }

    /**
     * 获取集合文档数量<br>
     * http://localhost:8080/getCollection?collectionName=user
     */
    @GetMapping("getCollection")
    public Result<Long> getCollection(String collectionName) {
        return Result.o(mongoTemp.getCollection(collectionName).countDocuments());
    }

    /**
     * 创建集合<br>
     * http://localhost:8080/createCollection?collectionName=user1
     */
    @GetMapping("createCollection")
    public Result<String> createCollection(String collectionName) {
        return Result.o(mongoTemp.createCollection(collectionName).getNamespace().getFullName());
    }

    /**
     * 是否存在集合<br>
     * http://localhost:8080/existCollection?collectionName=user1
     */
    @GetMapping("existCollection")
    public Result<Boolean> existCollection(String collectionName) {
        return Result.o(mongoTemp.existCollection(collectionName));
    }

    /**
     * 删除集合<br>
     * http://localhost:8080/deleteCollection?collectionName=user1
     */
    @GetMapping("deleteCollection")
    public Result deleteCollection(String collectionName) {
        mongoTemp.deleteCollection(collectionName);
        return Result.o();
    }

    /**
     * 获取集合所有索引信息<br>
     * http://localhost:8080/getCollectionIndexInfo?collectionName=user1
     */
    @GetMapping("getCollectionIndexInfo")
    public Result<List<IndexInfo>> getCollectionIndexInfo(String collectionName) {
        MongoCollection<Document> collection = mongoTemplate.getCollection(collectionName);
        collection.createIndex(Indexes.ascending("abc"));
        collection.createIndex(Indexes.compoundIndex(Indexes.ascending("field1", "field2")), new IndexOptions().unique(true));
        return Result.o(mongoTemp.getCollectionIndexInfo(collectionName));
    }

    /**
     * 删除集合索引<br>
     * http://localhost:8080/deleteCollectionIndex?collectionName=user1
     */
    @GetMapping("deleteCollectionIndex")
    public Result deleteCollectionIndex(String collectionName) {
        mongoTemp.deleteCollectionIndex(collectionName);
        return Result.o();
    }

    /**
     * 删除集合索引<br>
     * http://localhost:8080/deleteCollectionIndex2?collectionName=user1&indexName=a_-1
     */
    @GetMapping("deleteCollectionIndex2")
    public Result deleteCollectionIndex2(String collectionName, String indexName) {
        mongoTemp.deleteCollectionIndex(collectionName, indexName);
        return Result.o();
    }

    /**
     * 新增集合索引<br>
     * http://localhost:8080/addCollectionIndex?collectionName=user1&indexName=a
     */
    @GetMapping("addCollectionIndex")
    public Result<String> addCollectionIndex(String collectionName, String indexName) {
        IndexDefinition indexDefinition = new Index(indexName, Sort.Direction.DESC);
        return Result.o(mongoTemp.addCollectionIndex(collectionName, indexDefinition));
    }

}
