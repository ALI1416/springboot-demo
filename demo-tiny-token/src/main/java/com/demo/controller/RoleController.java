package com.demo.controller;

import cn.z.id.Id;
import cn.z.tinytoken.T4s;
import com.demo.base.ControllerBase;
import com.demo.base.EntityBase;
import com.demo.constant.ResultEnum;
import com.demo.entity.pojo.Result;
import com.demo.entity.vo.RoleRouteVo;
import com.demo.entity.vo.RoleVo;
import com.demo.entity.vo.UserVo;
import com.demo.service.RoleRouteService;
import com.demo.service.RoleService;
import com.demo.service.RouteService;
import com.demo.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    @PostMapping("update")
    public Result<Boolean> update(@RequestBody RoleVo role) {
        if (isNull(role.getId()) && !allNull(role.getName(), role.getSeq())) {
            return paramIsError();
        }
        return Result.o(roleService.update(role));
    }

    /**
     * 删除
     */
    @PostMapping("delete")
    public Result<Boolean> delete(@RequestBody EntityBase role) {
        if (isNull(role.getId())) {
            return paramIsError();
        }
        return Result.o(roleService.delete(role.getId()));
    }

    /**
     * 修改路由
     */
    @PostMapping("updateRouteIdList")
    public Result<Boolean> updateRouteIdList(@RequestBody RoleVo role) {
        Long roleId = role.getId();
        List<Long> routeIds = role.getRouteIds();
        if (existNull(roleId, routeIds) || routeIds.isEmpty()) {
            return paramIsError();
        }
        long userId = t4s.getId();
        // 非root用户
        if (userId != 0) {
            // 只能管理自己创建的角色
            if (!roleService.findByCreateId(userId).stream().map(RoleVo::getId).collect(Collectors.toList()).contains(userId)) {
                return Result.e(ResultEnum.INSUFFICIENT_PERMISSION);
            }
            // 只能管理自己有权限的路由
            if (!routeService.findChildrenIdByUserId(userId).containsAll(routeIds)) {
                return Result.e(ResultEnum.INSUFFICIENT_PERMISSION);
            }
        }
        List<RoleRouteVo> roleRoutes = new ArrayList<>();
        for (Long id : routeIds) {
            RoleRouteVo roleRoute = new RoleRouteVo();
            roleRoute.setId(Id.next());
            roleRoute.setRoleId(roleId);
            roleRoute.setRouteId(id);
            roleRoutes.add(roleRoute);
        }
        return Result.o(roleRouteService.deleteByRoleId(roleId) && roleService.addRouteIdList(roleRoutes));
    }

    /**
     * 查询所有
     */
    @PostMapping("findAll")
    public Result<List<RoleVo>> findAll() {
        return Result.o(roleService.findAll());
    }

    /**
     * 查询，通过UserId
     */
    @PostMapping("findByUserId")
    public Result<List<RoleVo>> findByUserId(@RequestBody EntityBase route) {
        return Result.o(roleService.findByUserId(route.getId()));
    }

    /**
     * 查询，通过CreateId
     */
    @PostMapping("findByCreateId")
    public Result<List<RoleVo>> findByCreateId(@RequestBody EntityBase route) {
        return Result.o(roleService.findByCreateId(route.getId()));
    }

    /**
     * TODO 复制该节点
     */
    @PostMapping("copy")
    public Result copy() {
        return Result.o();
    }

    /**
     * 刷新，通过RoleId
     */
    @PostMapping("refreshRole")
    public Result<Long> refreshRole(@RequestBody EntityBase role) {
        List<Long> ids = userService.findByRoleId(role.getId()).stream().map(UserVo::getId).collect(Collectors.toList());
        return Result.o(routeService.deleteRouteRole(role.getId()) + routeService.deleteRouteUser(ids));
    }

}
