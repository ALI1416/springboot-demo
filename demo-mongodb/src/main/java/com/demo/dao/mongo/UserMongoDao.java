package com.demo.dao.mongo;

import cn.z.clock.Clock;
import cn.z.id.Id;
import cn.z.mongo.MongoTemp;
import cn.z.mongo.entity.Page;
import cn.z.mongo.entity.Pageable;
import com.demo.base.DaoBase;
import com.demo.entity.vo.UserMongoVo;
import com.mongodb.client.result.UpdateResult;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <h1>用户Mongo</h1>
 *
 * <p>
 * createDate 2021/11/18 15:24:15
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Service
@AllArgsConstructor
public class UserMongoDao extends DaoBase {

    private static final Class<UserMongoVo> CLAZZ = UserMongoVo.class;
    private final MongoTemp mongoTemp;

    /* ==================== 存在、总数操作 ==================== */

    /**
     * 是否存在id
     *
     * @param id id
     * @return 是否存在
     */
    public boolean existId(long id) {
        return mongoTemp.exist(Query.query(Criteria.where("id").is(id)), CLAZZ);
    }

    /**
     * 是否存在
     *
     * @param query Query
     * @return 是否存在
     */
    public boolean exist(Query query) {
        return mongoTemp.exist(query, CLAZZ);
    }

    /**
     * 总数
     *
     * @return 总数
     */
    public long count() {
        return mongoTemp.count(CLAZZ);
    }

    /**
     * 总数
     *
     * @param query Query
     * @return 总数
     */
    public long count(Query query) {
        return mongoTemp.count(query, CLAZZ);
    }

    /* ==================== 插入、插入或更新操作 ==================== */

    /**
     * 插入
     *
     * @param userMongo UserMongoVo
     * @return ok:id,e:0
     */
    public long insert(UserMongoVo userMongo) {
        userMongo.setId(Id.next());
        userMongo.setDate(Clock.timestamp());
        if (tryAnyNoTransaction(() -> mongoTemp.insert(userMongo))) {
            return userMongo.getId();
        } else {
            return 0L;
        }
    }

    /**
     * 批量插入
     *
     * @param userMongoList UserMongoVo
     * @return 是否成功
     */
    public boolean batchInsert(List<UserMongoVo> userMongoList) {
        for (UserMongoVo userMongo : userMongoList) {
            userMongo.setId(Id.next());
            userMongo.setDate(Clock.timestamp());
        }
        return tryAnyNoTransaction(() -> mongoTemp.batchInsert(userMongoList));
    }

    /**
     * 插入或更新
     *
     * @param userMongo UserMongo
     * @return 是否成功
     */
    public boolean save(UserMongoVo userMongo) {
        return tryAnyNoTransaction(() -> mongoTemp.save(userMongo));
    }

    /* ==================== 删除操作 ==================== */

    /**
     * 删除
     *
     * @param id id
     * @return 是否成功
     */
    public boolean delete(long id) {
        UserMongoVo userMongo = new UserMongoVo();
        userMongo.setId(id);
        return tryEqTrueNoTransaction(() -> mongoTemp.deleteById(userMongo));
    }

    /**
     * 批量删除
     *
     * @param idList id
     * @return 是否成功
     */
    public boolean batchDelete(List<Long> idList) {
        return tryAnyNoTransaction(() -> mongoTemp.delete(Query.query(Criteria.where("id").in(idList)), CLAZZ));
    }

    /**
     * 删除
     *
     * @param query Query
     * @return 是否成功
     */
    public boolean delete(Query query) {
        return tryAnyNoTransaction(() -> mongoTemp.delete(query, CLAZZ));
    }

    /* ==================== 更新操作 ==================== */

    /**
     * 关注+1
     *
     * @param id id
     * @return UpdateResult
     */
    public UpdateResult addFollowers1(long id) {
        Query query = Query.query(Criteria.where("id").is(id));
        Update update = new Update().inc("follower", 1);
        return mongoTemp.updateOne(query, update, CLAZZ);
    }

    /**
     * 关注+2(不存在新增)
     *
     * @param id id
     * @return UpdateResult
     */
    public UpdateResult addFollowers2(long id) {
        Query query = Query.query(Criteria.where("id").is(id));
        Update update = new Update().inc("follower", 2);
        return mongoTemp.upsert(query, update, CLAZZ);
    }

    /**
     * 关注+3(全部)
     *
     * @return UpdateResult
     */
    public UpdateResult addFollowers3() {
        Update update = new Update().inc("follower", 3);
        return mongoTemp.update(new Query(), update, CLAZZ);
    }

    /* ==================== 查询操作 ==================== */

    /**
     * 查询通过id
     *
     * @param id id
     * @return UserMongoVo
     */
    public UserMongoVo findById(long id) {
        return mongoTemp.findById(id, CLAZZ);
    }

    /**
     * 查询第一个
     *
     * @param query Query
     * @return UserMongoVo
     */
    public UserMongoVo findOne(Query query) {
        return mongoTemp.findOne(query, CLAZZ);
    }

    /**
     * 查询指定字段的不同值
     *
     * @return 指定字段实体列表
     */
    public <T> List<T> findDistinct(String field, Class<T> fieldClazz) {
        return mongoTemp.findDistinct(CLAZZ, field, fieldClazz);
    }

    /**
     * 查询，通过id列表
     *
     * @param idList id
     * @return List UserMongo
     */
    public List<UserMongoVo> findByIdList(List<Long> idList) {
        return mongoTemp.find(Query.query(Criteria.where("id").in(idList)), CLAZZ);
    }

    /**
     * 查询
     *
     * @param query Query
     * @return List UserMongoVo
     */
    public List<UserMongoVo> find(Query query) {
        return mongoTemp.find(query, CLAZZ);
    }

    /**
     * 分页查询
     *
     * @param query    Query
     * @param pageable Pageable
     * @return Page UserMongoVo
     */
    public Page<UserMongoVo> findPage(Query query, Pageable pageable) {
        return mongoTemp.findPage(query, pageable, CLAZZ);
    }

    /* ==================== 查询并修改、替换、删除操作 ==================== */

}
