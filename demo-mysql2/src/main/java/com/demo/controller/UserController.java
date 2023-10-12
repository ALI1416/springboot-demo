package com.demo.controller;

import com.demo.base.ControllerBase;
import com.demo.entity.bak.UserBak;
import com.demo.entity.pojo.Result;
import com.demo.entity.pojo.ResultBatch;
import com.demo.entity.vo.UserVo;
import com.demo.service.UserService;
import com.github.pagehelper.PageInfo;
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
@RequestMapping("user")
@AllArgsConstructor
public class UserController extends ControllerBase {

    private final UserService userService;

    /**
     * 插入
     *
     * @param user account,pwd,createId
     * @return ok:id,e:0
     */
    @PostMapping("/insert")
    public Result<Long> insert(@RequestBody UserVo user) {
        return Result.o(userService.insert(user));
    }

    /**
     * 批量插入
     *
     * @param users List account,pwd,createId
     * @return 是否成功
     */
    @PostMapping("/batchInsert")
    public Result<Boolean> batchInsert(@RequestBody List<UserVo> users) {
        return Result.o(userService.batchInsert(users));
    }

    /**
     * 批量插入含详情
     *
     * @param users List account,pwd,createId
     * @return ResultBatch UserVo
     */
    @PostMapping("/batchInsertDetail")
    public Result<ResultBatch<UserVo>> batchInsertDetail(@RequestBody List<UserVo> users) {
        return Result.o(userService.batchInsertDetail(users));
    }

    /**
     * 更新
     *
     * @param user id,updateId;至少一个account,pwd,name,gender,year,profile,comment
     * @return 是否成功
     */
    @PatchMapping("/update")
    public Result<Boolean> update(@RequestBody UserVo user) {
        return Result.o(userService.update(user));
    }

    /**
     * 删除
     *
     * @param user id,updateId
     * @return 是否成功
     */
    @DeleteMapping("/delete")
    public Result<Boolean> delete(@RequestBody UserVo user) {
        return Result.o(userService.delete(user));
    }

    /**
     * 恢复
     *
     * @param user id,updateId
     * @return 是否成功
     */
    @PatchMapping("/restore")
    public Result<Boolean> restore(@RequestBody UserVo user) {
        return Result.o(userService.restore(user));
    }

    /**
     * 是否存在id
     *
     * @param id id
     * @return 是否存在
     */
    @GetMapping("/existId")
    public Result<Boolean> existId(long id) {
        return Result.o(userService.existId(id));
    }

    /**
     * 是否存在account
     *
     * @param account account
     * @return 是否存在
     */
    @GetMapping("/existAccount")
    public Result<Boolean> existAccount(String account) {
        return Result.o(userService.existAccount(account));
    }

    /**
     * 查询通过id
     *
     * @param id id
     * @return UserVo
     */
    @GetMapping("/findById")
    public Result<UserVo> findById(long id) {
        return Result.o(userService.findById(id));
    }

    /**
     * 查询通过account
     *
     * @param account account
     * @return PageInfo UserVo
     */
    @GetMapping("/findByAccount")
    public Result<UserVo> findByAccount(String account) {
        return Result.o(userService.findByAccount(account));
    }

    /**
     * 精确查询
     *
     * @param user UserVo
     * @return PageInfo UserVo
     */
    @PostMapping("/findExact")
    public Result<PageInfo<UserVo>> findExact(@RequestBody UserVo user) {
        return Result.o(userService.findExact(user));
    }

    /**
     * 查询
     *
     * @param user UserVo
     * @return PageInfo UserVo
     */
    @PostMapping("/find")
    public Result<PageInfo<UserVo>> find(@RequestBody UserVo user) {
        return Result.o(userService.find(user));
    }

    /**
     * 查询备份
     *
     * @param userBak UserBak
     * @return PageInfo UserBak
     */
    @PostMapping("/findBak")
    public Result<PageInfo<UserBak>> find(@RequestBody UserBak userBak) {
        return Result.o(userService.findBak(userBak));
    }

}
