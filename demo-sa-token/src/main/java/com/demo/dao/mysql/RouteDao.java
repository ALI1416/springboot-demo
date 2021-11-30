package com.demo.dao.mysql;

import com.demo.base.DaoBase;
import com.demo.entity.vo.RouteVo;
import com.demo.mapper.RouteMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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
     * 查询全部
     *
     * @return List<RouteVo>
     */
    public List<RouteVo> findAll() {
        return routeMapper.findAll();
    }

    /**
     * 查询全部，通过RoleId
     *
     * @return List<RouteVo>
     */
    public List<RouteVo> findByRoleId(Long id) {
        return routeMapper.findByRoleId(id);
    }

}
