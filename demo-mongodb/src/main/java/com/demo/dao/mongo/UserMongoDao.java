package com.demo.dao.mongo;

import com.demo.base.MongoDaoBase;
import com.demo.entity.mongo.UserMongo;
import com.demo.repo.UserMongoRepo;
import org.springframework.stereotype.Service;

/**
 * <h1>UserMongoDao</h1>
 *
 * <p>
 * createDate 2021/11/18 15:24:15
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Service
// @AllArgsConstructor
public class UserMongoDao extends MongoDaoBase<UserMongo> {

    private final UserMongoRepo userMongoRepo;

    public UserMongoDao(UserMongoRepo userMongoRepo) {
        super(userMongoRepo);
        this.userMongoRepo = userMongoRepo;
    }

}
