package com.demo.controller;

import cn.z.tinytoken.T4s;
import cn.z.tool.BCrypt;
import com.demo.base.ControllerBase;
import com.demo.constant.ResultEnum;
import com.demo.entity.po.User;
import com.demo.entity.pojo.Result;
import com.demo.entity.vo.RoleVo;
import com.demo.entity.vo.RouteVo;
import com.demo.entity.vo.UserVo;
import com.demo.service.RoleService;
import com.demo.service.RouteService;
import com.demo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <h1>用户</h1>
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
@Tag(name = "用户")
public class UserController extends ControllerBase {

    private final T4s t4s;
    private final UserService userService;
    private final RoleService roleService;
    private final RouteService routeService;

    /**
     * 用户登录
     */
    @PostMapping("login")
    @Operation(summary = "用户登录", description = "需要account/pwd")
    public Result<UserVo> login(@RequestBody UserVo user) {
        if (existNull(user.getAccount(), user.getPwd())) {
            return paramIsError();
        }
        UserVo u = userService.findByAccount(user.getAccount());
        if (u != null) {
            if (Boolean.TRUE.equals(u.getIsDelete())) {
                return Result.e(ResultEnum.ACCOUNT_DISABLE);
            }
            if (BCrypt.check(user.getPwd(), u.getPwd())) {
                u.setPwd(null);
                u.setToken(t4s.setToken(u.getId()));
                return Result.o(u);
            }
        }
        return Result.e(ResultEnum.LOGIN_FAIL);
    }

    /**
     * 用户注销
     */
    @GetMapping("logout")
    @Operation(summary = "用户注销", description = "需要登录")
    public Result<Boolean> logout() {
        return Result.o(t4s.deleteByToken());
    }

    /**
     * 用户注册
     */
    @PostMapping("register")
    @Operation(summary = "用户注册", description = "需要account/name/pwd<br>响应：成功id/失败0")
    public Result<Long> register(@RequestBody UserVo user) {
        if (existNull(user.getAccount(), user.getName(), user.getPwd())) {
            return paramIsError();
        }
        user.setCreateId(0L);
        if (userService.existAccount(user.getAccount())) {
            return Result.e(ResultEnum.ACCOUNT_EXIST);
        }
        long id = userService.register(user);
        return Result.o(id);
    }

    /**
     * 用户修改密码
     */
    @PatchMapping("updatePwd")
    @Operation(summary = "用户修改密码", description = "需要登录/pwd/newPwd")
    public Result<Boolean> updatePwd(@RequestBody UserVo user) {
        if (existNull(user.getPwd(), user.getNewPwd())) {
            return paramIsError();
        }
        user.setId(t4s.getId());
        User u = userService.findById(user.getId());
        if (!BCrypt.check(user.getPwd(), u.getPwd())) {
            return Result.e(ResultEnum.PASSWORD_ERROR);
        }
        UserVo u2 = new UserVo();
        u2.setId(user.getId());
        u2.setPwd(user.getNewPwd());
        return Result.o(userService.update(u2));
    }

    /**
     * 用户修改信息(除密码、删除)
     */
    @PatchMapping("update")
    @Operation(summary = "用户修改信息(除密码、删除)", description = "需要登录 至少一个account/name")
    public Result<Boolean> update(@RequestBody UserVo user) {
        if (user.getAccount() != null && userService.existAccount(user.getAccount())) {
            return Result.e(ResultEnum.ACCOUNT_EXIST);
        }
        UserVo u = new UserVo();
        u.setId(t4s.getId());
        u.setAccount(user.getAccount());
        u.setName(user.getName());
        return Result.o(userService.update(u));
    }

    /**
     * 获取用户信息
     */
    @GetMapping("get")
    @Operation(summary = "获取用户信息", description = "需要登录")
    public Result<UserVo> get() {
        UserVo user = userService.findById(t4s.getId());
        user.setPwd(null);
        return Result.o(user);
    }

    /**
     * 获取用户角色
     */
    @GetMapping("role")
    @Operation(summary = "获取用户角色", description = "需要登录")
    public Result<List<RoleVo>> role() {
        return Result.o(roleService.findByUserId(t4s.getId()));
    }

    /**
     * 获取用户路由
     */
    @GetMapping("route")
    @Operation(summary = "获取用户路由", description = "需要登录")
    public Result<RouteVo> route() {
        return Result.o(routeService.findByUserId(t4s.getId()));
    }

    /**
     * 获取用户头像
     */
    @GetMapping("avatar")
    @Operation(summary = "获取用户头像", description = "需要登录")
    public Result<Long> avatar() {
        return Result.o(t4s.getId());
    }

}
