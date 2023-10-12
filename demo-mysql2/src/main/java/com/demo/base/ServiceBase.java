package com.demo.base;

import cn.z.tool.Function;
import com.demo.constant.Constant;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;

import java.util.List;

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
     * 分页
     *
     * @param <T>      数据类型
     * @param base     实体层基类<br>
     *                 默认分页，默认排序：base == null<br>
     *                 默认页码：pages == null<br>
     *                 默认每页条数：rows == null || rows <= 0<br>
     *                 默认排序：orderBy == null<br>
     *                 全部查询，不排序：pages == 0 && orderBy == ""<br>
     *                 全部查询，排序：pages == 0 && orderBy != ""<br>
     *                 分页查询，不排序：pages != 0 && orderBy == ""<br>
     *                 分页查询，排序：pages != 0 && orderBy != ""<br>
     * @param function 查询语句
     * @return PageInfo
     */
    public static <T> PageInfo<T> pagination(EntityBase base, Function<List<T>> function) {
        return new PageInfo<>(paginationUnpack(base, function));
    }

    /**
     * 分页
     *
     * @param <T>      数据类型
     * @param base     实体层基类<br>
     *                 默认分页，默认排序：base == null<br>
     *                 默认页码：pages == null<br>
     *                 默认每页条数：rows == null || rows <= 0<br>
     *                 默认排序：orderBy == null<br>
     *                 全部查询，不排序：pages == 0 && orderBy == ""<br>
     *                 全部查询，排序：pages == 0 && orderBy != ""<br>
     *                 分页查询，不排序：pages != 0 && orderBy == ""<br>
     *                 分页查询，排序：pages != 0 && orderBy != ""<br>
     * @param function 查询语句
     * @return List
     */
    public static <T> List<T> paginationUnpack(EntityBase base, Function<List<T>> function) {
        /* 默认分页，默认排序(baseEntity为null) */
        if (base == null) {
            base = new EntityBase();
            base.setPages(Constant.PAGE_DEFAULT_PAGES);
            base.setRows(Constant.PAGE_DEFAULT_ROWS);
            base.setOrderBy(Constant.PAGE_DEFAULT_ORDER_BY);
        }
        // 页码(pages为null时，默认)
        Integer pages = base.getPages();
        if (pages == null) {
            pages = Constant.PAGE_DEFAULT_PAGES;
        }
        // 每页条数(rows为null或<=0时，默认)
        Integer rows = base.getRows();
        if (rows == null || rows <= 0) {
            rows = Constant.PAGE_DEFAULT_ROWS;
        }
        // 排序(orderBy为null时，默认)
        String orderBy = base.getOrderBy();
        if (orderBy == null) {
            orderBy = Constant.PAGE_DEFAULT_ORDER_BY;
        }
        /* 开始分页和排序 */
        if (pages == 0) {
            // 全部查询，不排序
            // pages == 0 && orderBy.isEmpty()
            if (!orderBy.isEmpty()) {
                // 全部查询，排序
                PageMethod.orderBy(orderBy);
            }
        } else {
            if (orderBy.isEmpty()) {
                // 分页查询，不排序
                PageMethod.startPage(pages, rows);
            } else {
                // 分页查询，排序
                PageMethod.startPage(pages, rows, orderBy);
            }
        }
        return function.run();
    }

}
