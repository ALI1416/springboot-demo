package com.demo.controller;

import cn.z.id.Id;
import cn.z.tinytoken.UserInfo;
import com.demo.base.ControllerBase;
import com.demo.constant.ResultCode;
import com.demo.entity.pojo.Result;
import com.demo.entity.vo.RoleRouteVo;
import com.demo.entity.vo.RoleVo;
import com.demo.service.RoleRouteService;
import com.demo.service.RoleService;
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
 * <h1>角色</h1>
 *
 * <p>
 * createDate 2021/11/29 15:56:18
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@RestController
@RequestMapping("role")
@AllArgsConstructor
@Tag(name = "角色")
public class RoleController extends ControllerBase {

    private final RoleService roleService;
    private final RoleRouteService roleRouteService;
    private final UserService userService;
    private final RouteService routeService;

    /**
     * 创建角色
     */
    @PostMapping("create")
    @Operation(summary = "创建角色", description = "需要登录/name/seq<br>响应：成功id/失败0")
    public Result<Long> create(@RequestBody RoleVo role) {
        if (existNull(role.getName(), role.getSeq())) {
            return paramError();
        }
        role.setCreateId(UserInfo.getId());
        return Result.o(roleService.insert(role));
    }

    /**
     * 删除角色(限制)
     */
    @DeleteMapping("deleteLimit")
    @Operation(summary = "删除角色(限制)", description = "需要登录")
    @Parameter(name = "id", description = "角色id")
    public Result<Boolean> deleteLimit(long id) {
        // 只能管理自己创建的角色
        if (!roleService.existIdAndCreateId(id, UserInfo.getId())) {
            return Result.e(ResultCode.INSUFFICIENT_PERMISSION);
        }
        boolean ok = roleService.delete(id);
        // 刷新缓存
        if (ok) {
            routeService.deleteRoleAndUserCacheByRoleId(id);
        }
        return Result.o(ok);
    }

    /**
     * 删除角色
     */
    @DeleteMapping("delete")
    @Operation(summary = "删除角色")
    @Parameter(name = "id", description = "角色id")
    public Result<Boolean> delete(long id) {
        boolean ok = roleService.delete(id);
        // 刷新缓存
        if (ok) {
            routeService.deleteRoleAndUserCacheByRoleId(id);
        }
        return Result.o(ok);
    }

    /**
     * 修改角色(限制)
     */
    @PatchMapping("updateLimit")
    @Operation(summary = "修改角色(限制)", description = "需要登录/id 至少一个name/seq")
    public Result<Boolean> updateLimit(@RequestBody RoleVo role) {
        if (isNull(role.getId()) && !allNull(role.getName(), role.getSeq())) {
            return paramError();
        }
        // 只能管理自己创建的角色
        if (!roleService.existIdAndCreateId(role.getId(), UserInfo.getId())) {
            return Result.e(ResultCode.INSUFFICIENT_PERMISSION);
        }
        return Result.o(roleService.update(role));
    }

    /**
     * 修改角色
     */
    @PatchMapping("update")
    @Operation(summary = "修改角色", description = "需要id 至少一个name/seq")
    public Result<Boolean> update(@RequestBody RoleVo role) {
        if (isNull(role.getId()) && !allNull(role.getName(), role.getSeq())) {
            return paramError();
        }
        return Result.o(roleService.update(role));
    }

    /**
     * 修改角色的路由(限制)
     */
    @PutMapping("updateRouteLimit")
    @Operation(summary = "修改角色的路由(限制)", description = "需要登录/id/routeIdList")
    public Result<Boolean> updateRouteLimit(@RequestBody RoleVo role) {
        Long roleId = role.getId();
        List<Long> routeIdList = role.getRouteIdList();
        if (existNull(roleId, routeIdList) || routeIdList.isEmpty()) {
            return paramError();
        }
        long userId = UserInfo.getId();
        // 只能管理自己创建的角色 只能管理自己有权限的路由
        if (!roleService.existIdAndCreateId(roleId, userId) && !routeService.findIdAndChildIdByUserId(userId).containsAll(routeIdList)) {
            return Result.e(ResultCode.INSUFFICIENT_PERMISSION);
        }
        List<RoleRouteVo> roleRouteList = new ArrayList<>();
        for (Long id : routeIdList) {
            RoleRouteVo roleRoute = new RoleRouteVo();
            roleRoute.setId(Id.next());
            roleRoute.setRoleId(roleId);
            roleRoute.setRouteId(id);
            roleRouteList.add(roleRoute);
        }
        boolean ok = roleRouteService.deleteByRoleId(roleId) && roleService.addRouteIdList(roleRouteList);
        // 刷新缓存
        if (ok) {
            routeService.deleteRoleAndUserCacheByRoleId(roleId);
        }
        return Result.o(ok);
    }

    /**
     * 修改角色的路由
     */
    @PutMapping("updateRoute")
    @Operation(summary = "修改角色的路由", description = "需要id/routeIdList")
    public Result<Boolean> updateRoute(@RequestBody RoleVo role) {
        Long roleId = role.getId();
        List<Long> routeIdList = role.getRouteIdList();
        if (existNull(roleId, routeIdList) || routeIdList.isEmpty()) {
            return paramError();
        }
        List<RoleRouteVo> roleRouteList = new ArrayList<>();
        for (Long id : routeIdList) {
            RoleRouteVo roleRoute = new RoleRouteVo();
            roleRoute.setId(Id.next());
            roleRoute.setRoleId(roleId);
            roleRoute.setRouteId(id);
            roleRouteList.add(roleRoute);
        }
        boolean ok = roleRouteService.deleteByRoleId(roleId) && roleService.addRouteIdList(roleRouteList);
        // 刷新缓存
        if (ok) {
            routeService.deleteRoleAndUserCacheByRoleId(roleId);
        }
        return Result.o(ok);
    }

    /**
     * 获取所有角色(限制)
     */
    @GetMapping("getLimit")
    @Operation(summary = "获取所有角色(限制)", description = "需要登录")
    public Result<List<RoleVo>> getLimit() {
        return Result.o(roleService.findByCreateId(UserInfo.getId()));
    }

    /**
     * 获取所有角色
     */
    @GetMapping("get")
    @Operation(summary = "获取所有角色")
    public Result<List<RoleVo>> get() {
        return Result.o(roleService.findAll());
    }

    /**
     * 获取用户的角色(限制)
     */
    @GetMapping("userLimit")
    @Operation(summary = "获取用户的角色(限制)", description = "需要登录")
    @Parameter(name = "userId", description = "用户id")
    public Result<List<RoleVo>> userLimit(long userId) {
        // 只能管理自己创建的用户
        if (!userService.existIdAndCreateId(userId, UserInfo.getId())) {
            return Result.e(ResultCode.INSUFFICIENT_PERMISSION);
        }
        return Result.o(roleService.findByUserId(userId));
    }

    /**
     * 获取用户的角色
     */
    @GetMapping("user")
    @Operation(summary = "获取用户的角色")
    @Parameter(name = "userId", description = "用户id")
    public Result<List<RoleVo>> user(long userId) {
        if (!userService.existId(userId)) {
            return Result.e(ResultCode.USER_NOT_EXIST);
        }
        return Result.o(roleService.findByUserId(userId));
    }

}
