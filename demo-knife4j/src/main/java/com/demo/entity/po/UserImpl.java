package com.demo.entity.po;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * <h1>用户实现</h1>
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
@Schema(description = "用户实现")
public class UserImpl implements UserInterface {

    /**
     * 密码2
     */
    @Schema(description = "密码2")
    private String pwd2;

}
