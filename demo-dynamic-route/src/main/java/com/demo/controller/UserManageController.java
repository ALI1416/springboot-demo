package com.demo.controller;

import cn.z.tinytoken.T4s;
import com.demo.base.ControllerBase;
import com.demo.constant.ResultEnum;
import com.demo.entity.pojo.Result;
import com.demo.entity.vo.UserVo;
import com.demo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <h1>用户管理</h1>
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
@Tag(name = "用户管理")
public class UserManageController extends ControllerBase {

    private final T4s t4s;
    private final UserService userService;

    /**
     * 注销用户token(限制)
     */
    @GetMapping("logoutTokenLimit")
    @Operation(summary = "注销用户token(限制)", description = "需要登录")
    @Parameter(name = "token", description = "用户token")
    public Result<Boolean> logoutTokenLimit(String token) {
        // 只能管理自己创建的用户
        Long id = t4s.getId(token);
        if (id == null) {
            return Result.e(ResultEnum.NOT_LOGIN);
        }
        if (!userService.existIdAndCreateId(id, t4s.getId())) {
            return Result.e(ResultEnum.INSUFFICIENT_PERMISSION);
        }
        return Result.o(t4s.deleteByToken(token));
    }

    /**
     * 注销用户token
     */
    @GetMapping("logoutToken")
    @Operation(summary = "注销用户token")
    @Parameter(name = "token", description = "用户token")
    public Result<Boolean> logoutToken(String token) {
        return Result.o(t4s.deleteByToken(token));
    }

    /**
     * 注销用户id(限制)
     */
    @GetMapping("logoutIdLimit")
    @Operation(summary = "注销用户id(限制)", description = "需要登录<br>响应：注销成功个数")
    @Parameter(name = "id", description = "用户id")
    public Result<Long> logoutIdLimit(long id) {
        // 只能管理自己创建的用户
        if (!userService.existIdAndCreateId(id, t4s.getId())) {
            return Result.e(ResultEnum.INSUFFICIENT_PERMISSION);
        }
        return Result.o(t4s.deleteById(id));
    }

    /**
     * 注销用户id
     */
    @GetMapping("logoutId")
    @Operation(summary = "注销用户id", description = "响应：注销成功个数")
    @Parameter(name = "id", description = "用户id")
    public Result<Long> logoutId(long id) {
        return Result.o(t4s.deleteById(id));
    }

    /**
     * 创建用户
     */
    @PostMapping("create")
    @Operation(summary = "创建用户", description = "需要登录/account/name/pwd<br>响应：成功id/失败0")
    public Result<Long> create(@RequestBody UserVo user) {
        if (existNull(user.getAccount(), user.getName(), user.getPwd())) {
            return paramIsError();
        }
        user.setCreateId(t4s.getId());
        if (userService.existAccount(user.getAccount())) {
            return Result.e(ResultEnum.ACCOUNT_EXIST);
        }
        long id = userService.register(user);
        return Result.o(id);
    }

    /**
     * 修改用户信息(限制)
     */
    @PatchMapping("updateLimit")
    @Operation(summary = "修改用户信息(限制)", description = "需要登录/id 至少一个account/name/pwd/isDelete")
    public Result<Boolean> updateLimit(@RequestBody UserVo user) {
        if (isNull(user.getId()) && !allNull(user.getName(), user.getAccount(), user.getPwd(), user.getIsDelete())) {
            return paramIsError();
        }
        // 只能管理自己创建的用户
        if (!userService.existIdAndCreateId(user.getId(), t4s.getId())) {
            return Result.e(ResultEnum.INSUFFICIENT_PERMISSION);
        }
        if (user.getAccount() != null && userService.existAccount(user.getAccount())) {
            return Result.e(ResultEnum.ACCOUNT_EXIST);
        }
        return Result.o(userService.update(user));
    }

    /**
     * 修改用户信息
     */
    @PatchMapping("update")
    @Operation(summary = "修改用户信息", description = "需要id 至少一个account/name/pwd/isDelete")
    public Result<Boolean> update(@RequestBody UserVo user) {
        if (isNull(user.getId()) && !allNull(user.getName(), user.getAccount(), user.getPwd(), user.getIsDelete())) {
            return paramIsError();
        }
        if (user.getAccount() != null && userService.existAccount(user.getAccount())) {
            return Result.e(ResultEnum.ACCOUNT_EXIST);
        }
        return Result.o(userService.update(user));
    }

    /**
     * 修改用户角色(限制)
     */
    @PutMapping("updateRoleLimit")
    @Operation(summary = "修改用户角色(限制)", description = "需要登录/id/roleIdList")
    public Result<Boolean> updateRoleLimit(@RequestBody UserVo user) {
        if (existNull(user.getId(), user.getRoleIdList())) {
            return paramIsError();
        }
        // 只能管理自己创建的用户
        if (!userService.existIdAndCreateId(user.getId(), t4s.getId())) {
            return Result.e(ResultEnum.INSUFFICIENT_PERMISSION);
        }
        return Result.o(userService.updateRole(user));
    }

    /**
     * 修改用户角色
     */
    @PutMapping("updateRole")
    @Operation(summary = "修改用户角色", description = "需要id/roleIdList")
    public Result<Boolean> updateRole(@RequestBody UserVo user) {
        if (existNull(user.getId(), user.getRoleIdList())) {
            return paramIsError();
        }
        return Result.o(userService.updateRole(user));
    }

    /**
     * 获取拥有指定角色的用户(限制)
     */
    @GetMapping("getByRoleIdLimit")
    @Operation(summary = "获取拥有指定角色的用户(限制)", description = "需要登录")
    @Parameter(name = "roleId", description = "角色id")
    public Result<List<UserVo>> getByRoleIdLimit(long roleId) {
        return Result.o(userService.findByRoleIdAndCreateId(roleId, t4s.getId()));
    }

    /**
     * 获取拥有指定角色的用户
     */
    @GetMapping("getByRoleId")
    @Operation(summary = "获取拥有指定角色的用户")
    @Parameter(name = "roleId", description = "角色id")
    public Result<List<UserVo>> getByRoleId(long roleId) {
        return Result.o(userService.findByRoleId(roleId));
    }

    /**
     * 获取所有用户(限制)
     */
    @GetMapping("getLimit")
    @Operation(summary = "获取所有用户(限制)", description = "需要登录")
    public Result<List<UserVo>> getLimit() {
        return Result.o(userService.findByCreateId(t4s.getId()));
    }

    /**
     * 获取所有用户
     */
    @GetMapping("get")
    @Operation(summary = "获取所有用户")
    public Result<List<UserVo>> get() {
        return Result.o(userService.findAll());
    }

}
