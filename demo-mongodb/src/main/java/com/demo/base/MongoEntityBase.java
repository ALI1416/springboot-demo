package com.demo.base;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Transient;

import java.sql.Timestamp;

/**
 * <h1>Mongo实体层基类</h1>
 *
 * <p>
 * createDate 2021/11/18 13:32:47
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Getter
@Setter
public class MongoEntityBase extends ToStringBase {

    /* ==================== po ==================== */
    /**
     * id<br>
     * 当字段名为id时，不需要加@Id注解指明它是主键
     */
    private Long id;
    /**
     * 创建时间
     */
    private Timestamp createTime;
    /**
     * 更新时间
     */
    private Timestamp updateTime;
    /**
     * 版本
     */
    private Integer version;

    /* ==================== vo ==================== */
    /**
     * 创建时间-结束<br>
     * 不保存到数据库中需要加@Transient注解
     */
    @Transient
    private Timestamp createTimeEnd;
    /**
     * 创建时间-否定
     */
    @Transient
    private Boolean createTimeNot;
    /**
     * 更新时间-结束
     */
    @Transient
    private Timestamp updateTimeEnd;
    /**
     * 更新时间-否定
     */
    @Transient
    private Boolean updateTimeNot;
    /**
     * 版本-结束
     */
    @Transient
    private Integer versionEnd;
    /**
     * 版本-否定
     */
    @Transient
    private Boolean versionNot;

    /* -------------------- 分页 -------------------- */
    /**
     * 分页-页码
     */
    @Transient
    private Integer pages;
    /**
     * 分页-每页条数
     */
    @Transient
    private Integer rows;
    /**
     * 分页-排序
     */
    @Transient
    private String orderBy;

    public MongoEntityBase() {

    }

    public MongoEntityBase(MongoEntityBase base) {
        this.id = base.id;
        this.createTime = base.createTime;
        this.updateTime = base.updateTime;
        this.version = base.version;
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
