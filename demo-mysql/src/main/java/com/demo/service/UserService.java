package com.demo.service;

import com.demo.base.ServiceBase;
import com.demo.dao.mysql.UserDao;
import com.demo.entity.bak.UserBak;
import com.demo.entity.pojo.PageInfo;
import com.demo.entity.pojo.ResultBatch;
import com.demo.entity.vo.UserVo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <h1>用户</h1>
 *
 * <p>
 * createDate 2021/10/26 15:37:20
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
     * 插入
     *
     * @param user account,pwd,createId
     * @return ok:id,e:0
     */
    @Transactional
    public long insert(UserVo user) {
        return userDao.insert(user);
    }

    /**
     * 批量插入
     *
     * @param userList account,pwd,createId
     * @return 是否成功
     */
    @Transactional
    public boolean batchInsert(List<UserVo> userList) {
        return userDao.batchInsert(userList);
    }

    /**
     * 批量插入含详情
     *
     * @param userList account,pwd,createId
     * @return ResultBatch UserVo
     */
    @Transactional
    public ResultBatch<UserVo> batchInsertDetail(List<UserVo> userList) {
        ResultBatch<UserVo> result = new ResultBatch<>();
        for (UserVo user : userList) {
            if (userDao.insertNotRollback(user) == 0) {
                result.add(false, user, "失败");
            } else {
                result.add(true, user, "成功");
            }
        }
        return result;
    }

    /**
     * 更新
     *
     * @param user id,updateId(必须),account,pwd,name,gender,year,profile,comment,isDelete(至少1个)
     * @return 是否成功
     */
    @Transactional
    public boolean update(UserVo user) {
        return userDao.update(user);
    }

    /**
     * 删除
     *
     * @param user id,updateId
     * @return 是否成功
     */
    @Transactional
    public boolean delete(UserVo user) {
        return userDao.delete(user);
    }

    /**
     * 恢复
     *
     * @param user id,updateId
     * @return 是否成功
     */
    @Transactional
    public boolean restore(UserVo user) {
        return userDao.restore(user);
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
     * 是否存在account
     *
     * @param account account
     * @return 是否存在
     */
    public boolean existAccount(String account) {
        return userDao.existAccount(account);
    }

    /**
     * 查询通过id
     *
     * @param id id
     * @return UserVo
     */
    public UserVo findById(long id) {
        return userDao.findById(id);
    }

    /**
     * 查询通过account
     *
     * @param account account
     * @return UserVo
     */
    public UserVo findByAccount(String account) {
        return userDao.findByAccount(account);
    }

    /**
     * 精确查询
     *
     * @param user UserVo
     * @return PageInfo UserVo
     */
    public PageInfo<UserVo> findExact(UserVo user) {
        return pagination(user, () -> userDao.findExact(user));
    }

    /**
     * 查询
     *
     * @param user UserVo
     * @return PageInfo UserVo
     */
    public PageInfo<UserVo> find(UserVo user) {
        return pagination(user, () -> userDao.find(user));
    }

    /**
     * 查询备份
     *
     * @param user UserBak
     * @return PageInfo UserBak
     */
    public PageInfo<UserBak> findBak(UserBak user) {
        return pagination(user, () -> userDao.findBak(user.getRefId()));
    }

}
