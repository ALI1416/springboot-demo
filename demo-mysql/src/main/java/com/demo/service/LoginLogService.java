package com.demo.service;

import com.demo.dao.mysql.LoginLogDao;
import com.demo.entity.po.LoginLog;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <h1>登录日志</h1>
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
public class LoginLogService {

    private final LoginLogDao loginLogDao;

    /**
     * 插入
     *
     * @param loginLog LoginLog
     * @return 是否成功
     */
    @Transactional
    public boolean insert(LoginLog loginLog) {
        return loginLogDao.insert(loginLog);
    }

    /**
     * 获取最后一条
     *
     * @return LoginLog
     */
    public LoginLog getLast() {
        return loginLogDao.getLast();
    }

}
