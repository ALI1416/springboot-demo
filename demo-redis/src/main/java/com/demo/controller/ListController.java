package com.demo.controller;

import com.demo.entity.pojo.Result;
import com.demo.util.RedisUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <h1>列表操作</h1>
 *
 * <p>
 * createDate 2021/09/09 10:35:04
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@RestController
@RequestMapping("list")
public class ListController {

    /**
     * <h3>获取指定下标的值</h3>
     * POST /hash/lGet?key=a<br>
     */
    @PostMapping("lGet")
    public Result lGet(String key, long index) {
        return Result.o(RedisUtils.lGet(key, index));
    }

    /**
     * <h3>获取第一个</h3>
     * POST /hash/lGetFirst?key=a<br>
     */
    @PostMapping("lGetFirst")
    public Result lGetFirst(String key) {
        return Result.o(RedisUtils.lGetFirst(key));
    }

    /**
     * <h3>获取</h3>
     * POST /hash/lGetLast?key=a<br>
     */
    @PostMapping("lGetLast")
    public Result lGetLast(String key) {
        return Result.o(RedisUtils.lGetLast(key));
    }

    /**
     * <h3>获取指定下标的值</h3>
     * POST /hash/lGet?key=a<br>
     */
    @PostMapping("lGetList")
    public Result lGetList(String key, long start, long end) {
        return Result.o(RedisUtils.lGetList(key, start, end));
    }

    /**
     * <h3>获取全部</h3>
     * POST /hash/lGetAll?key=a<br>
     */
    @PostMapping("lGetAll")
    public Result lGetAll(String key) {
        return Result.o(RedisUtils.lGetAll(key));
    }

    /**
     * <h3>修剪(只要start和end之间的值)</h3>
     * POST /hash/lTrim?key=a<br>
     */
    @PostMapping("lTrim")
    public Result lTrim(String key, long start, long end) {
        RedisUtils.lTrim(key, start, end);
        return Result.o();
    }

    /**
     * <h3>获取列表的长度</h3>
     * POST /hash/lSize?key=a<br>
     */
    @PostMapping("lSize")
    public Result lSize(String key) {
        return Result.o(RedisUtils.lSize(key));
    }

    /**
     * <h3>添加到左侧</h3>
     * POST /hash/lLeftPush?key=a<br>
     */
    @PostMapping("lLeftPush")
    public Result lLeftPush(String key, String value) {
        return Result.o(RedisUtils.lLeftPush(key, value));
    }

    /**
     * <h3>添加到左侧</h3>
     * POST /hash/lLeftPushArray?key=a<br>
     */
    @PostMapping("lLeftPushAllArray")
    public Result lLeftPushAll(String key, String[] value) {
        return Result.o(RedisUtils.lLeftPushAll(key, value));
    }

    /**
     * <h3>添加到左侧</h3>
     * POST /hash/lLeftPushList?key=a<br>
     */
    @PostMapping("lLeftPushAllList")
    public Result lLeftPushAll(String key, List<Object> value) {
        return Result.o(RedisUtils.lLeftPushAll(key, value));
    }

    /**
     * <h3>当列表存在时，添加到左侧</h3>
     * POST /hash/lLeftPushIfPresent?key=a<br>
     */
    @PostMapping("lLeftPushIfPresent")
    public Result lLeftPushIfPresent(String key, String value) {
        return Result.o(RedisUtils.lLeftPushIfPresent(key, value));
    }

    /**
     * <h3>添加到指定值的左侧</h3>
     * POST /hash/lLeftPushOfValue?key=a<br>
     */
    @PostMapping("lLeftPushOfValue")
    public Result lLeftPush(String key, String pivot, String value) {
        return Result.o(RedisUtils.lLeftPush(key, pivot, value));
    }

    /**
     * <h3>添加到右侧</h3>
     * POST /hash/lRightPush?key=a<br>
     */
    @PostMapping("lRightPush")
    public Result lRightPush(String key, String value) {
        return Result.o(RedisUtils.lRightPush(key, value));
    }

    /**
     * <h3>添加到右侧</h3>
     * POST /hash/lRightPushArray?key=a<br>
     */
    @PostMapping("lRightPushAllArray")
    public Result lRightPushAll(String key, String[] value) {
        return Result.o(RedisUtils.lRightPushAll(key, value));
    }

    /**
     * <h3>添加到右侧</h3>
     * POST /hash/lRightPushList?key=a<br>
     */
    @PostMapping("lRightPushAllList")
    public Result lRightPushAll(String key, List<Object> value) {
        return Result.o(RedisUtils.lRightPushAll(key, value));
    }

    /**
     * <h3>当列表存在时，添加到右侧</h3>
     * POST /hash/lRightPushIfPresent?key=a<br>
     */
    @PostMapping("lRightPushIfPresent")
    public Result lRightPushIfPresent(String key, String value) {
        return Result.o(RedisUtils.lRightPushIfPresent(key, value));
    }

    /**
     * <h3>添加到指定值的右侧</h3>
     * POST /hash/lRightPushOfValue?key=a<br>
     */
    @PostMapping("lRightPushOfValue")
    public Result lRightPush(String key, String pivot, String value) {
        return Result.o(RedisUtils.lRightPush(key, pivot, value));
    }

}
