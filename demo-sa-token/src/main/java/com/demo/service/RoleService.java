package com.demo.service;

import com.demo.base.ServiceBase;
import com.demo.dao.mysql.RoleDao;
import com.demo.entity.vo.RoleVo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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

    /**
     * 查询所有
     *
     * @return List<RoleVo>
     */
    public List<RoleVo> findAll() {
        return roleDao.findAll();
    }

    /**
     * 查询所有通过UserId
     *
     * @param id UserId
     * @return List<RoleVo>
     */
    public List<RoleVo> findByUserId(Long id) {
        return roleDao.findByUserId(id);
    }

}
