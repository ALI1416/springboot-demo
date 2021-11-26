package com.demo.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.demo.entity.pojo.Result;
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
public class IndexController {

    @GetMapping(value = {"", "/", "index"})
    public Result index() {
        StpUtil.login(10001L);
        return Result.o();
    }

    @GetMapping("logout")
    public Result logout() {
        StpUtil.logout();
        return Result.o();
    }

    @GetMapping("getLoginIdAsLong")
    public Result getLoginIdAsLong() {
        return Result.o(StpUtil.getLoginIdAsLong());
    }

    @GetMapping("getPermissionList")
    public Result getPermissionList() {
        return Result.o(StpUtil.getPermissionList());
    }

    @GetMapping("getRoleList")
    public Result getRoleList() {
        return Result.o(StpUtil.getRoleList());
    }

}
