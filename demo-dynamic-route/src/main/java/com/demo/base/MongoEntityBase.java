package com.demo.base;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "id")
    private Long id;
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
     * 创建时间-结束
     */
    @Transient
    @Schema(description = "创建时间-结束")
    private Timestamp createTimeEnd;
    /**
     * 创建时间-否定
     */
    @Transient
    @Schema(description = "创建时间-否定")
    private Boolean createTimeNot;
    /**
     * 更新时间-结束
     */
    @Transient
    @Schema(description = "更新时间-结束")
    private Timestamp updateTimeEnd;
    /**
     * 更新时间-否定
     */
    @Transient
    @Schema(description = "更新时间-否定")
    private Boolean updateTimeNot;

    /* -------------------- 分页 -------------------- */
    /**
     * 分页-页码
     */
    @Transient
    @Schema(description = "分页-页码")
    private Integer pages;
    /**
     * 分页-每页条数
     */
    @Transient
    @Schema(description = "分页-每页条数")
    private Integer rows;
    /**
     * 分页-排序
     */
    @Transient
    @Schema(description = "分页-排序")
    private String orderBy;

}
