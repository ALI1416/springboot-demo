package com.demo.dao.mysql;

import cn.z.id.Id;
import com.demo.base.DaoBase;
import com.demo.entity.vo.Route2NotInterceptVo;
import com.demo.mapper.Route2NotInterceptMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <h1>Route2NotInterceptDao</h1>
 *
 * <p>
 * createDate 2021/12/08 10:00:36
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Service
@AllArgsConstructor
public class Route2NotInterceptDao extends DaoBase {

    private final Route2NotInterceptMapper route2NotInterceptMapper;

    /**
     * 查询所有
     *
     * @return List&lt;Route2NotInterceptVo>
     */
    public List<Route2NotInterceptVo> findAll() {
        return route2NotInterceptMapper.findAll();
    }

    /**
     * 插入
     *
     * @param route2NotIntercept Route2NotInterceptVo
     * @return ok:id,e:0
     */
    public long insert(Route2NotInterceptVo route2NotIntercept) {
        route2NotIntercept.setId(Id.next());
        if (tryif(() -> route2NotInterceptMapper.insert(route2NotIntercept))) {
            return route2NotIntercept.getId();
        }
        return 0L;
    }

    /**
     * 删除
     *
     * @param id id
     * @return 是否成功
     */
    public boolean delete(Long id) {
        return tryif(() -> route2NotInterceptMapper.delete(id));
    }

    /**
     * 更新
     *
     * @param route2NotIntercept Route2NotInterceptVo
     * @return 是否成功
     */
    public boolean update(Route2NotInterceptVo route2NotIntercept) {
        return tryif(() -> route2NotInterceptMapper.update(route2NotIntercept));
    }

}
