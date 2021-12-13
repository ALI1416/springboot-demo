package com.demo.dao.mysql;

import cn.z.id.Id;
import com.demo.base.DaoBase;
import com.demo.entity.vo.Route2Vo;
import com.demo.mapper.Route2Mapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>Route2Dao</h1>
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
public class Route2Dao extends DaoBase {

    private final Route2Mapper route2Mapper;

    /**
     * 插入
     *
     * @param route2 path,name,seq,parentId
     * @return ok:id,e:0
     */
    public long insert(Route2Vo route2) {
        route2.setId(Id.next());
        if (tryif(() -> route2Mapper.insert(route2))) {
            return route2.getId();
        }
        return 0L;
    }

    /**
     * 更新
     *
     * @param route2 必须id，至少一个path,name,seq,parentId
     * @return 是否成功
     */
    public boolean update(Route2Vo route2) {
        return tryif(() -> route2Mapper.update(route2));
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
        return tryif2(() -> route2Mapper.deleteByIdList(ids) == ids.size());
    }


    /**
     * 查询通过id
     *
     * @param id id
     * @return Route2Vo
     */
    public Route2Vo findById(Long id) {
        return route2Mapper.findById(id);
    }

    /**
     * 查询通过父id
     *
     * @param parentId parentId
     * @return List&lt;Route2Vo>
     */
    public List<Route2Vo> findByParentId(Long parentId) {
        return route2Mapper.findByParentId(parentId);
    }

    /**
     * 查询全部
     *
     * @return List&lt;Route2Vo>
     */
    public List<Route2Vo> findAll() {
        return route2Mapper.findAll();
    }

    /**
     * 查询全部，通过RoleId
     *
     * @param roleId roleId
     * @return List&lt;Route2Vo>
     */
    public List<Route2Vo> findByRoleId(Long roleId) {
        return route2Mapper.findByRoleId(roleId);
    }

}
