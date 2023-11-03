package com.demo.mapper;

import com.demo.entity.po.LoginLog;

/**
 * <h1>登录日志</h1>
 *
 * <p>
 * createDate 2021/09/13 10:48:57
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public interface LoginLogMapper {

    /**
     * 插入
     *
     * @param loginLog LoginLog
     * @return 执行成功数量
     */
    int insert(LoginLog loginLog);

    /**
     * 删除，通过id
     *
     * @param id id
     * @return 执行成功数量
     */
    int deleteById(long id);

    /**
     * 获取最后一条
     *
     * @return LoginLog
     */
    LoginLog getLast();

}
