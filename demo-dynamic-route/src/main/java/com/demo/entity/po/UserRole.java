package com.demo.entity.po;

import com.demo.base.EntityBase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * <h1>用户-角色</h1>
 *
 * <p>
 * createDate 2021/11/29 14:56:24
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Getter
@Setter
@Schema(description = "用户-角色")
public class UserRole extends EntityBase {

    /**
     * 用户id
     */
    @Schema(description = "用户id")
    private Long userId;
    /**
     * 角色id
     */
    @Schema(description = "角色id")
    private Long roleId;

}
