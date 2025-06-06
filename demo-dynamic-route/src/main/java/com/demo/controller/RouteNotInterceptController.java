package com.demo.controller;

import com.demo.base.ControllerBase;
import com.demo.entity.pojo.Result;
import com.demo.entity.vo.RouteNotInterceptVo;
import com.demo.service.RouteNotInterceptService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <h1>路由不拦截</h1>
 *
 * <p>
 * createDate 2021/12/08 13:55:45
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@RestController
@RequestMapping("routeNotIntercept")
@AllArgsConstructor
@Tag(name = "路由不拦截")
public class RouteNotInterceptController extends ControllerBase {

    private final RouteNotInterceptService routeNotInterceptService;

    /**
     * 创建路由不拦截
     */
    @PostMapping("create")
    @Operation(summary = "创建路由不拦截", description = "需要path/name/isMatch/needLogin/seq<br>响应：成功id/失败0")
    public Result<Long> create(@RequestBody RouteNotInterceptVo routeNotIntercept) {
        if (existNull(routeNotIntercept.getPath(), routeNotIntercept.getName(),
                routeNotIntercept.getIsMatch(), routeNotIntercept.getNeedLogin(), routeNotIntercept.getSeq())) {
            return paramError();
        }
        long ok = routeNotInterceptService.insert(routeNotIntercept);
        // 刷新缓存
        if (ok > 0) {
            routeNotInterceptService.refreshCache();
        }
        return Result.o(ok);
    }

    /**
     * 删除路由不拦截
     */
    @DeleteMapping("delete")
    @Operation(summary = "删除路由不拦截")
    @Parameter(name = "id", description = "路由不拦截id")
    public Result<Boolean> delete(long id) {
        boolean ok = routeNotInterceptService.delete(id);
        // 刷新缓存
        if (ok) {
            routeNotInterceptService.refreshCache();
        }
        return Result.o(ok);
    }

    /**
     * 修改路由不拦截
     */
    @PatchMapping("update")
    @Operation(summary = "修改路由不拦截", description = "需要id 至少一个path/name/isMatch/needLogin/seq")
    public Result<Boolean> update(@RequestBody RouteNotInterceptVo routeNotIntercept) {
        if (isNull(routeNotIntercept.getId()) && !allNull(routeNotIntercept.getPath(), routeNotIntercept.getName(),
                routeNotIntercept.getIsMatch(), routeNotIntercept.getNeedLogin(), routeNotIntercept.getSeq())) {
            return paramError();
        }
        boolean ok = routeNotInterceptService.update(routeNotIntercept);
        // 刷新缓存
        if (ok) {
            routeNotInterceptService.refreshCache();
        }
        return Result.o(ok);
    }

    /**
     * 获取路由不拦截
     */
    @GetMapping("get")
    @Operation(summary = "获取路由不拦截")
    public Result<List<RouteNotInterceptVo>> get() {
        return Result.o(routeNotInterceptService.findAll());
    }

    /**
     * 获取路由不拦截列表
     */
    @GetMapping("list")
    @Operation(summary = "获取路由不拦截列表")
    public Result<RouteNotInterceptVo> list() {
        return Result.o(routeNotInterceptService.getLocalCache());
    }

}
