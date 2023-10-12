package com.demo.service;

import com.demo.dao.mysql.RoleDao;
import com.demo.dao.mysql.RoleRouteDao;
import com.demo.dao.mysql.RouteDao;
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
public class RoleService {

    private final RoleDao roleDao;
    private final RouteDao routeDao;
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
     * 删除
     *
     * @param id id
     * @return 是否成功
     */
    @Transactional
    public boolean delete(Long id) {
        return userRoleDao.deleteByRoleId(id) && roleRouteDao.deleteByRoleId(id) && roleDao.delete(id);
    }

    /**
     * 添加路由
     *
     * @param list id,roleId,routeId
     * @return 是否成功
     */
    @Transactional
    public boolean addRouteIdList(List<RoleRouteVo> list) {
        return roleRouteDao.insertList(list);
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
     * 查询，通过userId
     *
     * @param userId userId
     * @return List RoleVo
     */
    public List<RoleVo> findByUserId(Long userId) {
        return roleDao.findByUserId(userId);
    }

    /**
     * 查询id，通过userId
     *
     * @param userId userId
     * @return List Long
     */
    public List<Long> findIdByUserId(Long userId) {
        return roleDao.findIdByUserId(userId);
    }

    /**
     * 查询角色和路由，通过userId
     *
     * @param userId userId
     * @return List RoleVo
     */
    public List<RoleVo> findRoleAndRouteByUserId(Long userId) {
        List<RoleVo> roles = roleDao.findByUserId(userId);
        for (RoleVo role : roles) {
            role.setRouteIds(routeDao.findIdByRoleId(role.getId()));
        }
        return roles;
    }

    /**
     * 查询，通过createId
     *
     * @param createId createId
     * @return List RoleVo
     */
    public List<RoleVo> findByCreateId(Long createId) {
        return roleDao.findByCreateId(createId);
    }

}
