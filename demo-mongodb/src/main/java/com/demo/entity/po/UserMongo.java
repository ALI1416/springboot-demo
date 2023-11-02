package com.demo.entity.po;

import com.demo.base.MongoEntityBase;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.sql.Timestamp;

/**
 * <h1>用户Mongo</h1>
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
@Document(collection = "user")
@CompoundIndex(def = "{'follower': 1 , 'following': -1 }")
public class UserMongo extends MongoEntityBase {

    /**
     * 用户名
     */
    @Indexed()
    private String name;
    /**
     * 关注人数
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

}
