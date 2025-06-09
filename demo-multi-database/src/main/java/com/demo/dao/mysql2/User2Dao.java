package com.demo.dao.mysql2;

import cn.z.id.Id;
import com.demo.base.DaoBase;
import com.demo.entity.vo.User2Vo;
import com.demo.mapper2.User2Mapper;
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
public class User2Dao extends DaoBase {

    private final User2Mapper user2Mapper;

    /**
     * 插入
     *
     * @param user account,pwd
     * @return ok:id,e:0
     */
    public long insert(User2Vo user) {
        user.setId(Id.next());
        return tryEq1(() -> user2Mapper.insert(user)) ? user.getId() : 0L;
    }

    /**
     * 更新
     *
     * @param user id(必须),account,pwd,name,comment(至少1个)
     * @return 是否成功
     */
    public boolean update(User2Vo user) {
        return tryEq1(() -> user2Mapper.update(user));
    }

    /**
     * 删除通过account
     *
     * @param account account
     * @return 是否成功
     */
    public boolean deleteByAccount(String account) {
        User2Vo user2Vo = new User2Vo();
        user2Vo.setAccount(account);
        return tryEq1(() -> user2Mapper.deleteOne(user2Vo));
    }

    /**
     * 查询通过account
     *
     * @param account account
     * @return User2Vo
     */
    public User2Vo findByAccount(String account) {
        User2Vo user2Vo = new User2Vo();
        user2Vo.setAccount(account);
        return user2Mapper.findOne(user2Vo);
    }

}
