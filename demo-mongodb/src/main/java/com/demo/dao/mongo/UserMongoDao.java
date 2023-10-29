package com.demo.dao.mongo;

import cn.z.clock.Clock;
import cn.z.id.Id;
import cn.z.mongo.MongoTemp;
import com.demo.base.DaoBase;
import com.demo.entity.po.UserMongo;
import com.demo.entity.pojo.PageInfo;
import com.demo.repo.UserMongoRepo;
import com.mongodb.client.result.UpdateResult;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
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
@Slf4j
public class UserMongoDao extends DaoBase {

    private final UserMongoRepo userMongoRepo;
    private final MongoTemplate mongoTemplate;
    private final MongoTemp mongoTemp;

    // 通用JPA方法

    /**
     * 插入
     *
     * @param userMongo UserMongo
     * @return ok:id,e:0
     */
    public long insert(UserMongo userMongo) {
        userMongo.setId(Id.next());
        userMongo.setDate(Clock.timestamp());
        if (tryAnyNoTransaction(() -> userMongoRepo.insert(userMongo))) {
            return userMongo.getId();
        } else {
            return 0L;
        }
    }

    /**
     * 批量插入
     *
     * @param userMongoList UserMongo
     * @return 是否成功
     */
    public boolean batchInsert(List<UserMongo> userMongoList) {
        for (UserMongo userMongo : userMongoList) {
            userMongo.setId(Id.next());
            userMongo.setDate(Clock.timestamp());
        }
        return tryAnyNoTransaction(() -> userMongoRepo.insert(userMongoList));
    }

    /**
     * 插入或更新
     *
     * @param userMongo UserMongo
     * @return 是否成功
     */
    public boolean save(UserMongo userMongo) {
        return tryAnyNoTransaction(() -> userMongoRepo.save(userMongo));
    }

    /**
     * 批量插入或更新
     *
     * @param userMongoList UserMongo
     * @return 是否成功
     */
    public boolean batchSave(List<UserMongo> userMongoList) {
        return tryAnyNoTransaction(() -> userMongoRepo.saveAll(userMongoList));
    }

    /**
     * 删除
     *
     * @param id id
     * @return 是否成功
     */
    public boolean delete(long id) {
        return tryAnyNoTransaction(() -> userMongoRepo.deleteById(id));
    }

    /**
     * 删除，通过实体里的id
     *
     * @param userMongo UserMongo
     * @return 是否成功
     */
    public boolean deleteById(UserMongo userMongo) {
        return tryAnyNoTransaction(() -> userMongoRepo.delete(userMongo));
    }

    /**
     * 批量删除
     *
     * @param idList id
     * @return 是否成功
     */
    public boolean batchDelete(List<Long> idList) {
        return tryAnyNoTransaction(() -> userMongoRepo.deleteAllById(idList));
    }

    /**
     * 批量删除，通过实体里的id
     *
     * @param userMongoList UserMongo
     * @return 是否成功
     */
    public boolean batchDeleteById(List<UserMongo> userMongoList) {
        return tryAnyNoTransaction(() -> userMongoRepo.deleteAll(userMongoList));
    }

    /**
     * 全部删除
     *
     * @return 是否成功
     */
    public boolean deleteAll() {
        return tryAnyNoTransaction(userMongoRepo::deleteAll);
    }

    /**
     * 是否存在id
     *
     * @param id id
     * @return 是否存在
     */
    public boolean existId(long id) {
        return userMongoRepo.existsById(id);
    }

    /**
     * 是否存在
     *
     * @param criteria Criteria
     * @return 是否存在
     */
    public boolean exist(Criteria criteria) {
        return mongoTemplate.exists(Query.query(criteria), UserMongo.class);
    }

    /**
     * 记录总数
     *
     * @return 记录总数
     */
    public long countAll() {
        return userMongoRepo.count();
    }

    /**
     * 记录总数
     *
     * @param criteria Criteria
     * @return 记录总数
     */
    public long count(Criteria criteria) {
        return mongoTemplate.count(Query.query(criteria), UserMongo.class);
    }

    /**
     * 查询，通过id
     *
     * @param id id
     * @return UserMongo
     */
    public UserMongo findById(long id) {
        return userMongoRepo.findById(id).orElse(null);
    }

    /**
     * 查询第一个
     *
     * @param criteria Criteria
     * @return UserMongo
     */
    public UserMongo findOne(Criteria criteria) {
        return mongoTemplate.findOne(Query.query(criteria), UserMongo.class);
    }

    /**
     * 查询多个，通过id
     *
     * @param idList id
     * @return List UserMongo
     */
    public List<UserMongo> findByIdList(List<Long> idList) {
        return (List<UserMongo>) userMongoRepo.findAllById(idList);
    }

    /**
     * 查询所有
     *
     * @return List UserMongo
     */
    public List<UserMongo> findAll() {
        return userMongoRepo.findAll();
    }

    /**
     * 排序查询
     *
     * @param sort Sort
     * @return List UserMongo
     */
    public List<UserMongo> findSort(Sort sort) {
        return userMongoRepo.findAll(sort);
    }

    /**
     * 分页查询
     *
     * @param pageable Pageable
     * @return PageInfo UserMongo
     */
    public Page<UserMongo> findPage(Pageable pageable) {
        return userMongoRepo.findAll(pageable);
    }

    // 拓展JPA方法

    /**
     * 条件查询
     *
     * @param criteria Criteria
     * @return List UserMongo
     */
    public List<UserMongo> find(Criteria criteria) {
        return find(mongoTemplate, UserMongo.class, criteria);
    }

    /**
     * 条件和排序查询
     *
     * @param criteria Criteria
     * @param sort     Sort
     * @return List UserMongo
     */
    public List<UserMongo> findSort(Criteria criteria, Sort sort) {
        return sort(mongoTemplate, UserMongo.class, criteria, sort);
    }

    /**
     * 条件和分页查询
     *
     * @param criteria Criteria
     * @param pageable Pageable
     * @return PageInfo UserMongo
     */
    public PageInfo<UserMongo> findPage(Criteria criteria, Pageable pageable) {
        return pagination(mongoTemplate, UserMongo.class, criteria, pageable);
    }

    // 自定义JPA方法

    /**
     * 根据名字查询并分页
     *
     * @param name     姓名
     * @param pageable Pageable
     * @return Page
     */
    public Page<UserMongo> findByName(String name, Pageable pageable) {
        return userMongoRepo.findByName(name, pageable);
    }

    // MongoTemplate方法

    /**
     * 关注+1
     *
     * @param id id
     */
    public UpdateResult addFollowers(long id) {
        Query query = Query.query(Criteria.where("_id").is(id));
        Update update = new Update();
        update.inc("follower", 1);
        return mongoTemplate.updateFirst(query, update, UserMongo.class);
    }

}
