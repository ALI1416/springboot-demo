package com.demo.controller;

import com.demo.entity.pojo.Result;
import com.demo.util.RedisUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * <h1>哈希操作</h1>
 *
 * <p>
 * createDate 2021/09/09 10:35:04
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@RestController
@RequestMapping("hash")
public class HashController {

    /**
     * <h3>删除map中指定多个项</h3>
     * POST /hash/hDeleteArray?key=a&items=a1&items=a2&items=a4<br>
     * 存在项a1/a2/a3 2
     */
    @PostMapping("hDeleteArray")
    public Result hDelete(String key, String[] items) {
        return Result.o(RedisUtils.hDelete(key, items));
    }

    /**
     * <h3>删除map中指定多个项</h3>
     * POST /hash/hDeleteList?key=a<br>
     * body JSON ["a1","a2","a4"]<br>
     * 存在项a1/a2/a3 2
     */
    @PostMapping("hDeleteList")
    public Result hDelete(String key, @RequestBody List<String> items) {
        return Result.o(RedisUtils.hDelete(key, items));
    }

    /**
     * <h3>判断map中是否有指定项</h3>
     * POST /hash/hHasKey?key=a&item=a1<br>
     * 存在key a和item a1 true<br>
     * 不存在key a或item a1 false
     */
    @PostMapping("hHasKey")
    public Result hHasKey(String key, String item) {
        return Result.o(RedisUtils.hHasKey(key, item));
    }

    /**
     * <h3>获取map中指定项的值</h3>
     * POST /hash/hGet?key=a&item=a1<br>
     * 存在key a和item a1和value "abc" "abc"<br>
     * 不存在key a或item a1 null
     */
    @PostMapping("hGet")
    public Result hGet(String key, String item) {
        return Result.o(RedisUtils.hGet(key, item));
    }

    /**
     * <h3>获取map中指定多个项的值</h3>
     * POST /hash/hMultiGetArray?key=a&items=a1&items=a2&items=a4<br>
     * 存在项a1/a2/a3值为111/222/333 [111,222,null]
     */
    @PostMapping("hMultiGetArray")
    public Result hMultiGet(String key, String[] items) {
        return Result.o(RedisUtils.hMultiGet(key, items));
    }

    /**
     * <h3>获取map中指定多个项的值</h3>
     * POST /hash/hMultiGetList?key=a<br>
     * body JSON ["a1","a2","a4"]<br>
     * 存在项a1/a2/a3值为111/222/333 [111,222,null]
     */
    @PostMapping("hMultiGetList")
    public Result hMultiGet(String key, @RequestBody List<String> items) {
        return Result.o(RedisUtils.hMultiGet(key, items));
    }

    /**
     * <h3>map指定项的值递增1</h3>
     * POST /hash/hIncrement1?key=a&item=a1<br>
     * 值为123 返回124，值改变为124
     */
    @PostMapping("hIncrement1")
    public Result hIncrement(String key, String item) {
        return Result.o(RedisUtils.hIncrement(key, item));
    }

    /**
     * <h3>map指定项的值递增1</h3>
     * POST /hash/hIncrement?key=a&item=a1&delta=2<br>
     * 值为123 返回125，值改变为125
     */
    @PostMapping("hIncrement")
    public Result hIncrement(String key, String item, long delta) {
        return Result.o(RedisUtils.hIncrement(key, item, delta));
    }

    /**
     * <h3>map指定项的值递减1</h3>
     * POST /hash/hDecrement1?key=a&item=a1<br>
     * 值为123 返回122，值改变为122
     */
    @PostMapping("hDecrement1")
    public Result hDecrement(String key, String item) {
        return Result.o(RedisUtils.hDecrement(key, item));
    }

    /**
     * <h3>map指定项的值递减1</h3>
     * POST /hash/hDecrement?key=a&item=a1&delta=2<br>
     * 值为123 返回121，值改变为121
     */
    @PostMapping("hDecrement")
    public Result hDecrement(String key, String item, long delta) {
        return Result.o(RedisUtils.hDecrement(key, item, delta));
    }

    /**
     * <h3>获取项的个数</h3>
     * POST /hash/hSize?key=a<br>
     * 存在项a1/a2/a3 3
     */
    @PostMapping("hSize")
    public Result hSize(String key) {
        return Result.o(RedisUtils.hSize(key));
    }

    /**
     * <h3>设置map的多个键值</h3>
     * POST /hash/hPutAll?key=a<br>
     * body JSON {"a":"111","b":"222","c":"333"}
     * Redis中"a":"111","b":"222","c":"333"
     */
    @PostMapping("hPutAll")
    public Result hPutAll(String key, @RequestBody Map<String, Object> map) {
        RedisUtils.hPutAll(key, map);
        return Result.o();
    }

    /**
     * <h3>设置map的1个键值</h3>
     * POST /hash/hPut?key=a&item=a1&value=abc<br>
     * Redis中"a1":"abc"
     */
    @PostMapping("hPut")
    public Result hPut(String key, String item, String value) {
        RedisUtils.hPut(key, item, value);
        return Result.o();
    }

    /**
     * <h3>项不存在时，设置map的1个键值</h3>
     * POST /hash/hPutIfAbsent?key=a&item=a1&value=abc<br>
     * 存在key a和item a1 false<br>
     * 不存在key a或item a1 true
     */
    @PostMapping("hPutIfAbsent")
    public Result hPutIfAbsent(String key, String item, String value) {
        return Result.o(RedisUtils.hPutIfAbsent(key, item, value));
    }

    /**
     * <h3>获取map中所有的项</h3>
     * POST /hash/hGetAllItem?key=a&item=a1&value=abc<br>
     * 存在项a1/a2/a3值为111/222/333 ["a1","a2","a3"]
     */
    @PostMapping("hGetAllItem")
    public Result hGetAllItem(String key) {
        return Result.o(RedisUtils.hGetAllItem(key));
    }

    /**
     * <h3>获取map中所有的值</h3>
     * POST /hash/hGetAllValue?key=a&item=a1&value=abc<br>
     * 存在项a1/a2/a3值为111/222/333 [111,222,333]
     */
    @PostMapping("hGetAllValue")
    public Result hGetAllValue(String key) {
        return Result.o(RedisUtils.hGetAllValue(key));
    }

    /**
     * <h3>获取map中所有的项和值</h3>
     * POST /hash/hGetAllItemAndValue?key=a&item=a1&value=abc<br>
     * 存在项a1/a2/a3值为111/222/333 {"a":"111","b":"222","c":"333"}
     */
    @PostMapping("hGetAllItemAndValue")
    public Result hGetAllItemAndValue(String key) {
        return Result.o(RedisUtils.hGetAllItemAndValue(key));
    }

}
