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
    @Operation(summary = "创建路由不拦截", description = "需要path/name/isMatch/needLogin/seq")
    public Result<Long> create(@RequestBody RouteNotInterceptVo routeNotIntercept) {
        if (existNull(routeNotIntercept.getPath(), routeNotIntercept.getName(), //
                routeNotIntercept.getIsMatch(), routeNotIntercept.getNeedLogin(), routeNotIntercept.getSeq())) {
            return paramIsError();
        }
        return Result.o(routeNotInterceptService.insert(routeNotIntercept));
    }

    /**
     * 删除路由不拦截
     */
    @DeleteMapping("delete")
    @Operation(summary = "删除路由不拦截")
    @Parameter(name = "id", description = "路由不拦截id")
    public Result<Boolean> delete(long id) {
        return Result.o(routeNotInterceptService.delete(id));
    }

    /**
     * 修改路由不拦截
     */
    @PatchMapping("update")
    @Operation(summary = "修改路由不拦截", description = "需要id 至少一个path/name/isMatch/needLogin/seq")
    public Result<Boolean> update(@RequestBody RouteNotInterceptVo routeNotIntercept) {
        if (isNull(routeNotIntercept.getId()) && !allNull(routeNotIntercept.getPath(), routeNotIntercept.getName(), //
                routeNotIntercept.getIsMatch(), routeNotIntercept.getNeedLogin(), routeNotIntercept.getSeq())) {
            return paramIsError();
        }
        return Result.o(routeNotInterceptService.update(routeNotIntercept));
    }

    /**
     * 获取所有路由不拦截
     */
    @GetMapping("get")
    @Operation(summary = "获取所有路由不拦截")
    public Result<List<RouteNotInterceptVo>> get() {
        return Result.o(routeNotInterceptService.findAll());
    }

    /**
     * 获取缓存路由不拦截
     */
    @GetMapping
    @Operation(summary = "获取缓存路由不拦截")
    public Result<RouteNotInterceptVo> localCache() {
        return Result.o(routeNotInterceptService.getLocalCache());
    }

    /**
     * 刷新缓存路由不拦截
     */
    @GetMapping("refreshCache")
    @Operation(summary = "刷新缓存路由不拦截")
    public Result refreshCache() {
        routeNotInterceptService.refreshCache();
        return Result.o();
    }

}
