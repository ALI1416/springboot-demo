package com.demo.base;

import com.demo.entity.pojo.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.IntSupplier;

/**
 * <h1>Dao层基类</h1>
 *
 * <p>
 * createDate 2022/01/04 17:17:26
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Slf4j
public class DaoBase {

    /**
     * 尝试执行(无事务)：必须无异常、结果必须为1
     *
     * @param function 函数
     * @return 是否成功
     */
    public static boolean tryEq1NoTransaction(IntSupplier function) {
        try {
            if (function.getAsInt() != 1) {
                // 结果必须为1
                return false;
            }
        } catch (Exception e) {
            log.error("tryEq1NoTransaction", e);
            // 必须无异常
            return false;
        }
        return true;
    }

    /**
     * 尝试执行(无事务)：必须无异常、结果必须为true
     *
     * @param function 函数
     * @return 是否成功
     */
    public static boolean tryEqTrueNoTransaction(BooleanSupplier function) {
        try {
            if (function.getAsBoolean()) {
                // 结果必须为true
                return false;
            }
        } catch (Exception e) {
            log.error("tryEqTrueNoTransaction", e);
            // 必须无异常
            return false;
        }
        return true;
    }

    /**
     * 尝试执行(无事务)：必须无异常
     *
     * @param function 函数
     * @return 是否成功
     */
    public static boolean tryAnyNoTransaction(Runnable function) {
        try {
            function.run();
        } catch (Exception e) {
            log.error("tryAnyNoTransaction", e);
            // 必须无异常
            return false;
        }
        return true;
    }

    /**
     * <h2>条件查询</h2>
     *
     * @param <T>      数据类型
     * @param template MongoTemplate
     * @param clazz    T.class
     * @param criteria Criteria
     * @return List
     */
    public static <T> List<T> find(MongoTemplate template, Class<T> clazz, Criteria criteria) {
        return template.find(Query.query(criteria), clazz);
    }

    /**
     * <h2>条件和排序查询</h2>
     *
     * @param <T>      数据类型
     * @param template MongoTemplate
     * @param clazz    T.class
     * @param criteria Criteria
     * @param sort     Sort
     * @return List
     */
    public static <T> List<T> sort(MongoTemplate template, Class<T> clazz, Criteria criteria, Sort sort) {
        return template.find(Query.query(criteria).with(sort), clazz);
    }

    /**
     * <h2>条件和分页查询</h2>
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

}
