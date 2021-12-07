package com.demo.dao.mysql;

import cn.z.id.Id;
import com.demo.base.DaoBase;
import com.demo.entity.vo.RouteNotInterceptVo;
import com.demo.entity.vo.RouteVo;
import com.demo.mapper.RouteMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>RouteDao</h1>
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
        if (tryif(() -> routeMapper.insert(route))) {
            return route.getId();
        }
        return 0L;
    }

    /**
     * 更新
     *
     * @param route 必须id，至少一个path,name,seq,parentId
     * @return 是否成功
     */
    public boolean update(RouteVo route) {
        return tryif(() -> routeMapper.update(route));
    }

    /**
     * 删除
     *
     * @param id id
     * @return 是否成功
     */
    public boolean deleteById(Long id) {
        List<Long> ids = new ArrayList<>();
        ids.add(id);
        return deleteByIdList(ids);
    }

    /**
     * 删除
     *
     * @param ids ids
     * @return 是否成功
     */
    public boolean deleteByIdList(List<Long> ids) {
        return tryif2(() -> routeMapper.deleteByIdList(ids) == ids.size());
    }

    /**
     * 删除RoleRoute表，通过RouteId
     *
     * @param id RouteId
     * @return 是否成功
     */
    public boolean deleteRoleRouteByRouteId(Long id) {
        List<Long> ids = new ArrayList<>();
        ids.add(id);
        return deleteRoleRouteByRouteIdList(ids);
    }

    /**
     * 删除RoleRoute表，通过RouteId
     *
     * @param ids RouteId
     * @return 是否成功
     */
    public boolean deleteRoleRouteByRouteIdList(List<Long> ids) {
        return tryif3(() -> routeMapper.deleteRoleRouteByRouteIdList(ids));
    }

    /**
     * 查询通过id
     *
     * @param id id
     * @return RouteVo
     */
    public RouteVo findById(Long id) {
        return routeMapper.findById(id);
    }

    /**
     * 查询通过父id
     *
     * @param parentId parentId
     * @return List&lt;RouteVo>
     */
    public List<RouteVo> findByParentId(Long parentId) {
        return routeMapper.findByParentId(parentId);
    }

    /**
     * 查询全部
     *
     * @return List&lt;RouteVo>
     */
    public List<RouteVo> findAll() {
        return routeMapper.findAll();
    }

    /**
     * 查询全部，通过RoleId
     *
     * @param roleId roleId
     * @return List&lt;RouteVo>
     */
    public List<RouteVo> findByRoleId(Long roleId) {
        return routeMapper.findByRoleId(roleId);
    }

    /**
     * 查询所有路由不拦截
     *
     * @return List&lt;RouteNotInterceptVo>
     */
    public List<RouteNotInterceptVo> findAllRouteNotIntercept() {
        return routeMapper.findAllRouteNotIntercept();
    }

}
