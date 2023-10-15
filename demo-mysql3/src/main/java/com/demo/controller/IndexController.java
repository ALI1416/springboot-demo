package com.demo.controller;

import com.demo.entity.pojo.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * <h1>首页</h1>
 *
 * <p>
 * createDate 2023/10/12 14:17:18
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@RestController
public class IndexController {

    /**
     * 首页
     */
    @GetMapping(value = {"", "/"})
    public Result index() {
        return Result.o();
    }

    /**
     * 查看用户头像
     */
    @GetMapping("avatar/{id}")
    public Result<Long> avatar(@PathVariable long id) {
        return Result.o(id);
    }

}
