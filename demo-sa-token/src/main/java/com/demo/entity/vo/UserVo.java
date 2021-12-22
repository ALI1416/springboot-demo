package com.demo.entity.vo;

import com.demo.entity.po.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * <h1>UserVo</h1>
 *
 * <p>
 * createDate 2021/11/29 15:17:57
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Getter
@Setter
public class UserVo extends User {

    /**
     * 新密码
     */
    private String newPwd;
    /**
     * 角色
     */
    private List<RoleVo> roles;
    /**
     * 角色id
     */
    private List<Long> roleIds;
    /**
     * token
     */
    private String token;

}
