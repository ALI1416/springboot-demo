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
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <h1>用户Controller</h1>
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

    private final T4s t4s;
    private final UserService userService;
    private final RoleService roleService;
    private final RouteService routeService;

    /**
     * 登录
     */
    @PostMapping("login")
    public Result<UserVo> login(@RequestBody UserVo user) {
        if (existNull(user.getAccount(), user.getPwd())) {
            return paramIsError();
        }
        UserVo u = userService.findByAccount(user.getAccount());
        if (u != null && BCrypt.check(user.getPwd(), u.getPwd())) {
            String token = t4s.setToken(u.getId());
            u.setPwd(null);
            u.setToken(token);
            return Result.o(u);
        }
        return Result.e(ResultEnum.LOGIN_FAIL);
    }

    /**
     * 注销
     */
    @PostMapping("logout")
    public Result<Boolean> logout() {
        return Result.o(t4s.deleteByToken());
    }

    /**
     * 注册
     */
    @PostMapping("register")
    public Result<Long> register(@RequestBody UserVo user) {
        if (existNull(user.getAccount(), user.getName(), user.getPwd())) {
            return paramIsError();
        }
        user.setCreateId(0L);
        if (userService.existAccount(user.getAccount())) {
            return Result.e(ResultEnum.ACCOUNT_EXIST);
        }
        long id = userService.register(user);
        if (id == 0L) {
            return Result.e(ResultEnum.REGISTER_FAIL);
        }
        return Result.o(id);
    }

    /**
     * 修改密码
     */
    @PostMapping("changePwd")
    public Result<Boolean> changePwd(@RequestBody UserVo user) {
        if (existNull(user.getPwd(), user.getNewPwd())) {
            return paramIsError();
        }
        user.setId(t4s.getId());
        User u = userService.info(user.getId());
        if (!BCrypt.check(user.getPwd(), u.getPwd())) {
            return Result.e(ResultEnum.PASSWORD_ERROR);
        }
        return Result.o(userService.changePwd(user));
    }

    /**
     * 修改个人信息(除密码)
     */
    @PostMapping("update")
    public Result<Boolean> update(@RequestBody UserVo user) {
        if (user.getAccount() != null && userService.existAccount(user.getAccount())) {
            return Result.e(ResultEnum.ACCOUNT_EXIST);
        }
        user.setId(t4s.getId());
        user.setPwd(null);
        return Result.o(userService.update(user));
    }

    /**
     * 获取路由
     */
    @PostMapping("findRoute")
    public Result<RouteVo> findRoute() {
        return Result.o(routeService.findByUserId(t4s.getId()));
    }

    /**
     * 获取角色和路由
     */
    @PostMapping("findRoleAndRoute")
    public Result<List<RoleVo>> findRoleAndRoute() {
        return Result.o(roleService.findRoleAndRouteByUserId(t4s.getId()));
    }

}
