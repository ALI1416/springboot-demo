package com.demo.controller;

import cn.z.id.Id;
import com.demo.base.ControllerBase;
import com.demo.entity.pojo.Result;
import com.demo.entity.vo.RouteVo;
import com.demo.service.RouteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "路由")
public class RouteController extends ControllerBase {

    private final RouteService routeService;

    /**
     * 创建路由
     */
    @PostMapping("create")
    @Operation(summary = "创建路由", description = "需要path/name/seq/parentId")
    public Result<Long> create(@RequestBody RouteVo route) {
        if (existNull(route.getPath(), route.getName(), route.getSeq(), route.getParentId())) {
            return paramIsError();
        }
        return Result.o(routeService.insert(route));
    }

    /**
     * 修改路由
     */
    @PatchMapping("update")
    @Operation(summary = "修改路由", description = "需要id 至少一个path/name/seq/parentId")
    public Result<Boolean> update(@RequestBody RouteVo route) {
        if (isNull(route.getId()) && !allNull(route.getPath(), route.getName(), route.getSeq(), route.getParentId())) {
            return paramIsError();
        }
        return Result.o(routeService.update(route));
    }

    /**
     * 删除路由
     */
    @DeleteMapping("delete")
    @Operation(summary = "删除路由")
    @Parameter(name = "id", description = "路由id")
    @Parameter(name = "deleteChild", description = "true删除自己和所有子节点 false删除自己，不删除子节点，移动子节点到上一级")
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
     * 复制到父路由下
     */
    @GetMapping("copy")
    @Operation(summary = "复制到父路由下")
    @Parameter(name = "id", description = "路由id")
    @Parameter(name = "parentId", description = "父id")
    @Parameter(name = "copyChild", description = "true复制自己和所有子节点 false复制自己，不复制子节点")
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
     * 获取用户路由
     */
    @GetMapping("user")
    @Operation(summary = "获取用户路由")
    @Parameter(name = "userId", description = "用户id")
    public Result<RouteVo> user(long userId) {
        return Result.o(routeService.findByUserId(userId));
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
     * 获取路由列表
     */
    @GetMapping("list")
    @Operation(summary = "获取路由列表")
    public Result<RouteVo> list() {
        return Result.o(routeService.findExpandList());
    }

    /**
     * 刷新路由缓存
     */
    @GetMapping("refreshCache")
    @Operation(summary = "刷新路由缓存")
    public Result refreshCache() {
        routeService.deleteRouteCache();
        return Result.o();
    }

    /**
     * 刷新用户的路由缓存
     */
    @GetMapping("refreshRouteCache")
    @Operation(summary = "刷新用户的路由缓存")
    @Parameter(name = "userId", description = "用户id")
    public Result refreshRouteCache(long userId) {
        routeService.deleteRouteUserCache(userId);
        return Result.o();
    }

}
