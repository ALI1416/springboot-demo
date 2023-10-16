package com.demo.base;

import com.alibaba.fastjson2.util.DateUtils;
import com.demo.entity.pojo.PageInfo;
import com.demo.entity.pojo.ParamQuery;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

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
public class DaoBase {

    /**
     * 自定义查询分页排序
     *
     * @param <T>      数据类型
     * @param template MongoTemplate
     * @param clazz    T.class
     * @param criteria Criteria
     * @param pageable Pageable
     * @return PageInfo
     */
    public static <T> PageInfo<T> find(MongoTemplate template, Class<T> clazz, Criteria criteria, Pageable pageable) {
        Query query = Query.query(criteria);
        return new PageInfo<>(template.find(query.with(pageable), clazz), pageable, template.count(query, clazz));
    }

    /**
     * 构建查询参数
     *
     * @param criteria Criteria
     * @param list     List ParamQuery
     */
    public static void buildParamQuery(Criteria criteria, List<ParamQuery> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        for (ParamQuery query : list) {
            buildCriteria(criteria, query.getOperator(), query.getField(), conversionType(query.getType(), query.getValue(), query.getValue2()));
        }
    }

    /**
     * 转换类型
     *
     * @param type   数据类型
     * @param value  值
     * @param value2 值2
     * @return 值对象数组
     */
    private static Object[] conversionType(String type, String value, String value2) {
        Object[] objectArray = new Object[2];
        if (value == null) {
            return new Object[0];
        }
        // 默认string
        if (type == null) {
            type = "string";
        }
        switch (type) {
            case "int": {
                objectArray[0] = Integer.parseInt(value);
                if (value2 != null) {
                    objectArray[1] = Integer.parseInt(value2);
                }
                break;
            }
            case "long": {
                objectArray[0] = Long.parseLong(value);
                if (value2 != null) {
                    objectArray[1] = Long.parseLong(value2);
                }
                break;
            }
            case "boolean": {
                objectArray[0] = Boolean.parseBoolean(value);
                if (value2 != null) {
                    objectArray[1] = Boolean.parseBoolean(value2);
                }
                break;
            }
            case "timestamp": {
                objectArray[0] = new Timestamp(DateUtils.parseMillis(value));
                if (value2 != null) {
                    objectArray[1] = new Timestamp(DateUtils.parseMillis(value2));
                }
                break;
            }
            case "string":
            default: {
                objectArray[0] = value;
                if (value2 != null) {
                    objectArray[1] = value2;
                }
            }
        }
        return objectArray;
    }

    /**
     * 构建查询
     *
     * @param criteria Criteria
     * @param operator 操作符
     * @param field    字段
     * @param value    值对象数组
     */
    private static void buildCriteria(Criteria criteria, String operator, String field, Object[] value) {
        if (field == null || value == null || value.length == 0) {
            return;
        }
        // 默认eq
        if (operator == null) {
            operator = "eq";
        }
        switch (operator) {
            // 不等
            case "neq": {
                criteria.and(field).ne(value[0]);
                return;
            }
            // 小于
            case "lt": {
                criteria.and(field).lte(value[0]);
                return;
            }
            // 大于
            case "gt": {
                criteria.and(field).gte(value[0]);
                return;
            }
            // 在...与...之间
            case "bt": {
                criteria.and(field).gte(value[0]).lte(value[1]);
                return;
            }
            // 不在...与...之间
            case "nbt": {
                criteria.orOperator(Criteria.where(field).lte(value[0]), Criteria.where(field).gte(value[1]));
                return;
            }
            // 等于
            case "eq":
            default: {
                criteria.and(field).is(value[0]);
            }
        }
    }

    /**
     * 构建范围查询
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
