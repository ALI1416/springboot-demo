package cn.z.mongo;

import cn.z.mongo.entity.Page;
import cn.z.mongo.entity.Pageable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.FindAndReplaceOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.IndexDefinition;
import org.springframework.data.mongodb.core.index.IndexInfo;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.UpdateDefinition;

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

    /* ==================== 数据库、集合、索引操作 ==================== */
    // region 数据库、集合、索引操作

    /**
     * 获取数据库
     *
     * @return 数据库
     */
    public MongoDatabase getDb() {
        return mongoTemplate.getDb();
    }

    /**
     * 获取集合名
     *
     * @return 集合名
     */
    public Set<String> getCollectionName() {
        return mongoTemplate.getCollectionNames();
    }

    /**
     * 获取集合名
     *
     * @param clazz 集合类型
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
     * @param <T>   集合类型
     * @param clazz 集合类型
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
     * @param <T>   集合类型
     * @param clazz 集合类型
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
     * @param <T>   集合类型
     * @param clazz 集合类型
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
     * 获取集合所有索引信息
     *
     * @param clazz 集合类型
     * @return 所有索引信息
     */
    public List<IndexInfo> getCollectionIndexInfo(Class<?> clazz) {
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
     * @param clazz 集合类型
     */
    public void deleteCollectionIndex(Class<?> clazz) {
        mongoTemplate.indexOps(clazz).dropAllIndexes();
    }

    /**
     * 删除集合索引
     *
     * @param collectionName 集合名
     */
    public void deleteCollectionIndex(String collectionName) {
        mongoTemplate.indexOps(collectionName).dropAllIndexes();
    }

    /**
     * 删除集合索引
     *
     * @param clazz     集合类型
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
     * @param clazz 集合类型
     * @param index 索引定义
     * @return 索引名
     */
    public String addCollectionIndex(Class<?> clazz, IndexDefinition index) {
        return mongoTemplate.indexOps(clazz).ensureIndex(index);
    }

    /**
     * 新增集合索引
     *
     * @param collectionName  集合名
     * @param indexDefinition 索引定义
     * @return 索引名
     */
    public String addCollectionIndex(String collectionName, IndexDefinition indexDefinition) {
        return mongoTemplate.indexOps(collectionName).ensureIndex(indexDefinition);
    }

    // endregion

    /* ==================== 存在、总数操作 ==================== */
    // region 存在、总数操作

    /**
     * 是否存在
     *
     * @param query Query
     * @param clazz 集合类型
     * @return 是否存在
     */
    public boolean exist(Query query, Class<?> clazz) {
        return mongoTemplate.exists(query, clazz);
    }

    /**
     * 是否存在
     *
     * @param query          Query
     * @param collectionName 集合名
     * @return 是否存在
     */
    public boolean exist(Query query, String collectionName) {
        return mongoTemplate.exists(query, collectionName);
    }

    /**
     * 是否存在
     *
     * @param query          Query
     * @param clazz          集合类型
     * @param collectionName 集合名
     * @return 是否存在
     */
    public boolean exist(Query query, Class<?> clazz, String collectionName) {
        return mongoTemplate.exists(query, clazz, collectionName);
    }

    /**
     * 总数
     *
     * @param clazz 集合类型
     * @return 总数
     */
    public long count(Class<?> clazz) {
        return mongoTemplate.estimatedCount(clazz);
    }

    /**
     * 总数
     *
     * @param collectionName 集合名
     * @return 总数
     */
    public long count(String collectionName) {
        return mongoTemplate.estimatedCount(collectionName);
    }

    /**
     * 总数
     *
     * @param query Query
     * @param clazz 集合类型
     * @return 总数
     */
    public long count(Query query, Class<?> clazz) {
        return mongoTemplate.count(query, clazz);
    }

    /**
     * 总数
     *
     * @param query          Query
     * @param collectionName 集合名
     * @return 总数
     */
    public long count(Query query, String collectionName) {
        return mongoTemplate.count(query, collectionName);
    }

    /**
     * 总数
     *
     * @param query          Query
     * @param clazz          集合类型
     * @param collectionName 集合名
     * @return 总数
     */
    public long count(Query query, Class<?> clazz, String collectionName) {
        return mongoTemplate.count(query, clazz, collectionName);
    }

    // endregion

    /* ==================== 插入、插入或更新操作 ==================== */
    // region 插入、插入或更新操作

    /**
     * 插入
     *
     * @param <T>    集合类型
     * @param entity 实体
     * @return 实体
     */
    public <T> T insert(T entity) {
        return mongoTemplate.insert(entity);
    }

    /**
     * 插入
     *
     * @param <T>            集合类型
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
     * @param <T>        集合类型
     * @param entityList 实体列表
     * @return 实体列表
     */
    public <T> Collection<T> batchInsert(Collection<? extends T> entityList) {
        return mongoTemplate.insertAll(entityList);
    }

    /**
     * 批量插入
     *
     * @param <T>        集合类型
     * @param entityList 实体列表
     * @param clazz      集合类型
     * @return 实体列表
     */
    public <T> Collection<T> batchInsert(Collection<? extends T> entityList, Class<?> clazz) {
        return mongoTemplate.insert(entityList, clazz);
    }

    /**
     * 批量插入
     *
     * @param <T>            集合类型
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
     * @param <T>    集合类型
     * @param entity 实体
     * @return 实体
     */
    public <T> T save(T entity) {
        return mongoTemplate.save(entity);
    }

    /**
     * 插入或更新
     *
     * @param <T>            集合类型
     * @param entity         实体
     * @param collectionName 集合名
     * @return 实体
     */
    public <T> T save(T entity, String collectionName) {
        return mongoTemplate.save(entity, collectionName);
    }

    // endregion

    /* ==================== 删除操作 ==================== */
    // region 删除操作

    /**
     * 删除通过id
     *
     * @param entity 实体(需要id)
     * @return 是否成功
     */
    public boolean deleteById(Object entity) {
        return mongoTemplate.remove(entity).getDeletedCount() == 1L;
    }

    /**
     * 删除通过id
     *
     * @param entity         实体(需要id)
     * @param collectionName 集合名
     * @return 是否成功
     */
    public boolean deleteById(Object entity, String collectionName) {
        return mongoTemplate.remove(entity, collectionName).getDeletedCount() == 1L;
    }

    /**
     * 删除
     *
     * @param query Query
     * @param clazz 集合类型
     * @return 删除条数
     */
    public long delete(Query query, Class<?> clazz) {
        return mongoTemplate.remove(query, clazz).getDeletedCount();
    }

    /**
     * 删除
     *
     * @param query          Query
     * @param collectionName 集合名
     * @return 删除条数
     */
    public long delete(Query query, String collectionName) {
        return mongoTemplate.remove(query, collectionName).getDeletedCount();
    }

    /**
     * 删除
     *
     * @param query          Query
     * @param clazz          集合类型
     * @param collectionName 集合名
     * @return 删除条数
     */
    public long delete(Query query, Class<?> clazz, String collectionName) {
        return mongoTemplate.remove(query, clazz, collectionName).getDeletedCount();
    }

    // endregion

    /* ==================== 更新操作 ==================== */
    // region 更新操作

    /**
     * 更新或插入<br>
     * 注意：不支持排序，请使用{@link #findOneAndUpdate(Query, UpdateDefinition, Class)}
     *
     * @param query  Query
     * @param update 更新定义
     * @param clazz  集合类型
     * @return 更新结果
     */
    public UpdateResult upsert(Query query, UpdateDefinition update, Class<?> clazz) {
        return mongoTemplate.upsert(query, update, clazz);
    }

    /**
     * 更新或插入<br>
     * 注意：不支持排序，请使用{@link #findOneAndUpdate(Query, UpdateDefinition, Class, String)}
     *
     * @param query          Query
     * @param update         更新定义
     * @param collectionName 集合名
     * @return 更新结果
     */
    public UpdateResult upsert(Query query, UpdateDefinition update, String collectionName) {
        return mongoTemplate.upsert(query, update, collectionName);
    }

    /**
     * 更新或插入<br>
     * 注意：不支持排序，请使用{@link #findOneAndUpdate(Query, UpdateDefinition, Class, String)}
     *
     * @param query          Query
     * @param update         更新定义
     * @param clazz          集合类型
     * @param collectionName 集合名
     * @return 更新结果
     */
    public UpdateResult upsert(Query query, UpdateDefinition update, Class<?> clazz, String collectionName) {
        return mongoTemplate.upsert(query, update, clazz, collectionName);
    }

    /**
     * 更新第一个<br>
     * 注意：不支持排序，请使用{@link #findOneAndUpdate(Query, UpdateDefinition, Class)}
     *
     * @param query  Query
     * @param update 更新定义
     * @param clazz  集合类型
     * @return 更新结果
     */
    public UpdateResult updateOne(Query query, UpdateDefinition update, Class<?> clazz) {
        return mongoTemplate.updateFirst(query, update, clazz);
    }

    /**
     * 更新第一个<br>
     * 注意：不支持排序，请使用{@link #findOneAndUpdate(Query, UpdateDefinition, Class, String)}
     *
     * @param query          Query
     * @param update         更新定义
     * @param collectionName 集合名
     * @return 更新结果
     */
    public UpdateResult updateOne(Query query, UpdateDefinition update, String collectionName) {
        return mongoTemplate.updateFirst(query, update, collectionName);
    }

    /**
     * 更新第一个<br>
     * 注意：不支持排序，请使用{@link #findOneAndUpdate(Query, UpdateDefinition, Class, String)}
     *
     * @param query          Query
     * @param update         更新定义
     * @param clazz          集合类型
     * @param collectionName 集合名
     * @return 更新结果
     */
    public UpdateResult updateOne(Query query, UpdateDefinition update, Class<?> clazz, String collectionName) {
        return mongoTemplate.updateFirst(query, update, clazz, collectionName);
    }

    /**
     * 更新
     *
     * @param query  Query
     * @param update 更新定义
     * @param clazz  集合类型
     * @return 更新结果
     */
    public UpdateResult update(Query query, UpdateDefinition update, Class<?> clazz) {
        return mongoTemplate.updateMulti(query, update, clazz);
    }

    /**
     * 更新
     *
     * @param query          Query
     * @param update         更新定义
     * @param collectionName 集合名
     * @return 更新结果
     */
    public UpdateResult update(Query query, UpdateDefinition update, String collectionName) {
        return mongoTemplate.updateMulti(query, update, collectionName);
    }

    /**
     * 更新
     *
     * @param query          Query
     * @param update         更新定义
     * @param clazz          集合类型
     * @param collectionName 集合名
     * @return 更新结果
     */
    public UpdateResult update(Query query, UpdateDefinition update, Class<?> clazz, String collectionName) {
        return mongoTemplate.updateMulti(query, update, clazz, collectionName);
    }

    // endregion

    /* ==================== 查询操作 ==================== */
    // region 查询操作

    /**
     * 查询通过id
     *
     * @param <T>   集合类型
     * @param id    id
     * @param clazz 集合类型
     * @return 实体
     */
    public <T> T findById(Object id, Class<T> clazz) {
        return mongoTemplate.findById(id, clazz);
    }

    /**
     * 查询通过id
     *
     * @param <T>            集合类型
     * @param id             id
     * @param clazz          集合类型
     * @param collectionName 集合名
     * @return 实体
     */
    public <T> T findById(Object id, Class<T> clazz, String collectionName) {
        return mongoTemplate.findById(id, clazz, collectionName);
    }

    /**
     * 查询第一个
     *
     * @param <T>   集合类型
     * @param query Query
     * @param clazz 集合类型
     * @return 实体
     */
    public <T> T findOne(Query query, Class<T> clazz) {
        return mongoTemplate.findOne(query, clazz);
    }

    /**
     * 查询第一个
     *
     * @param <T>            集合类型
     * @param query          Query
     * @param clazz          集合类型
     * @param collectionName 集合名
     * @return 实体
     */
    public <T> T findOne(Query query, Class<T> clazz, String collectionName) {
        return mongoTemplate.findOne(query, clazz, collectionName);
    }

    /**
     * 查询指定字段的不同值
     *
     * @param <T>        指定字段的数据类型
     * @param clazz      集合类型
     * @param fieldName  字段名
     * @param fieldClazz 指定字段的数据类型
     * @return 指定字段实体列表
     */
    public <T> List<T> findDistinct(Class<?> clazz, String fieldName, Class<T> fieldClazz) {
        return mongoTemplate.findDistinct(fieldName, clazz, fieldClazz);
    }

    /**
     * 查询指定字段的不同值
     *
     * @param <T>        指定字段的数据类型
     * @param query      Query
     * @param clazz      集合类型
     * @param fieldName  字段名
     * @param fieldClazz 指定字段的数据类型
     * @return 指定字段实体列表
     */
    public <T> List<T> findDistinct(Query query, Class<?> clazz, String fieldName, Class<T> fieldClazz) {
        return mongoTemplate.findDistinct(query, fieldName, clazz, fieldClazz);
    }

    /**
     * 查询指定字段的不同值
     *
     * @param <T>            指定字段的数据类型
     * @param query          Query
     * @param collectionName 集合名
     * @param fieldName      字段名
     * @param fieldClazz     指定字段的数据类型
     * @return 指定字段实体列表
     */
    public <T> List<T> findDistinct(Query query, String collectionName, String fieldName, Class<T> fieldClazz) {
        return mongoTemplate.findDistinct(query, fieldName, collectionName, fieldClazz);
    }

    /**
     * 查询指定字段的不同值
     *
     * @param <T>            指定字段的数据类型
     * @param query          Query
     * @param clazz          集合类型
     * @param collectionName 集合名
     * @param fieldName      字段名
     * @param fieldClazz     指定字段的数据类型
     * @return 指定字段实体列表
     */
    public <T> List<T> findDistinct(Query query, Class<?> clazz, String collectionName, String fieldName, Class<T> fieldClazz) {
        return mongoTemplate.findDistinct(query, fieldName, collectionName, clazz, fieldClazz);
    }

    /**
     * 查询
     *
     * @param <T>   集合类型
     * @param clazz 集合类型
     * @return 实体数组
     */
    public <T> List<T> find(Class<T> clazz) {
        return mongoTemplate.findAll(clazz);
    }

    /**
     * 查询
     *
     * @param <T>            集合类型
     * @param clazz          集合类型
     * @param collectionName 集合名
     * @return 实体数组
     */
    public <T> List<T> find(Class<T> clazz, String collectionName) {
        return mongoTemplate.findAll(clazz, collectionName);
    }

    /**
     * 查询
     *
     * @param <T>   集合类型
     * @param query Query
     * @param clazz 集合类型
     * @return 实体数组
     */
    public <T> List<T> find(Query query, Class<T> clazz) {
        return mongoTemplate.find(query, clazz);
    }

    /**
     * 查询
     *
     * @param <T>            集合类型
     * @param query          Query
     * @param clazz          集合类型
     * @param collectionName 集合名
     * @return 实体数组
     */
    public <T> List<T> find(Query query, Class<T> clazz, String collectionName) {
        return mongoTemplate.find(query, clazz, collectionName);
    }

    /**
     * 排序查询
     *
     * @param <T>   集合类型
     * @param query Query
     * @param sort  Sort
     * @param clazz 集合类型
     * @return 实体数组
     */
    public <T> List<T> findSort(Query query, Sort sort, Class<T> clazz) {
        if (sort == null) {
            return mongoTemplate.find(query, clazz);
        } else {
            return mongoTemplate.find(query.with(sort), clazz);
        }
    }

    /**
     * 排序查询
     *
     * @param <T>            集合类型
     * @param query          Query
     * @param sort           Sort
     * @param clazz          集合类型
     * @param collectionName 集合名
     * @return 实体数组
     */
    public <T> List<T> findSort(Query query, Sort sort, Class<T> clazz, String collectionName) {
        if (sort == null) {
            return mongoTemplate.find(query, clazz, collectionName);
        } else {
            return mongoTemplate.find(query.with(sort), clazz, collectionName);
        }
    }

    /**
     * 分页查询
     *
     * @param <T>      集合类型
     * @param query    Query
     * @param pageable Pageable
     * @param clazz    集合类型
     * @return Page
     */
    public <T> Page<T> findPage(Query query, Pageable pageable, Class<T> clazz) {
        // 分页查询
        if (pageable.getPageable() != null) {
            long count = mongoTemplate.count(query, clazz);
            List<T> list = mongoTemplate.find(query.with(pageable.getPageable()), clazz);
            return new Page<>(query, list, count);
        }
        // 排序查询
        if (pageable.getSort() != null) {
            List<T> list = mongoTemplate.find(query.with(pageable.getSort()), clazz);
            return new Page<>(query, list, null);
        }
        // 全部查询
        List<T> list = mongoTemplate.find(query, clazz);
        return new Page<>(query, list, null);
    }

    /**
     * 分页查询
     *
     * @param <T>            集合类型
     * @param query          Query
     * @param pageable       Pageable
     * @param clazz          集合类型
     * @param collectionName 集合名
     * @return Page
     */
    public <T> Page<T> findPage(Query query, Pageable pageable, Class<T> clazz, String collectionName) {
        // 分页查询
        if (pageable.getPageable() != null) {
            long count = mongoTemplate.count(query, clazz, collectionName);
            List<T> list = mongoTemplate.find(query.with(pageable.getPageable()), clazz, collectionName);
            return new Page<>(query, list, count);
        }
        // 排序查询
        if (pageable.getSort() != null) {
            List<T> list = mongoTemplate.find(query.with(pageable.getSort()), clazz, collectionName);
            return new Page<>(query, list, null);
        }
        // 全部查询
        List<T> list = mongoTemplate.find(query, clazz, collectionName);
        return new Page<>(query, list, null);
    }

    // endregion

    /* ==================== 查询并修改、替换、删除操作 ==================== */
    // region 查询并修改、替换、删除操作

    /**
     * 查询第一个并修改
     *
     * @param <T>    集合类型
     * @param query  Query
     * @param update 修改定义
     * @param clazz  集合类型
     * @return 实体
     */
    public <T> T findOneAndUpdate(Query query, UpdateDefinition update, Class<T> clazz) {
        return mongoTemplate.findAndModify(query, update, clazz);
    }

    /**
     * 查询第一个并修改
     *
     * @param <T>            集合类型
     * @param query          Query
     * @param update         修改定义
     * @param clazz          集合类型
     * @param collectionName 集合名
     * @return 实体
     */
    public <T> T findOneAndUpdate(Query query, UpdateDefinition update, Class<T> clazz, String collectionName) {
        return mongoTemplate.findAndModify(query, update, clazz, collectionName);
    }

    /**
     * 查询第一个并修改
     *
     * @param <T>     集合类型
     * @param query   Query
     * @param update  修改定义
     * @param options 查询第一个并修改选项
     * @param clazz   集合类型
     * @return 实体
     */
    public <T> T findOneAndUpdate(Query query, UpdateDefinition update, FindAndModifyOptions options, Class<T> clazz) {
        return mongoTemplate.findAndModify(query, update, options, clazz);
    }

    /**
     * 查询第一个并修改
     *
     * @param <T>            集合类型
     * @param query          Query
     * @param update         修改定义
     * @param options        查询第一个并修改选项
     * @param clazz          集合类型
     * @param collectionName 集合名
     * @return 实体
     */
    public <T> T findOneAndUpdate(Query query, UpdateDefinition update, FindAndModifyOptions options, Class<T> clazz, String collectionName) {
        return mongoTemplate.findAndModify(query, update, options, clazz, collectionName);
    }

    /**
     * 查询第一个并替换
     *
     * @param <T>         集合类型
     * @param query       Query
     * @param replacement 替换值(不能有id)
     * @return 实体
     */
    public <T> T findOneAndReplace(Query query, T replacement) {
        return mongoTemplate.findAndReplace(query, replacement);
    }

    /**
     * 查询第一个并替换
     *
     * @param <T>            集合类型
     * @param query          Query
     * @param collectionName 集合名
     * @param replacement    替换值(不能有id)
     * @return 实体
     */
    public <T> T findOneAndReplace(Query query, String collectionName, T replacement) {
        return mongoTemplate.findAndReplace(query, replacement, collectionName);
    }

    /**
     * 查询第一个并替换
     *
     * @param <T>         集合类型
     * @param query       Query
     * @param options     查询第一个并替换选项
     * @param replacement 替换值(不能有id)
     * @return 实体
     */
    public <T> T findOneAndReplace(Query query, FindAndReplaceOptions options, T replacement) {
        return mongoTemplate.findAndReplace(query, replacement, options);
    }

    /**
     * 查询第一个并替换
     *
     * @param <T>            集合类型
     * @param query          Query
     * @param options        查询第一个并替换选项
     * @param collectionName 集合名
     * @param replacement    替换值(不能有id)
     * @return 实体
     */
    public <T> T findOneAndReplace(Query query, FindAndReplaceOptions options, String collectionName, T replacement) {
        return mongoTemplate.findAndReplace(query, replacement, options, collectionName);
    }

    /**
     * 查询第一个并替换
     *
     * @param <T>            集合类型
     * @param query          Query
     * @param options        查询第一个并替换选项
     * @param clazz          集合类型
     * @param collectionName 集合名
     * @param replacement    替换值(不能有id)
     * @return 实体
     */
    public <T> T findOneAndReplace(Query query, FindAndReplaceOptions options, Class<T> clazz, String collectionName, T replacement) {
        return mongoTemplate.findAndReplace(query, replacement, options, clazz, collectionName);
    }

    /**
     * 查询第一个并替换
     *
     * @param <T>              集合类型
     * @param <R>              替换值类型
     * @param query            Query
     * @param options          查询第一个并替换选项
     * @param clazz            集合类型
     * @param replacement      替换值(不能有id)
     * @param replacementClazz 替换值类型
     * @return 实体
     */
    public <R, T> T findOneAndReplace(Query query, FindAndReplaceOptions options, Class<T> clazz, R replacement, Class<R> replacementClazz) {
        return mongoTemplate.findAndReplace(query, replacement, options, replacementClazz, clazz);
    }

    /**
     * 查询第一个并替换
     *
     * @param <T>              集合类型
     * @param <R>              替换值类型
     * @param query            Query
     * @param options          查询第一个并替换选项
     * @param clazz            集合类型
     * @param collectionName   集合名
     * @param replacement      替换值(不能有id)
     * @param replacementClazz 替换值类型
     * @return 实体
     */
    public <R, T> T findOneAndReplace(Query query, FindAndReplaceOptions options, Class<T> clazz, String collectionName, R replacement, Class<R> replacementClazz) {
        return mongoTemplate.findAndReplace(query, replacement, options, replacementClazz, collectionName, clazz);
    }

    /**
     * 查询第一个并删除
     *
     * @param <T>   集合类型
     * @param query Query
     * @param clazz 集合类型
     * @return 实体
     */
    public <T> T findOneAndDelete(Query query, Class<T> clazz) {
        return mongoTemplate.findAndRemove(query, clazz);
    }

    /**
     * 查询第一个并删除
     *
     * @param <T>            集合类型
     * @param query          Query
     * @param clazz          集合类型
     * @param collectionName 集合名
     * @return 实体
     */
    public <T> T findOneAndDelete(Query query, Class<T> clazz, String collectionName) {
        return mongoTemplate.findAndRemove(query, clazz, collectionName);
    }

    /**
     * 查询并删除
     *
     * @param <T>   集合类型
     * @param query Query
     * @param clazz 集合类型
     * @return 实体数组
     */
    public <T> List<T> findAndDelete(Query query, Class<T> clazz) {
        return mongoTemplate.findAllAndRemove(query, clazz);
    }

    /**
     * 查询并删除
     *
     * @param <T>            集合类型
     * @param query          Query
     * @param collectionName 集合名
     * @return 实体数组
     */
    public <T> List<T> findAndDelete(Query query, String collectionName) {
        return mongoTemplate.findAllAndRemove(query, collectionName);
    }

    /**
     * 查询并删除
     *
     * @param <T>            集合类型
     * @param query          Query
     * @param clazz          集合类型
     * @param collectionName 集合名
     * @return 实体数组
     */
    public <T> List<T> findAndDelete(Query query, Class<T> clazz, String collectionName) {
        return mongoTemplate.findAllAndRemove(query, clazz, collectionName);
    }

    // endregion

}
