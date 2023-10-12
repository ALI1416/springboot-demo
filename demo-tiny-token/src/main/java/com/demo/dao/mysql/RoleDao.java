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
        if (tryif(() -> roleMapper.insert(role))) {
            return role.getId();
        }
        return 0L;
    }

    /**
     * 更新
     *
     * @param role id(必须),name,seq(至少1个)
     * @return 是否成功
     */
    public boolean update(RoleVo role) {
        return tryif(() -> roleMapper.update(role));
    }

    /**
     * 删除
     *
     * @param id id
     * @return 是否成功
     */
    public boolean delete(long id) {
        return tryif(() -> roleMapper.delete(id));
    }

    /**
     * 查询所有
     *
     * @return List RoleVo
     */
    public List<RoleVo> findAll() {
        return roleMapper.findAll();
    }

    /**
     * 查询，通过createId
     *
     * @param createId createId
     * @return List RoleVo
     */
    public List<RoleVo> findByCreateId(long createId) {
        return roleMapper.findByCreateId(createId);
    }

    /**
     * 查询id，通过createId
     *
     * @param createId createId
     * @return List Long
     */
    public List<Long> findIdByCreateId(long createId) {
        return roleMapper.findIdByCreateId(createId);
    }

    /**
     * 查询，通过userId
     *
     * @param userId userId
     * @return List RoleVo
     */
    public List<RoleVo> findByUserId(long userId) {
        return roleMapper.findByUserId(userId);
    }

    /**
     * 查询id，通过userId
     *
     * @param userId userId
     * @return List Long
     */
    public List<Long> findIdByUserId(long userId) {
        return roleMapper.findIdByUserId(userId);
    }

}
