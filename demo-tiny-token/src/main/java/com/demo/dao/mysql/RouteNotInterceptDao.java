package com.demo.dao.mysql;

import cn.z.id.Id;
import com.demo.base.DaoBase;
import com.demo.entity.vo.RouteNotInterceptVo;
import com.demo.mapper.RouteNotInterceptMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <h1>路由-不拦截</h1>
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
public class RouteNotInterceptDao extends DaoBase {

    private final RouteNotInterceptMapper routeNotInterceptMapper;

    /**
     * 查询所有
     *
     * @return List RouteNotInterceptVo
     */
    public List<RouteNotInterceptVo> findAll() {
        return routeNotInterceptMapper.findAll();
    }

    /**
     * 插入
     *
     * @param routeNotIntercept path,name,isMatch,needLogin,seq
     * @return ok:id,e:0
     */
    public long insert(RouteNotInterceptVo routeNotIntercept) {
        routeNotIntercept.setId(Id.next());
        if (tryif(() -> routeNotInterceptMapper.insert(routeNotIntercept))) {
            return routeNotIntercept.getId();
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
        return tryif(() -> routeNotInterceptMapper.delete(id));
    }

    /**
     * 更新
     *
     * @param routeNotIntercept id(必须),path,name,isMatch,needLogin,seq(至少1个)
     * @return 是否成功
     */
    public boolean update(RouteNotInterceptVo routeNotIntercept) {
        return tryif(() -> routeNotInterceptMapper.update(routeNotIntercept));
    }

}
