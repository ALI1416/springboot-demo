package com.demo.controller;

import cn.z.tinytoken.T4s;
import com.demo.base.ControllerBase;
import com.demo.constant.ResultEnum;
import com.demo.entity.pojo.Result;
import com.demo.entity.vo.UserVo;
import com.demo.service.UserService;
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
public class UserManageController extends ControllerBase {

    private final T4s t4s;
    private final UserService userService;

    /**
     * 注销id
     */
    @GetMapping("logoutById")
    public Result<Long> logoutById(long id) {
        // 只能管理自己创建的用户
        if (!userService.findExistByIdAndCreateId(id, t4s.getId())) {
            return Result.e(ResultEnum.INSUFFICIENT_PERMISSION);
        }
        return Result.o(t4s.deleteById(id));
    }

    /**
     * 注销token
     */
    @GetMapping("logoutByToken")
    public Result<Boolean> logoutByToken(String token) {
        // 只能管理自己创建的用户
        Long id = t4s.getId(token);
        if (id == null) {
            return Result.e(ResultEnum.NOT_LOGIN);
        }
        if (!userService.findExistByIdAndCreateId(id, t4s.getId())) {
            return Result.e(ResultEnum.INSUFFICIENT_PERMISSION);
        }
        return Result.o(t4s.deleteByToken(token));
    }

    /**
     * 新增
     */
    @PostMapping("insert")
    public Result<Long> insert(@RequestBody UserVo user) {
        if (existNull(user.getAccount(), user.getName(), user.getPwd())) {
            return paramIsError();
        }
        user.setCreateId(t4s.getId());
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
     * 修改信息
     */
    @PatchMapping("update")
    public Result<Boolean> update(@RequestBody UserVo user) {
        if (isNull(user.getId()) && !allNull(user.getName(), user.getAccount(), user.getPwd(), user.getIsDelete())) {
            return paramIsError();
        }
        // 只能管理自己创建的用户
        if (!userService.findExistByIdAndCreateId(user.getId(), t4s.getId())) {
            return Result.e(ResultEnum.INSUFFICIENT_PERMISSION);
        }
        if (user.getAccount() != null && userService.existAccount(user.getAccount())) {
            return Result.e(ResultEnum.ACCOUNT_EXIST);
        }
        return Result.o(userService.update(user));
    }

    /**
     * 查询，通过角色id
     */
    @GetMapping("findByRoleId")
    public Result<List<UserVo>> findByRoleId(long roleId) {
        return Result.o(userService.findByRoleIdAndCreateId(roleId, t4s.getId()));
    }

    /**
     * 查询全部
     */
    @GetMapping("getAll")
    public Result<List<UserVo>> getAll() {
        return Result.o(userService.findByCreateId(t4s.getId()));
    }

    /**
     * 修改角色
     */
    @PutMapping("updateRole")
    public Result<Boolean> updateRole(@RequestBody UserVo user) {
        if (existNull(user.getId(), user.getRoleIdList())) {
            return paramIsError();
        }
        // 只能管理自己创建的用户
        if (!userService.findExistByIdAndCreateId(user.getId(), t4s.getId())) {
            return Result.e(ResultEnum.INSUFFICIENT_PERMISSION);
        }
        return Result.o(userService.updateRole(user));
    }

}
