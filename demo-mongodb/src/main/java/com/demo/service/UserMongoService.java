package com.demo.service;

import com.demo.base.ServiceBase;
import com.demo.dao.mongo.UserMongoDao;
import com.demo.entity.po.UserMongo;
import com.demo.entity.pojo.PageInfo;
import com.demo.entity.vo.UserMongoVo;
import com.mongodb.client.result.UpdateResult;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <h1>用户Mongo</h1>
 *
 * <p>
 * createDate 2021/03/27 19:39:47
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Service
@AllArgsConstructor
public class UserMongoService extends ServiceBase {

    private final UserMongoDao userMongoDao;

    // 通用JPA方法

    /**
     * 插入
     *
     * @param userMongo UserMongoVo
     * @return ok:id,e:0
     */
    public long insert(UserMongoVo userMongo) {
        return userMongoDao.insert(new UserMongo(userMongo));
    }

    /**
     * 批量插入
     *
     * @param userMongoList UserMongoVo
     * @return 是否成功
     */
    public boolean batchInsert(List<UserMongoVo> userMongoList) {
        return userMongoDao.batchInsert(userMongoList.stream().map(UserMongo::new).collect(Collectors.toList()));
    }

    /**
     * 插入或更新
     *
     * @param userMongo UserMongoVo
     * @return 是否成功
     */
    public boolean save(UserMongoVo userMongo) {
        return userMongoDao.save(new UserMongo(userMongo));
    }

    /**
     * 批量插入或更新
     *
     * @param userMongoList UserMongoVo
     * @return 是否成功
     */
    public boolean batchSave(List<UserMongoVo> userMongoList) {
        return userMongoDao.batchSave(userMongoList.stream().map(UserMongo::new).collect(Collectors.toList()));
    }

    /**
     * 删除
     *
     * @param id id
     * @return 是否成功
     */
    public boolean delete(Long id) {
        return userMongoDao.delete(id);
    }

    /**
     * 批量删除
     *
     * @param idList id
     * @return 是否成功
     */
    public boolean batchDelete(List<Long> idList) {
        return userMongoDao.batchDelete(idList);
    }

    /**
     * 全部删除
     *
     * @return 是否成功
     */
    public boolean deleteAll() {
        return userMongoDao.deleteAll();
    }

    /**
     * 是否存在id
     *
     * @param id id
     * @return 是否存在
     */
    public boolean existId(long id) {
        return userMongoDao.existId(id);
    }

    private static Criteria getCriteria(UserMongoVo userMongo) {
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
        buildRange(criteria, "date", userMongo.getDate(), userMongo.getDateEnd(), userMongo.getDateNot());
        return criteria;
    }

    /**
     * 是否存在
     *
     * @param userMongo UserMongoVo
     * @return 是否存在
     */
    public boolean exist(UserMongoVo userMongo) {
        return userMongoDao.exist(getCriteria(userMongo));
    }

    /**
     * 记录总数
     *
     * @return 记录总数
     */
    public long countAll() {
        return userMongoDao.countAll();
    }

    /**
     * 记录总数
     *
     * @param userMongo UserMongoVo
     * @return 记录总数
     */
    public long count(UserMongoVo userMongo) {
        return userMongoDao.count(getCriteria(userMongo));
    }

    /**
     * 查询，通过id
     *
     * @param id id
     * @return UserMongo
     */
    public UserMongo findById(Long id) {
        return userMongoDao.findById(id);
    }

    /**
     * 查询第一个
     *
     * @param userMongo UserMongoVo
     * @return UserMongo
     */
    public UserMongo findOne(UserMongoVo userMongo) {
        return userMongoDao.findOne(getCriteria(userMongo));
    }

    /**
     * 查询多个，通过id
     *
     * @param idList id
     * @return List UserMongo
     */
    public List<UserMongo> findByIdList(List<Long> idList) {
        return userMongoDao.findByIdList(idList);
    }

    /**
     * 查询所有
     *
     * @return List UserMongo
     */
    public List<UserMongo> findAll() {
        return userMongoDao.findAll();
    }

    /**
     * 排序查询
     *
     * @param userMongo UserMongoVo
     * @return List UserMongo
     */
    public List<UserMongo> findSort(UserMongoVo userMongo) {
        return userMongoDao.findSort(buildSort(userMongo.getOrderBy()));
    }

    /**
     * 分页查询
     *
     * @param userMongo UserMongoVo
     * @return PageInfo UserMongo
     */
    public PageInfo<UserMongo> findPage(UserMongoVo userMongo) {
        return new PageInfo<>(userMongoDao.findPage(buildPage(userMongo.getPages(), userMongo.getRows(), userMongo.getOrderBy())));
    }

    // 拓展JPA方法

    /**
     * 条件查询
     *
     * @param userMongo UserMongoVo
     * @return List
     */
    public List<UserMongo> find(UserMongoVo userMongo) {
        return userMongoDao.find(getCriteria(userMongo));
    }

    /**
     * 条件和排序查询
     *
     * @param userMongo UserMongoVo
     * @return List
     */
    public List<UserMongo> findSort2(UserMongoVo userMongo) {
        return userMongoDao.findSort(getCriteria(userMongo), buildSort(userMongo.getOrderBy()));
    }

    /**
     * 条件和分页查询
     *
     * @param userMongo UserMongo
     * @return PageInfo
     */
    public PageInfo<UserMongo> findPage2(UserMongoVo userMongo) {
        return userMongoDao.findPage(getCriteria(userMongo), buildPage(userMongo.getPages(), userMongo.getRows(), userMongo.getOrderBy()));
    }

    // 自定义JPA方法

    /**
     * 根据名字查询并分页
     *
     * @param name     姓名
     * @param pageable Pageable
     * @return PageInfo
     */
    public PageInfo<UserMongo> findByName(String name, Pageable pageable) {
        return new PageInfo<>(userMongoDao.findByName(name, pageable));
    }

    // MongoTemplate方法

    /**
     * 关注+1
     *
     * @param id id
     * @return UpdateResult
     */
    public UpdateResult addFollowers(Long id) {
        return userMongoDao.addFollowers(id);
    }

}
