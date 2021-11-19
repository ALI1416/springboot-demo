package com.demo.service;

import com.demo.repo.UserMongoRepo;
import com.demo.entity.mongo.UserMongo;
import com.mongodb.client.result.UpdateResult;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <h1>用户Mongo服务</h1>
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
public class UserMongoService {

    private final UserMongoRepo userMongoDao;

    /**
     * 插入
     */
    public long insert(UserMongo userMongo) {
        return userMongoDao.insert(userMongo);
    }

    /**
     * 更新
     */
    public boolean update(UserMongo userMongo) {
        return userMongoDao.update(userMongo);
    }

    /**
     * 删除，通过id
     */
    public boolean deleteById(Long id) {
        return userMongoDao.deleteById(id);
    }

    /**
     * 查询，通过id
     */
    public UserMongo findById(Long id) {
        return userMongoDao.findById(id);
    }

    /**
     * 查询，通过name
     */
    public List<UserMongo> findByName(String name) {
        return userMongoDao.find("name", name);
    }

    /**
     * 关注+1
     */
    public UpdateResult addFollowers(Long id) {
        return userMongoDao.incrementInt1ById(id, "follower");
    }

}
