package com.demo.util.hutool.useragent;

import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <h1>UserAgent</h1>
 *
 * <p>
 * createDate 2022/02/22 14:59:11
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@RestController
@Slf4j
public class UserAgentController {

    @GetMapping("/ua")
    public UserAgent captcha(HttpServletRequest request) {
        String uaStr = request.getHeader("User-Agent");
        log.info(uaStr);
        return UserAgentUtil.parse(uaStr);
    }

}
