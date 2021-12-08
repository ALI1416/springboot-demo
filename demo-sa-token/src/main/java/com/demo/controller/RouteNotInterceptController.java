package com.demo.controller;

import com.demo.entity.pojo.Result;
import com.demo.entity.vo.RouteNotInterceptVo;
import com.demo.service.RouteNotInterceptService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <h1>RouteNotInterceptController</h1>
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
public class RouteNotInterceptController {

    private final RouteNotInterceptService routeNotInterceptService;

    /**
     * 查询所有路由不拦截
     */
    @PostMapping("findAll")
    public Result findAll() {
        return Result.o(routeNotInterceptService.findAll());
    }

    /**
     * 插入
     */
    @PostMapping("insert")
    public Result insert(@RequestBody RouteNotInterceptVo routeNotIntercept) {
        return Result.o(routeNotInterceptService.insert(routeNotIntercept));
    }

    /**
     * 删除
     */
    @PostMapping("delete")
    public Result delete(@RequestBody RouteNotInterceptVo routeNotIntercept) {
        return Result.o(routeNotInterceptService.delete(routeNotIntercept.getId()));
    }

    /**
     * 更新
     */
    @PostMapping("update")
    public Result update(@RequestBody RouteNotInterceptVo routeNotIntercept) {
        return Result.o(routeNotInterceptService.update(routeNotIntercept));
    }


}
