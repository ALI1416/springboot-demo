package com.demo.controller;

import com.demo.base.ControllerBase;
import com.demo.entity.pojo.Result;
import com.demo.entity.vo.RouteVo;
import com.demo.service.RouteService;
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
public class RouteController extends ControllerBase {

    private final RouteService routeService;

    /**
     * 新增
     */
    @PostMapping("insert")
    public Result<Long> insert(@RequestBody RouteVo route) {
        if (existNull(route.getPath(), route.getName(), route.getSeq())) {
            return paramIsError();
        }
        return Result.o(routeService.insert(route));
    }

    /**
     * 查询列表
     */
    @GetMapping("findList")
    public Result<List<RouteVo>> findList() {
        return Result.o(routeService.findList());
    }

    /**
     * 查询树
     */
    @GetMapping("findTree")
    public Result<RouteVo> findTree() {
        return Result.o(routeService.findTree());
    }

    /**
     * 查询展开后的列表
     */
    @GetMapping("findExpandedList")
    public Result<RouteVo> findExpandedList() {
        return Result.o(routeService.findExpandedList());
    }

    /**
     * 删除
     */
    @DeleteMapping("delete")
    public Result<Boolean> delete(long id, boolean deleteChildren) {
        if (deleteChildren) {
            // 删除自己和子节点
            return Result.o(routeService.deleteAndChildren(id));
        } else {
            // 删除自己，不删除子节点，移动子节点到上一级
            return Result.o(routeService.deleteAndMoveChildren(id));
        }
    }

    /**
     * 更新
     */
    @PatchMapping("update")
    public Result<Boolean> update(@RequestBody RouteVo route) {
        if (isNull(route.getId()) && !allNull(route.getPath(), route.getName(), route.getSeq())) {
            return paramIsError();
        }
        return Result.o(routeService.update(route));
    }

    /**
     * 查询，通过UserId
     */
    @GetMapping("findByUserId")
    public Result<RouteVo> findByUserId(long id) {
        return Result.o(routeService.findByUserId(id));
    }

    /**
     * 查询id，通过RoleId
     */
    @GetMapping("findIdByRoleId")
    public Result<List<Long>> findIdByRoleId(long id) {
        return Result.o(routeService.findIdByRoleId(id));
    }

    /**
     * TODO 移动该节点
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
    @GetMapping("refresh")
    public Result<Long> refresh() {
        return Result.o(routeService.deleteRoute());
    }

}
