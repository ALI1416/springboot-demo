package com.demo.mapper;

import com.demo.entity.vo.UserVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <h1>用户</h1>
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
     * @param user id,account,password,name,createId
     * @return 执行成功数量
     */
    int insert(UserVo user);

    /**
     * 更新
     *
     * @param user id(必须),account,password,name,isDelete(至少1个)
     * @return 执行成功数量
     */
    int update(UserVo user);

    /**
     * 是否存在
     *
     * @param user UserVo
     * @return 是否存在
     */
    boolean exist(UserVo user);

    /**
     * 查询
     *
     * @param user UserVo
     * @return List UserVo
     */
    List<UserVo> find(UserVo user);

    /**
     * 查询id
     *
     * @param user UserVo
     * @return List Long
     */
    List<Long> findId(UserVo user);

    /**
     * 查询唯一键
     *
     * @param user id,account(至少1个)
     * @return UserVo
     */
    UserVo findOne(UserVo user);

    /**
     * 查询id，通过roleId
     *
     * @param roleId roleId
     * @return List Long
     */
    List<Long> findIdByRoleId(long roleId);

    /**
     * 查询，通过roleId
     *
     * @param roleId roleId
     * @return List UserVo
     */
    List<UserVo> findByRoleId(long roleId);

    /**
     * 查询，通过roleId和createId
     *
     * @param roleId   roleId
     * @param createId createId
     * @return List UserVo
     */
    List<UserVo> findByRoleIdAndCreateId(@Param("roleId") long roleId, @Param("createId") long createId);

}
