package com.demo.base;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

/**
 * <h1>MongoDao层基类</h1>
 *
 * <p>
 * createDate 2021/11/18 13:33:48
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Component
@AllArgsConstructor
@Slf4j
public class MongoDaoBase<T> {

    private final MongoRepository<T, Long> mongoRepo;

    /**
     * 插入一个，id已存在会失败
     *
     * @param t 实体，必须有id
     * @return 是否成功
     */
    public boolean insert(T t) {
        try {
            mongoRepo.insert(t);
            return true;
        } catch (Exception e) {
            log.error("id已存在", e);
            return false;
        }
    }

    /**
     * 插入多个，id存在会失败，后面不再插入
     *
     * @param t 实体，必须有id
     * @return 是否成功
     */
    public boolean insertList(List<T> t) {
        try {
            mongoRepo.insert(t);
            return true;
        } catch (Exception e) {
            log.error("id已存在", e);
            return false;
        }
    }

    /**
     * 保存一个，id已存在会更新，不存在会插入
     *
     * @param t 实体，必须有id
     */
    public void save(T t) {
        mongoRepo.save(t);
    }

    /**
     * 保存多个，id已存在会更新，不存在会插入
     *
     * @param t 实体，必须有id
     */
    public void saveList(List<T> t) {
        mongoRepo.saveAll(t);
    }

    /**
     * 是否存在id
     *
     * @param id id
     * @return 是否存在
     */
    public boolean existsById(long id) {
        return mongoRepo.existsById(id);
    }

    /**
     * 是否存在
     *
     * @param example Example
     * @return 是否存在
     */
    public boolean exists(Example<T> example) {
        return mongoRepo.exists(example);
    }

    /**
     * 记录总数
     *
     * @return 记录总数
     */
    public long countAll() {
        return mongoRepo.count();
    }

    /**
     * 记录总数
     *
     * @param example Example
     * @return 记录总数
     */
    public long count(Example<T> example) {
        return mongoRepo.count(example);
    }

    /**
     * 删除，通过id，不存在不会报错
     *
     * @param id id
     */
    public void deleteById(long id) {
        mongoRepo.deleteById(id);
    }

    /**
     * 删除，通过实体里的id，不存在不会报错
     *
     * @param t 实体，必须有id
     */
    public void delete(T t) {
        mongoRepo.delete(t);
    }

    /**
     * 删除多个，通过id，不存在不会报错
     *
     * @param ids ids
     */
    public void deleteListById(List<Long> ids) {
        mongoRepo.deleteAllById(ids);
    }

    /**
     * 删除多个，通过实体里的id，不存在不会报错
     *
     * @param t 实体，必须有id
     */
    public void deleteList(List<T> t) {
        mongoRepo.deleteAll(t);
    }

    /**
     * 删除所有
     */
    public void deleteAll() {
        mongoRepo.deleteAll();
    }

    /**
     * 查找，通过id
     *
     * @param id id
     * @return 存在:实体;不存在:null
     */
    public T findById(long id) {
        return mongoRepo.findById(id).orElse(null);
    }

    /**
     * 查找第一个
     *
     * @param example Example
     * @return 存在:实体;不存在:null
     */
    public T findOne(Example<T> example) {
        return mongoRepo.findOne(example).orElse(null);
    }

    /**
     * findBy
     *
     * @param example       Example
     * @param queryFunction Function<FluentQuery.FetchableFluentQuery<T>, Long>
     * @return Long
     */
    public Long findBy(Example<T> example, Function<FluentQuery.FetchableFluentQuery<T>, Long> queryFunction) {
        return mongoRepo.findBy(example, queryFunction);
    }

    /**
     * 查找多个，通过id
     *
     * @param ids ids
     * @return 存在:[实体];不存在:[]
     */
    public Iterable<T> findListById(List<Long> ids) {
        return mongoRepo.findAllById(ids);
    }

    /**
     * 查找所有
     *
     * @return [实体]
     */
    public List<T> findAll() {
        return mongoRepo.findAll();
    }

    /**
     * 查找所有
     *
     * @param sort Sort
     * @return [实体]
     */
    public List<T> findList(Sort sort) {
        return mongoRepo.findAll(sort);
    }

    /**
     * 查询所有
     *
     * @param example Example
     * @return [实体]
     */
    public List<T> findList(Example<T> example) {
        return mongoRepo.findAll(example);
    }

    /**
     * 查询所有
     *
     * @param example Example
     * @param sort    Sort
     * @return [实体]
     */
    public List<T> findList(Example<T> example, Sort sort) {
        return mongoRepo.findAll(example, sort);
    }

    /**
     * 查询所有
     *
     * @param pageRequest PageRequest
     * @return Page
     */
    public Page<T> findList(PageRequest pageRequest) {
        return mongoRepo.findAll(pageRequest);
    }

    /**
     * 查询所有
     *
     * @param example     Example
     * @param pageRequest PageRequest
     * @return Page
     */
    public Page<T> findList(Example<T> example, PageRequest pageRequest) {
        return mongoRepo.findAll(example, pageRequest);
    }

    // public UpdateResult incrementInt1ById(Long id, String field) {
    //     Query query = Query.query(Criteria.where("_id").is(id));
    //     Update update = new Update();
    //     update.inc(field, 1);
    //     return mongoTemplate.updateFirst(query, update, getEntityClass());
    // }
    //
    // public UpdateResult incrementLong1ById(Long id, String field) {
    //     Query query = Query.query(Criteria.where("_id").is(id));
    //     Update update = new Update();
    //     update.inc(field);
    //     return mongoTemplate.updateFirst(query, update, getEntityClass());
    // }
    //
    // public UpdateResult incrementById(Long id, String field, Number delta) {
    //     Query query = Query.query(Criteria.where("_id").is(id));
    //     Update update = new Update();
    //     update.inc(field, delta);
    //     return mongoTemplate.updateFirst(query, update, getEntityClass());
    // }

}
