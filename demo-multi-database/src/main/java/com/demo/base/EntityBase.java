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
     * 创建时间
     */
    private Timestamp createTime;

}
