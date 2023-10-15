package com.demo.controller.root;

import cn.z.id.Id;
import cn.z.tinytoken.T4s;
import com.demo.base.ControllerBase;
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
 * createDate 2023/10/13 17:44:14
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@RestController
@RequestMapping("root/role")
@AllArgsConstructor
public class RootRoleController extends ControllerBase {

    private final T4s t4s;
    private final RoleService roleService;
    private final RoleRouteService roleRouteService;
    private final RouteService routeService;
    private final UserService userService;

    /**
     * 更新
     */
    @PatchMapping("update")
    public Result<Boolean> update(@RequestBody RoleVo role) {
        if (isNull(role.getId()) && !allNull(role.getName(), role.getSeq())) {
            return paramIsError();
        }
        return Result.o(roleService.update(role));
    }

    /**
     * 删除
     */
    @DeleteMapping("delete")
    public Result<Boolean> delete(long id) {
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
     * 查询全部
     */
    @GetMapping("getAll")
    public Result<List<RoleVo>> getAll() {
        return Result.o(roleService.findAll());
    }

    /**
     * 获取用户id的角色
     */
    @GetMapping("findByUserId")
    public Result<List<RoleVo>> findByUserId(long userId) {
        return Result.o(roleService.findByUserId(userId));
    }

    /**
     * 复制角色
     */
    @GetMapping("copy")
    public Result<Long> copy(long id) {
        RoleVo role = roleService.findById(id);
        role.setId(Id.next());
        role.setCreateId(t4s.getId());
        return Result.o(roleService.insert(role));
    }

    /**
     * 刷新，通过角色id
     */
    @GetMapping("refresh")
    public Result<Long> refresh(long id) {
        return Result.o(routeService.deleteRouteRole(id) + routeService.deleteRouteUser(userService.findIdByRoleId(id)));
    }

}
