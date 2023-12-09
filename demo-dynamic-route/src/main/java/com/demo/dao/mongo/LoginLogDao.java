package com.demo.dao.mongo;

import cn.z.mongo.MongoTemp;
import cn.z.mongo.entity.Page;
import cn.z.mongo.entity.Pageable;
import com.demo.base.DaoBase;
import com.demo.entity.vo.LoginLogVo;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.query.Query;
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

    /**
     * 插入
     *
     * @param loginLog LoginLogVo
     * @return ok:T,e:null
     */
    public LoginLogVo insert(LoginLogVo loginLog) {
        return tryAnyNoTransactionReturnT(() -> mongoTemp.insert(loginLog));
    }

    /**
     * 分页查询
     *
     * @param query    Query
     * @param pageable Pageable
     * @return Page LoginLogVo
     */
    public Page<LoginLogVo> findPage(Query query, Pageable pageable) {
        return mongoTemp.findPage(query, pageable, CLAZZ);
    }

}
