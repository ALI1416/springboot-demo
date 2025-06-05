package com.demo.controller;

import cn.z.tinytoken.T4s;
import cn.z.tinytoken.UserInfo;
import cn.z.tinytoken.entity.TokenInfo;
import cn.z.tinytoken.entity.TokenInfoExtra;
import com.demo.base.ControllerBase;
import com.demo.constant.ResultCode;
import com.demo.entity.pojo.PageInfo;
import com.demo.entity.pojo.Result;
import com.demo.entity.vo.LoginLogVo;
import com.demo.entity.vo.UserVo;
import com.demo.service.LoginLogService;
import com.demo.service.RoleService;
import com.demo.service.RouteService;
import com.demo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
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
    private final RoleService roleService;
    private final RouteService routeService;
    private final LoginLogService loginLogService;

    /**
     * 创建用户
     */
    @PostMapping("create")
    @Operation(summary = "创建用户", description = "需要登录/account/name/password<br>响应：成功id/失败0")
    public Result<Long> create(@RequestBody UserVo user) {
        if (existNull(user.getAccount(), user.getName(), user.getPassword())) {
            return paramError();
        }
        user.setCreateId(UserInfo.getId());
        if (userService.existAccount(user.getAccount())) {
            return Result.e(ResultCode.ACCOUNT_EXIST);
        }
        return Result.o(userService.register(user));
    }

    /**
     * 修改用户信息(限制)
     */
    @PatchMapping("updateLimit")
    @Operation(summary = "修改用户信息(限制)", description = "需要登录/id 至少一个account/name/password/isDelete")
    public Result<Boolean> updateLimit(@RequestBody UserVo user) {
        if (isNull(user.getId()) && !allNull(user.getName(), user.getAccount(), user.getPassword(), user.getIsDelete())) {
            return paramError();
        }
        // 只能管理自己创建的用户
        if (!userService.existIdAndCreateId(user.getId(), UserInfo.getId())) {
            return Result.e(ResultCode.INSUFFICIENT_PERMISSION);
        }
        if (user.getAccount() != null && userService.existAccount(user.getAccount())) {
            return Result.e(ResultCode.ACCOUNT_EXIST);
        }
        return Result.o(userService.update(user));
    }

    /**
     * 修改用户信息
     */
    @PatchMapping("update")
    @Operation(summary = "修改用户信息", description = "需要id 至少一个account/name/password/isDelete")
    public Result<Boolean> update(@RequestBody UserVo user) {
        if (isNull(user.getId()) && !allNull(user.getName(), user.getAccount(), user.getPassword(), user.getIsDelete())) {
            return paramError();
        }
        if (user.getAccount() != null && userService.existAccount(user.getAccount())) {
            return Result.e(ResultCode.ACCOUNT_EXIST);
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
            return paramError();
        }
        // 只能管理自己创建的用户
        if (!userService.existIdAndCreateId(user.getId(), UserInfo.getId())) {
            return Result.e(ResultCode.INSUFFICIENT_PERMISSION);
        }
        // 只能分配自己拥有的角色
        if (!new HashSet<>(user.getRoleIdList()).containsAll(roleService.findIdByUserId(UserInfo.getId()))) {
            return Result.e(ResultCode.INSUFFICIENT_PERMISSION);
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
            return paramError();
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
        return Result.o(userService.findByRoleIdAndCreateId(roleId, UserInfo.getId(), pages, rows, orderBy));
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
        return Result.o(userService.findByCreateId(UserInfo.getId(), pages, rows, orderBy));
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
            return Result.e(ResultCode.USER_NOT_LOGIN);
        }
        if (!userService.existIdAndCreateId(id, UserInfo.getId())) {
            return Result.e(ResultCode.INSUFFICIENT_PERMISSION);
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
        if (!userService.existIdAndCreateId(id, UserInfo.getId())) {
            return Result.e(ResultCode.INSUFFICIENT_PERMISSION);
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
    @Parameter(name = "extra", description = "是否查询拓展信息(默认:false)")
    public Result<? extends TokenInfo> getInfoByTokenLimit(String token, @RequestParam(required = false, defaultValue = "false") boolean extra) {
        // 只能管理自己创建的用户
        Long id = t4s.getId(token);
        if (id == null) {
            return Result.e(ResultCode.USER_NOT_LOGIN);
        }
        if (!userService.existIdAndCreateId(id, UserInfo.getId())) {
            return Result.e(ResultCode.INSUFFICIENT_PERMISSION);
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
    @Parameter(name = "extra", description = "是否查询拓展信息(默认:false)")
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
    @Parameter(name = "extra", description = "是否查询拓展信息(默认:false)")
    public Result<List<? extends TokenInfo>> getInfoByTokenLimit(long id, @RequestParam(required = false, defaultValue = "false") boolean extra) {
        // 只能管理自己创建的用户
        if (!userService.existIdAndCreateId(id, UserInfo.getId())) {
            return Result.e(ResultCode.INSUFFICIENT_PERMISSION);
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
    @Parameter(name = "extra", description = "是否查询拓展信息(默认:false)")
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
    @Parameter(name = "extra", description = "是否查询拓展信息(默认:false)")
    public Result<List<? extends TokenInfo>> getInfoLimit(@RequestParam(required = false, defaultValue = "false") boolean extra) {
        List<Long> userIdList = userService.findIdByCreateId(UserInfo.getId());
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
    @Parameter(name = "extra", description = "是否查询拓展信息(默认:false)")
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
    @Parameter(name = "extra", description = "是否查询拓展信息(默认:false)")
    public Result<List<? extends TokenInfo>> getInfoPersistLimit(@RequestParam(required = false, defaultValue = "false") boolean extra) {
        List<Long> userIdList = userService.findIdByCreateId(UserInfo.getId());
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
    @Parameter(name = "extra", description = "是否查询拓展信息(默认:false)")
    public Result<List<? extends TokenInfo>> getInfoPersist(@RequestParam(required = false, defaultValue = "false") boolean extra) {
        if (extra) {
            return Result.o(t4s.getInfoExtraPersist());
        } else {
            return Result.o(t4s.getInfoPersist());
        }
    }

    /**
     * 设置用户token过期时间(限制)
     */
    @GetMapping("setExpireByTokenLimit")
    @Operation(summary = "设置用户token过期时间(限制)")
    @Parameter(name = "token", description = "用户token")
    @Parameter(name = "timeout", description = "过期时间(默认:默认值)")
    public Result<Boolean> setExpireByTokenLimit(String token, @RequestParam(required = false, defaultValue = "-1") long timeout) {
        // 只能管理自己创建的用户
        Long id = t4s.getId(token);
        if (id == null) {
            return Result.e(ResultCode.USER_NOT_LOGIN);
        }
        if (!userService.existIdAndCreateId(id, UserInfo.getId())) {
            return Result.e(ResultCode.INSUFFICIENT_PERMISSION);
        }
        if (timeout == -1) {
            return Result.o(t4s.expire(token));
        } else {
            return Result.o(t4s.expire(token, timeout));
        }
    }

    /**
     * 设置用户token过期时间
     */
    @GetMapping("setExpireByToken")
    @Operation(summary = "设置用户token过期时间")
    @Parameter(name = "token", description = "用户token")
    @Parameter(name = "timeout", description = "过期时间(默认:默认值)")
    public Result<Boolean> setExpireByToken(String token, @RequestParam(required = false, defaultValue = "-1") long timeout) {
        if (timeout == -1) {
            return Result.o(t4s.expire(token));
        } else {
            return Result.o(t4s.expire(token, timeout));
        }
    }

    /**
     * 设置用户token永不过期(限制)
     */
    @GetMapping("setPersistByTokenLimit")
    @Operation(summary = "设置用户token永不过期(限制)")
    @Parameter(name = "token", description = "用户token")
    public Result<Boolean> setPersistByTokenLimit(String token) {
        // 只能管理自己创建的用户
        Long id = t4s.getId(token);
        if (id == null) {
            return Result.e(ResultCode.USER_NOT_LOGIN);
        }
        if (!userService.existIdAndCreateId(id, UserInfo.getId())) {
            return Result.e(ResultCode.INSUFFICIENT_PERMISSION);
        }
        return Result.o(t4s.persist(token));
    }

    /**
     * 设置用户token永不过期
     */
    @GetMapping("setPersistByToken")
    @Operation(summary = "设置用户token永不过期")
    @Parameter(name = "token", description = "用户token")
    public Result<Boolean> setPersistByToken(String token) {
        return Result.o(t4s.persist(token));
    }

    /**
     * 设置用户token拓展内容(限制)
     */
    @GetMapping("setExtraByTokenLimit")
    @Operation(summary = "设置用户token拓展内容(限制)")
    @Parameter(name = "token", description = "用户token")
    @Parameter(name = "extra", description = "拓展内容(默认:清除)")
    public Result<Boolean> setExtraByTokenLimit(String token, @RequestParam(required = false, defaultValue = "") String extra) {
        // 只能管理自己创建的用户
        Long id = t4s.getId(token);
        if (id == null) {
            return Result.e(ResultCode.USER_NOT_LOGIN);
        }
        if (!userService.existIdAndCreateId(id, UserInfo.getId())) {
            return Result.e(ResultCode.INSUFFICIENT_PERMISSION);
        }
        if (extra.isEmpty()) {
            return Result.o(t4s.clearExtra(token));
        } else {
            return Result.o(t4s.setExtra(token, extra));
        }
    }

    /**
     * 设置用户token拓展内容
     */
    @GetMapping("setExtraByToken")
    @Operation(summary = "设置用户token拓展内容")
    @Parameter(name = "token", description = "用户token")
    @Parameter(name = "extra", description = "拓展内容(默认:清除)")
    public Result<Boolean> setExtraByToken(String token, @RequestParam(required = false, defaultValue = "") String extra) {
        if (extra.isEmpty()) {
            return Result.o(t4s.clearExtra(token));
        } else {
            return Result.o(t4s.setExtra(token, extra));
        }
    }

    /**
     * 获取用户登录日志(限制)
     */
    @PostMapping("getLoginLogLimit")
    @Operation(summary = "获取用户登录日志(限制)", description = "需要登录/createId(查询指定用户)")
    public Result<PageInfo<LoginLogVo>> getLoginLogLimit(@RequestBody LoginLogVo loginLog) {
        // 只能管理自己创建的用户
        if (loginLog.getCreateId() == null || !userService.existIdAndCreateId(loginLog.getCreateId(), UserInfo.getId())) {
            return Result.e(ResultCode.INSUFFICIENT_PERMISSION);
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
