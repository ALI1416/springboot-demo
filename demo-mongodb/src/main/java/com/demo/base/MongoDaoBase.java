package com.demo.base;

import cn.z.id.Id;
import com.mongodb.client.result.UpdateResult;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.List;

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
public abstract class MongoDaoBase<T> {

    private final MongoTemplate mongoTemplate;

    /**
     * 获取实体类.class
     *
     * @return 实体类.class
     */
    public abstract Class<T> getEntityClass();

    /**
     * 插入(自动生成id)
     *
     * @param mongoEntityBase MongoEntityBase
     * @return ok:id,e:0
     */
    public long insert(MongoEntityBase mongoEntityBase) {
        mongoEntityBase.setId(Id.next());
        try {
            mongoTemplate.insert(mongoEntityBase);
            return mongoEntityBase.getId();
        } catch (Exception e) {
            log.error("插入异常", e);
            return 0L;
        }
    }

    /**
     * 更新(必须传入id，如果id不存在则插入)
     *
     * @param mongoEntityBase MongoEntityBase
     * @return 是否成功
     */
    public boolean update(MongoEntityBase mongoEntityBase) {
        try {
            mongoTemplate.save(mongoEntityBase);
            return true;
        } catch (Exception e) {
            log.error("插入异常", e);
            return false;
        }
    }

    /**
     * 删除，根据id
     *
     * @param id id
     * @return 是否成功
     */
    public boolean deleteById(Long id) {
        MongoEntityBase mongoEntityBase = new MongoEntityBase();
        mongoEntityBase.setId(id);
        return mongoTemplate.remove(mongoEntityBase).getDeletedCount() == 1;
    }

    /**
     * 查询，根据id
     *
     * @param id id
     * @return 对象
     */
    public T findById(Long id) {
        return mongoTemplate.findById(id, getEntityClass());
    }

    public List<T> find(String field, Object value) {
        Query query = Query.query(Criteria.where(field).is(value));
        return mongoTemplate.find(query, getEntityClass());
    }

    public UpdateResult incrementInt1ById(Long id, String field) {
        Query query = Query.query(Criteria.where("_id").is(id));
        Update update = new Update();
        update.inc(field, 1);
        return mongoTemplate.updateFirst(query, update, getEntityClass());
    }

    public UpdateResult incrementLong1ById(Long id, String field) {
        Query query = Query.query(Criteria.where("_id").is(id));
        Update update = new Update();
        update.inc(field);
        return mongoTemplate.updateFirst(query, update, getEntityClass());
    }

    public UpdateResult incrementById(Long id, String field, Number delta) {
        Query query = Query.query(Criteria.where("_id").is(id));
        Update update = new Update();
        update.inc(field, delta);
        return mongoTemplate.updateFirst(query, update, getEntityClass());
    }

}
