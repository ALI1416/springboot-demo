package com.demo.controller;

import com.demo.base.ControllerBase;
import com.demo.entity.pojo.Result;
import com.demo.entity.vo.Route2NotInterceptVo;
import com.demo.service.Route2NotInterceptService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <h1>Route2NotInterceptController</h1>
 *
 * <p>
 * createDate 2021/12/08 13:55:45
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@RestController
@RequestMapping("route2NotIntercept")
@AllArgsConstructor
public class Route2NotInterceptController extends ControllerBase {

    private final Route2NotInterceptService route2NotInterceptService;

    /**
     * 查询所有
     */
    @PostMapping("findAll")
    public Result findAll() {
        return Result.o(route2NotInterceptService.findAll());
    }

    /**
     * 插入
     */
    @PostMapping("insert")
    public Result insert(@RequestBody Route2NotInterceptVo route2NotIntercept) {
        if (existNull(route2NotIntercept.getPages(), route2NotIntercept.getName(), route2NotIntercept.getSeq(),
                route2NotIntercept.getComponent(), route2NotIntercept.getRedirect(), route2NotIntercept.getMeta())) {
            return paramIsError();
        }
        return Result.o(route2NotInterceptService.insert(route2NotIntercept));
    }

    /**
     * 删除
     */
    @PostMapping("delete")
    public Result delete(@RequestBody Route2NotInterceptVo route2NotIntercept) {
        if (isNull(route2NotIntercept.getId())) {
            return paramIsError();
        }
        return Result.o(route2NotInterceptService.delete(route2NotIntercept.getId()));
    }

    /**
     * 更新
     */
    @PostMapping("update")
    public Result update(@RequestBody Route2NotInterceptVo route2NotIntercept) {
        if (isNull(route2NotIntercept.getId())) {
            return paramIsError();
        }
        return Result.o(route2NotInterceptService.update(route2NotIntercept));
    }

}
