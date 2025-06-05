package com.demo.service;

import cn.z.id.Id;
import cn.z.tool.BCrypt;
import com.demo.base.ServiceBase;
import com.demo.dao.mysql.UserDao;
import com.demo.dao.mysql.UserRoleDao;
import com.demo.entity.pojo.PageInfo;
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
     * @param user account,name,password,createId
     * @return ok:id,e:0
     */
    @Transactional
    public long register(UserVo user) {
        user.setPassword(BCrypt.encode(user.getPassword()));
        return userDao.insert(user);
    }

    /**
     * 是否存在id
     *
     * @param id id
     * @return 是否存在
     */
    public boolean existId(long id) {
        return userDao.existId(id);
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
     * 查询用户，通过id
     *
     * @param id id
     * @return UserVo
     */
    public UserVo findById(long id) {
        return userDao.findById(id);
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
     * @param user id(必须),account,password,name,isDelete(至少1个)
     * @return 是否成功
     */
    @Transactional
    public boolean update(UserVo user) {
        if (user.getPassword() != null) {
            user.setPassword(BCrypt.encode(user.getPassword()));
        }
        return userDao.update(user);
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
     * @return PageInfo UserVo
     */
    public PageInfo<UserVo> findByRoleId(long roleId, Integer pages, Integer rows, String orderBy) {
        return pagination(() -> userDao.findByRoleId(roleId), pages, rows, orderBy);
    }

    /**
     * 查询，通过创建者id
     *
     * @param createId createId
     * @return PageInfo UserVo
     */
    public PageInfo<UserVo> findByCreateId(long createId, Integer pages, Integer rows, String orderBy) {
        return pagination(() -> userDao.findByCreateId(createId), pages, rows, orderBy);
    }

    /**
     * 查询id，通过创建者id
     *
     * @param createId createId
     * @return List Long
     */
    public List<Long> findIdByCreateId(long createId) {
        return userDao.findIdByCreateId(createId);
    }

    /**
     * 查询，通过角色id和创建者id
     *
     * @param roleId   roleId
     * @param createId createId
     * @return PageInfo UserVo
     */
    public PageInfo<UserVo> findByRoleIdAndCreateId(long roleId, long createId, Integer pages, Integer rows, String orderBy) {
        return pagination(() -> userDao.findByRoleIdAndCreateId(roleId, createId), pages, rows, orderBy);
    }

    /**
     * 查询id是否为创建者id创建的
     *
     * @param id       id
     * @param createId createId
     * @return 是否
     */
    public boolean existIdAndCreateId(long id, long createId) {
        return userDao.existIdAndCreateId(id, createId);
    }

    /**
     * 查询全部
     *
     * @return PageInfo UserVo
     */
    public PageInfo<UserVo> findAll(Integer pages, Integer rows, String orderBy) {
        return pagination(userDao::findAll, pages, rows, orderBy);
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
