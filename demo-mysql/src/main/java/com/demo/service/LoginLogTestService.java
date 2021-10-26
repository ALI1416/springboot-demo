package com.demo.service;

import com.demo.dao.mysql.LoginLogTestDao;
import com.demo.entity.po.LoginLogTest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <h1>登录日志测试Service</h1>
 *
 * <p>
 * createDate 2021/09/13 10:49:28
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Service
@AllArgsConstructor
public class LoginLogTestService {

    private final LoginLogTestDao loginLogTestDao;

    /**
     * 插入
     */
    @Transactional
    public boolean insert(LoginLogTest loginLogTest) {
        return loginLogTestDao.insert(loginLogTest);
    }

}
