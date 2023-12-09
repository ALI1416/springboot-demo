package com.demo.dao.mysql;

import cn.z.id.Id;
import com.demo.base.DaoBase;
import com.demo.entity.vo.RouteVo;
import com.demo.mapper.RouteMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <h1>路由</h1>
 *
 * <p>
 * createDate 2021/11/29 14:05:18
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Service
@AllArgsConstructor
public class RouteDao extends DaoBase {

    private final RouteMapper routeMapper;

    /**
     * 插入
     *
     * @param route path,name,seq,parentId
     * @return ok:id,e:0
     */
    public long insert(RouteVo route) {
        route.setId(Id.next());
        return tryEq1(() -> routeMapper.insert(route)) ? route.getId() : 0;
    }

    /**
     * 批量插入
     *
     * @param list id,path,name,seq,parentId
     * @return 是否成功
     */
    public boolean batchInsert(List<RouteVo> list) {
        return tryAny(() -> routeMapper.batchInsert(list));
    }

    /**
     * 删除
     *
     * @param id id
     * @return 是否成功删除1条
     */
    public boolean deleteById(long id) {
        return tryEq1(() -> routeMapper.delete(id));
    }

    /**
     * 批量删除
     *
     * @param list id
     * @return 是否成功
     */
    public boolean batchDelete(List<Long> list) {
        return tryAny(() -> routeMapper.batchDelete(list));
    }

    /**
     * 更新
     *
     * @param route id(必须),path,name,seq,parentId(至少1个)
     * @return 是否成功更新1条
     */
    public boolean update(RouteVo route) {
        return tryEq1(() -> routeMapper.update(route));
    }

    /**
     * 查询，通过id
     *
     * @param id id
     * @return RouteVo
     */
    public RouteVo findById(long id) {
        return routeMapper.findById(id);
    }

    /**
     * 查询，通过父id
     *
     * @param parentId parentId
     * @return List RouteVo
     */
    public List<RouteVo> findByParentId(long parentId) {
        RouteVo route = new RouteVo();
        route.setParentId(parentId);
        return routeMapper.find(route);
    }

    /**
     * 查询所有
     *
     * @return List RouteVo
     */
    public List<RouteVo> findAll() {
        return routeMapper.find(null);
    }

    /**
     * 查询id，通过角色id
     *
     * @param roleId roleId
     * @return List Long
     */
    public List<Long> findIdByRoleId(long roleId) {
        return routeMapper.findIdByRoleId(roleId);
    }

}
