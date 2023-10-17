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

    /* ==================== po ==================== */
    /**
     * id
     */
    private Long id;
    /**
     * 已删除
     */
    private Boolean isDelete;
    /**
     * 创建者id
     */
    private Long createId;
    /**
     * 创建时间
     */
    private Timestamp createTime;

    /* ==================== vo ==================== */
    /**
     * 创建时间-否定
     */
    private Timestamp createTimeNot;
    /**
     * 创建时间-结束
     */
    private Timestamp createTimeEnd;

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
