package com.demo.entity.po;

import cn.z.ip2region.Region;
import cn.z.tool.useragent.UserAgent;
import com.demo.base.MongoEntityBase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * <h1>登录日志</h1>
 *
 * <p>
 * createDate 2023/10/28 16:04:42
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Getter
@Setter
@Document(collection = "login_log")
@Schema(description = "登录日志")
public class LoginLog extends MongoEntityBase {

    /**
     * token
     */
    @Schema(description = "token")
    private String token;
    /**
     * IP地址
     */
    @Schema(description = "IP地址")
    private String ip;
    /**
     * IP地址详情
     */
    @Schema(description = "IP地址详情")
    private Region ipInfo;
    /**
     * UserAgent
     */
    @Schema(description = "UserAgent")
    private String userAgent;
    /**
     * UserAgent详情
     */
    @Schema(description = "UserAgent详情")
    private UserAgent userAgentInfo;

}
