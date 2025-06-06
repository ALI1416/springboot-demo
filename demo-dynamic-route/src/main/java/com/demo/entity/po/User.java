package com.demo.entity.po;

import com.demo.base.EntityBase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * <h1>用户</h1>
 *
 * <p>
 * createDate 2021/09/09 15:41:33
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Getter
@Setter
@Schema(description = "用户")
public class User extends EntityBase {

    /**
     * 账号
     */
    @Schema(description = "账号")
    private String account;
    /**
     * 密码
     */
    @Schema(description = "密码")
    private String password;
    /**
     * 用户名
     */
    @Schema(description = "用户名")
    private String name;

}
