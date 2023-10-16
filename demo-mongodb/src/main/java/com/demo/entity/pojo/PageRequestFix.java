package com.demo.entity.pojo;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

/**
 * <h1>分页请求修正</h1>
 *
 * <p>
 * createDate 2023/10/15 16:59:16
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class PageRequestFix extends PageRequest {

    /**
     * @param page 页码(从1开始)
     * @param size 每页条数
     * @param sort 排序
     */
    public PageRequestFix(int page, int size, Sort sort) {
        super(page - 1, size, sort);
    }

    /**
     * @param page 页码(从1开始)
     * @param size 每页条数
     * @param sort 排序
     */
    public static PageRequestFix of(int page, int size, Sort sort) {
        return new PageRequestFix(page, size, sort);
    }

    /**
     * @param page 页码(从1开始)
     * @param size 每页条数
     */
    public static PageRequestFix of(int page, int size) {
        return of(page, size, Sort.unsorted());
    }

    /**
     * @param page       页码(从1开始)
     * @param size       每页条数
     * @param direction  方向
     * @param properties 属性
     */
    public static PageRequestFix of(int page, int size, Sort.Direction direction, String... properties) {
        return of(page, size, Sort.by(direction, properties));
    }

}
