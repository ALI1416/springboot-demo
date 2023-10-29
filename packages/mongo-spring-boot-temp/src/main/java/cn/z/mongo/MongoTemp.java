package cn.z.mongo;

import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * <h1>MongoDB模板</h1>
 *
 * <p>
 * createDate 2023/08/15 15:00:38
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class MongoTemp {

    /**
     * MongoDB模板
     */
    private final MongoTemplate mongoTemplate;

    /**
     * 构造函数(自动注入)
     *
     * @param mongoTemplate MongoTemplate
     */
    public MongoTemp(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

}
