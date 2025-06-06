package com.demo.dao.mysql;

import cn.z.id.Id;
import com.demo.base.DaoBase;
import com.demo.entity.vo.UserVo;
import com.demo.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <h1>用户</h1>
 *
 * <p>
 * createDate 2021/12/08 16:36:40
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Service
@AllArgsConstructor
public class UserDao extends DaoBase {

    private final UserMapper userMapper;

    /**
     * 插入
     *
     * @param user account,password,name,createId
     * @return ok:id,e:0
     */
    public long insert(UserVo user) {
        user.setId(Id.next());
        return tryEq1(() -> userMapper.insert(user)) ? user.getId() : 0;
    }

    /**
     * 更新
     *
     * @param user id(必须),account,password,name,isDelete(至少1个)
     * @return 是否成功更新1条
     */
    public boolean update(UserVo user) {
        return tryEq1(() -> userMapper.update(user));
    }

    /**
     * 是否存在id
     *
     * @param id id
     * @return 是否存在
     */
    public boolean existId(long id) {
        UserVo user = new UserVo();
        user.setId(id);
        return userMapper.exist(user);
    }

    /**
     * 是否存在账号
     *
     * @param account account
     * @return 是否存在
     */
    public boolean existAccount(String account) {
        UserVo user = new UserVo();
        user.setAccount(account);
        return userMapper.exist(user);
    }

    /**
     * 是否存在id和创建者id
     *
     * @param id       id
     * @param createId createId
     * @return 是否存在
     */
    public boolean existIdAndCreateId(long id, long createId) {
        UserVo user = new UserVo();
        user.setId(id);
        user.setCreateId(createId);
        return userMapper.exist(user);
    }

    /**
     * 查询通过id
     *
     * @param id id
     * @return UserVo
     */
    public UserVo findById(long id) {
        UserVo user = new UserVo();
        user.setId(id);
        return userMapper.findOne(user);
    }

    /**
     * 查询通过账号
     *
     * @param account account
     * @return UserVo
     */
    public UserVo findByAccount(String account) {
        UserVo user = new UserVo();
        user.setAccount(account);
        return userMapper.findOne(user);
    }

    /**
     * 查询，通过创建者id
     *
     * @param createId createId
     * @return List UserVo
     */
    public List<UserVo> findByCreateId(long createId) {
        UserVo user = new UserVo();
        user.setCreateId(createId);
        return userMapper.find(user);
    }

    /**
     * 查询id，通过创建者id
     *
     * @param createId createId
     * @return List Long
     */
    public List<Long> findIdByCreateId(long createId) {
        UserVo user = new UserVo();
        user.setCreateId(createId);
        return userMapper.findId(user);
    }

    /**
     * 查询全部
     *
     * @return List UserVo
     */
    public List<UserVo> findAll() {
        return userMapper.find(null);
    }

    /**
     * 查询id，通过角色id
     *
     * @param roleId roleId
     * @return List Long
     */
    public List<Long> findIdByRoleId(long roleId) {
        return userMapper.findIdByRoleId(roleId);
    }

    /**
     * 查询，通过角色id
     *
     * @param roleId roleId
     * @return List UserVo
     */
    public List<UserVo> findByRoleId(long roleId) {
        return userMapper.findByRoleId(roleId);
    }

    /**
     * 查询，通过角色id和创建者id
     *
     * @param roleId   roleId
     * @param createId createId
     * @return List UserVo
     */
    public List<UserVo> findByRoleIdAndCreateId(long roleId, long createId) {
        return userMapper.findByRoleIdAndCreateId(roleId, createId);
    }

}
