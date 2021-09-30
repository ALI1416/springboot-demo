package com.demo.mapper;

import com.demo.entity.vo.UserProfileVo;

import java.util.List;

/**
 * <h1>UserProfileMapper</h1>
 *
 * <p>
 * createDate 2021/09/13 10:48:57
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public interface UserProfileMapper {

    /**
     * 插入(需id,account,pwd,createId)
     */
    int insert(UserProfileVo userProfileVo);

    /**
     * 注册，通过account(需id,account,pwd)
     */
    int register(UserProfileVo userProfileVo);

    /**
     * 存在一个唯一键(仅一个id,account)
     */
    boolean existUniqueKey(UserProfileVo userProfileVo);

    /**
     * 查询一个唯一键(仅一个id,account)
     */
    UserProfileVo findByUniqueKey(UserProfileVo userProfileVo);

    /**
     * 更新(需id;至少一个account,pwd,name,gender,year,profile,comment)
     */
    int update(UserProfileVo userProfileVo);

    /**
     * 删除(需id)
     */
    int delete(UserProfileVo userProfileVo);

    /**
     * 查找account列表(需account)
     */
    List<UserProfileVo> findByAccountList(List<String> accountList);

    /**
     * 精确查询
     */
    List<UserProfileVo> findExact(UserProfileVo userProfileVo);

    /**
     * 查询
     */
    List<UserProfileVo> find(UserProfileVo userProfileVo);
}
