package com.demo.mapper;

import com.demo.entity.po.LoginLogTest;

/**
 * <h1>登录日志测试</h1>
 *
 * <p>
 * createDate 2021/09/13 10:48:57
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public interface LoginLogTestMapper {

    /**
     * 插入
     *
     * @param loginLogTest LoginLogTest
     * @return 执行成功数量
     */
    int insert(LoginLogTest loginLogTest);

}
