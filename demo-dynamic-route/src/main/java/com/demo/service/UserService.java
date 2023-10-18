package com.demo.service;

import cn.z.id.Id;
import cn.z.tool.BCrypt;
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
 * <h1>用户</h1>
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
     * 注册
     *
     * @param user account,name,pwd,createId
     * @return ok:id,e:0
     */
    @Transactional
    public long register(UserVo user) {
        user.setPwd(BCrypt.encode(user.getPwd()));
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
     * 查询通过账号
     *
     * @param account account
     * @return UserVo
     */
    public UserVo findByAccount(String account) {
        return userDao.findByAccount(account);
    }

    /**
     * 修改用户信息
     *
     * @param user id(必须),account,pwd,name,isDelete(至少1个)
     * @return 是否成功
     */
    @Transactional
    public boolean update(UserVo user) {
        if (user.getPwd() != null) {
            user.setPwd(BCrypt.encode(user.getPwd()));
        }
        return userDao.update(user);
    }

    /**
     * 查询用户，通过id
     *
     * @param id id
     * @return UserVo
     */
    public UserVo findById(long id) {
        return userDao.findById(id);
    }

    /**
     * 查询id，通过角色id
     *
     * @param roleId roleId
     * @return List Long
     */
    public List<Long> findIdByRoleId(long roleId) {
        return userDao.findIdByRoleId(roleId);
    }

    /**
     * 查询，通过角色id
     *
     * @param roleId roleId
     * @return List UserVo
     */
    public List<UserVo> findByRoleId(long roleId) {
        return userDao.findByRoleId(roleId);
    }

    /**
     * 查询，通过创建者id
     *
     * @param createId createId
     * @return List UserVo
     */
    public List<UserVo> findByCreateId(long createId) {
        return userDao.findByCreateId(createId);
    }

    /**
     * 查询，通过角色id和创建者id
     *
     * @param roleId   roleId
     * @param createId createId
     * @return List UserVo
     */
    public List<UserVo> findByRoleIdAndCreateId(long roleId, long createId) {
        return userDao.findByRoleIdAndCreateId(roleId, createId);
    }

    /**
     * 查询id是否为创建者id创建的
     *
     * @param id       id
     * @param createId createId
     * @return 是否
     */
    public boolean findExistByIdAndCreateId(long id, long createId) {
        return userDao.findExistByIdAndCreateId(id, createId);
    }

    /**
     * 查询全部
     *
     * @return List UserVo
     */
    public List<UserVo> findAll() {
        return userDao.findAll();
    }

    /**
     * 更新用户的角色
     *
     * @param user id,roleIdList
     * @return 是否成功
     */
    @Transactional
    public boolean updateRole(UserVo user) {
        List<UserRoleVo> userRoleList = new ArrayList<>();
        for (Long roleId : user.getRoleIdList()) {
            UserRoleVo u = new UserRoleVo();
            u.setId(Id.next());
            u.setUserId(user.getId());
            u.setRoleId(roleId);
            userRoleList.add(u);
        }
        return execute(() -> userRoleDao.deleteByUserId(user.getId()), () -> userRoleDao.insertList(userRoleList));
    }

}
