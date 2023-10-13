package com.demo.mapper;

import com.demo.entity.bak.UserBak;
import com.demo.entity.vo.UserVo;

import java.util.List;

/**
 * <h1>用户</h1>
 *
 * <p>
 * createDate 2021/10/26 14:04:27
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public interface UserMapper {

    /**
     * 插入
     *
     * @param user id,account,pwd,createId
     * @return 执行成功数量
     */
    int insert(UserVo user);

    /**
     * 批量插入
     *
     * @param users List UserVo id,account,pwd,createId
     * @return 执行成功数量
     */
    int batchInsert(List<UserVo> users);

    /**
     * 备份
     *
     * @param user id,refId
     * @return 执行成功数量
     */
    int bak(UserBak user);

    /**
     * 更新
     *
     * @param user id,updateId(必须),account,pwd,name,gender,year,profile,comment,isDelete(至少1个)
     * @return 执行成功数量
     */
    int update(UserVo user);

    /**
     * 是否存在唯一键
     *
     * @param user id,account(至少1个)
     * @return 是否存在
     */
    boolean existUniqueKey(UserVo user);

    /**
     * 是否存在唯一键
     *
     * @param user id,account(至少1个)
     * @return UserVo
     */
    UserVo findByUniqueKey(UserVo user);

    /**
     * 精确查询
     *
     * @param user UserVo
     * @return List UserVo
     */
    List<UserVo> findExact(UserVo user);

    /**
     * 查询
     *
     * @param user UserVo
     * @return List UserVo
     */
    List<UserVo> find(UserVo user);

    /**
     * 查询备份
     *
     * @param refId refId
     * @return List UserBak
     */
    List<UserBak> findBak(long refId);

}
