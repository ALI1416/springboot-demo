package com.demo.controller;

import com.demo.constant.ResultCodeEnum;
import com.demo.entity.po.LoginLogTest;
import com.demo.entity.pojo.Result;
import com.demo.service.LoginLogTestService;
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
    private final LoginLogTestService loginLogTestService;

    /**
     * 首页
     *
     * @return LoginLogTest
     */
    @GetMapping(value = {"", "/", "index"})
    public Result index() {
        LoginLogTest loginLogTest = new LoginLogTest(request);
        if (loginLogTestService.insert(loginLogTest)) {
            return Result.o(loginLogTest);
        } else {
            return Result.e(ResultCodeEnum.ERROR, loginLogTest);
        }
    }

}
