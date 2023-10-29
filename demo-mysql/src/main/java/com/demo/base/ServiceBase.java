package com.demo.base;

import com.demo.constant.Constant;
import com.demo.entity.pojo.PageInfo;
import com.github.pagehelper.Page;
import com.github.pagehelper.page.PageMethod;

import java.util.List;
import java.util.function.Supplier;

/**
 * <h1>Service层基类</h1>
 *
 * <p>
 * createDate 2021/10/27 14:33:27
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class ServiceBase {

    /**
     * <h2>分页查询</h2>
     * 默认页码：pages == null<br>
     * 默认每页条数：rows == null || rows <= 0<br>
     * 默认排序：orderBy == null<br>
     * 全部查询，不排序：pages == 0 && orderBy == ""<br>
     * 全部查询，排序：pages == 0 && orderBy != ""<br>
     * 分页查询，不排序：pages != 0 && orderBy == ""<br>
     * 分页查询，排序：pages != 0 && orderBy != ""
     *
     * @param <T>      数据类型
     * @param function 函数
     * @param pages    页码
     * @param rows     每页条数
     * @param orderBy  排序
     * @return PageInfo
     */
    public static <T> PageInfo<T> pagination(Supplier<List<T>> function, Integer pages, Integer rows, String orderBy) {
        // 默认页码
        if (pages == null) {
            pages = Constant.PAGE_DEFAULT_PAGES;
        }
        // 默认每页条数
        if (rows == null || rows <= 0) {
            rows = Constant.PAGE_DEFAULT_ROWS;
        }
        // 默认排序
        if (orderBy == null) {
            orderBy = Constant.PAGE_DEFAULT_ORDER_BY;
        }
        Page<T> page = null;
        if (pages == 0) {
            if (!orderBy.isEmpty()) {
                // 全部查询，排序
                PageMethod.orderBy(orderBy);
                page = PageMethod.getLocalPage();
            }
        } else {
            if (orderBy.isEmpty()) {
                // 分页查询，不排序
                page = PageMethod.startPage(pages, rows);
            } else {
                // 分页查询，排序
                page = PageMethod.startPage(pages, rows, orderBy);
            }
        }
        List<T> list = function.get();
        // 全部查询，不排序
        if (page == null) {
            return new PageInfo<>(list);
        }
        return new PageInfo<>(page);
    }

}
