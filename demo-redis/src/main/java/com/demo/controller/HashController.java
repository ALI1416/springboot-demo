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
     * POST /hash/hDelete?key=a&item=a1<br>
     * 存在项a1/a2/a3 返回1<br>
     * 存在项a/b/c 返回0
     */
    @PostMapping("hDelete")
    public Result hDelete(String key, String item) {
        return Result.o(RedisUtils.hDelete(key, item));
    }

    /**
     * <h3>删除map中指定多个项</h3>
     * POST /hash/hDeleteArray?key=a&items=a1&items=a2&items=a4<br>
     * 存在项a1/a2/a3 返回2<br>
     * 存在项a/b/c 返回0
     */
    @PostMapping("hDeleteArray")
    public Result hDelete(String key, String[] items) {
        return Result.o(RedisUtils.hDeleteMulti(key, items));
    }

    /**
     * <h3>删除map中指定多个项</h3>
     * POST /hash/hDeleteList?key=a<br>
     * body JSON ["a1","a2","a4"]<br>
     * 存在项a1/a2/a3 返回2<br>
     * 存在项a/b/c 返回0
     */
    @PostMapping("hDeleteList")
    public Result hDelete(String key, @RequestBody List<String> items) {
        return Result.o(RedisUtils.hDeleteMulti(key, items));
    }

    /**
     * <h3>判断map中是否有指定项</h3>
     * POST /hash/hHasKey?key=a&item=a1<br>
     * 存在key a和item a1 返回true<br>
     * 不存在key a或item a1 返回false
     */
    @PostMapping("hHasKey")
    public Result hHasKey(String key, String item) {
        return Result.o(RedisUtils.hExists(key, item));
    }

    /**
     * <h3>获取map中指定项的值</h3>
     * POST /hash/hGet?key=a&item=a1<br>
     * 存在key a和item a1和value "abc" 返回"abc"<br>
     * 不存在key a或item a1 返回null
     */
    @PostMapping("hGet")
    public Result hGet(String key, String item) {
        return Result.o(RedisUtils.hGet(key, item));
    }

    /**
     * <h3>获取map中指定多个项的值</h3>
     * POST /hash/hMultiGetArray?key=a&items=a1&items=a2&items=a4<br>
     * 存在项a1/a2/a3值为111/222/333 返回[111,222,null]
     */
    @PostMapping("hMultiGetArray")
    public Result hMultiGet(String key, String[] items) {
        return Result.o(RedisUtils.hGetMulti(key, items));
    }

    /**
     * <h3>获取map中指定多个项的值</h3>
     * POST /hash/hMultiGetList?key=a<br>
     * body JSON ["a1","a2","a4"]<br>
     * 存在项a1/a2/a3值为111/222/333 返回[111,222,null]
     */
    @PostMapping("hMultiGetList")
    public Result hMultiGet(String key, @RequestBody List<String> items) {
        return Result.o(RedisUtils.hGetMulti(key, items));
    }

    /**
     * <h3>map指定项的值递增1</h3>
     * POST /hash/hIncrement1?key=a&item=a1<br>
     * 值为123 返回124，值改变为124<br>
     * 不存在键或项 返回1，值改变为1
     */
    @PostMapping("hIncrement1")
    public Result hIncrement(String key, String item) {
        return Result.o(RedisUtils.hIncrement(key, item));
    }

    /**
     * <h3>map指定项的值递增</h3>
     * POST /hash/hIncrement?key=a&item=a1&delta=2<br>
     * 值为123 返回125，值改变为125<br>
     * 不存在键或项 返回2，值改变为2
     */
    @PostMapping("hIncrement")
    public Result hIncrement(String key, String item, long delta) {
        return Result.o(RedisUtils.hIncrement(key, item, delta));
    }

    /**
     * <h3>map指定项的值递增</h3>
     * POST /hash/hIncrementD?key=a&item=a1&delta=1.2<br>
     * 值为123 返回124.2，值改变为124.2<br>
     * 不存在键或项 返回1.2，值改变为1.2
     */
    @PostMapping("hIncrementD")
    public Result hIncrementD(String key, String item, double delta) {
        return Result.o(RedisUtils.hIncrement(key, item, delta));
    }

    /**
     * <h3>map指定项的值递减1</h3>
     * POST /hash/hDecrement1?key=a&item=a1<br>
     * 值为123 返回122，值改变为122<br>
     * 不存在键或项 返回-1，值改变为-1
     */
    @PostMapping("hDecrement1")
    public Result hDecrement(String key, String item) {
        return Result.o(RedisUtils.hDecrement(key, item));
    }

    /**
     * <h3>map指定项的值递减</h3>
     * POST /hash/hDecrement?key=a&item=a1&delta=2<br>
     * 值为123 返回121，值改变为121<br>
     * 不存在键或项 返回-2，值改变为-2
     */
    @PostMapping("hDecrement")
    public Result hDecrement(String key, String item, long delta) {
        return Result.o(RedisUtils.hDecrement(key, item, delta));
    }

    /**
     * <h3>map指定项的值递减</h3>
     * POST /hash/hDecrementD?key=a&item=a1&delta=1.2<br>
     * 值为123 返回121.8，值改变为121.8<br>
     * 不存在键或项 返回-1.2，值改变为-1.2
     */
    @PostMapping("hDecrementD")
    public Result hDecrementD(String key, String item, double delta) {
        return Result.o(RedisUtils.hDecrement(key, item, delta));
    }

    /**
     * <h3>获取一个随机的项</h3>
     * POST /hash/hRandomItem?key=a<br>
     * 存在项a1/a2/a3 返回a2<br>
     * 不存在键 null
     */
    @PostMapping("hRandomItem")
    public Result hRandomItem(String key) {
        return Result.o(RedisUtils.hRandomItem(key));
    }

    /**
     * <h3>获取一个随机的项和值</h3>
     * POST /hash/hRandomMap?key=a<br>
     * 存在项a1/a2/a3 值1/2/3 返回a2/2<br>
     * 不存在键 null
     */
    @PostMapping("hRandomMap")
    public Result hRandomMap(String key) {
        return Result.o(RedisUtils.hRandomMap(key));
    }

    /**
     * <h3>获取多个随机的项</h3>
     * POST /hash/hRandomItem2?key=a&count=2<br>
     * 存在项a1/a2/a3 返回a2/a3<br>
     * 不存在键 []
     */
    @PostMapping("hRandomItem2")
    public Result hRandomItem2(String key, long count) {
        return Result.o(RedisUtils.hRandomItem(key, count));
    }

    /**
     * <h3>获取多个随机的项和值</h3>
     * POST /hash/hRandomMap2?key=a&count=2<br>
     * 存在项a1/a2/a3 值1/2/3 返回a2/2 a3/3<br>
     * 不存在键 报错
     */
    @PostMapping("hRandomMap2")
    public Result hRandomMap2(String key, long count) {
        return Result.o(RedisUtils.hRandomMap(key, count));
    }

    /**
     * <h3>获取项的个数</h3>
     * POST /hash/hLengthOfValue?key=a&item=a1<br>
     * 存在项a1 值123 返回3<br>
     * 不存在键 返回0
     */
    @PostMapping("hLengthOfValue")
    public Result hLengthOfValue(String key, String item) {
        return Result.o(RedisUtils.hLengthOfValue(key, item));
    }

    /**
     * <h3>获取项的个数</h3>
     * POST /hash/hSize?key=a<br>
     * 存在项a1/a2/a3 返回3<br>
     * 不存在键 返回0
     */
    @PostMapping("hSize")
    public Result hSize(String key) {
        return Result.o(RedisUtils.hSize(key));
    }

    /**
     * <h3>设置map的多个键值(项已存在会被覆盖)</h3>
     * POST /hash/hPutAll?key=a<br>
     * body JSON {"a":"111","b":"222","c":"333"}
     * Redis中"a":"111","b":"222","c":"333"
     */
    @PostMapping("hPutAll")
    public Result hPutAll(String key, @RequestBody Map<String, Object> map) {
        RedisUtils.hSetMulti(key, map);
        return Result.o();
    }

    /**
     * <h3>设置map的1个键值(项已存在会被覆盖)</h3>
     * POST /hash/hPut?key=a&item=a1&value=abc<br>
     * Redis中"a1":"abc"
     */
    @PostMapping("hPut")
    public Result hPut(String key, String item, String value) {
        RedisUtils.hSet(key, item, value);
        return Result.o();
    }

    /**
     * <h3>项不存在时，设置map的1个键值</h3>
     * POST /hash/hPutIfAbsent?key=a&item=a1&value=abc<br>
     * 存在key a和item a1 返回false<br>
     * 不存在key a或item a1 返回true
     */
    @PostMapping("hPutIfAbsent")
    public Result hPutIfAbsent(String key, String item, String value) {
        return Result.o(RedisUtils.hPutIfAbsent(key, item, value));
    }

    /**
     * <h3>获取map中所有的项</h3>
     * POST /hash/hGetAllItem?key=a<br>
     * 存在项a1/a2/a3值为111/222/333 返回["a1","a2","a3"]
     */
    @PostMapping("hGetAllItem")
    public Result hGetAllItem(String key) {
        return Result.o(RedisUtils.hGetAllItem(key));
    }

    /**
     * <h3>获取map中所有的值</h3>
     * POST /hash/hGetAllValue?key=a<br>
     * 存在项a1/a2/a3值为111/222/333 返回[111,222,333]
     */
    @PostMapping("hGetAllValue")
    public Result hGetAllValue(String key) {
        return Result.o(RedisUtils.hGetAllValue(key));
    }

    /**
     * <h3>获取map中所有的项和值</h3>
     * POST /hash/hGetAllItemAndValue?key=a<br>
     * 存在项a1/a2/a3值为111/222/333 返回{"a":"111","b":"222","c":"333"}
     */
    @PostMapping("hGetAllItemAndValue")
    public Result hGetAllItemAndValue(String key) {
        return Result.o(RedisUtils.hGetAllItemAndValue(key));
    }

    /**
     * <h3>模糊查询</h3>
     * 实际存在键a项和值有a 1 /aabbcc 2 /abc 3 /abd 4 /b 5 /bc 6 /bd 7 /c 8 /[] 9<br>
     * POST /hScan?key=a&match=a 不匹配字符[a 1]<br>
     * POST /hScan?key=a&match=b? 右侧匹配1个字符[bc 6,bd 7]<br>
     * POST /hScan?key=a&match=*c 左侧匹配0+个字符[aabbcc 2,abc 3,bc 6,c 8]<br>
     * POST /hScan?key=a&match=*b* 两侧匹配0+个字符[aabbcc 2,abc 3,abd 4,b 5,bc 6,bd 7]<br>
     * POST /hScan?key=a&match=[abd] 匹配1个指定字符[a 1,b 5]<br>
     * POST /hScan?key=a&match=[^abd] 不匹配1个指定字符[c 8]<br>
     * POST /hScan?key=a&match=[A-z] 匹配1个指定字符[a 1,b 5,c 8]<br>
     * POST /hScan?key=a&match=\[* 转义匹配匹配1个指定字符[[] 9]
     */
    @PostMapping("hScan")
    public Result hScan(String key, String match) {
        return Result.o(RedisUtils.hScan(key, match));
    }

}
