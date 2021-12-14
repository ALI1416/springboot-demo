package com.demo.mapper;

import com.demo.entity.po.RoleRoute2;
import com.demo.entity.vo.RoleRoute2Vo;
import com.demo.entity.vo.RoleRouteVo;

import java.util.List;

/**
 * <h1>RoleRoute2Mapper</h1>
 *
 * <p>
 * createDate 2021/12/08 09:49:54
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public interface RoleRoute2Mapper {

    /**
     * 插入多个
     *
     * @param roleRoute2s RoleRoute2Vo
     * @return 执行成功数量
     */
    int insertList(List<RoleRoute2Vo> roleRoute2s);

    /**
     * 删除，通过Route2Id
     *
     * @param ids Route2Id
     * @return 执行成功数量
     */
    int deleteByRoute2IdList(List<Long> ids);

    /**
     * 删除，通过RoleId
     *
     * @param roleId roleId
     * @return 执行成功数量
     */
    int deleteByRoleId(Long roleId);

}
