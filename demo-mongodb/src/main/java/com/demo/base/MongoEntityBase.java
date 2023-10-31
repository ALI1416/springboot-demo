package com.demo.base;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import java.sql.Timestamp;

/**
 * <h1>MongoDB实体层基类</h1>
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
     * id
     */
    @Id
    private Long id;
    /**
     * 创建时间
     */
    private Timestamp createTime;
    /**
     * 更新时间
     */
    private Timestamp updateTime;

    /* ==================== vo ==================== */
    /**
     * 创建时间-结束
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

    /**
     * 构造函数
     */
    public MongoEntityBase() {
    }

    /**
     * 构造函数
     */
    public MongoEntityBase(MongoEntityBase base) {
        this.id = base.id;
        this.createTime = base.createTime;
        this.updateTime = base.updateTime;
        this.createTimeEnd = base.createTimeEnd;
        this.createTimeNot = base.createTimeNot;
        this.updateTimeEnd = base.updateTimeEnd;
        this.updateTimeNot = base.updateTimeNot;
        this.pages = base.pages;
        this.rows = base.rows;
        this.orderBy = base.orderBy;
    }

}
