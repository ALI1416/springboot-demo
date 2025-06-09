package com.demo.service;

import com.demo.dao.mysql.UserDao;
import com.demo.dao.mysql2.User2Dao;
import com.demo.entity.vo.User2Vo;
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
    private final User2Dao user2Dao;

    /**
     * 插入
     *
     * @param user account,pwd
     * @return ok:id,e:0
     */
    @Transactional
    public long insert(UserVo user) {
        if (userDao.insert(user) != 0) {
            User2Vo user2 = new User2Vo();
            user2.setAccount(user.getAccount());
            user2.setPwd(user.getPwd());
            return user2Dao.insert(user2);
        }
        return 0;
    }

    /**
     * 更新
     *
     * @param user id(必须),account,pwd,name,gender,year,profile,comment(至少1个)
     * @return 是否成功
     */
    @Transactional
    public boolean update(UserVo user) {
        if (userDao.update(user)) {
            User2Vo user2 = new User2Vo();
            user.setId(user.getId());
            user2.setAccount(user.getAccount());
            user2.setPwd(user.getPwd());
            user2.setName(user.getName());
            user2.setComment(user.getComment());
            return user2Dao.update(user2);
        }
        return false;
    }

    /**
     * 删除通过account
     *
     * @param account account
     * @return 是否成功
     */
    @Transactional
    public boolean delete(String account) {
        if (userDao.deleteByAccount(account)) {
            return user2Dao.deleteByAccount(account);
        }
        return false;
    }


    /**
     * 查询通过account
     *
     * @param account account
     * @return UserVo
     */
    public UserVo findByAccount(String account) {
        UserVo user = userDao.findByAccount(account);
        user.setComment(user2Dao.findByAccount(account).toString());
        return user;
    }

}
