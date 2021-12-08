package com.demo.dao.mysql;

import cn.z.id.Id;
import com.demo.base.DaoBase;
import com.demo.entity.vo.RoleVo;
import com.demo.mapper.RoleMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <h1>RoleDao</h1>
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
     * @param role 必须id，至少一个name,seq
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
    public boolean delete(Long id) {
        return tryif(() -> roleMapper.delete(id));
    }

    /**
     * 删除RoleRoute表，通过RoleId
     *
     * @param id RoleId
     * @return 是否成功
     */
    public boolean deleteRoleRouteByRoleId(Long id) {
        return tryif3(() -> roleMapper.deleteRoleRouteByRoleId(id));
    }

    /**
     * 查询所有
     *
     * @return List&lt;RoleVo>
     */
    public List<RoleVo> findAll() {
        return roleMapper.findAll();
    }

    /**
     * 查询所有通过CreateId
     *
     * @param createId CreateId
     * @return List&lt;RoleVo>
     */
    public List<RoleVo> findByCreateId(Long createId) {
        return roleMapper.findByCreateId(createId);
    }

    /**
     * 查询UserId拥有的角色
     *
     * @param userId userId
     * @return List&lt;RoleVo>
     */
    public List<RoleVo> findOwnByUserId(Long userId) {
        return roleMapper.findOwnByUserId(userId);
    }

}
