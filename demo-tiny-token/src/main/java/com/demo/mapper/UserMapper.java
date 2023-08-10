package com.demo.mapper;

import com.demo.entity.vo.UserVo;

import java.util.List;

/**
 * <h1>用户Mapper</h1>
 *
 * <p>
 * createDate 2021/11/29 15:00:01
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public interface UserMapper {

    /**
     * 插入
     *
     * @param user id,account,pwd,name,createId
     * @return 执行成功数量
     */
    int insert(UserVo user);

    /**
     * 更新
     *
     * @param user id(必须),account,pwd,name(至少1个)
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
     * 查找唯一键
     *
     * @param user id,account(至少1个)
     * @return UserVo
     */
    UserVo findByUniqueKey(UserVo user);

    /**
     * 查询，通过roleId
     *
     * @param roleId roleId
     * @return List UserVo
     */
    List<UserVo> findByRoleId(Long roleId);

    /**
     * 查询全部
     *
     * @return List UserVo
     */
    List<UserVo> findAll();

}
