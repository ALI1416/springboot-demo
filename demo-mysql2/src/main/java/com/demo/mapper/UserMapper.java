package com.demo.mapper;

import com.demo.entity.po.UserBak;
import com.demo.entity.vo.UserVo;

import java.util.List;

/**
 * <h1>用户Mapper</h1>
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
     * @param user id,updateId;至少一个account,pwd,name,gender,year,profile,comment,isDelete
     * @return 执行成功数量
     */
    int update(UserVo user);

    /**
     * 存在一个唯一键
     *
     * @param user 仅一个id,account
     * @return 是否存在
     */
    boolean existUniqueKey(UserVo user);

    /**
     * 查询一个唯一键
     *
     * @param user 仅一个id,account
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
     * @param id id
     * @return List UserBak
     */
    List<UserBak> findBak(Long id);

}
