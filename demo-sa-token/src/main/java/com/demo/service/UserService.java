package com.demo.service;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.crypto.digest.BCrypt;
import cn.z.id.Id;
import com.demo.base.ServiceBase;
import com.demo.dao.mysql.UserDao;
import com.demo.dao.mysql.UserRoleDao;
import com.demo.entity.vo.UserRoleVo;
import com.demo.entity.vo.UserVo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    private final UserRoleDao userRoleDao;

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
        user.setCreateId(StpUtil.getLoginIdAsLong());
        return userDao.insert(user);
    }

    /**
     * 是否存在账号
     *
     * @param account account
     * @return 是否存在
     */
    public boolean existAccount(String account) {
        return userDao.existAccount(account);
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
     * 修改个人信息(除密码)
     *
     * @param user UserVo
     * @return 是否成功
     */
    @Transactional
    public boolean updateMyInfo(UserVo user) {
        user.setPwd(null);
        return userDao.update(user);
    }

    /**
     * 修改用户信息
     *
     * @param user UserVo
     * @return 是否成功
     */
    @Transactional
    public boolean update(UserVo user) {
        if (user.getPwd() != null) {
            user.setPwd(BCrypt.hashpw(user.getPwd()));
        }
        return userDao.update(user);
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

    /**
     * 更新用户的角色
     *
     * @param user user
     * @return 是否成功
     */
    @Transactional
    public boolean updateRole(UserVo user) {
        List<UserRoleVo> userRoles = new ArrayList<>();
        for (Long roleId : user.getRoleIds()) {
            UserRoleVo u = new UserRoleVo();
            u.setId(Id.next());
            u.setUserId(user.getId());
            u.setRoleId(roleId);
            userRoles.add(u);
        }
        return userRoleDao.deleteByUserId(user.getId()) && userRoleDao.insertList(userRoles);
    }

}
