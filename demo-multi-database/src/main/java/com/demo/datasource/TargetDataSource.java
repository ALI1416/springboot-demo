package com.demo.datasource;

import java.lang.annotation.*;

/**
 * <h1>目标数据源</h1>
 *
 * <p>
 * createDate 2020/11/11 11:11:11
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TargetDataSource {

    /**
     * 数据源类型
     */
    DataSourceType value() default DataSourceType.DB1;

}
