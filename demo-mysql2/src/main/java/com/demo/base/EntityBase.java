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
     * 创建时间-结束
     */
    private Timestamp createTimeEnd;
    /**
     * 创建时间-否定
     */
    private Boolean createTimeNot;
    /**
     * 更新时间-结束
     */
    private Timestamp updateTimeEnd;
    /**
     * 更新时间-否定
     */
    private Boolean updateTimeNot;
    /**
     * 版本-结束
     */
    private Integer versionEnd;
    /**
     * 版本-否定
     */
    private Boolean versionNot;
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

    public EntityBase() {

    }

    public EntityBase(EntityBase base) {
        this.id = base.id;
        this.isDelete = base.isDelete;
        this.createId = base.createId;
        this.createTime = base.createTime;
        this.updateId = base.updateId;
        this.updateTime = base.updateTime;
        this.version = base.version;
        this.refId = base.refId;
        this.createTimeEnd = base.createTimeEnd;
        this.createTimeNot = base.createTimeNot;
        this.updateTimeEnd = base.updateTimeEnd;
        this.updateTimeNot = base.updateTimeNot;
        this.versionEnd = base.versionEnd;
        this.versionNot = base.versionNot;
        this.pages = base.pages;
        this.rows = base.rows;
        this.orderBy = base.orderBy;
    }

}
