package com.demo.controller;

import com.demo.base.ControllerBase;
import com.demo.base.EntityBase;
import com.demo.entity.pojo.Result;
import com.demo.entity.vo.RouteVo;
import com.demo.interceptor.RouteInterceptor;
import com.demo.service.RouteService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <h1>路由Controller</h1>
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
    private final RouteInterceptor routeInterceptor;

    /**
     * 插入
     */
    @PostMapping("insert")
    public Result insert(@RequestBody RouteVo route) {
        if (existNull(route.getPath(), route.getName(), route.getSeq())) {
            return paramIsError();
        }
        return Result.o(routeService.insert(route));
    }

    /**
     * 查询列表
     */
    @PostMapping("findList")
    public Result findList() {
        return Result.o(routeService.findList());
    }

    /**
     * 查询树
     */
    @PostMapping("findTree")
    public Result findTree() {
        return Result.o(routeService.findTree());
    }

    /**
     * 查询展开后的列表
     */
    @PostMapping("findExpandedList")
    public Result findExpandedList() {
        return Result.o(routeService.findExpandedList());
    }

    /**
     * 删除(deleteChildren是否删除子节点)
     */
    @PostMapping("delete")
    public Result delete(@RequestBody RouteVo route) {
        if (existNull(route.getId(), route.getDeleteChildren())) {
            return paramIsError();
        }
        if (Boolean.TRUE.equals(route.getDeleteChildren())) {
            return Result.o(routeService.deleteWithChildren(route.getId()));
        } else {
            return Result.o(routeService.deleteAndMoveChildren(route.getId()));
        }
    }

    /**
     * 更新
     */
    @PostMapping("update")
    public Result update(@RequestBody RouteVo route) {
        if (isNull(route.getId()) && !allNull(route.getPath(), route.getName(), route.getSeq())) {
            return paramIsError();
        }
        return Result.o(routeService.update(route));
    }

    /**
     * 查询，通过UserId
     */
    @PostMapping("findByUserId")
    public Result findByUserId(@RequestBody EntityBase route) {
        return Result.o(routeService.findByUserId(route.getId()));
    }

    /**
     * 查询id，通过RoleId
     */
    @PostMapping("findIdByRoleId")
    public Result findIdByRoleId(@RequestBody EntityBase route) {
        return Result.o(routeService.findIdByRoleId(route.getId()));
    }

    /**
     * TODO 移动该节点到其他节点(moveId)下
     */
    @PostMapping("move")
    public Result move() {
        return Result.o();
    }

    /**
     * TODO 复制该节点
     */
    @PostMapping("copy")
    public Result copy() {
        return Result.o();
    }

    /**
     * 刷新
     */
    @PostMapping("refresh")
    public Result refresh() {
        routeInterceptor.deleteRoute();
        routeInterceptor.deleteRouteUser();
        return Result.o();
    }

}
