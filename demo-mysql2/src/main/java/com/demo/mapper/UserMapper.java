package com.demo.mapper;

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
     * 插入(需id,account,pwd,createId)
     */
    int insert(UserVo user);

    /**
     * 更新(需id,updateId;至少一个account,pwd,name,gender,year,profile,comment)
     */
    int update(UserVo user);

    /**
     * 删除(需id,updateId)
     */
    int delete(UserVo user);

    /**
     * 存在一个唯一键(仅一个id,account)
     */
    boolean existUniqueKey(UserVo user);

    /**
     * 查询一个唯一键(仅一个id,account)
     */
    UserVo findByUniqueKey(UserVo user);

    /**
     * 精确查询
     */
    List<UserVo> findExact(UserVo user);

    /**
     * 查询
     */
    List<UserVo> find(UserVo user);

}
