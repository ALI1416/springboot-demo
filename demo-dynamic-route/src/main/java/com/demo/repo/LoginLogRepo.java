package com.demo.repo;

import com.demo.entity.po.LoginLog;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * <h1>登录日志</h1>
 *
 * <p>
 * createDate 2023/10/28 16:04:12
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public interface LoginLogRepo extends MongoRepository<LoginLog, Long> {

}
