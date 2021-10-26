package com.demo.dao.mysql;

import cn.z.id.Id;
import com.demo.base.DaoBase;
import com.demo.entity.vo.UserVo;
import com.demo.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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
     * 插入(需id,account,pwd,createId)
     */
    public long insert(UserVo user) {
        user.setId(Id.next());
        return tryif(() -> userMapper.insert(user)) ? user.getId() : 0L;
    }


}
