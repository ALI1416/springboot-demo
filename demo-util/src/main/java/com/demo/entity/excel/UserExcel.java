package com.demo.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.demo.base.ToStringBase;
import lombok.Getter;
import lombok.Setter;

/**
 * <h1>用户Excel</h1>
 *
 * <p>
 * createDate 2021/02/04 15:30:52
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Getter
@Setter
public class UserExcel extends ToStringBase {

    /**
     * 账号
     */
    @ColumnWidth(10)
    @ExcelProperty("账号")
    private String account;
    /**
     * 密码
     */
    @ColumnWidth(10)
    @ExcelProperty("密码")
    private String pwd;
    /**
     * 用户名
     */
    @ColumnWidth(10)
    @ExcelProperty("用户名")
    private String name;
    /**
     * 性别
     */
    @ColumnWidth(10)
    @ExcelProperty("性别")
    private String gender;
    /**
     * 出生年
     */
    @ColumnWidth(10)
    @ExcelProperty("出生年")
    private Integer year;

}
