package com.demo.service;

import com.demo.base.ServiceBase;
import com.demo.dao.mongo.UserMongoDao;
import com.demo.entity.mongo.UserMongo;
import com.demo.entity.vo.UserMongoVo;
import com.mongodb.client.result.UpdateResult;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    /**
     * 插入一个，id已存在会失败
     *
     * @param userMongo 实体，必须有id
     * @return 是否成功
     */
    public boolean insert(UserMongoVo userMongo) {
        return userMongoDao.insert(userMongo);
    }

    /**
     * 插入多个，id存在会失败，后面不再插入
     *
     * @param userMongo 实体，必须有id
     * @return 是否成功
     */
    public boolean insertList(List<UserMongo> userMongo) {
        return userMongoDao.insertList(userMongo);
    }

    /**
     * 保存一个，id已存在会更新，不存在会插入
     *
     * @param userMongo 实体，必须有id
     */
    public void save(UserMongoVo userMongo) {
        userMongoDao.save(userMongo);
    }

    /**
     * 保存多个，id已存在会更新，不存在会插入
     *
     * @param userMongo 实体，必须有id
     */
    public void saveList(List<UserMongo> userMongo) {
        userMongoDao.saveList(userMongo);
    }

    /**
     * 是否存在id
     *
     * @param id id
     * @return 是否存在
     */
    public boolean existsById(long id) {
        return userMongoDao.existsById(id);
    }

    /**
     * 是否存在
     *
     * @param example Example
     * @return 是否存在
     */
    public boolean exists(Example<UserMongo> example) {
        return userMongoDao.exists(example);
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
     * @param example Example
     * @return 记录总数
     */
    public long count(Example<UserMongo> example) {
        return userMongoDao.count(example);
    }

    /**
     * 删除，通过id，不存在不会报错
     *
     * @param id id
     */
    public void deleteById(Long id) {
        userMongoDao.deleteById(id);
    }

    /**
     * 删除，通过实体里的id，不存在不会报错
     *
     * @param userMongo 实体，必须有id
     */
    public void delete(UserMongoVo userMongo) {
        userMongoDao.delete(userMongo);
    }

    /**
     * 删除多个，通过id，不存在不会报错
     *
     * @param ids ids
     */
    public void deleteListById(List<Long> ids) {
        userMongoDao.deleteListById(ids);
    }

    /**
     * 删除多个，通过实体里的id，不存在不会报错
     *
     * @param userMongo 实体，必须有id
     */
    public void deleteList(List<UserMongo> userMongo) {
        userMongoDao.deleteList(userMongo);
    }

    /**
     * 删除所有
     */
    public void deleteAll() {
        userMongoDao.deleteAll();
    }

    /**
     * 查找，通过id
     *
     * @param id id
     * @return 存在:实体;不存在:null
     */
    public UserMongo findById(Long id) {
        return userMongoDao.findById(id);
    }

    /**
     * 查找第一个
     *
     * @param example Example
     * @return 存在:实体;不存在:null
     */
    public UserMongo findOne(Example<UserMongo> example) {
        return userMongoDao.findOne(example);
    }

    /**
     * 查找多个，通过id
     *
     * @param ids ids
     * @return 存在:[实体];不存在:[]
     */
    public List<UserMongo> findListById(List<Long> ids) {
        return userMongoDao.findListById(ids);
    }

    /**
     * 查找所有
     *
     * @return [实体]
     */
    public List<UserMongo> findAll() {
        return userMongoDao.findAll();
    }

    /**
     * 查找所有
     *
     * @param sort Sort
     * @return [实体]
     */
    public List<UserMongo> findList(Sort sort) {
        return userMongoDao.findSort(sort);
    }

    /**
     * 查询所有
     *
     * @param example Example
     * @return [实体]
     */
    public List<UserMongo> findList(Example<UserMongo> example) {
        return userMongoDao.findList(example);
    }

    /**
     * 查询所有
     *
     * @param example Example
     * @param sort    Sort
     * @return [实体]
     */
    public List<UserMongo> findList(Example<UserMongo> example, Sort sort) {
        return userMongoDao.findList(example, sort);
    }

    /**
     * 查询所有
     *
     * @param pageRequest PageRequest
     * @return Page
     */
    public Page<UserMongo> findList(PageRequest pageRequest) {
        return userMongoDao.findPage(pageRequest);
    }

    /**
     * 分页查询
     *
     * @param userMongo UserMongo
     * @return Page
     */
    public Page<UserMongo> findPage(UserMongoVo userMongo) {
        return userMongoDao.findPage(buildPage(userMongo));
    }

    /**
     * 分页查询2
     *
     * @param userMongo UserMongo
     * @return Page
     */
    public Page<UserMongo> findPage2(UserMongoVo userMongo) {
        return userMongoDao.findPage2(userMongo, buildPage(userMongo));
    }

    /**
     * 排序查询
     *
     * @param userMongo UserMongo
     * @return List
     */
    public List<UserMongo> findSort(UserMongoVo userMongo) {
        return userMongoDao.findSort(buildSort(userMongo.getOrderBy()));
    }

    /**
     * 排序查询2
     *
     * @param userMongo UserMongo
     * @return List
     */
    public List<UserMongo> findSort2(UserMongoVo userMongo) {
        return userMongoDao.findSort2(userMongo, buildSort(userMongo.getOrderBy()));
    }

    /**
     * 查询所有
     *
     * @param example     Example
     * @param pageRequest PageRequest
     * @return Page
     */
    public Page<UserMongo> findList(Example<UserMongo> example, PageRequest pageRequest) {
        return userMongoDao.findList(example, pageRequest);
    }

    /**
     * 根据名字查询并分页
     *
     * @param name        姓名
     * @param pageRequest 分页
     * @return Page
     */
    public Page<UserMongo> findByName(String name, PageRequest pageRequest) {
        return userMongoDao.findByName(name, pageRequest);
    }

    /**
     * 关注+1
     *
     * @param id id
     */
    public UpdateResult addFollowers(Long id) {
        return userMongoDao.addFollowers(id);
    }

}
