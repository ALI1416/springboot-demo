package com.demo.entity.mongo;

import com.demo.base.MongoEntityBase;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.sql.Timestamp;

/**
 * <h1>用户Mongo实体</h1>
 *
 * <p>
 * createDate 2021/03/27 19:12:56
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Getter
@Setter
// @Document()注解是声明为MongoDB文档，collection是别名
@Document(collection = "user")
// @CompoundIndex()注解是指明复合索引，def是排序字段
@CompoundIndex(def = "{'follower': 1 , 'following': -1 }")
public class UserMongo extends MongoEntityBase {

    /**
     * 用户名<br>
     * 索引使用@Indexed()注解，默认升序
     * 降序需要@Indexed(direction = IndexDirection.DESCENDING)这样使用
     */
    @Indexed()
    private String name;

    /**
     * 关注人数<br>
     * 别名使用@Field()注解
     */
    @Field("follower")
    private Integer followers;

    /**
     * 粉丝人数
     */
    private Integer following;

    /**
     * 时间
     */
    private Timestamp date;

    public UserMongo() {

    }

    public UserMongo(UserMongo mongo) {
        this.name = mongo.name;
        this.followers = mongo.followers;
        this.following = mongo.following;
        this.date = mongo.date;
    }

}
