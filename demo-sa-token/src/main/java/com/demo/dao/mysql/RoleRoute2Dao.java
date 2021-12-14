package com.demo.dao.mysql;

import com.demo.base.DaoBase;
import com.demo.entity.vo.RoleRoute2Vo;
import com.demo.mapper.RoleRoute2Mapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>RoleRoute2Dao</h1>
 *
 * <p>
 * createDate 2021/12/08 09:53:08
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Service
@AllArgsConstructor
public class RoleRoute2Dao extends DaoBase {

    private final RoleRoute2Mapper roleRoute2Mapper;

    /**
     * 插入多个
     *
     * @param roleRoute2s RoleRoute2Vo
     * @return 是否成功
     */
    public boolean insertList(List<RoleRoute2Vo> roleRoute2s) {
        return tryif2(() -> roleRoute2Mapper.insertList(roleRoute2s) == roleRoute2s.size());
    }

    /**
     * 删除，通过Route2Id
     *
     * @param ids Route2Id
     * @return 是否成功
     */
    public boolean deleteByRoute2IdList(List<Long> ids) {
        return tryif3(() -> roleRoute2Mapper.deleteByRoute2IdList(ids));
    }

    /**
     * 删除，通过RoleId
     *
     * @param roleId   roleId
     * @return 是否成功
     */
    public boolean deleteByRoleId(Long roleId) {
        return tryif3(() -> roleRoute2Mapper.deleteByRoleId(roleId));
    }

    /**
     * 删除，通过Route2Id
     *
     * @param id Route2Id
     * @return 是否成功
     */
    public boolean deleteByRoute2Id(Long id) {
        List<Long> ids = new ArrayList<>();
        ids.add(id);
        return deleteByRoute2IdList(ids);
    }

}
