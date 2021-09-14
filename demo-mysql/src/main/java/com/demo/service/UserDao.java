package com.demo.service;

import com.demo.base.DaoBase;
import com.demo.dao.mysql.UserProfileDao;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * <h1>UserService</h1>
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
public class UserDao extends DaoBase {

    private final UserProfileDao userProfileDao;

}
