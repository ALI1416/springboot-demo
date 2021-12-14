package com.demo.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.z.id.Id;
import com.demo.base.ControllerBase;
import com.demo.constant.ResultCodeEnum;
import com.demo.entity.pojo.Result;
import com.demo.entity.vo.RoleRoute2Vo;
import com.demo.entity.vo.RoleRouteVo;
import com.demo.entity.vo.RoleVo;
import com.demo.entity.vo.UserVo;
import com.demo.interceptor.RouteInterceptor;
import com.demo.service.*;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <h1>RoleController</h1>
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
public class RoleController extends ControllerBase {

    private final RoleService roleService;
    private final RoleRouteService roleRouteService;
    private final RoleRoute2Service roleRoute2Service;
    private final UserService userService;
    private final RouteService routeService;
    private final Route2Service route2Service;
    private final RouteInterceptor routeInterceptor;

    /**
     * 插入
     */
    @PostMapping("insert")
    public Result insert(@RequestBody RoleVo role) {
        if (existNull(role.getName(), role.getSeq())) {
            return paramIsError();
        }
        role.setCreateId(StpUtil.getLoginIdAsLong());
        return Result.o(roleService.insert(role));
    }

    /**
     * 更新
     */
    @PostMapping("update")
    public Result update(@RequestBody RoleVo role) {
        if (isNull(role.getId())) {
            return paramIsError();
        }
        return Result.o(roleService.update(role));
    }

    /**
     * 删除
     */
    @PostMapping("delete")
    public Result delete(@RequestBody RoleVo role) {
        if (isNull(role.getId())) {
            return paramIsError();
        }
        return Result.o(roleService.delete(role.getId()));
    }

    /**
     * 修改路由
     */
    @PostMapping("updateRouteIdList")
    public Result addRouteIdList(@RequestBody RoleVo role) {
        Long roleId = role.getId();
        if (isNull(roleId)) {
            return paramIsError();
        }
        long userId = StpUtil.getLoginIdAsLong();
        // 非root用户
        if (userId != 0) {
            // 只能管理自己创建的角色
            if (!roleService.findByCreateId(userId).stream().map(RoleVo::getId).collect(Collectors.toList()).contains(userId)) {
                return Result.e(ResultCodeEnum.INSUFFICIENT_PERMISSION);
            }
            // 只能管理自己有权限的路由
            if (!routeService.findChildrenIdByUserId(userId).containsAll(role.getRouteIds())) {
                return Result.e(ResultCodeEnum.INSUFFICIENT_PERMISSION);
            }
        }
        List<RoleRouteVo> roleRoutes = new ArrayList<>();
        for (Long id : role.getRouteIds()) {
            RoleRouteVo roleRoute = new RoleRouteVo();
            roleRoute.setId(Id.next());
            roleRoute.setRoleId(roleId);
            roleRoute.setRouteId(id);
            roleRoutes.add(roleRoute);
        }
        return Result.o(roleRouteService.deleteByRoleId(roleId) && roleService.addRouteIdList(roleRoutes));
    }

    /**
     * 修改前端路由
     */
    @PostMapping("updateRoute2IdList")
    public Result addRoute2IdList(@RequestBody RoleVo role) {
        Long roleId = role.getId();
        if (isNull(roleId)) {
            return paramIsError();
        }
        long userId = StpUtil.getLoginIdAsLong();
        // 非root用户
        if (userId != 0) {
            // 只能管理自己创建的角色
            if (!roleService.findByCreateId(userId).stream().map(RoleVo::getId).collect(Collectors.toList()).contains(userId)) {
                return Result.e(ResultCodeEnum.INSUFFICIENT_PERMISSION);
            }
            // 只能管理自己有权限的路由
            if (!route2Service.findChildrenIdByUserId(userId).containsAll(role.getRoute2Ids())) {
                return Result.e(ResultCodeEnum.INSUFFICIENT_PERMISSION);
            }
        }
        List<RoleRoute2Vo> roleRoute2s = new ArrayList<>();
        for (Long id : role.getRoute2Ids()) {
            RoleRoute2Vo roleRoute = new RoleRoute2Vo();
            roleRoute.setId(Id.next());
            roleRoute.setRoleId(roleId);
            roleRoute.setRoute2Id(id);
            roleRoute2s.add(roleRoute);
        }
        return Result.o(roleRoute2Service.deleteByRoleId(roleId) && roleService.addRoute2IdList(roleRoute2s));
    }

    /**
     * 查询所有
     */
    @PostMapping("findAll")
    public Result findAll() {
        return Result.o(roleService.findAll());
    }

    /**
     * 查询UserId拥有的角色
     */
    @PostMapping("findByUserId")
    public Result findByUserId(@RequestBody RoleVo route) {
        return Result.o(roleService.findByUserId(route.getId()));
    }

    /**
     * 查询所有通过CreateId
     */
    @PostMapping("findByCreateId")
    public Result findByCreateId(@RequestBody RoleVo route) {
        return Result.o(roleService.findByCreateId(route.getId()));
    }

    /**
     * 复制
     */
    @PostMapping("copy")
    public Result copy() {
        return Result.o();
    }

    /**
     * 仅刷新修改的角色
     */
    @PostMapping("refreshRole")
    public Result refreshRole(@RequestBody RoleVo role) {
        routeInterceptor.deleteRouteRole(role.getId());
        List<Long> ids =
                userService.findByRoleId(role.getId()).stream().map(UserVo::getId).collect(Collectors.toList());
        routeInterceptor.deleteRouteUser(ids);
        return Result.o();
    }

    /**
     * 刷新全部
     */
    @PostMapping("refresh")
    public Result refresh() {
        routeInterceptor.deleteRouteRole();
        routeInterceptor.deleteRouteUser();
        return Result.o();
    }

}
