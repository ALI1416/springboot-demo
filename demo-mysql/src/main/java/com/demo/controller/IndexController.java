package com.demo.controller;

import com.demo.constant.ResultEnum;
import com.demo.entity.po.LoginLog;
import com.demo.entity.pojo.Result;
import com.demo.service.LoginLogService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <h1>首页</h1>
 *
 * <p>
 * createDate 2021/09/09 10:35:04
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@RestController
@AllArgsConstructor
public class IndexController {

    private final HttpServletRequest request;
    private final LoginLogService loginLogService;

    /**
     * 首页
     */
    @GetMapping(value = {"", "/", "index"})
    public Result<LoginLog> index() {
        LoginLog loginLog = new LoginLog(request);
        if (loginLogService.insert(loginLog)) {
            return Result.o(loginLog);
        } else {
            return Result.e(ResultEnum.ERROR, loginLog);
        }
    }

}
