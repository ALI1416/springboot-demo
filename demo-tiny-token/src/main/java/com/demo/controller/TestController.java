package com.demo.controller;

import com.demo.entity.pojo.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <h1>测试Controller</h1>
 *
 * <p>
 * createDate 2023/08/11 11:15:46
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@RestController
@RequestMapping("test")
public class TestController {

    /**
     * 测试1
     */
    @PostMapping("test1")
    public Result test1() {
        return Result.o();
    }

    /**
     * 测试1-1
     */
    @PostMapping("test1/test1")
    public Result test1test1() {
        return Result.o();
    }

    /**
     * 测试1-2
     */
    @PostMapping("test1/test2")
    public Result test1test2() {
        return Result.o();
    }

    /**
     * 测试1-1-1
     */
    @PostMapping("test1/test1/test1")
    public Result test1test1test1() {
        return Result.o();
    }

    /**
     * 测试2
     */
    @PostMapping("test2")
    public Result test2() {
        return Result.o();
    }

    /**
     * 测试3
     */
    @PostMapping("test3")
    public Result test3() {
        return Result.o();
    }

}
