package com.demo.dao.mongo;

import com.demo.base.DaoBase;
import com.demo.entity.mongo.UserMongo;
import com.demo.entity.vo.UserMongoVo;
import com.demo.repo.UserMongoRepo;
import com.mongodb.client.result.UpdateResult;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    // 通用JPA方法

    /**
     * 插入一个，id已存在会失败
     *
     * @param userMongo 实体，必须有id
     * @return 是否成功
     */
    public boolean insert(UserMongo userMongo) {
        try {
            userMongoRepo.insert(userMongo);
            return true;
        } catch (Exception e) {
            log.error("id已存在", e);
            return false;
        }
    }

    /**
     * 插入多个，id存在会失败，后面不再插入
     *
     * @param userMongo 实体，必须有id
     * @return 是否成功
     */
    public boolean insertList(List<UserMongo> userMongo) {
        try {
            userMongoRepo.insert(userMongo);
            return true;
        } catch (Exception e) {
            log.error("id已存在", e);
            return false;
        }
    }

    /**
     * 保存一个，id已存在会更新，不存在会插入
     *
     * @param userMongo 实体，必须有id
     */
    public void save(UserMongo userMongo) {
        userMongoRepo.save(userMongo);
    }

    /**
     * 保存多个，id已存在会更新，不存在会插入
     *
     * @param userMongo 实体，必须有id
     */
    public void saveList(List<UserMongo> userMongo) {
        userMongoRepo.saveAll(userMongo);
    }

    /**
     * 是否存在id
     *
     * @param id id
     * @return 是否存在
     */
    public boolean existsById(long id) {
        return userMongoRepo.existsById(id);
    }

    /**
     * 是否存在
     *
     * @param example Example
     * @return 是否存在
     */
    public boolean exists(Example<UserMongo> example) {
        return userMongoRepo.exists(example);
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
     * @param example Example
     * @return 记录总数
     */
    public long count(Example<UserMongo> example) {
        return userMongoRepo.count(example);
    }

    /**
     * 删除，通过id，不存在不会报错
     *
     * @param id id
     */
    public void deleteById(long id) {
        userMongoRepo.deleteById(id);
    }

    /**
     * 删除，通过实体里的id，不存在不会报错
     *
     * @param userMongo 实体，必须有id
     */
    public void delete(UserMongo userMongo) {
        userMongoRepo.delete(userMongo);
    }

    /**
     * 删除多个，通过id，不存在不会报错
     *
     * @param ids ids
     */
    public void deleteListById(List<Long> ids) {
        userMongoRepo.deleteAllById(ids);
    }

    /**
     * 删除多个，通过实体里的id，不存在不会报错
     *
     * @param userMongo 实体，必须有id
     */
    public void deleteList(List<UserMongo> userMongo) {
        userMongoRepo.deleteAll(userMongo);
    }

    /**
     * 删除所有
     */
    public void deleteAll() {
        userMongoRepo.deleteAll();
    }

    /**
     * 查找，通过id
     *
     * @param id id
     * @return 存在:实体;不存在:null
     */
    public UserMongo findById(long id) {
        return userMongoRepo.findById(id).orElse(null);
    }

    /**
     * 查找第一个
     *
     * @param example Example
     * @return 存在:实体;不存在:null
     */
    public UserMongo findOne(Example<UserMongo> example) {
        return userMongoRepo.findOne(example).orElse(null);
    }

    /**
     * 查找多个，通过id
     *
     * @param ids ids
     * @return 存在:[实体];不存在:[]
     */
    public List<UserMongo> findListById(List<Long> ids) {
        return (List<UserMongo>) userMongoRepo.findAllById(ids);
    }

    /**
     * 查找所有
     *
     * @return [实体]
     */
    public List<UserMongo> findAll() {
        return userMongoRepo.findAll();
    }

    /**
     * 排序查询
     *
     * @param sort Sort
     * @return [实体]
     */
    public List<UserMongo> findSort(Sort sort) {
        return userMongoRepo.findAll(sort);
    }

    /**
     * 查询所有
     *
     * @param example Example
     * @return [实体]
     */
    public List<UserMongo> findList(Example<UserMongo> example) {
        return userMongoRepo.findAll(example);
    }

    /**
     * 查询所有
     *
     * @param example Example
     * @param sort    Sort
     * @return [实体]
     */
    public List<UserMongo> findList(Example<UserMongo> example, Sort sort) {
        return userMongoRepo.findAll(example, sort);
    }

    /**
     * 分页查询
     *
     * @param pageRequest PageRequest
     * @return Page
     */
    public Page<UserMongo> findPage(PageRequest pageRequest) {
        return userMongoRepo.findAll(pageRequest);
    }

    /**
     * 分页查询
     *
     * @param pageRequest PageRequest
     * @return Page
     */
    public Page<UserMongo> findPage2(UserMongoVo userMongo, PageRequest pageRequest) {
        Criteria criteria = new Criteria();
        if (userMongo.getName() != null) {
            criteria.and("name").is(userMongo.getName());
        }
        if (userMongo.getFollowers() != null) {
            criteria.and("follower").is(userMongo.getFollowers());
        }
        if (userMongo.getFollowing() != null) {
            criteria.and("following").is(userMongo.getFollowing());
        }
        buildParamsQuery(criteria, userMongo.getParamsQueryList());
        buildRange(criteria, "date", userMongo.getDate(), userMongo.getDateEnd(), userMongo.getDateNot());
        return find(mongoTemplate, UserMongo.class, criteria, pageRequest);
    }

    /**
     * 排序查询
     *
     * @param sort Sort
     * @return Page
     */
    public List<UserMongo> findSort2(UserMongoVo userMongo, Sort sort) {
        Criteria criteria = new Criteria();
        if (userMongo.getName() != null) {
            criteria.and("name").is(userMongo.getName());
        }
        if (userMongo.getFollowers() != null) {
            criteria.and("follower").is(userMongo.getFollowers());
        }
        if (userMongo.getFollowing() != null) {
            criteria.and("following").is(userMongo.getFollowing());
        }
        buildParamsQuery(criteria, userMongo.getParamsQueryList());
        buildRange(criteria, "date", userMongo.getDate(), userMongo.getDateEnd(), userMongo.getDateNot());
        return mongoTemplate.find(Query.query(criteria).with(sort), UserMongo.class);
    }

    /**
     * 查询所有
     *
     * @param example     Example
     * @param pageRequest PageRequest
     * @return Page
     */
    public Page<UserMongo> findList(Example<UserMongo> example, PageRequest pageRequest) {
        return userMongoRepo.findAll(example, pageRequest);
    }

    // 自定义JPA方法

    /**
     * 根据名字查询并分页
     *
     * @param name        姓名
     * @param pageRequest 分页
     * @return Page
     */
    public Page<UserMongo> findByName(String name, PageRequest pageRequest) {
        return userMongoRepo.findByName(name, pageRequest);
    }

    // MongoTemplate方法

    /**
     * 关注+1
     *
     * @param id id
     */
    public UpdateResult addFollowers(Long id) {
        Query query = Query.query(Criteria.where("_id").is(id));
        Update update = new Update();
        update.inc("follower", 1);
        return mongoTemplate.updateFirst(query, update, UserMongo.class);
    }

}
