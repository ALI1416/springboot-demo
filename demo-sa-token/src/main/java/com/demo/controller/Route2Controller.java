package com.demo.controller;

import com.demo.base.ControllerBase;
import com.demo.entity.pojo.Result;
import com.demo.entity.vo.Route2Vo;
import com.demo.entity.vo.RouteVo;
import com.demo.service.Route2Service;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <h1>Route2Controller</h1>
 *
 * <p>
 * createDate 2021/11/26 14:05:46
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@RestController
@RequestMapping("route2")
@AllArgsConstructor
public class Route2Controller extends ControllerBase {

    private final Route2Service route2Service;

    /**
     * 插入
     */
    @PostMapping("insert")
    public Result insert(@RequestBody Route2Vo route2) {
        if (existNull(route2.getPath(), route2.getName(), route2.getSeq(), route2.getComponent(),
                route2.getRedirect(), route2.getMeta())) {
            return paramIsError();
        }
        return Result.o(route2Service.insert(route2));
    }

    /**
     * 查询列表
     */
    @PostMapping("findList")
    public Result findList() {
        return Result.o(route2Service.findList());
    }

    /**
     * 查询树
     */
    @PostMapping("findTree")
    public Result findTree() {
        return Result.o(route2Service.findTree());
    }

    /**
     * 查询展开后的列表
     */
    @PostMapping("findExpandedList")
    public Result findExpandedList() {
        return Result.o(route2Service.findExpandedList());
    }

    /**
     * 删除(deleteChildren是否删除子节点)
     */
    @PostMapping("delete")
    public Result delete(@RequestBody Route2Vo route2) {
        if (existNull(route2.getId(), route2.getDeleteChildren())) {
            return paramIsError();
        }
        if (route2.getDeleteChildren()) {
            return Result.o(route2Service.deleteWithChildren(route2.getId()));
        } else {
            return Result.o(route2Service.deleteAndMoveChildren(route2.getId()));
        }
    }

    /**
     * 更新
     */
    @PostMapping("update")
    public Result update(@RequestBody Route2Vo route2) {
        if (isNull(route2.getId())) {
            return paramIsError();
        }
        return Result.o(route2Service.update(route2));
    }

    /**
     * 查询UserId拥有的路由
     */
    @PostMapping("findByUserId")
    public Result findByUserId(@RequestBody Route2Vo route2) {
        return Result.o(route2Service.findByUserId(route2.getId()));
    }

    /**
     * 查询全部id，通过RoleId
     */
    @PostMapping("findIdByRoleId")
    public Result findIdByRoleId(@RequestBody RouteVo route) {
        return Result.o(route2Service.findIdByRoleId(route.getId()));
    }

    /**
     * 移动该节点到其他节点(moveId)下
     */
    @PostMapping("move")
    public Result move() {
        return Result.o();
    }

    /**
     * 复制该节点
     */
    @PostMapping("copy")
    public Result copy() {
        return Result.o();
    }

}
