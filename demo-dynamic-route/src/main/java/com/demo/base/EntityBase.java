package com.demo.base;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "实体层基类")
public class EntityBase extends ToStringBase {

    /* ==================== po ==================== */
    /**
     * id
     */
    @Schema(description = "id")
    private Long id;
    /**
     * 已删除
     */
    @Schema(description = "已删除")
    private Boolean isDelete;
    /**
     * 创建者id
     */
    @Schema(description = "创建者id")
    private Long createId;
    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private Timestamp createTime;
    /**
     * 更新者id
     */
    @Schema(description = "更新者id")
    private Long updateId;
    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    private Timestamp updateTime;

    /* ==================== vo ==================== */
    /**
     * 创建时间-否定
     */
    @Schema(description = "创建时间-否定")
    private Timestamp createTimeNot;
    /**
     * 创建时间-结束
     */
    @Schema(description = "创建时间-结束")
    private Timestamp createTimeEnd;
    /**
     * 更新时间-结束
     */
    @Schema(description = "更新时间-否定")
    private Timestamp updateTimeEnd;
    /**
     * 更新时间-否定
     */
    @Schema(description = "更新时间-结束")
    private Boolean updateTimeNot;

    /* -------------------- 分页 -------------------- */
    /**
     * 分页-页码
     */
    @Schema(description = "分页-页码")
    private Integer pages;
    /**
     * 分页-每页条数
     */
    @Schema(description = "分页-每页条数")
    private Integer rows;
    /**
     * 分页-排序
     */
    @Schema(description = "分页-排序")
    private String orderBy;

}
