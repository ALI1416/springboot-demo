package com.demo.dao.mongo;

import cn.z.mongo.MongoTemp;
import com.demo.base.DaoBase;
import com.demo.entity.vo.LoginLogVo;
import lombok.AllArgsConstructor;
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
public class LoginLogDao extends DaoBase {

    private static final Class<LoginLogVo> CLAZZ = LoginLogVo.class;
    private final MongoTemp mongoTemp;

}
