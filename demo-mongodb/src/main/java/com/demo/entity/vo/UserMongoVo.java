package com.demo.entity.vo;

import com.demo.entity.po.UserMongo;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Transient;

import java.sql.Timestamp;

/**
 * <h1>用户Mongo</h1>
 *
 * <p>
 * createDate 2022/06/02 15:32:09
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Getter
@Setter
public class UserMongoVo extends UserMongo {

    /**
     * 时间-结束
     */
    @Transient
    private Timestamp dateEnd;
    /**
     * 时间-否定
     */
    @Transient
    private Boolean dateNot;

}
