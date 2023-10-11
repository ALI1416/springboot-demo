package com.demo.base;

import com.demo.constant.Constant;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>服务层基类</h1>
 *
 * <p>
 * createDate 2022/01/04 10:45:06
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class ServiceBase {

    /**
     * 构建分页
     *
     * @param mongoEntityBase mongo基实体<br>
     *                        默认分页，默认排序：mongoEntityBase == null<br>
     *                        默认页码：pages == null<br>
     *                        默认每页条数：rows == null || rows <= 0<br>
     *                        默认排序：orderBy == null<br>
     *                        分页查询，不排序：orderBy == ""<br>
     *                        分页查询，排序：orderBy != ""<br>
     * @return PageRequest
     */
    public static PageRequest buildPage(MongoEntityBase mongoEntityBase) {
        /* 默认分页，默认排序(mongoEntityBase为null) */
        if (mongoEntityBase == null) {
            mongoEntityBase = new MongoEntityBase();
            mongoEntityBase.setPages(Constant.MONGO_PAGE_DEFAULT_PAGES);
            mongoEntityBase.setRows(Constant.MONGO_PAGE_DEFAULT_ROWS);
            mongoEntityBase.setOrderBy(Constant.MONGO_PAGE_DEFAULT_ORDER_BY);
        }
        // 页码(pages为null时，默认)
        Integer pages = mongoEntityBase.getPages();
        if (pages == null) {
            pages = Constant.MONGO_PAGE_DEFAULT_PAGES;
        }
        // 每页条数(rows为null或<=0时，默认)
        Integer rows = mongoEntityBase.getRows();
        if (rows == null || rows <= 0) {
            rows = Constant.MONGO_PAGE_DEFAULT_ROWS;
        }
        // 排序(orderBy为null时，默认)
        String orderBy = mongoEntityBase.getOrderBy();
        if (orderBy == null) {
            orderBy = Constant.MONGO_PAGE_DEFAULT_ORDER_BY;
        }
        /* 开始分页和排序 */
        if (orderBy.trim().isEmpty()) {
            return PageRequest.of(pages, rows);
        } else {
            // 当排序不为空时
            return PageRequest.of(pages, rows, buildSort(orderBy));
        }
    }

    /**
     * 构建排序
     *
     * @param orderBy 排序字符串
     * @return Sort
     */
    public static Sort buildSort(String orderBy) {
        // 排序转为List<Sort.Order>
        List<Sort.Order> orders = new ArrayList<>();
        // 取出每个字段的排序
        for (String fieldOrderBy : orderBy.split(",")) {
            // 当字段的排序不为空时
            if (!fieldOrderBy.trim().isEmpty()) {
                // 取出字段名和顺序
                String[] s = fieldOrderBy.trim().split(" ");
                if (s.length == 1) {
                    orders.add(Sort.Order.asc(s[0]));
                } else if (s.length == 2) {
                    if ("asc".equalsIgnoreCase(s[1])) {
                        orders.add(Sort.Order.asc(s[0]));
                    } else if ("desc".equalsIgnoreCase(s[1])) {
                        orders.add(Sort.Order.desc(s[0]));
                    } else {
                        throw new IllegalStateException("未知排序顺序:[" + s[1] + "],排序语句:[" + orderBy + "]");
                    }
                } else {
                    throw new IllegalStateException("排序语句错误:[" + orderBy + "]");
                }
            }
        }
        return Sort.by(orders);
    }

}
