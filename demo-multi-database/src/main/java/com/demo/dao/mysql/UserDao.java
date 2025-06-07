package com.demo.dao.mysql;

import cn.z.id.Id;
import com.demo.base.DaoBase;
import com.demo.entity.vo.UserVo;
import com.demo.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * <h1>用户</h1>
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
     * @param user account,pwd
     * @return ok:id,e:0
     */
    public long insert(UserVo user) {
        user.setId(Id.next());
        return tryEq1(() -> userMapper.insert(user)) ? user.getId() : 0L;
    }

    /**
     * 更新
     *
     * @param user id(必须),account,pwd,name,gender,year,profile,comment(至少1个)
     * @return 是否成功
     */
    public boolean update(UserVo user) {
        return tryEq1(() -> userMapper.update(user));
    }

    /**
     * 删除通过account
     *
     * @param account account
     * @return 是否成功
     */
    public boolean deleteByAccount(String account) {
        UserVo userVo = new UserVo();
        userVo.setAccount(account);
        return tryEq1(() -> userMapper.deleteOne(userVo));
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
        return userMapper.findOne(userVo);
    }

}
