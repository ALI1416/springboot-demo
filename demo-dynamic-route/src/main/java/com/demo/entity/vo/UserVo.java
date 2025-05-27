package com.demo.entity.vo;

import com.demo.entity.po.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * <h1>用户</h1>
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
@Schema(description = "用户")
public class UserVo extends User {

    /**
     * 新密码
     */
    @Schema(description = "新密码")
    private String newPassword;
    /**
     * 角色id列表
     */
    @Schema(description = "角色id列表")
    private List<Long> roleIdList;
    /**
     * token
     */
    @Schema(description = "token")
    private String token;

}
