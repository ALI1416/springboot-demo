package com.demo.dao.mysql;

import cn.z.id.Id;
import com.demo.base.DaoBase;
import com.demo.entity.vo.UserVo;
import com.demo.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <h1>用户Dao</h1>
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
     * @param user account,pwd,name,createId
     * @return ok:id,e:0
     */
    public long insert(UserVo user) {
        user.setId(Id.next());
        if (tryif(() -> userMapper.insert(user))) {
            return user.getId();
        }
        return 0L;
    }

    /**
     * 更新
     *
     * @param user id(必须),account,pwd,name(至少1个)
     * @return 是否成功
     */
    public boolean update(UserVo user) {
        return tryif(() -> userMapper.update(user));
    }

    /**
     * 是否存在account
     *
     * @param account account
     * @return 是否存在
     */
    public boolean existAccount(String account) {
        UserVo user = new UserVo();
        user.setAccount(account);
        return userMapper.existUniqueKey(user);
    }

    /**
     * 查询通过id
     *
     * @param id id
     * @return UserVo
     */
    public UserVo findById(Long id) {
        UserVo user = new UserVo();
        user.setId(id);
        return userMapper.findByUniqueKey(user);
    }

    /**
     * 查询通过account
     *
     * @param account account
     * @return UserVo
     */
    public UserVo findByAccount(String account) {
        UserVo user = new UserVo();
        user.setAccount(account);
        return userMapper.findByUniqueKey(user);
    }

    /**
     * 查询，通过roleId
     *
     * @param roleId roleId
     * @return List UserVo
     */
    public List<UserVo> findByRoleId(Long roleId) {
        return userMapper.findByRoleId(roleId);
    }

    /**
     * 查询全部
     *
     * @return List UserVo
     */
    public List<UserVo> findAll() {
        return userMapper.findAll();
    }

}
