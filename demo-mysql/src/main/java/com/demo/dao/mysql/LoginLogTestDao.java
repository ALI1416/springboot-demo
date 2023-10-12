package com.demo.dao.mysql;

import com.demo.base.DaoBase;
import com.demo.entity.po.LoginLogTest;
import com.demo.mapper.LoginLogTestMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * <h1>登录日志测试</h1>
 *
 * <p>
 * createDate 2021/09/13 10:46:29
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Service
@AllArgsConstructor
public class LoginLogTestDao extends DaoBase {

    private final LoginLogTestMapper loginLogTestMapper;

    /**
     * 插入
     *
     * @param loginLogTest LoginLogTest
     * @return 是否成功
     */
    public boolean insert(LoginLogTest loginLogTest) {
        return tryif(() -> loginLogTestMapper.insert(loginLogTest));
    }

}
