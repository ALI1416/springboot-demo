package com.demo.controller;

import com.demo.entity.pojo.Result;
import com.demo.entity.vo.UserVo;
import com.demo.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <h1>用户</h1>
 *
 * <p>
 * createDate 2021/10/26 15:38:52
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@RestController
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 插入<br>
     * http://localhost:8080/insert?account=asd&pwd=zxc
     */
    @GetMapping("insert")
    public Result<Long> insert(UserVo user) {
        return Result.o(userService.insert(user));
    }

    /**
     * 更新<br>
     * http://localhost:8080/update?id=0&account=asd4
     */
    @GetMapping("update")
    public Result<Boolean> update(UserVo user) {
        return Result.o(userService.update(user));
    }

    /**
     * 删除通过account<br>
     * http://localhost:8080/delete?account=asd
     */
    @GetMapping("delete")
    public Result<Boolean> delete(String account) {
        return Result.o(userService.delete(account));
    }

    /**
     * 查询通过account<br>
     * http://localhost:8080/findByAccount?account=ROOT
     */
    @GetMapping("findByAccount")
    public Result<UserVo> findByAccount(String account) {
        return Result.o(userService.findByAccount(account));
    }

}
