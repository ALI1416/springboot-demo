package com.demo.service;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.demo.base.ServiceBase;
import com.demo.dao.mysql.UserDao;
import com.demo.entity.vo.UserVo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    /**
     * 登录
     *
     * @param user UserVo
     * @return 是否成功
     */
    public boolean login(UserVo user) {
        UserVo u = userDao.findByAccount(user.getAccount());
        if (u == null) {
            return false;
        }
        if (BCrypt.checkpw(user.getPwd(), u.getPwd())) {
            StpUtil.login(u.getId());
            return true;
        }
        return false;
    }

    /**
     * 注册
     *
     * @param user UserVo
     * @return ok:id,e:0
     */
    @Transactional
    public long register(UserVo user) {
        user.setPwd(BCrypt.hashpw(user.getPwd()));
        if (userDao.existAccount(user.getAccount())) {
            return 0L;
        }
        user.setCreateId(StpUtil.getLoginIdAsLong());
        return userDao.insert(user);
    }

    /**
     * 修改密码
     *
     * @param user UserVo
     * @return 是否成功
     */
    @Transactional
    public boolean changePwd(UserVo user) {
        UserVo u = new UserVo();
        u.setId(user.getId());
        u.setPwd(BCrypt.hashpw(user.getNewPwd()));
        return userDao.update(u);
    }

    /**
     * 查询用户信息
     *
     * @param id id
     * @return UserVo
     */
    public UserVo info(Long id) {
        return userDao.findById(id);
    }

    /**
     * 查询拥有指定角色id的用户
     *
     * @param roleId 角色id
     * @return List&lt;UserVo>
     */
    public List<UserVo> findByRoleId(Long roleId) {
        return userDao.findByRoleId(roleId);
    }

    /**
     * 查询全部
     *
     * @return List&lt;UserVo>
     */
    public List<UserVo> findAll() {
        return userDao.findAll();
    }

}
