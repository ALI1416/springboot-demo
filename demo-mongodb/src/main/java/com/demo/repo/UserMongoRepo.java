package com.demo.repo;

import com.demo.entity.mongo.UserMongo;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * <h1>用户MongoDao</h1>
 *
 * <p>
 * createDate 2021/03/27 19:34:51
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public interface UserMongoRepo extends MongoRepository<UserMongo, Long> {

}
