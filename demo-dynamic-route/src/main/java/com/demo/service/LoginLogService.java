package com.demo.service;

import com.demo.base.ServiceBase;
import com.demo.dao.mongo.LoginLogDao;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * <h1>登录日志</h1>
 *
 * <p>
 * createDate 2023/11/04 17:28:27
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Service
@AllArgsConstructor
public class LoginLogService extends ServiceBase {

    private final LoginLogDao loginLogDao;

}
