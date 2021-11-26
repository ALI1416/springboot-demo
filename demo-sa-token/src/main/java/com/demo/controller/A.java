package com.demo.controller;

import com.demo.entity.pojo.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <h1>A</h1>
 *
 * <p>
 * createDate 2021/11/26 14:05:46
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@RestController
@RequestMapping("a")
public class A {

    @GetMapping(value = {"", "/"})
    public Result index() {
        return Result.o();
    }

    @GetMapping("logout")
    public Result logout() {
        return Result.o();
    }

}
