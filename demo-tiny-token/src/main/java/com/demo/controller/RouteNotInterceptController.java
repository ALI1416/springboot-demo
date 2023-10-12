package com.demo.controller;

import com.demo.base.ControllerBase;
import com.demo.entity.pojo.Result;
import com.demo.entity.vo.RouteNotInterceptVo;
import com.demo.service.RouteNotInterceptService;
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
public class RouteNotInterceptController extends ControllerBase {

    private final RouteNotInterceptService routeNotInterceptService;

    /**
     * 新增
     */
    @PostMapping("insert")
    public Result<Long> insert(@RequestBody RouteNotInterceptVo routeNotIntercept) {
        if (existNull(routeNotIntercept.getPath(), routeNotIntercept.getName(),
                routeNotIntercept.getIsMatch(), routeNotIntercept.getNeedLogin(), routeNotIntercept.getSeq())) {
            return paramIsError();
        }
        return Result.o(routeNotInterceptService.insert(routeNotIntercept));
    }

    /**
     * 删除
     */
    @DeleteMapping("delete")
    public Result<Boolean> delete(long id) {
        return Result.o(routeNotInterceptService.delete(id));
    }

    /**
     * 更新
     */
    @PatchMapping("update")
    public Result<Boolean> update(@RequestBody RouteNotInterceptVo routeNotIntercept) {
        if (isNull(routeNotIntercept.getId()) && !allNull(routeNotIntercept.getPath(), routeNotIntercept.getName(),
                routeNotIntercept.getIsMatch(), routeNotIntercept.getNeedLogin(), routeNotIntercept.getSeq())) {
            return paramIsError();
        }
        return Result.o(routeNotInterceptService.update(routeNotIntercept));
    }

    /**
     * 查询所有
     */
    @GetMapping("findAll")
    public Result<List<RouteNotInterceptVo>> findAll() {
        return Result.o(routeNotInterceptService.findAll());
    }

    /**
     * 刷新
     */
    @GetMapping("refresh")
    public Result refresh() {
        routeNotInterceptService.refresh();
        return Result.o();
    }

}
