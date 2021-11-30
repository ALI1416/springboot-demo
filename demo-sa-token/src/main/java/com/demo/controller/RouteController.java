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
     * 插入节点
     */
    @PostMapping("insert")
    public Result insert(@RequestBody RouteVo route) {
        return Result.o();
    }

    /**
     * 删除(deleteChildren可选择删除子节点和移动子节点到父节点)
     */
    @PostMapping("delete")
    public Result delete(@RequestBody RouteVo route) {
        return Result.o();
    }

    /**
     * 更新(Id、parentId除外)
     */
    @PostMapping("update")
    public Result update(@RequestBody RouteVo route) {
        return Result.o();
    }

    /**
     * 移动该节点到其他节点(moveId)下
     */
    @PostMapping("move")
    public Result move(@RequestBody RouteVo route) {
        return Result.o();
    }

    /**
     * 刷新
     */
    @PostMapping("refresh")
    public Result refresh() {
        return Result.o();
    }

    /**
     * 查询全部通过UserId
     */
    @PostMapping("findByUserId")
    public Result findByUserId(Long id) {
        return Result.o(routeService.findByUserId(id));
    }

}
