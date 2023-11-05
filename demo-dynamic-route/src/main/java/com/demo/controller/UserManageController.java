package com.demo.controller;

import cn.z.tinytoken.T4s;
import cn.z.tinytoken.entity.TokenInfo;
import cn.z.tinytoken.entity.TokenInfoExtra;
import com.demo.base.ControllerBase;
import com.demo.constant.ResultEnum;
import com.demo.entity.pojo.PageInfo;
import com.demo.entity.pojo.Result;
import com.demo.entity.vo.LoginLogVo;
import com.demo.entity.vo.UserVo;
import com.demo.service.LoginLogService;
import com.demo.service.RouteService;
import com.demo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    private final RouteService routeService;
    private final LoginLogService loginLogService;

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
        return Result.o(userService.register(user));
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
        boolean ok = userService.updateRole(user);
        // 刷新缓存
        if (ok) {
            routeService.deleteUserCacheByUserId(user.getId());
        }
        return Result.o(ok);
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
        boolean ok = userService.updateRole(user);
        // 刷新缓存
        if (ok) {
            routeService.deleteUserCacheByUserId(user.getId());
        }
        return Result.o(ok);
    }

    /**
     * 获取拥有指定角色的用户(限制)
     */
    @GetMapping("getByRoleIdLimit")
    @Operation(summary = "获取拥有指定角色的用户(限制)", description = "需要登录")
    @Parameter(name = "roleId", description = "角色id")
    public Result<PageInfo<UserVo>> getByRoleIdLimit(long roleId, Integer pages, Integer rows, String orderBy) {
        return Result.o(userService.findByRoleIdAndCreateId(roleId, t4s.getId(), pages, rows, orderBy));
    }

    /**
     * 获取拥有指定角色的用户
     */
    @GetMapping("getByRoleId")
    @Operation(summary = "获取拥有指定角色的用户")
    @Parameter(name = "roleId", description = "角色id")
    public Result<PageInfo<UserVo>> getByRoleId(long roleId, Integer pages, Integer rows, String orderBy) {
        return Result.o(userService.findByRoleId(roleId, pages, rows, orderBy));
    }

    /**
     * 获取所有用户(限制)
     */
    @GetMapping("getLimit")
    @Operation(summary = "获取所有用户(限制)", description = "需要登录")
    public Result<PageInfo<UserVo>> getLimit(Integer pages, Integer rows, String orderBy) {
        return Result.o(userService.findByCreateId(t4s.getId(), pages, rows, orderBy));
    }

    /**
     * 获取所有用户
     */
    @GetMapping("get")
    @Operation(summary = "获取所有用户")
    public Result<PageInfo<UserVo>> get(Integer pages, Integer rows, String orderBy) {
        return Result.o(userService.findAll(pages, rows, orderBy));
    }

    /**
     * 注销用户token认证(限制)
     */
    @GetMapping("logoutTokenLimit")
    @Operation(summary = "注销用户token认证(限制)", description = "需要登录")
    @Parameter(name = "token", description = "用户token")
    public Result<Boolean> logoutTokenLimit(String token) {
        // 只能管理自己创建的用户
        Long id = t4s.getId(token);
        if (id == null) {
            return Result.e(ResultEnum.USER_NOT_LOGIN);
        }
        if (!userService.existIdAndCreateId(id, t4s.getId())) {
            return Result.e(ResultEnum.INSUFFICIENT_PERMISSION);
        }
        return Result.o(t4s.deleteByToken(token));
    }

    /**
     * 注销用户token认证
     */
    @GetMapping("logoutToken")
    @Operation(summary = "注销用户token认证")
    @Parameter(name = "token", description = "用户token")
    public Result<Boolean> logoutToken(String token) {
        return Result.o(t4s.deleteByToken(token));
    }

    /**
     * 注销用户id认证(限制)
     */
    @GetMapping("logoutIdLimit")
    @Operation(summary = "注销用户id认证(限制)", description = "需要登录<br>响应：注销成功个数")
    @Parameter(name = "id", description = "用户id")
    public Result<Long> logoutIdLimit(long id) {
        // 只能管理自己创建的用户
        if (!userService.existIdAndCreateId(id, t4s.getId())) {
            return Result.e(ResultEnum.INSUFFICIENT_PERMISSION);
        }
        return Result.o(t4s.deleteById(id));
    }

    /**
     * 注销用户id认证
     */
    @GetMapping("logoutId")
    @Operation(summary = "注销用户id认证", description = "响应：注销成功个数")
    @Parameter(name = "id", description = "用户id")
    public Result<Long> logoutId(long id) {
        return Result.o(t4s.deleteById(id));
    }

    /**
     * 获取用户token认证信息(限制)
     */
    @GetMapping("getInfoByTokenLimit")
    @Operation(summary = "获取用户token认证信息(限制)")
    @Parameter(name = "token", description = "用户token")
    @Parameter(name = "extra", description = "是否查询拓展信息(默认false)")
    public Result<? extends TokenInfo> getInfoByTokenLimit(String token, @RequestParam(required = false, defaultValue = "false") boolean extra) {
        // 只能管理自己创建的用户
        Long id = t4s.getId(token);
        if (id == null) {
            return Result.e(ResultEnum.USER_NOT_LOGIN);
        }
        if (!userService.existIdAndCreateId(id, t4s.getId())) {
            return Result.e(ResultEnum.INSUFFICIENT_PERMISSION);
        }
        if (extra) {
            return Result.o(t4s.getInfoExtraByToken(token));
        } else {
            return Result.o(t4s.getInfoByToken(token));
        }
    }

    /**
     * 获取用户token认证信息
     */
    @GetMapping("getInfoByToken")
    @Operation(summary = "获取用户token认证信息")
    @Parameter(name = "token", description = "用户token")
    @Parameter(name = "extra", description = "是否查询拓展信息(默认false)")
    public Result<? extends TokenInfo> getInfoByToken(String token, @RequestParam(required = false, defaultValue = "false") boolean extra) {
        if (extra) {
            return Result.o(t4s.getInfoExtraByToken(token));
        } else {
            return Result.o(t4s.getInfoByToken(token));
        }
    }

    /**
     * 获取用户id认证信息(限制)
     */
    @GetMapping("getInfoByIdLimit")
    @Operation(summary = "获取用户id认证信息(限制)")
    @Parameter(name = "id", description = "用户id")
    @Parameter(name = "extra", description = "是否查询拓展信息(默认false)")
    public Result<List<? extends TokenInfo>> getInfoByTokenLimit(long id, @RequestParam(required = false, defaultValue = "false") boolean extra) {
        // 只能管理自己创建的用户
        if (!userService.existIdAndCreateId(id, t4s.getId())) {
            return Result.e(ResultEnum.INSUFFICIENT_PERMISSION);
        }
        if (extra) {
            return Result.o(t4s.getInfoExtraById(id));
        } else {
            return Result.o(t4s.getInfoById(id));
        }
    }

    /**
     * 获取用户id认证信息
     */
    @GetMapping("getInfoById")
    @Operation(summary = "获取用户id认证信息")
    @Parameter(name = "id", description = "用户id")
    @Parameter(name = "extra", description = "是否查询拓展信息(默认false)")
    public Result<List<? extends TokenInfo>> getInfoByToken(long id, @RequestParam(required = false, defaultValue = "false") boolean extra) {
        if (extra) {
            return Result.o(t4s.getInfoExtraById(id));
        } else {
            return Result.o(t4s.getInfoById(id));
        }
    }

    /**
     * 获取所有用户认证信息(限制)
     */
    @GetMapping("getInfoLimit")
    @Operation(summary = "获取所有用户认证信息(限制)")
    @Parameter(name = "extra", description = "是否查询拓展信息(默认false)")
    public Result<List<? extends TokenInfo>> getInfoLimit(@RequestParam(required = false, defaultValue = "false") boolean extra) {
        List<Long> userIdList = userService.findIdByCreateId(t4s.getId());
        if (extra) {
            List<TokenInfoExtra> list = new ArrayList<>();
            for (Long userId : userIdList) {
                list.addAll(t4s.getInfoExtraById(userId));
            }
            return Result.o(list);
        } else {
            List<TokenInfo> list = new ArrayList<>();
            for (Long userId : userIdList) {
                list.addAll(t4s.getInfoById(userId));
            }
            return Result.o(list);
        }
    }

    /**
     * 获取所有用户认证信息
     */
    @GetMapping("getInfo")
    @Operation(summary = "获取所有用户认证信息")
    @Parameter(name = "extra", description = "是否查询拓展信息(默认false)")
    public Result<List<? extends TokenInfo>> getInfo(@RequestParam(required = false, defaultValue = "false") boolean extra) {
        if (extra) {
            return Result.o(t4s.getInfoExtra());
        } else {
            return Result.o(t4s.getInfo());
        }
    }

    /**
     * 获取不过期用户认证信息(限制)
     */
    @GetMapping("getInfoPersistLimit")
    @Operation(summary = "获取用户不过期认证信息(限制)")
    @Parameter(name = "extra", description = "是否查询拓展信息(默认false)")
    public Result<List<? extends TokenInfo>> getInfoPersistLimit(@RequestParam(required = false, defaultValue = "false") boolean extra) {
        List<Long> userIdList = userService.findIdByCreateId(t4s.getId());
        if (extra) {
            List<TokenInfoExtra> list = new ArrayList<>();
            List<TokenInfoExtra> persistList = t4s.getInfoExtraPersist();
            for (TokenInfoExtra info : persistList) {
                if (userIdList.contains(info.getId())) {
                    list.add(info);
                }
            }
            return Result.o(list);
        } else {
            List<TokenInfo> list = new ArrayList<>();
            List<TokenInfo> persistList = t4s.getInfoPersist();
            for (TokenInfo info : persistList) {
                if (userIdList.contains(info.getId())) {
                    list.add(info);
                }
            }
            return Result.o(list);
        }
    }

    /**
     * 获取不过期用户认证信息
     */
    @GetMapping("getInfoPersist")
    @Operation(summary = "获取用户不过期认证信息")
    @Parameter(name = "extra", description = "是否查询拓展信息(默认false)")
    public Result<List<? extends TokenInfo>> getInfoPersist(@RequestParam(required = false, defaultValue = "false") boolean extra) {
        if (extra) {
            return Result.o(t4s.getInfoExtraPersist());
        } else {
            return Result.o(t4s.getInfoPersist());
        }
    }

    /**
     * 获取用户登录日志(限制)
     */
    @PostMapping("getLoginLogLimit")
    @Operation(summary = "获取用户登录日志(限制)", description = "需要登录/createId(查询指定用户)")
    public Result<PageInfo<LoginLogVo>> getLoginLogLimit(@RequestBody LoginLogVo loginLog) {
        // 只能管理自己创建的用户
        if (loginLog.getCreateId() == null || !userService.existIdAndCreateId(loginLog.getCreateId(), t4s.getId())) {
            return Result.e(ResultEnum.INSUFFICIENT_PERMISSION);
        }
        return Result.o(loginLogService.findPage(loginLog));
    }

    /**
     * 获取用户登录日志
     */
    @PostMapping("getLoginLog")
    @Operation(summary = "获取用户登录日志")
    public Result<PageInfo<LoginLogVo>> getLoginLog(@RequestBody LoginLogVo loginLog) {
        return Result.o(loginLogService.findPage(loginLog));
    }

}
