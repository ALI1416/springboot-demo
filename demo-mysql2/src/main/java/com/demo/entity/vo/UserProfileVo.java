package com.demo.entity.vo;

import com.demo.entity.po.UserProfile;
import lombok.Getter;
import lombok.Setter;

/**
 * <h1>UserProfileVo</h1>
 *
 * <p>
 * createDate 2020/11/11 11:11:11
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Getter
@Setter
public class UserProfileVo extends UserProfile {

    /**
     * 新密码
     */
    private String newPwd;
    /**
     * 出生年-否定
     */
    private Integer yearNot;
    /**
     * 出生年-结束
     */
    private Integer yearEnd;
}
