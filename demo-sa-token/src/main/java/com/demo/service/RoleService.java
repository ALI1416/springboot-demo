package com.demo.service;

import com.demo.base.ServiceBase;
import com.demo.dao.mysql.RoleDao;
import com.demo.dao.mysql.RoleRouteDao;
import com.demo.entity.vo.RoleRouteVo;
import com.demo.entity.vo.RoleVo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <h1>RoleService</h1>
 *
 * <p>
 * createDate 2021/11/29 15:43:03
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Service
@AllArgsConstructor
public class RoleService extends ServiceBase {

    private final RoleDao roleDao;
    private final RoleRouteDao roleRouteDao;

    /**
     * 插入
     *
     * @param role name,seq,createId
     * @return ok:id,e:0
     */
    @Transactional
    public long insert(RoleVo role) {
        return roleDao.insert(role);
    }

    /**
     * 更新(Id、createId除外)
     *
     * @param role RoleVo
     * @return 是否成功
     */
    @Transactional
    public boolean update(RoleVo role) {
        return roleDao.update(role);
    }

    /**
     * 删除
     *
     * @param id id
     * @return 是否成功
     */
    @Transactional
    public boolean delete(Long id) {
        return roleDao.deleteRoleRouteByRoleId(id) && roleDao.delete(id);
    }

    /**
     * 插入多个
     *
     * @param roleRoutes RoleRouteVo
     * @return 是否成功
     */
    @Transactional
    public boolean addRouteIdList(List<RoleRouteVo> roleRoutes) {
        return roleRouteDao.insertList(roleRoutes);
    }

    /**
     * 查询所有
     *
     * @return List&lt;RoleVo>
     */
    public List<RoleVo> findAll() {
        return roleDao.findAll();
    }

    /**
     * 查询UserId拥有的角色
     *
     * @param userId userId
     * @return List&lt;RoleVo>
     */
    public List<RoleVo> findOwnByUserId(Long userId) {
        return roleDao.findOwnByUserId(userId);
    }

    /**
     * 查询所有通过CreateId
     *
     * @param createId CreateId
     * @return List&lt;RoleVo>
     */
    public List<RoleVo> findByCreateId(Long createId) {
        return roleDao.findByCreateId(createId);
    }

}
