package com.demo.dao.mysql;

import com.demo.base.DaoBase;
import com.demo.entity.po.LoginLog;
import com.demo.mapper.LoginLogMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * <h1>登录日志</h1>
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
public class LoginLogDao extends DaoBase {

    private final LoginLogMapper loginLogMapper;

    /**
     * 插入
     *
     * @param loginLog LoginLog
     * @return 是否成功
     */
    public boolean insert(LoginLog loginLog) {
        return tryEq1(() -> loginLogMapper.insert(loginLog));
    }

    /**
     * 删除，通过id
     *
     * @param id id
     * @return 是否成功
     */
    public boolean deleteById(long id) {
        return tryEq1(() -> loginLogMapper.deleteById(id));
    }

    /**
     * 获取最后一条
     *
     * @return LoginLog
     */
    public LoginLog getLast() {
        return loginLogMapper.getLast();
    }

}
