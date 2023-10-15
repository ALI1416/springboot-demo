package com.demo.entity.pojo;

import com.demo.base.ToStringBase;
import lombok.Getter;
import lombok.Setter;

/**
 * <h1>查询参数</h1>
 *
 * <p>
 * createDate 2022/06/02 09:32:53
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Getter
@Setter
public class ParamQuery extends ToStringBase {

    /**
     * 字段
     */
    private String field;
    /**
     * 值
     */
    private String value;
    /**
     * 值2
     */
    private String value2;
    /**
     * 值类型
     */
    private String type;
    /**
     * 操作符
     */
    private String operator;

}
