package com.demo.controller;

import com.demo.base.ControllerBase;
import com.demo.entity.bak.UserBak;
import com.demo.entity.pojo.PageInfo;
import com.demo.entity.pojo.Result;
import com.demo.entity.pojo.ResultBatch;
import com.demo.entity.vo.UserVo;
import com.demo.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <h1>用户</h1>
 *
 * <p>
 * createDate 2021/10/26 15:38:52
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@RestController
@AllArgsConstructor
public class UserController extends ControllerBase {

    private final UserService userService;

    /**
     * 插入<br>
     * http://localhost:8080/insert<br>
     * {"account":"asd","pwd":"zxc","createId":0}
     */
    @PostMapping("insert")
    public Result<Long> insert(@RequestBody UserVo user) {
        return Result.o(userService.insert(user));
    }

    /**
     * 批量插入<br>
     * http://localhost:8080/batchInsert<br>
     * [{"account":"asd","pwd":"zxc","createId":0},{"account":"qwe","pwd":"zxc","createId":0}]
     */
    @PostMapping("batchInsert")
    public Result<Boolean> batchInsert(@RequestBody List<UserVo> userList) {
        return Result.o(userService.batchInsert(userList));
    }

    /**
     * 批量插入返回详情<br>
     * http://localhost:8080/batchInsertDetail<br>
     * [{"account":"asd","pwd":"zxc","createId":0},{"account":"qwe","pwd":"zxc","createId":0}]
     */
    @PostMapping("batchInsertDetail")
    public Result<ResultBatch<UserVo>> batchInsertDetail(@RequestBody List<UserVo> userList) {
        return Result.o(userService.batchInsertDetail(userList));
    }

    /**
     * 更新<br>
     * http://localhost:8080/update<br>
     * {"id":0,"account":"asd4","updateId":0}
     */
    @PatchMapping("update")
    public Result<Boolean> update(@RequestBody UserVo user) {
        return Result.o(userService.update(user));
    }

    /**
     * 删除<br>
     * http://localhost:8080/delete<br>
     * {"id":1,"updateId":0}
     */
    @DeleteMapping("delete")
    public Result<Boolean> delete(@RequestBody UserVo user) {
        return Result.o(userService.delete(user));
    }

    /**
     * 恢复<br>
     * http://localhost:8080/restore<br>
     * {"id":1,"updateId":0}
     */
    @PatchMapping("restore")
    public Result<Boolean> restore(@RequestBody UserVo user) {
        return Result.o(userService.restore(user));
    }

    /**
     * 是否存在id<br>
     * http://localhost:8080/existId?id=0
     */
    @GetMapping("existId")
    public Result<Boolean> existId(long id) {
        return Result.o(userService.existId(id));
    }

    /**
     * 是否存在account<br>
     * http://localhost:8080/existAccount?account=ROOT
     */
    @GetMapping("existAccount")
    public Result<Boolean> existAccount(String account) {
        return Result.o(userService.existAccount(account));
    }

    /**
     * 查询通过id<br>
     * http://localhost:8080/findById?id=0
     */
    @GetMapping("findById")
    public Result<UserVo> findById(long id) {
        return Result.o(userService.findById(id));
    }

    /**
     * 查询通过account<br>
     * http://localhost:8080/findByAccount?account=ROOT
     */
    @GetMapping("findByAccount")
    public Result<UserVo> findByAccount(String account) {
        return Result.o(userService.findByAccount(account));
    }

    /**
     * 分页查询<br>
     * http://localhost:8080/findPage<br>
     * {"pages":1,"rows":2,"createId":0}
     */
    @PostMapping("findPage")
    public Result<PageInfo<UserVo>> findPage(@RequestBody UserVo user) {
        return Result.o(userService.findPage(user));
    }

    /**
     * 分页模糊查询<br>
     * http://localhost:8080/findFuzzyPage<br>
     * {"pages":1,"rows":2,"account":"a"}
     */
    @PostMapping("findFuzzyPage")
    public Result<PageInfo<UserVo>> findFuzzyPage(@RequestBody UserVo user) {
        return Result.o(userService.findFuzzyPage(user));
    }

    /**
     * 分页查询备份<br>
     * http://localhost:8080/findBakPage<br>
     * {"pages":1,"rows":2,"refId":0}
     */
    @PostMapping("findBakPage")
    public Result<PageInfo<UserBak>> findBakPage(@RequestBody UserBak userBak) {
        return Result.o(userService.findBakPage(userBak));
    }

}
