package com.demo.service;

import com.demo.base.ServiceBase;
import com.demo.dao.mysql.UserDao;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * <h1>UserService</h1>
 *
 * <p>
 * createDate 2021/12/08 16:49:48
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Service
@AllArgsConstructor
public class UserService extends ServiceBase {

    private final UserDao userDao;


}
