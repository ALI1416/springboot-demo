package com.demo.service;

import com.demo.dao.mysql.UserDao;
import com.demo.entity.vo.UserVo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <h1>用户Service</h1>
 *
 * <p>
 * createDate 2021/10/26 15:37:20
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Service
@AllArgsConstructor
public class UserService {

    private final UserDao userDao;

    /**
     * 插入
     */
    @Transactional
    public Long insert(UserVo user) {
        UserVo userVo = new UserVo();
        userVo.setAccount("a");
        userVo.setPwd("111");
        userVo.setCreateId(0L);
        userDao.insert(userVo);
        return userDao.insert(user);
    }

}
