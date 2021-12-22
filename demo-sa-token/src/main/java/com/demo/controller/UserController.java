package com.demo.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.demo.base.ControllerBase;
import com.demo.constant.ResultCodeEnum;
import com.demo.entity.po.User;
import com.demo.entity.pojo.Result;
import com.demo.entity.vo.UserVo;
import com.demo.interceptor.RouteInterceptor;
import com.demo.service.RoleService;
import com.demo.service.Route2Service;
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
    private final Route2Service route2Service;
    private final RouteInterceptor routeInterceptor;

    /**
     * 登录
     */
    @PostMapping("login")
    public Result login(@RequestBody UserVo user) {
        if (existNull(user.getAccount(), user.getPwd())) {
            return paramIsError();
        }
        if (userService.login(user)) {
            SaTokenInfo saTokenInfo = StpUtil.getTokenInfo();
            UserVo u = userService.info(StpUtil.getLoginIdAsLong());
            u.setPwd(null);
            u.setToken(saTokenInfo.tokenValue);
            return Result.o(u);
        } else {
            return Result.e(ResultCodeEnum.LOGIN_FAIL);
        }
    }

    /**
     * 注册
     */
    @PostMapping("register")
    public Result register(@RequestBody UserVo user) {
        if (existNull(user.getAccount(), user.getName(), user.getPwd())) {
            return paramIsError();
        }
        if (userService.existAccount(user.getAccount())) {
            return Result.e(ResultCodeEnum.ACCOUNT_EXIST);
        }
        long id = userService.register(user);
        if (id == 0L) {
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
     * 修改个人信息(除密码)
     */
    @PostMapping("updateMyInfo")
    public Result updateMyInfo(@RequestBody UserVo user) {
        if (user.getAccount() != null && userService.existAccount(user.getAccount())) {
            return Result.e(ResultCodeEnum.ACCOUNT_EXIST);
        }
        user.setId(StpUtil.getLoginIdAsLong());
        return Result.o(userService.updateMyInfo(user));
    }

    /**
     * 修改用户信息
     */
    @PostMapping("update")
    public Result update(@RequestBody UserVo user) {
        if (isNull(user.getId())) {
            return paramIsError();
        }
        if (user.getAccount() != null && userService.existAccount(user.getAccount())) {
            return Result.e(ResultCodeEnum.ACCOUNT_EXIST);
        }
        return Result.o(userService.update(user));
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
     * 查询当前用户拥有的角色和路由
     */
    @PostMapping("findRole")
    public Result findRole() {
        return Result.o(roleService.findAndRouteByUserId(StpUtil.getLoginIdAsLong()));
    }

    /**
     * 查询当前用户拥有的路由
     */
    @PostMapping("findRoute")
    public Result findRoute() {
        return Result.o(routeService.findByUserId(StpUtil.getLoginIdAsLong()));
    }

    /**
     * 查询当前用户拥有的前端路由
     */
    @PostMapping("findRoute2")
    public Result findRoute2() {
        return Result.o(route2Service.findByUserId(StpUtil.getLoginIdAsLong()));
    }

    /**
     * 查询全部
     */
    @PostMapping("findAll")
    public Result findAll() {
        return Result.o(userService.findAll());
    }

    /**
     * 修改用户的角色
     */
    @PostMapping("updateRole")
    public Result updateRole(@RequestBody UserVo user) {
        if (existNull(user.getId(), user.getRoleIds()) || user.getRoleIds().size() == 0) {
            return paramIsError();
        }
        return Result.o(userService.updateRole(user));
    }

    /**
     * 刷新用户的角色
     */
    @PostMapping("refreshRole")
    public Result refreshRole(@RequestBody UserVo user) {
        if (isNull(user.getId())) {
            return paramIsError();
        }
        routeInterceptor.deleteRouteUser(user.getId());
        return Result.o();
    }

}
