package com.demo.controller;

import cn.z.id.Id;
import cn.z.tinytoken.T4s;
import com.demo.base.ControllerBase;
import com.demo.constant.ResultEnum;
import com.demo.entity.pojo.Result;
import com.demo.entity.vo.RoleRouteVo;
import com.demo.entity.vo.RoleVo;
import com.demo.service.RoleRouteService;
import com.demo.service.RoleService;
import com.demo.service.RouteService;
import com.demo.service.UserService;
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
public class RoleController extends ControllerBase {

    private final T4s t4s;
    private final RoleService roleService;
    private final RoleRouteService roleRouteService;
    private final UserService userService;
    private final RouteService routeService;

    /**
     * 新增
     */
    @PostMapping("insert")
    public Result<Long> insert(@RequestBody RoleVo role) {
        if (existNull(role.getName(), role.getSeq())) {
            return paramIsError();
        }
        role.setCreateId(t4s.getId());
        return Result.o(roleService.insert(role));
    }

    /**
     * 更新
     */
    @PatchMapping("update")
    public Result<Boolean> update(@RequestBody RoleVo role) {
        if (isNull(role.getId()) && !allNull(role.getName(), role.getSeq())) {
            return paramIsError();
        }
        // 只能管理自己创建的角色
        if (!roleService.findExistByIdAndCreateId(role.getId(), t4s.getId())) {
            return Result.e(ResultEnum.INSUFFICIENT_PERMISSION);
        }
        return Result.o(roleService.update(role));
    }

    /**
     * 删除
     */
    @DeleteMapping("delete")
    public Result<Boolean> delete(long id) {
        // 只能管理自己创建的角色
        if (!roleService.findExistByIdAndCreateId(id, t4s.getId())) {
            return Result.e(ResultEnum.INSUFFICIENT_PERMISSION);
        }
        return Result.o(roleService.delete(id));
    }

    /**
     * 修改路由
     */
    @PutMapping("updateRouteIdList")
    public Result<Boolean> updateRouteIdList(@RequestBody RoleVo role) {
        Long roleId = role.getId();
        List<Long> routeIdList = role.getRouteIdList();
        if (existNull(roleId, routeIdList) || routeIdList.isEmpty()) {
            return paramIsError();
        }
        long userId = t4s.getId();
        // 只能管理自己创建的角色 只能管理自己有权限的路由
        if (!roleService.findExistByIdAndCreateId(roleId, userId) && !routeService.findIdAndChildrenIdByUserId(userId).containsAll(routeIdList)) {
            return Result.e(ResultEnum.INSUFFICIENT_PERMISSION);
        }
        List<RoleRouteVo> roleRouteList = new ArrayList<>();
        for (Long id : routeIdList) {
            RoleRouteVo roleRoute = new RoleRouteVo();
            roleRoute.setId(Id.next());
            roleRoute.setRoleId(roleId);
            roleRoute.setRouteId(id);
            roleRouteList.add(roleRoute);
        }
        return Result.o(roleRouteService.deleteByRoleId(roleId) && roleService.addRouteIdList(roleRouteList));
    }

    /**
     * 获取用户id的角色
     */
    @GetMapping("findByUserId")
    public Result<List<RoleVo>> findByUserId(long userId) {
        // 只能管理自己创建的用户
        if (!userService.findExistByIdAndCreateId(userId, t4s.getId())) {
            return Result.e(ResultEnum.INSUFFICIENT_PERMISSION);
        }
        return Result.o(roleService.findByUserId(userId));
    }

    /**
     * 复制角色
     */
    @GetMapping("copy")
    public Result<Long> copy(long id) {
        // 只能管理自己创建的角色
        if (!roleService.findExistByIdAndCreateId(id, t4s.getId())) {
            return Result.e(ResultEnum.INSUFFICIENT_PERMISSION);
        }
        RoleVo role = roleService.findById(id);
        role.setId(Id.next());
        return Result.o(roleService.insert(role));
    }

    /**
     * 刷新，通过角色id
     */
    @GetMapping("refresh")
    public Result<Long> refresh(long id) {
        // 只能管理自己创建的角色
        if (!roleService.findExistByIdAndCreateId(id, t4s.getId())) {
            return Result.e(ResultEnum.INSUFFICIENT_PERMISSION);
        }
        return Result.o(routeService.deleteRouteRole(id) + routeService.deleteRouteUser(userService.findIdByRoleId(id)));
    }

}
