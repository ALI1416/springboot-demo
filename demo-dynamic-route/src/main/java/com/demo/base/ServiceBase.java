package com.demo.base;

import com.demo.constant.Constant;
import com.demo.entity.pojo.PageInfo;
import com.demo.entity.pojo.PageRequestFix;
import com.github.pagehelper.Page;
import com.github.pagehelper.page.PageMethod;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;
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
     * <h2>连续执行</h2>
     * 当前面返回false时，后面不再执行
     *
     * @param functionArray 函数数组
     * @return 是否全部返回true
     */
    public static boolean execute(BooleanSupplier... functionArray) {
        for (BooleanSupplier function : functionArray) {
            if (!function.getAsBoolean()) {
                return false;
            }
        }
        return true;
    }

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

    /**
     * <h2>构建分页</h2>
     * 默认页码：pages == null<br>
     * 默认每页条数：rows == null || rows <= 0<br>
     * 默认排序：orderBy == null<br>
     * 分页查询，不排序：orderBy == ""<br>
     * 分页查询，排序：orderBy != ""
     *
     * @param pages   页码
     * @param rows    每页条数
     * @param orderBy 排序
     * @return PageRequestFix
     */
    public static PageRequestFix buildPage(Integer pages, Integer rows, String orderBy) {
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
        if (orderBy.trim().isEmpty()) {
            // 不排序
            return PageRequestFix.of(pages, rows);
        } else {
            // 排序
            return PageRequestFix.of(pages, rows, buildSort(orderBy));
        }
    }

    /**
     * <h3>构建排序</h3>
     *
     * @param orderByString 排序字符串
     * @return Sort
     */
    public static Sort buildSort(String orderByString) {
        // 排序转为List<Sort.Order>
        List<Sort.Order> orderList = new ArrayList<>();
        // 取出每个字段的排序
        for (String orderBy : orderByString.split(",")) {
            // 当字段的排序不为空时
            if (!orderBy.trim().isEmpty()) {
                // 取出字段名和顺序
                String[] s = orderBy.trim().split(" ");
                if (s.length == 1) {
                    // 升序
                    orderList.add(Sort.Order.asc(s[0]));
                } else if (s.length == 2) {
                    if ("asc".equalsIgnoreCase(s[1])) {
                        // 升序
                        orderList.add(Sort.Order.asc(s[0]));
                    } else if ("desc".equalsIgnoreCase(s[1])) {
                        // 降序
                        orderList.add(Sort.Order.desc(s[0]));
                    } else {
                        throw new IllegalStateException("未知排序顺序:[" + s[1] + "],排序语句:[" + orderByString + "]");
                    }
                } else {
                    throw new IllegalStateException("排序语句错误:[" + orderByString + "]");
                }
            }
        }
        return Sort.by(orderList);
    }

}
