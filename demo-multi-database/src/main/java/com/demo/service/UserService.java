package com.demo.service;

import com.demo.dao.mysql.UserDao;
import com.demo.entity.vo.UserVo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <h1>用户</h1>
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
     *
     * @param user account,pwd
     * @return ok:id,e:0
     */
    @Transactional
    public long insert(UserVo user) {
        return userDao.insert(user);
    }

    /**
     * 更新
     *
     * @param user id(必须),account,pwd,name,gender,year,profile,comment(至少1个)
     * @return 是否成功
     */
    @Transactional
    public boolean update(UserVo user) {
        return userDao.update(user);
    }

    /**
     * 删除通过account
     *
     * @param account account
     * @return 是否成功
     */
    @Transactional
    public boolean delete(String account) {
        return userDao.deleteByAccount(account);
    }


    /**
     * 查询通过account
     *
     * @param account account
     * @return UserVo
     */
    public UserVo findByAccount(String account) {
        return userDao.findByAccount(account);
    }

}
