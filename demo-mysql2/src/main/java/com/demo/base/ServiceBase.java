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
     * @param <E>        对象类型
     * @param entityBase 基实体<br>
     *                   默认分页，默认排序：entityBase == null<br>
     *                   默认页码：pages == null<br>
     *                   默认每页条数：rows == null || rows <= 0<br>
     *                   默认排序：orderBy == null<br>
     *                   全部查询，不排序：pages == 0 && orderBy == ""<br>
     *                   全部查询，排序：pages == 0 && orderBy != ""<br>
     *                   分页查询，不排序：pages != 0 && orderBy == ""<br>
     *                   分页查询，排序：pages != 0 && orderBy != ""<br>
     * @param function   要执行的查询语句
     * @return PageInfo封装的对象
     */
    public static <E> PageInfo<E> pagination(EntityBase entityBase, Function<List<E>> function) {
        return new PageInfo<>(paginationUnpack(entityBase, function));
    }

    /**
     * 分页
     *
     * @param <E>        对象类型
     * @param entityBase 基实体<br>
     *                   默认分页，默认排序：entityBase == null<br>
     *                   默认页码：pages == null<br>
     *                   默认每页条数：rows == null || rows <= 0<br>
     *                   默认排序：orderBy == null<br>
     *                   全部查询，不排序：pages == 0 && orderBy == ""<br>
     *                   全部查询，排序：pages == 0 && orderBy != ""<br>
     *                   分页查询，不排序：pages != 0 && orderBy == ""<br>
     *                   分页查询，排序：pages != 0 && orderBy != ""<br>
     * @param function   要执行的查询语句
     * @return List封装的对象
     */
    public static <E> List<E> paginationUnpack(EntityBase entityBase, Function<List<E>> function) {
        /* 默认分页，默认排序(baseEntity为null) */
        if (entityBase == null) {
            entityBase = new EntityBase();
            entityBase.setPages(Constant.PAGE_DEFAULT_PAGES);
            entityBase.setRows(Constant.PAGE_DEFAULT_ROWS);
            entityBase.setOrderBy(Constant.PAGE_DEFAULT_ORDER_BY);
        }
        // 页码(pages为null时，默认)
        Integer pages = entityBase.getPages();
        if (pages == null) {
            pages = Constant.PAGE_DEFAULT_PAGES;
        }
        // 每页条数(rows为null或<=0时，默认)
        Integer rows = entityBase.getRows();
        if (rows == null || rows <= 0) {
            rows = Constant.PAGE_DEFAULT_ROWS;
        }
        // 排序(orderBy为null时，默认)
        String orderBy = entityBase.getOrderBy();
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
