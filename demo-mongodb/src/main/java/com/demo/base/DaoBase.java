package com.demo.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

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
     * @param mongoTemplate mongoTemplate
     * @param entityClass   类
     * @param criteria      查询
     * @param pageable      分页器
     * @return Page
     */
    public <T> Page<T> find(MongoTemplate mongoTemplate, Class<T> entityClass, Criteria criteria, Pageable pageable) {
        Query query = Query.query(criteria).with(pageable);
        return new PageImpl<>( //
                mongoTemplate.find(query, entityClass), // 查询
                pageable, // 分页
                mongoTemplate.count(query, entityClass) // 总数
        );
    }

}
