package com.demo.dao.mongo;

import com.demo.base.DaoBase;
import com.demo.repo.LoginLogRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

/**
 * <h1>登录日志</h1>
 *
 * <p>
 * createDate 2023/10/28 16:19:58
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Service
@AllArgsConstructor
@Slf4j
public class LoginLogDao extends DaoBase {

    private final LoginLogRepo loginLogRepo;
    private final MongoTemplate mongoTemplate;


}
