package com.demo.base;

import com.demo.constant.Constant;
import com.demo.entity.pojo.PageRequestFix;
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
     * @param base MongoDB实体层基类<br>
     *             默认页码：pages == null<br>
     *             默认每页条数：rows == null || rows <= 0<br>
     *             默认排序：orderBy == null<br>
     *             分页查询，不排序：orderBy == ""<br>
     *             分页查询，排序：orderBy != ""<br>
     * @return PageRequest
     */
    public static PageRequestFix buildPage(MongoEntityBase base) {
        Integer pages = base.getPages();
        // 默认页码
        if (pages == null) {
            pages = Constant.MONGO_PAGE_DEFAULT_PAGES;
        }
        Integer rows = base.getRows();
        // 默认每页条数
        if (rows == null || rows <= 0) {
            rows = Constant.MONGO_PAGE_DEFAULT_ROWS;
        }
        String orderBy = base.getOrderBy();
        // 默认排序
        if (orderBy == null) {
            orderBy = Constant.MONGO_PAGE_DEFAULT_ORDER_BY;
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
     * 构建排序
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
