package com.demo.entity.po;

import com.demo.base.ToStringBase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

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
public class User extends ToStringBase {

    /**
     * 账号
     */
    @Schema(description = "账号")
    private String account;

    /**
     * 用户名
     */
    @Schema(description = "用户名")
    private String name;

    /**
     * 出生年
     */
    @Schema(description = "出生年")
    private int year;

    /**
     * 性别
     */
    @Schema(description = "性别")
    private Boolean gender;

    /**
     * 日期
     */
    @Schema(description = "日期")
    private Date date;

    /**
     * 子用户
     */
    @Schema(description = "子用户")
    private SubUser subUser;

    /**
     * 子用户2
     */
    @Schema(description = "子用户2")
    private UserInterface userInterface;

}
