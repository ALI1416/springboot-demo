package cn.z.mongo;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.IndexDefinition;
import org.springframework.data.mongodb.core.index.IndexInfo;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * <h1>MongoDB模板</h1>
 *
 * <p>
 * createDate 2023/08/15 15:00:38
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class MongoTemp {

    /**
     * MongoDB模板
     */
    private final MongoTemplate mongoTemplate;

    /**
     * 构造函数(自动注入)
     *
     * @param mongoTemplate MongoTemplate
     */
    public MongoTemp(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    /**
     * 获取数据库
     *
     * @return 数据库
     */
    public MongoDatabase getDb() {
        return mongoTemplate.getDb();
    }

    /**
     * 获取所有集合名
     *
     * @return 所有集合名
     */
    public Set<String> getCollectionName() {
        return mongoTemplate.getCollectionNames();
    }

    /**
     * 获取集合名
     *
     * @param clazz 对象
     * @return 集合名
     */
    public String getCollectionName(Class<?> clazz) {
        return mongoTemplate.getCollectionName(clazz);
    }

    /**
     * 获取集合
     *
     * @param collectionName 集合名
     * @return 集合
     */
    public MongoCollection<Document> getCollection(String collectionName) {
        return mongoTemplate.getCollection(collectionName);
    }

    /**
     * 创建集合
     *
     * @param <T>   数据类型
     * @param clazz 对象
     * @return 集合
     */
    public <T> MongoCollection<Document> createCollection(Class<T> clazz) {
        return mongoTemplate.createCollection(clazz);
    }

    /**
     * 创建集合
     *
     * @param collectionName 集合名
     * @return 集合
     */
    public MongoCollection<Document> createCollection(String collectionName) {
        return mongoTemplate.createCollection(collectionName);
    }

    /**
     * 是否存在集合
     *
     * @param <T>   数据类型
     * @param clazz 对象
     * @return 是否存在
     */
    public <T> boolean existCollection(Class<T> clazz) {
        return mongoTemplate.collectionExists(clazz);
    }

    /**
     * 是否存在集合
     *
     * @param collectionName 集合名
     * @return 是否存在
     */
    public boolean existCollection(String collectionName) {
        return mongoTemplate.collectionExists(collectionName);
    }

    /**
     * 删除集合
     *
     * @param <T>   数据类型
     * @param clazz 对象
     */
    public <T> void deleteCollection(Class<T> clazz) {
        mongoTemplate.dropCollection(clazz);
    }

    /**
     * 删除集合
     *
     * @param collectionName 集合名
     */
    public void deleteCollection(String collectionName) {
        mongoTemplate.dropCollection(collectionName);
    }

    /**
     * 获取集合所有索引
     *
     * @param clazz 对象
     * @return 所有索引
     */
    public List<IndexInfo> getCollectionIndex(Class<?> clazz) {
        return mongoTemplate.indexOps(clazz).getIndexInfo();
    }

    /**
     * 获取集合所有索引信息
     *
     * @param collectionName 集合名
     * @return 所有索引信息
     */
    public List<IndexInfo> getCollectionIndexInfo(String collectionName) {
        return mongoTemplate.indexOps(collectionName).getIndexInfo();
    }

    /**
     * 删除集合所有索引
     *
     * @param clazz 对象
     */
    public void deleteCollectionIndex(Class<?> clazz) {
        mongoTemplate.indexOps(clazz).dropAllIndexes();
    }

    /**
     * 删除集合所有索引
     *
     * @param collectionName 集合名
     */
    public void deleteCollectionIndex(String collectionName) {
        mongoTemplate.indexOps(collectionName).dropAllIndexes();
    }

    /**
     * 删除集合索引
     *
     * @param clazz     对象
     * @param indexName 索引名
     */
    public void deleteCollectionIndex(Class<?> clazz, String indexName) {
        mongoTemplate.indexOps(clazz).dropIndex(indexName);
    }

    /**
     * 删除集合索引
     *
     * @param collectionName 集合名
     * @param indexName      索引名
     */
    public void deleteCollectionIndex(String collectionName, String indexName) {
        mongoTemplate.indexOps(collectionName).dropIndex(indexName);
    }

    /**
     * 新增集合索引
     *
     * @param clazz           对象
     * @param indexDefinition IndexDefinition
     * @return 索引名
     */
    public String addCollectionIndex(Class<?> clazz, IndexDefinition indexDefinition) {
        return mongoTemplate.indexOps(clazz).ensureIndex(indexDefinition);
    }

    /**
     * 新增集合索引
     *
     * @param collectionName  集合名
     * @param indexDefinition IndexDefinition
     * @return 索引名
     */
    public String addCollectionIndex(String collectionName, IndexDefinition indexDefinition) {
        return mongoTemplate.indexOps(collectionName).ensureIndex(indexDefinition);
    }

    /**
     * 插入
     *
     * @param <T>    数据类型
     * @param entity 实体
     * @return 实体
     */
    public <T> T insert(T entity) {
        return mongoTemplate.insert(entity);
    }

    /**
     * 插入
     *
     * @param <T>            数据类型
     * @param entity         实体
     * @param collectionName 集合名
     * @return 实体
     */
    public <T> T insert(T entity, String collectionName) {
        return mongoTemplate.insert(entity, collectionName);
    }

    /**
     * 批量插入
     *
     * @param <T>        数据类型
     * @param entityList 实体列表
     * @return 实体列表
     */
    public <T> Collection<T> batchInsert(Collection<? extends T> entityList) {
        return mongoTemplate.insertAll(entityList);
    }

    /**
     * 批量插入
     *
     * @param <T>        数据类型
     * @param entityList 实体列表
     * @param clazz      对象
     * @return 实体列表
     */
    public <T> Collection<T> batchInsert(Collection<? extends T> entityList, Class<?> clazz) {
        return mongoTemplate.insert(entityList, clazz);
    }

    /**
     * 批量插入
     *
     * @param <T>            数据类型
     * @param entityList     实体列表
     * @param collectionName 集合名
     * @return 实体列表
     */
    public <T> Collection<T> batchInsert(Collection<? extends T> entityList, String collectionName) {
        return mongoTemplate.insert(entityList, collectionName);
    }

    /**
     * 插入或更新
     *
     * @param <T>    数据类型
     * @param entity 实体
     * @return 实体
     */
    public <T> T save(T entity) {
        return mongoTemplate.save(entity);
    }

    /**
     * 插入或更新
     *
     * @param <T>            数据类型
     * @param entity         实体
     * @param collectionName 集合名
     * @return 实体
     */
    public <T> T save(T entity, String collectionName) {
        return mongoTemplate.save(entity, collectionName);
    }

    /**
     * 删除
     *
     * @param <T>    数据类型
     * @param entity 实体
     * @return 删除结果
     */
    public <T> DeleteResult delete(T entity) {
        return mongoTemplate.remove(entity);
    }

    /**
     * 删除
     *
     * @param <T>            数据类型
     * @param entity         实体
     * @param collectionName 集合名
     * @return 删除结果
     */
    public <T> DeleteResult delete(T entity, String collectionName) {
        return mongoTemplate.remove(entity, collectionName);
    }

}
