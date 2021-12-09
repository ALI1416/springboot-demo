package com.demo.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.demo.base.ControllerBase;
import com.demo.constant.ResultCodeEnum;
import com.demo.entity.po.User;
import com.demo.entity.pojo.Result;
import com.demo.entity.vo.UserVo;
import com.demo.service.RoleService;
import com.demo.service.RouteService;
import com.demo.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
public class UserController extends ControllerBase {

    private final UserService userService;
    private final RoleService roleService;
    private final RouteService routeService;

    /**
     * 登录
     */
    @PostMapping("login")
    public Result login(@RequestBody UserVo user) {
        if (existNull(user.getAccount(), user.getPwd())) {
            return paramIsError();
        }
        if (userService.login(user)) {
            return Result.o(StpUtil.getTokenInfo());
        } else {
            return Result.e(ResultCodeEnum.LOGIN_FAIL);
        }
    }

    /**
     * 注册
     */
    @PostMapping("register")
    public Result register(@RequestBody UserVo user) {
        if (existNull(user.getAccount(), user.getPwd())) {
            return paramIsError();
        }
        long id = userService.register(user);
        if (id == 0) {
            return Result.e(ResultCodeEnum.REGISTER_FAIL);
        }
        return Result.o(id);
    }

    /**
     * 修改密码
     */
    @PostMapping("changePwd")
    public Result changePwd(@RequestBody UserVo user) {
        if (existNull(user.getPwd(), user.getNewPwd())) {
            return paramIsError();
        }
        user.setId(StpUtil.getLoginIdAsLong());
        User u = userService.info(user.getId());
        if (!BCrypt.checkpw(user.getPwd(), u.getPwd())) {
            return Result.e(ResultCodeEnum.PASSWORD_ERROR);
        }
        return Result.o(userService.changePwd(user));
    }

    /**
     * 注销
     */
    @PostMapping("logout")
    public Result logout() {
        StpUtil.logout();
        return Result.o();
    }

    /**
     * 查询当前用户拥有的角色
     */
    @PostMapping("findRole")
    public Result findRole() {
        return Result.o(roleService.findOwnByUserId(StpUtil.getLoginIdAsLong()));
    }

    /**
     * 查询当前用户拥有的路由
     */
    @PostMapping("findRoute")
    public Result findRoute() {
        return Result.o(routeService.findOwnByUserId(StpUtil.getLoginIdAsLong()));
    }

}
