package com.demo.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.demo.entity.pojo.Result;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <h1>UserController</h1>
 *
 * <p>
 * createDate 2021/11/29 16:45:45
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@RestController
@RequestMapping("user")
@AllArgsConstructor
public class UserController {


    @GetMapping("login")
    public Result login(Long id) {
        StpUtil.login(id);
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

}
