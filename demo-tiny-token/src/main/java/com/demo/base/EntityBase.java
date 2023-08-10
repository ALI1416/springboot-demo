package com.demo.base;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

/**
 * <h1>实体层基类</h1>
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
public class EntityBase extends ToStringBase {

    /* ++++++++++++++++++++ 属性 ++++++++++++++++++++ */
    /* ==================== po ==================== */
    /* -------------------- 所有表 -------------------- */
    /**
     * id
     */
    private Long id;
    /* -------------------- 大多数表 -------------------- */
    /**
     * 已删除
     */
    private Integer isDelete;
    /**
     * 创建者id
     */
    private Long createId;
    /**
     * 创建时间
     */
    private Timestamp createTime;
    /**
     * 更新者id
     */
    private Long updateId;
    /**
     * 更新时间
     */
    private Timestamp updateTime;
    /**
     * 版本
     */
    private Integer version;
    /* -------------------- 备份、日志表 -------------------- */
    /**
     * 被备份的id/被登录的id
     */
    private Long refId;

    /* ==================== vo ==================== */
    /* -------------------- 大多数表 -------------------- */
    /**
     * 创建时间-否定
     */
    private Timestamp createTimeNot;
    /**
     * 创建时间-结束
     */
    private Timestamp createTimeEnd;
    /**
     * 更新时间-否定
     */
    private Timestamp updateTimeNot;
    /**
     * 更新时间-结束
     */
    private Timestamp updateTimeEnd;
    /**
     * 版本-否定
     */
    private Integer versionNot;
    /**
     * 版本-结束
     */
    private Integer versionEnd;
    /* -------------------- 分页 -------------------- */
    /**
     * 分页-页码
     */
    private Integer pages;
    /**
     * 分页-每页条数
     */
    private Integer rows;
    /**
     * 分页-排序
     */
    private String orderBy;

}
