package com.demo.base;

import cn.z.mongo.entity.Pageable;
import com.demo.constant.Constant;
import com.demo.entity.pojo.PageInfo;
import com.demo.entity.pojo.PageRequestFix;
import com.github.pagehelper.Page;
import com.github.pagehelper.page.PageMethod;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
        if (pages == 0) {
            if (orderBy.isEmpty()) {
                // 全部查询，不排序
                return new PageInfo<>(function.get());
            } else {
                // 全部查询，排序
                PageMethod.orderBy(orderBy);
                Page<T> page = PageMethod.getLocalPage();
                function.get();
                return new PageInfo<>((List<T>) page);
            }
        } else {
            Page<T> page;
            if (orderBy.isEmpty()) {
                // 分页查询，不排序
                page = PageMethod.startPage(pages, rows);
            } else {
                // 分页查询，排序
                page = PageMethod.startPage(pages, rows, orderBy);
            }
            function.get();
            return new PageInfo<>(page);
        }
    }

    /**
     * <h2>构建分页</h2>
     * 默认页码：pages == null<br>
     * 默认每页条数：rows == null || rows <= 0<br>
     * 默认排序：orderBy == null<br>
     * 全部查询，不排序：pages == 0 && orderBy == ""<br>
     * 全部查询，排序：pages == 0 && orderBy != ""<br>
     * 分页查询，不排序：pages != 0 && orderBy == ""<br>
     * 分页查询，排序：pages != 0 && orderBy != ""
     *
     * @param pages   页码
     * @param rows    每页条数
     * @param orderBy 排序
     * @return Pageable
     */
    public static Pageable buildPage(Integer pages, Integer rows, String orderBy) {
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
        if (pages == 0) {
            if (orderBy.isEmpty()) {
                // 全部查询，不排序
                return new Pageable(null, null);
            } else {
                // 全部查询，排序
                return new Pageable(null, buildSort(orderBy));
            }
        } else {
            if (orderBy.isEmpty()) {
                // 分页查询，不排序
                return new Pageable(PageRequestFix.of(pages, rows), null);
            } else {
                // 分页查询，排序
                return new Pageable(PageRequestFix.of(pages, rows, buildSort(orderBy)), null);
            }
        }
    }

    /**
     * <h2>构建排序</h2>
     *
     * @param orderBy 排序
     * @return Sort
     */
    public static Sort buildSort(String orderBy) {
        // 排序转为List<Sort.Order>
        List<Sort.Order> orderList = new ArrayList<>();
        // 取出每个字段的排序
        for (String order : orderBy.split(",")) {
            // 当字段的排序不为空时
            if (!order.trim().isEmpty()) {
                // 取出字段名和顺序
                String[] split = order.trim().split(" ");
                if (split.length == 1) {
                    // 升序
                    orderList.add(Sort.Order.asc(split[0]));
                } else if (split.length == 2) {
                    if ("asc".equalsIgnoreCase(split[1])) {
                        // 升序
                        orderList.add(Sort.Order.asc(split[0]));
                    } else if ("desc".equalsIgnoreCase(split[1])) {
                        // 降序
                        orderList.add(Sort.Order.desc(split[0]));
                    } else {
                        throw new IllegalStateException("未知排序顺序:[" + split[1] + "],排序语句:[" + orderBy + "]");
                    }
                } else {
                    throw new IllegalStateException("排序语句错误:[" + orderBy + "]");
                }
            }
        }
        return Sort.by(orderList);
    }

    /**
     * <h2>构建范围</h2>
     *
     * @param criteria Criteria
     * @param field    字段名
     * @param start    开始
     * @param end      结束
     * @param not      否定
     */
    public static <T> void buildRange(Criteria criteria, String field, T start, T end, Boolean not) {
        /* 不查询，SE同时为null */
        if (start == null && end == null) {
            return;
        }
        /* SE不同时为null。SE相等时，根据N是否为null或false决定==和!= */
        if (Objects.equals(start, end)) {
            if (not == null || !not) {
                // ___(S=E)___ 等于
                criteria.and(field).is(start);
            } else {
                // _xxx(S=E)xxx_(N) 不等
                criteria.and(field).ne(start);
            }
            return;
        }
        /* SE有一个为null，N无效。根据SE是否为null<和> */
        if (start == null) {
            // _xxx(E)___ 小于
            criteria.and(field).lte(end);
            return;
        }
        if (end == null) {
            // ___(S)xxx_ 大于
            criteria.and(field).gte(start);
            return;
        }
        /* SE既不同时为null也不相等。根据N是否为null或false决定between和not between */
        if (not == null || !not) {
            // ___(S)xxx(E)___ 在...与...之间
            criteria.and(field).gte(start).lte(end);
        } else {
            // _xxx(S)___(E)xxx_(N) 不在...与...之间
            criteria.orOperator(Criteria.where(field).lte(start), Criteria.where(field).gte(end));
        }
    }

}
