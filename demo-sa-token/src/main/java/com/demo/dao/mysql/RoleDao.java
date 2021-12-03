package com.demo.dao.mysql;

import com.demo.base.DaoBase;
import com.demo.entity.vo.RoleVo;
import com.demo.mapper.RoleMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <h1>RoleDao</h1>
 *
 * <p>
 * createDate 2021/11/29 15:43:49
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Service
@AllArgsConstructor
public class RoleDao extends DaoBase {

    private final RoleMapper roleMapper;

    /**
     * 查询所有
     *
     * @return List&lt;RoleVo>
     */
    public List<RoleVo> findAll() {
        return roleMapper.findAll();
    }

    /**
     * 查询所有通过UserId
     *
     * @param userId userId
     * @return List&lt;RoleVo>
     */
    public List<RoleVo> findByUserId(Long userId) {
        return roleMapper.findByUserId(userId);
    }

}
