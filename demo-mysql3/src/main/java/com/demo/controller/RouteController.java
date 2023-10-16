package com.demo.controller;

import cn.z.id.Id;
import com.demo.base.ControllerBase;
import com.demo.entity.pojo.Result;
import com.demo.entity.vo.RouteVo;
import com.demo.service.RouteService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
public class RouteController extends ControllerBase {

    private final RouteService routeService;

    /**
     * 新增
     */
    @PostMapping("insert")
    public Result<Long> insert(@RequestBody RouteVo route) {
        if (existNull(route.getPath(), route.getName(), route.getSeq(), route.getParentId())) {
            return paramIsError();
        }
        return Result.o(routeService.insert(route));
    }

    /**
     * 查询列表
     */
    @GetMapping("getList")
    public Result<List<RouteVo>> getList() {
        return Result.o(routeService.findList());
    }

    /**
     * 查询树
     */
    @GetMapping("getTree")
    public Result<RouteVo> getTree() {
        return Result.o(routeService.findTree());
    }

    /**
     * 查询展开后的列表
     */
    @GetMapping("getExpandedList")
    public Result<RouteVo> getExpandedList() {
        return Result.o(routeService.findExpandedList());
    }

    /**
     * 删除
     */
    @DeleteMapping("delete")
    public Result<Boolean> delete(long id, boolean deleteChild) {
        if (deleteChild) {
            // 删除自己和所有子节点
            return Result.o(routeService.deleteAndChild(id));
        } else {
            // 删除自己，不删除子节点，移动子节点到上一级
            return Result.o(routeService.deleteAndMoveChild(id));
        }
    }

    /**
     * 复制到父id下
     */
    @GetMapping("copy")
    public Result<Boolean> copy(long id, long parentId, boolean copyChild) {
        RouteVo route = routeService.findById(id);
        long newId = Id.next();
        route.setId(newId);
        route.setParentId(parentId);
        if (copyChild) {
            // 复制自己和所有子节点
            List<RouteVo> routeList = new ArrayList<>();
            routeList.add(route);
            List<RouteVo> childList = routeService.findChildByParentId(id);
            for (RouteVo r : childList) {
                r.setId(Id.next());
                r.setParentId(newId);
            }
            routeList.addAll(childList);
            return Result.o(routeService.insertList(routeList));
        } else {
            // 复制自己，不复制子节点
            return Result.o(routeService.insert(route) != 0);
        }
    }

    /**
     * 更新
     */
    @PatchMapping("update")
    public Result<Boolean> update(@RequestBody RouteVo route) {
        if (isNull(route.getId()) && !allNull(route.getPath(), route.getName(), route.getSeq(), route.getParentId())) {
            return paramIsError();
        }
        return Result.o(routeService.update(route));
    }

    /**
     * 查询，通过用户id
     */
    @GetMapping("findByUserId")
    public Result<RouteVo> findByUserId(long id) {
        return Result.o(routeService.findByUserId(id));
    }

    /**
     * 查询id，通过角色id
     */
    @GetMapping("findIdByRoleId")
    public Result<List<Long>> findIdByRoleId(long id) {
        return Result.o(routeService.findIdByRoleId(id));
    }

    /**
     * 刷新
     */
    @GetMapping("refresh")
    public Result<Long> refresh() {
        return Result.o(routeService.deleteRoute());
    }

    /**
     * 刷新，通过用户id
     */
    @GetMapping("refreshRoute")
    public Result<Long> refreshRoute(long userId) {
        return Result.o(routeService.deleteRouteUser(userId));
    }

}
