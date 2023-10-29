package com.demo.base;

import com.demo.entity.pojo.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Objects;
import java.util.function.BooleanSupplier;
import java.util.function.IntSupplier;

/**
 * <h1>Dao层基类</h1>
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
     * 尝试执行：捕获到异常、结果不为1回滚<br>
     * 注意：回滚后，后面的语句还会继续执行
     *
     * @param function 函数
     * @return 是否成功
     */
    public static boolean tryEq1(IntSupplier function) {
        return tryEq1(function, true, true);
    }

    /**
     * 尝试执行：捕获到异常回滚<br>
     * 注意：回滚后，后面的语句还会继续执行
     *
     * @param function     函数
     * @param inconformity 结果不为1回滚
     * @return 是否成功
     */
    public static boolean tryEq1(IntSupplier function, boolean inconformity) {
        return tryEq1(function, true, inconformity);
    }

    /**
     * 尝试执行<br>
     * 注意：回滚后，后面的语句还会继续执行
     *
     * @param function     函数
     * @param exception    捕获到异常回滚
     * @param inconformity 结果不为1回滚
     * @return 是否成功
     */
    public static boolean tryEq1(IntSupplier function, boolean exception, boolean inconformity) {
        try {
            if (function.getAsInt() != 1) {
                // 结果不为1
                if (inconformity) {
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                }
                return false;
            }
        } catch (Exception e) {
            log.error("tryEq1", e);
            // 捕获到异常
            if (exception) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
            return false;
        }
        return true;
    }

    /**
     * 尝试执行：捕获到异常、结果不为true回滚<br>
     * 注意：回滚后，后面的语句还会继续执行
     *
     * @param function 函数
     * @return 是否成功
     */
    public static boolean tryEqTrue(BooleanSupplier function) {
        return tryEqTrue(function, true, true);
    }

    /**
     * 尝试执行：捕获到异常回滚<br>
     * 注意：回滚后，后面的语句还会继续执行
     *
     * @param function     函数
     * @param inconformity 结果不为true回滚
     * @return 是否成功
     */
    public static boolean tryEqTrue(BooleanSupplier function, boolean inconformity) {
        return tryEqTrue(function, true, inconformity);
    }

    /**
     * 尝试执行<br>
     * 注意：回滚后，后面的语句还会继续执行
     *
     * @param function     函数
     * @param exception    捕获到异常回滚
     * @param inconformity 结果不为true回滚
     * @return 是否成功
     */
    public static boolean tryEqTrue(BooleanSupplier function, boolean exception, boolean inconformity) {
        try {
            if (function.getAsBoolean()) {
                // 结果不为true
                if (inconformity) {
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                }
                return false;
            }
        } catch (Exception e) {
            log.error("tryEqTrue", e);
            // 捕获到异常
            if (exception) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
            return false;
        }
        return true;
    }

    /**
     * 尝试执行：捕获到异常回滚<br>
     * 注意：回滚后，后面的语句还会继续执行
     *
     * @param function 函数
     * @return 是否成功
     */
    public static boolean tryAny(Runnable function) {
        return tryAny(function, true);
    }

    /**
     * 尝试执行<br>
     * 注意：回滚后，后面的语句还会继续执行
     *
     * @param function  函数
     * @param exception 捕获到异常回滚
     * @return 是否成功
     */
    public static boolean tryAny(Runnable function, boolean exception) {
        try {
            function.run();
        } catch (Exception e) {
            log.error("tryAny", e);
            // 捕获到异常
            if (exception) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
            return false;
        }
        return true;
    }

    /**
     * <h2>分页查询</h2>
     *
     * @param <T>      数据类型
     * @param template MongoTemplate
     * @param clazz    T.class
     * @param criteria Criteria
     * @param pageable Pageable
     * @return PageInfo
     */
    public static <T> PageInfo<T> pagination(MongoTemplate template, Class<T> clazz, Criteria criteria, Pageable pageable) {
        Query query = Query.query(criteria);
        return new PageInfo<>(template.find(query.with(pageable), clazz), pageable, template.count(query, clazz));
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
