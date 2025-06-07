package com.demo.mapper;

import com.demo.entity.vo.UserVo;

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
     * @param user id,account,pwd
     * @return 执行成功数量
     */
    int insert(UserVo user);

    /**
     * 更新
     *
     * @param user id(必须),account,pwd,name,gender,year,profile,comment(至少1个)
     * @return 执行成功数量
     */
    int update(UserVo user);

    /**
     * 删除唯一键
     *
     * @param user id,account(至少1个)
     * @return 执行成功数量
     */
    int deleteOne(UserVo user);

    /**
     * 查询唯一键
     *
     * @param user id,account(至少1个)
     * @return UserVo
     */
    UserVo findOne(UserVo user);

}
