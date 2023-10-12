package com.demo.dao.mysql;

import cn.z.id.Id;
import com.demo.base.DaoBase;
import com.demo.entity.vo.RouteVo;
import com.demo.mapper.RouteMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        if (tryif(() -> routeMapper.insert(route))) {
            return route.getId();
        }
        return 0L;
    }

    /**
     * 更新
     *
     * @param route id(必须),path,name,seq,parentId(至少1个)
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
    public boolean deleteById(long id) {
        List<Long> ids = new ArrayList<>();
        ids.add(id);
        return deleteByIdList(ids);
    }

    /**
     * 删除
     *
     * @param list id
     * @return 是否成功
     */
    public boolean deleteByIdList(List<Long> list) {
        return tryif2(() -> routeMapper.deleteByIdList(list) == list.size());
    }

    /**
     * 查询通过id
     *
     * @param id id
     * @return RouteVo
     */
    public RouteVo findById(long id) {
        return routeMapper.findById(id);
    }

    /**
     * 查询id和parentId，通过parentId
     *
     * @param parentId parentId
     * @return List RouteVo
     */
    public List<RouteVo> findIdParentIdAndByParentId(long parentId) {
        return routeMapper.findIdParentIdAndByParentId(parentId);
    }

    /**
     * 查询，通过parentId
     *
     * @param parentId parentId
     * @return List RouteVo
     */
    public List<RouteVo> findByParentId(long parentId) {
        return routeMapper.findByParentId(parentId);
    }

    /**
     * 查询所有
     *
     * @return List RouteVo
     */
    public List<RouteVo> findAll() {
        return routeMapper.findAll();
    }

    /**
     * 查询，通过roleId
     *
     * @param roleId roleId
     * @return List RouteVo
     */
    public List<RouteVo> findByRoleId(long roleId) {
        return routeMapper.findByRoleId(roleId);
    }

    /**
     * 查询id，通过roleId
     *
     * @param roleId roleId
     * @return List Long
     */
    public List<Long> findIdByRoleId(long roleId) {
        return routeMapper.findIdByRoleId(roleId);
    }

}
