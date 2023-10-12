package com.demo.repo;

import com.demo.entity.mongo.UserMongo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * <h1>用户Mongo</h1>
 *
 * <p>
 * createDate 2021/03/27 19:34:51
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public interface UserMongoRepo extends MongoRepository<UserMongo, Long> {

    /**
     * JPA：根据名字查询并分页
     *
     * @param name     姓名
     * @param pageable 分页器
     * @return Page
     */
    Page<UserMongo> findByName(String name, Pageable pageable);

}
