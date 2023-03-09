package com.demo.util.hutool.servlet;

import cn.hutool.extra.servlet.ServletUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <h1>Servlet</h1>
 *
 * <p>
 * createDate 2022/03/25 10:35:38
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@RestController
@Slf4j
@RequestMapping("servlet")
public class ServletController {

    @GetMapping("/header")
    public Map<String, String> header(HttpServletRequest request) {
        Map<String, String> header = ServletUtil.getHeaderMap(request);
        log.info(header.toString());
        return header;
    }

    @GetMapping("/ip")
    public String ip(HttpServletRequest request) {
        String ip = ServletUtil.getClientIP(request);
        log.info(ip);
        return ip;
    }

}
