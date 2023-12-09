package com.demo.dao.mysql;

import cn.z.id.Id;
import com.demo.base.DaoBase;
import com.demo.entity.vo.RoleVo;
import com.demo.mapper.RoleMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <h1>角色</h1>
 *
 * <p>
 * createDate 2021/11/29 15:43:49
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Service
@AllArgsConstructor
public class RoleDao extends DaoBase {

    private final RoleMapper roleMapper;

    /**
     * 插入
     *
     * @param role name,seq,createId
     * @return ok:id,e:0
     */
    public long insert(RoleVo role) {
        role.setId(Id.next());
        return tryEq1(() -> roleMapper.insert(role)) ? role.getId() : 0;
    }

    /**
     * 更新
     *
     * @param role id(必须),name,seq(至少1个)
     * @return 是否成功更新1条
     */
    public boolean update(RoleVo role) {
        return tryEq1(() -> roleMapper.update(role));
    }

    /**
     * 删除
     *
     * @param id id
     * @return 是否成功删除1条
     */
    public boolean delete(long id) {
        return tryEq1(() -> roleMapper.delete(id));
    }

    /**
     * 查询所有
     *
     * @return List RoleVo
     */
    public List<RoleVo> findAll() {
        return roleMapper.find(null);
    }

    /**
     * 查询，通过创建者id
     *
     * @param createId createId
     * @return List RoleVo
     */
    public List<RoleVo> findByCreateId(long createId) {
        RoleVo role = new RoleVo();
        role.setCreateId(createId);
        return roleMapper.find(role);
    }

    /**
     * 是否存在id
     *
     * @param id id
     * @return 是否存在
     */
    public boolean existId(long id) {
        RoleVo role = new RoleVo();
        role.setId(id);
        return roleMapper.exist(role);
    }

    /**
     * 是否存在id和创建者id
     *
     * @param id       id
     * @param createId createId
     * @return 是否存在
     */
    public boolean existIdAndCreateId(long id, long createId) {
        RoleVo role = new RoleVo();
        role.setId(id);
        role.setCreateId(createId);
        return roleMapper.exist(role);
    }

    /**
     * 查询，通过id
     *
     * @param id id
     * @return RoleVo
     */
    public RoleVo findById(long id) {
        return roleMapper.findById(id);
    }

    /**
     * 查询id，通过用户id
     *
     * @param userId userId
     * @return List Long
     */
    public List<Long> findIdByUserId(long userId) {
        return roleMapper.findIdByUserId(userId);
    }

    /**
     * 查询，通过用户id
     *
     * @param userId userId
     * @return List RoleVo
     */
    public List<RoleVo> findByUserId(long userId) {
        return roleMapper.findByUserId(userId);
    }

}
