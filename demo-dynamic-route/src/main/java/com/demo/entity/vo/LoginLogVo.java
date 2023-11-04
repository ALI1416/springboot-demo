package com.demo.entity.vo;

import com.demo.entity.po.LoginLog;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * <h1>登录日志</h1>
 *
 * <p>
 * createDate 2023/11/04 17:26:57
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Getter
@Setter
@Document(collection = "login_log")
@Schema(description = "登录日志")
public class LoginLogVo extends LoginLog {

}
