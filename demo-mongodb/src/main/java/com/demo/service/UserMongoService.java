package com.demo.service;

import cn.z.mongo.entity.Pageable;
import com.demo.base.ServiceBase;
import com.demo.dao.mongo.UserMongoDao;
import com.demo.entity.pojo.PageInfo;
import com.demo.entity.vo.UserMongoVo;
import com.mongodb.client.result.UpdateResult;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

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

    /* ==================== 存在、总数操作 ==================== */

    /**
     * 是否存在id
     *
     * @param id id
     * @return 是否存在
     */
    public boolean existId(long id) {
        return userMongoDao.existId(id);
    }

    /**
     * 是否存在
     *
     * @param userMongo UserMongoVo
     * @return 是否存在
     */
    public boolean exist(UserMongoVo userMongo) {
        return userMongoDao.exist(Query.query(getCriteria(userMongo)));
    }

    /**
     * 总数
     *
     * @return 总数
     */
    public long count() {
        return userMongoDao.count();
    }

    /**
     * 总数
     *
     * @param userMongo UserMongoVo
     * @return 总数
     */
    public long count(UserMongoVo userMongo) {
        return userMongoDao.count(Query.query(getCriteria(userMongo)));
    }

    /* ==================== 插入、插入或更新操作 ==================== */

    /**
     * 插入
     *
     * @param userMongo UserMongoVo
     * @return ok:id,e:0
     */
    public long insert(UserMongoVo userMongo) {
        return userMongoDao.insert(userMongo);
    }

    /**
     * 批量插入
     *
     * @param userMongoList UserMongoVo
     * @return 是否成功
     */
    public boolean batchInsert(List<UserMongoVo> userMongoList) {
        return userMongoDao.batchInsert(userMongoList);
    }

    /**
     * 插入或更新
     *
     * @param userMongo UserMongoVo
     * @return 是否成功
     */
    public boolean save(UserMongoVo userMongo) {
        return userMongoDao.save(userMongo);
    }

    /* ==================== 删除操作 ==================== */

    /**
     * 删除
     *
     * @param id id
     * @return 是否成功
     */
    public boolean delete(long id) {
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
     * 删除
     *
     * @param userMongo UserMongoVo
     * @return 是否成功
     */
    public boolean delete(UserMongoVo userMongo) {
        return userMongoDao.delete(Query.query(getCriteria(userMongo)));
    }

    /* ==================== 更新操作 ==================== */

    /**
     * 关注+1
     *
     * @param id id
     * @return UpdateResult
     */
    public UpdateResult addFollowers1(long id) {
        return userMongoDao.addFollowers1(id);
    }

    /**
     * 关注+2(不存在新增)
     *
     * @param id id
     * @return UpdateResult
     */
    public UpdateResult addFollowers2(long id) {
        return userMongoDao.addFollowers2(id);
    }

    /**
     * 关注+3(全部)
     *
     * @return UpdateResult
     */
    public UpdateResult addFollowers3() {
        return userMongoDao.addFollowers3();
    }

    /* ==================== 查询操作 ==================== */

    /**
     * 查询，通过id
     *
     * @param id id
     * @return UserMongoVo
     */
    public UserMongoVo findById(Long id) {
        return userMongoDao.findById(id);
    }

    /**
     * 查询第一个
     *
     * @param userMongo UserMongoVo
     * @return UserMongoVo
     */
    public UserMongoVo findOne(UserMongoVo userMongo) {
        return userMongoDao.findOne(Query.query(getCriteria(userMongo)));
    }

    /**
     * 查询姓名的不同值
     *
     * @return 姓名列表
     */
    public List<String> findDistinctName() {
        return userMongoDao.findDistinct("name", String.class);
    }

    /**
     * 查询，通过id列表
     *
     * @param idList id
     * @return List UserMongoVo
     */
    public List<UserMongoVo> findByIdList(List<Long> idList) {
        return userMongoDao.findByIdList(idList);
    }

    /**
     * 分页查询
     *
     * @param userMongo UserMongoVo
     * @return PageInfo UserMongoVo
     */
    public PageInfo<UserMongoVo> findPage(UserMongoVo userMongo) {
        Query query = Query.query(getCriteria(userMongo));
        Pageable pageable = buildPage(userMongo.getPages(), userMongo.getRows(), userMongo.getOrderBy());
        return new PageInfo<>(userMongoDao.findPage(query, pageable));
    }

}
