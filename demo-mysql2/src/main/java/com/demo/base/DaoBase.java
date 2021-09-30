package com.demo.base;

import com.demo.constant.Constant;
import com.demo.tool.Function;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;

/**
 * <h1>DaoBase</h1>
 *
 * <p>
 * createDate 2020/11/11 11:11:11
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Slf4j
public class DaoBase {

    /**
     * try-if简化，不符合function条件的回滚
     *
     * @param function 要执行的函数，返回结果为1时才算符合条件
     */
    public static boolean tryif(Function<Integer> function) {
        return tryif(true, function);
    }

    /**
     * try-if简化
     *
     * @param rollbackIf 不符合function条件的是否回滚
     * @param function   要执行的函数，返回结果为1时才算符合条件
     */
    public static boolean tryif(boolean rollbackIf, Function<Integer> function) {
        try {
            if (function.run() != 1) {
                if (rollbackIf) {
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                }
                return false;
            }
        } catch (Exception e) {
            log.error("try-if简化捕获到异常", e);
            return false;
        }
        return true;
    }

    /**
     * try-if2简化，不符合function条件的回滚
     *
     * @param function 要执行的函数
     */
    public static boolean tryif2(Function<Boolean> function) {
        return tryif2(true, function);
    }

    /**
     * try-if2简化
     *
     * @param rollbackIf 不符合function条件的是否回滚
     * @param function   要执行的函数
     */
    public static boolean tryif2(boolean rollbackIf, Function<Boolean> function) {
        try {
            if (!function.run()) {
                if (rollbackIf) {
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                }
                return false;
            }
        } catch (Exception e) {
            log.error("try-if2简化捕获到异常", e);
            return false;
        }
        return true;
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
        /* 开始排序 */
        if (pages == 0) {
            // 全部查询，不排序
            // pages == 0 && orderBy.isEmpty()
            if (!orderBy.isEmpty()) {
                // 全部查询，排序
                PageHelper.orderBy(orderBy);
            }
        } else {
            if (orderBy.isEmpty()) {
                // 分页查询，不排序
                PageHelper.startPage(pages, rows);
            } else {
                // 分页查询，排序
                PageHelper.startPage(pages, rows, orderBy);
            }
        }
        return function.run();
    }

    /**
     * 记录备份
     *
     * @param function 要执行的语句
     */
    public static void recordBak(Function<Integer> function) {
        if (Constant.ENABLE_BAK) {
            try {
                function.run();
            } catch (Exception e) {
                log.error("记录备份异常", e);
            }
        }
    }

    /**
     * 记录日志
     *
     * @param function 要执行的语句
     */
    public static void recordLog(Function<Integer> function) {
        if (Constant.ENABLE_LOG) {
            try {
                function.run();
            } catch (Exception e) {
                log.error("记录日志异常", e);
            }
        }
    }

}
