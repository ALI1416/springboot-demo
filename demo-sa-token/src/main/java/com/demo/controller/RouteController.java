package com.demo.controller;

import com.demo.entity.pojo.Result;
import com.demo.entity.vo.RouteVo;
import com.demo.service.RouteService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <h1>RouteController</h1>
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
public class RouteController {

    private final RouteService routeService;

    /**
     * 插入
     *
     * @param route path,name,seq,parentId
     * @return ok:id,e:0
     */
    @PostMapping("insert")
    public Result insert(RouteVo route) {
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
     * 删除
     *
     * @param route id,deleteChildren
     */
    @PostMapping("delete")
    public Result delete(@RequestBody RouteVo route) {
        if (route.getDeleteChildren()) {
            return Result.o(routeService.deleteWithChildren(route.getId()));
        } else {
            return Result.o(routeService.deleteAndMoveChildren(route.getId()));
        }
    }

    /**
     * 更新(Id、parentId除外)
     */
    @PostMapping("update")
    public Result update(@RequestBody RouteVo route) {
        return Result.o(routeService.update(route));
    }

    /**
     * 移动该节点到其他节点(moveId)下
     */
    @PostMapping("move")
    public Result move(@RequestBody RouteVo route) {
        return Result.o();
    }

    /**
     * 复制该节点
     */
    @PostMapping("copy")
    public Result copy(@RequestBody RouteVo route) {
        return Result.o();
    }

    /**
     * 刷新
     */
    @PostMapping("refresh")
    public Result refresh() {
        return Result.o();
    }

}
