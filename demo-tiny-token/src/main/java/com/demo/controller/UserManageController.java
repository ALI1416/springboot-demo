package com.demo.controller;

import cn.z.tinytoken.T4s;
import com.demo.base.ControllerBase;
import com.demo.base.EntityBase;
import com.demo.constant.ResultCodeEnum;
import com.demo.entity.pojo.Result;
import com.demo.entity.vo.UserVo;
import com.demo.interceptor.RouteInterceptor;
import com.demo.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <h1>用户管理Controller</h1>
 *
 * <p>
 * createDate 2021/11/29 16:45:45
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@RestController
@RequestMapping("userManage")
@AllArgsConstructor
public class UserManageController extends ControllerBase {

    private final T4s t4s;
    private final UserService userService;
    private final RouteInterceptor routeInterceptor;

    /**
     * 新增用户
     */
    @PostMapping("insert")
    public Result insert(@RequestBody UserVo user) {
        if (existNull(user.getAccount(), user.getName(), user.getPwd())) {
            return paramIsError();
        }
        user.setCreateId(t4s.getId());
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
     * 修改用户信息
     */
    @PostMapping("update")
    public Result update(@RequestBody UserVo user) {
        if (isNull(user.getId()) && !allNull(user.getName(), user.getAccount(), user.getPwd())) {
            return paramIsError();
        }
        if (user.getAccount() != null && userService.existAccount(user.getAccount())) {
            return Result.e(ResultCodeEnum.ACCOUNT_EXIST);
        }
        return Result.o(userService.update(user));
    }

    /**
     * 查询全部用户
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
        if (existNull(user.getId(), user.getRoleIds()) || user.getRoleIds().isEmpty()) {
            return paramIsError();
        }
        return Result.o(userService.updateRole(user));
    }

    /**
     * 刷新角色，通过UserId
     */
    @PostMapping("refreshRole")
    public Result refreshRole(@RequestBody EntityBase user) {
        if (isNull(user.getId())) {
            return paramIsError();
        }
        return Result.o(routeInterceptor.deleteRouteUser(user.getId()));
    }

}