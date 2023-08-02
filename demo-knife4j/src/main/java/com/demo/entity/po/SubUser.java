package com.demo.entity.po;

import com.demo.base.ToStringBase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * <h1>子用户</h1>
 *
 * <p>
 * createDate 2023/08/02 14:52:53
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Getter
@Setter
@Schema(description = "子用户")
public class SubUser extends ToStringBase {

    /**
     * 密码
     */
    @Schema(description = "密码")
    private String pwd;

}
