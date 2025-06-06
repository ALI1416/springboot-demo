package com.demo.controller;

import com.demo.base.ControllerBase;
import com.demo.constant.ResultCode;
import com.demo.entity.pojo.Result;
import com.demo.entity.vo.RouteVo;
import com.demo.service.RoleService;
import com.demo.service.RouteService;
import com.demo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <h1>路由</h1>
 *
 * <p>
 * createDate 2021/11/26 14:05:46
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@RestController
@RequestMapping("route")
@AllArgsConstructor
@Tag(name = "路由")
public class RouteController extends ControllerBase {

    private final UserService userService;
    private final RoleService roleService;
    private final RouteService routeService;

    /**
     * 创建路由
     */
    @PostMapping("create")
    @Operation(summary = "创建路由", description = "需要path/name/seq/parentId<br>响应：成功id/失败0")
    public Result<Long> create(@RequestBody RouteVo route) {
        if (existNull(route.getPath(), route.getName(), route.getSeq(), route.getParentId())) {
            return paramError();
        }
        long ok = routeService.insert(route);
        // 刷新缓存
        if (ok > 0) {
            routeService.deleteRouteCache();
        }
        return Result.o(ok);
    }

    /**
     * 修改路由
     */
    @PatchMapping("update")
    @Operation(summary = "修改路由", description = "需要id 至少一个path/name/seq/parentId")
    public Result<Boolean> update(@RequestBody RouteVo route) {
        if (isNull(route.getId()) && !allNull(route.getPath(), route.getName(), route.getSeq(), route.getParentId())) {
            return paramError();
        }
        boolean ok = routeService.update(route);
        // 刷新缓存
        if (ok) {
            routeService.deleteRouteCache();
        }
        return Result.o(ok);
    }

    /**
     * 删除路由和子路由
     */
    @DeleteMapping("delete")
    @Operation(summary = "删除路由和子路由")
    @Parameter(name = "id", description = "路由id")
    public Result<Boolean> delete(long id) {
        boolean ok = routeService.deleteAndChild(id);
        // 刷新缓存
        if (ok) {
            routeService.deleteRouteCache();
        }
        return Result.o(ok);
    }

    /**
     * 获取角色路由
     */
    @GetMapping("role")
    @Operation(summary = "获取角色路由")
    @Parameter(name = "roleId", description = "角色id")
    public Result<RouteVo> role(long roleId) {
        if (!roleService.existId(roleId)) {
            return Result.e(ResultCode.ROLE_NOT_EXIST);
        }
        return Result.o(routeService.findByRoleId(roleId));
    }

    /**
     * 获取用户路由
     */
    @GetMapping("user")
    @Operation(summary = "获取用户路由")
    @Parameter(name = "userId", description = "用户id")
    public Result<RouteVo> user(long userId) {
        if (!userService.existId(userId)) {
            return Result.e(ResultCode.USER_NOT_EXIST);
        }
        return Result.o(routeService.findByUserId(userId));
    }

    /**
     * 获取路由列表
     */
    @GetMapping("get")
    @Operation(summary = "获取路由列表")
    public Result<List<RouteVo>> get() {
        return Result.o(routeService.findAll());
    }

    /**
     * 获取路由树
     */
    @GetMapping("tree")
    @Operation(summary = "获取路由树")
    public Result<RouteVo> tree() {
        return Result.o(routeService.findTree());
    }

    /**
     * 获取展开后的路由列表
     */
    @GetMapping("list")
    @Operation(summary = "获取展开后的路由列表")
    public Result<RouteVo> list() {
        return Result.o(routeService.findExpandList());
    }

}
