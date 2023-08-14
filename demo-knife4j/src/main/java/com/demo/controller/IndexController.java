package com.demo.controller;

import com.demo.entity.po.SubUser;
import com.demo.entity.po.User;
import com.demo.entity.po.UserImpl;
import com.demo.entity.pojo.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
@Tag(name = "首页")
public class IndexController {

    // knife4j文档 http://localhost:8080/doc.html
    // swagger文档 http://localhost:8080/swagger-ui/index.html

    /**
     * <h3>int测试</h3>
     */
    @GetMapping("intTest")
    @Operation(summary = "int测试", description = "描述<br>换行")
    @Parameter(name = "n", description = "数字")
    public Result<Integer> intTest(int n) {
        return Result.o(n);
    }

    /**
     * <h3>user测试</h3>
     */
    @GetMapping("userTest")
    @Operation(summary = "user测试")
    @Parameter(name = "u", hidden = true)
    @Parameter(name = "account", description = "账号")
    @Parameter(name = "year", description = "年", required = true, example = "2000")
    public Result<User> userTest(User u) {
        return Result.o(u);
    }

    /**
     * <h3>user测试2</h3>
     */
    @PostMapping("userTest2")
    @Operation(summary = "user测试2")
    public Result<User> userTest2(@RequestBody User u) {
        return Result.o(u);
    }

    /**
     * <h3>user测试3</h3>
     */
    @PostMapping("userTest3")
    @Operation(summary = "user测试3")
    public Result<User> userTest3(@RequestBody User u) {
        SubUser subUser = new SubUser();
        subUser.setPwd("pwd");
        u.setSubUser(subUser);
        return Result.o(u);
    }

    /**
     * <h3>user测试4</h3>
     */
    @PostMapping("userTest4")
    @Operation(summary = "user测试4")
    public Result<User> userTest4(@RequestBody User u) {
        UserImpl userImpl = new UserImpl();
        userImpl.setPwd2("pwd2");
        u.setUserInterface(userImpl);
        return Result.o(u);
    }

}
