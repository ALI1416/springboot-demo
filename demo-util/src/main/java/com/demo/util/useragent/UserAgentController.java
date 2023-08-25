package com.demo.util.useragent;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <h1>UserAgentController</h1>
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
@RequestMapping("userAgent")
public class UserAgentController {

    /**
     * 解析<br>
     * http://localhost:8080/userAgent/parse
     */
    @GetMapping("/parse")
    public UserAgent parse(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        log.info(userAgent);
        return UserAgent.parse(userAgent);
    }

}
