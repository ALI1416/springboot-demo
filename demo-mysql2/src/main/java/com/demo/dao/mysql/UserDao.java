package com.demo.dao.mysql;

import cn.z.id.Id;
import com.demo.base.DaoBase;
import com.demo.entity.po.UserBak;
import com.demo.entity.vo.UserVo;
import com.demo.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <h1>用户Dao</h1>
 *
 * <p>
 * createDate 2021/10/26 14:51:26
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
     * @param user account,pwd,createId
     * @return ok:id,e:0
     */
    public long insert(UserVo user) {
        user.setId(Id.next());
        if (tryif(() -> userMapper.insert(user))) {
            userMapper.bak(new UserBak(user.getId()));
            return user.getId();
        } else {
            return 0L;
        }
    }

    /**
     * 更新
     *
     * @param user id,updateId;至少一个account,pwd,name,gender,year,profile,comment
     * @return 是否成功
     */
    public boolean update(UserVo user) {
        user.setIsDelete(null);
        if (tryif(() -> userMapper.update(user))) {
            userMapper.bak(new UserBak(user.getId()));
            return true;
        } else {
            return false;
        }
    }

    /**
     * 删除
     *
     * @param user id,updateId
     * @return 是否成功
     */
    public boolean delete(UserVo user) {
        UserVo userVo = new UserVo();
        userVo.setId(user.getId());
        userVo.setAccount(user.getId().toString());
        userVo.setIsDelete(1);
        userVo.setUpdateId(user.getUpdateId());
        if (tryif(() -> userMapper.update(userVo))) {
            userMapper.bak(new UserBak(user.getId()));
            return true;
        } else {
            return false;
        }
    }

    /**
     * 恢复
     *
     * @param user id,updateId
     * @return 是否成功
     */
    public boolean restore(UserVo user) {
        UserVo userVo = new UserVo();
        userVo.setId(user.getId());
        userVo.setIsDelete(0);
        userVo.setUpdateId(user.getUpdateId());
        if (tryif(() -> userMapper.update(userVo))) {
            userMapper.bak(new UserBak(user.getId()));
            return true;
        } else {
            return false;
        }
    }

    /**
     * 是否存在id
     *
     * @param id id
     * @return 是否存在
     */
    public boolean existId(long id) {
        UserVo userVo = new UserVo();
        userVo.setId(id);
        return userMapper.existUniqueKey(userVo);
    }

    /**
     * 是否存在account
     *
     * @param account account
     * @return 是否存在
     */
    public boolean existAccount(String account) {
        UserVo userVo = new UserVo();
        userVo.setAccount(account);
        return userMapper.existUniqueKey(userVo);
    }

    /**
     * 查询通过id
     *
     * @param id id
     * @return UserVo
     */
    public UserVo findById(long id) {
        UserVo userVo = new UserVo();
        userVo.setId(id);
        return userMapper.findByUniqueKey(userVo);
    }

    /**
     * 查询通过account
     *
     * @param account account
     * @return UserVo
     */
    public UserVo findByAccount(String account) {
        UserVo userVo = new UserVo();
        userVo.setAccount(account);
        return userMapper.findByUniqueKey(userVo);
    }

    /**
     * 精确查询
     *
     * @param user UserVo
     * @return List<UserVo>
     */
    public List<UserVo> findExact(UserVo user) {
        return userMapper.findExact(user);
    }

    /**
     * 查询
     *
     * @param user UserVo
     * @return List<UserVo>
     */
    public List<UserVo> find(UserVo user) {
        return userMapper.find(user);
    }

    /**
     * 查询备份
     *
     * @param id id
     * @return List<UserBak>
     */
    public List<UserBak> findBak(Long id) {
        return userMapper.findBak(id);
    }

}
