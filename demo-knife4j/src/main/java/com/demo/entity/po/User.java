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
    @Schema(description = "出生年", minimum = "1900", maximum = "2100")
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
     * 子用户(Object)无法映射
     */
    @Schema(description = "子用户(Object)无法映射")
    private Object subUser;
    /**
     * 子用户2(接口)无法映射
     */
    @Schema(description = "子用户2(接口)无法映射")
    private UserInterface userInterface;
    /**
     * 测试1(ABC)前2字符不能大写
     */
    @Schema(description = "测试1(ABC)前2字符不能大写")
    private String ABC;
    /**
     * 测试2(Bcd)前2字符不能大写
     */
    @Schema(description = "测试2(Bcd)前2字符不能大写")
    private String Bcd;
    /**
     * 测试3(cDe)前2字符不能大写
     */
    @Schema(description = "测试3(cDe)前2字符不能大写")
    private String cDe;
    /**
     * 测试4
     */
    @Schema(description = "测试4")
    private String deF;

}
