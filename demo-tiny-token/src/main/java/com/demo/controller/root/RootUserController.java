package com.demo.controller.root;

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
 * <h1>用户</h1>
 *
 * <p>
 * createDate 2023/10/13 14:16:14
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@RestController
@RequestMapping("root/user")
@AllArgsConstructor
public class RootUserController extends ControllerBase {

    private final T4s t4s;
    private final UserService userService;

    /**
     * 注销id
     */
    @GetMapping("logoutById")
    public Result<Long> logoutById(long id) {
        return Result.o(t4s.deleteById(id));
    }

    /**
     * 注销token
     */
    @GetMapping("logoutByToken")
    public Result<Boolean> logoutByToken(String token) {
        return Result.o(t4s.deleteByToken(token));
    }

    /**
     * 修改信息
     */
    @PatchMapping("update")
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
     * 查询，通过角色id
     */
    @GetMapping("findByRoleId")
    public Result<List<UserVo>> findUserByRoleId(long roleId) {
        return Result.o(userService.findByRoleId(roleId));
    }

    /**
     * 查询全部
     */
    @GetMapping("getAll")
    public Result<List<UserVo>> getAll() {
        return Result.o(userService.findAll());
    }

    /**
     * 修改角色
     */
    @PutMapping("updateRole")
    public Result<Boolean> updateRole(@RequestBody UserVo user) {
        if (existNull(user.getId(), user.getRoleIdList())) {
            return paramIsError();
        }
        return Result.o(userService.updateRole(user));
    }

}
