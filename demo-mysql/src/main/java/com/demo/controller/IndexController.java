package com.demo.controller;

import com.demo.constant.ResultCode;
import com.demo.entity.po.LoginLog;
import com.demo.entity.pojo.Result;
import com.demo.service.LoginLogService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
     * 首页<br>
     * http://localhost:8080
     */
    @GetMapping
    public Result<LoginLog> index() {
        LoginLog loginLog = new LoginLog(request);
        if (loginLogService.insert(loginLog)) {
            return Result.o(loginLog);
        } else {
            return Result.e(ResultCode.ERROR, loginLog);
        }
    }

    /**
     * 删除，通过id<br>
     * http://localhost:8080/deleteById?id=1
     */
    @DeleteMapping("deleteById")
    public Result<Boolean> deleteById(long id) {
        return Result.o(loginLogService.deleteById(id));
    }

    /**
     * 获取最后一条<br>
     * http://localhost:8080/getLast
     */
    @GetMapping("getLast")
    public Result<LoginLog> getLast() {
        return Result.o(loginLogService.getLast());
    }

}
