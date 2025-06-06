package com.demo.service;

import com.demo.base.ServiceBase;
import com.demo.dao.mysql.RoleDao;
import com.demo.dao.mysql.RoleRouteDao;
import com.demo.dao.mysql.UserRoleDao;
import com.demo.entity.vo.RoleRouteVo;
import com.demo.entity.vo.RoleVo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <h1>角色</h1>
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
    private final UserRoleDao userRoleDao;
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
     * 删除
     *
     * @param id id
     * @return 是否成功
     */
    @Transactional
    public boolean delete(long id) {
        return execute(() -> userRoleDao.deleteByRoleId(id), () -> roleRouteDao.deleteByRoleId(id), () -> roleDao.delete(id));
    }

    /**
     * 更新
     *
     * @param role id(必须),name,seq(至少1个)
     * @return 是否成功
     */
    @Transactional
    public boolean update(RoleVo role) {
        return roleDao.update(role);
    }

    /**
     * 添加路由
     *
     * @param list id,roleId,routeId
     * @return 是否成功
     */
    @Transactional
    public boolean addRouteIdList(List<RoleRouteVo> list) {
        return roleRouteDao.batchInsert(list);
    }

    /**
     * 查询所有
     *
     * @return List RoleVo
     */
    public List<RoleVo> findAll() {
        return roleDao.findAll();
    }

    /**
     * 查询，通过创建者id
     *
     * @param createId createId
     * @return List RoleVo
     */
    public List<RoleVo> findByCreateId(long createId) {
        return roleDao.findByCreateId(createId);
    }

    /**
     * 查询id，通过用户id
     *
     * @param userId userId
     * @return List Long
     */
    public List<Long> findIdByUserId(long userId) {
        return roleDao.findIdByUserId(userId);
    }

    /**
     * 查询，通过用户id
     *
     * @param userId userId
     * @return List RoleVo
     */
    public List<RoleVo> findByUserId(long userId) {
        return roleDao.findByUserId(userId);
    }

    /**
     * 查询，通过id
     *
     * @param id id
     * @return RoleVo
     */
    public RoleVo findById(long id) {
        return roleDao.findById(id);
    }

    /**
     * 是否存在id
     *
     * @param id id
     * @return 是否存在
     */
    public boolean existId(long id) {
        return roleDao.existId(id);
    }

    /**
     * 查询id是否为创建者id创建的
     *
     * @param id       id
     * @param createId createId
     * @return 是否存在
     */
    public boolean existIdAndCreateId(long id, long createId) {
        return roleDao.existIdAndCreateId(id, createId);
    }

}
