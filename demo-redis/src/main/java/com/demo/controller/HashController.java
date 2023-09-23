package com.demo.controller;

import cn.z.redis.RedisTemp;
import com.demo.entity.pojo.Result;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

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
@AllArgsConstructor
public class HashController {

    private final RedisTemp redisTemp;

    /**
     * <h3>删除map中指定多个项</h3>
     * http://localhost:8080/hash/hDelete?key=a&item=a1<br>
     * 存在项a1/a2/a3 返回1<br>
     * 存在项a/b/c 返回0
     */
    @GetMapping("hDelete")
    public Result<Long> hDelete(String key, String item) {
        return Result.o(redisTemp.hDelete(key, item));
    }

    /**
     * <h3>删除map中指定多个项</h3>
     * http://localhost:8080/hash/hDeleteArray?key=a&items=a1&items=a2&items=a4<br>
     * 存在项a1/a2/a3 返回2<br>
     * 存在项a/b/c 返回0
     */
    @GetMapping("hDeleteArray")
    public Result<Long> hDelete(String key, String[] items) {
        return Result.o(redisTemp.hDeleteMulti(key, items));
    }

    /**
     * <h3>删除map中指定多个项</h3>
     * POST http://localhost:8080/hash/hDeleteList?key=a<br>
     * body JSON ["a1","a2","a4"]<br>
     * 存在项a1/a2/a3 返回2<br>
     * 存在项a/b/c 返回0
     */
    @PostMapping("hDeleteList")
    public Result<Long> hDelete(String key, @RequestBody List<String> items) {
        return Result.o(redisTemp.hDeleteMulti(key, items));
    }

    /**
     * <h3>判断map中是否有指定项</h3>
     * http://localhost:8080/hash/hHasKey?key=a&item=a1<br>
     * 存在key a和item a1 返回true<br>
     * 不存在key a或item a1 返回false
     */
    @GetMapping("hHasKey")
    public Result<Boolean> hHasKey(String key, String item) {
        return Result.o(redisTemp.hExists(key, item));
    }

    /**
     * <h3>获取map中指定项的值</h3>
     * http://localhost:8080/hash/hGet?key=a&item=a1<br>
     * 存在key a和item a1和value "abc" 返回"abc"<br>
     * 不存在key a或item a1 返回null
     */
    @GetMapping("hGet")
    public Result<Object> hGet(String key, String item) {
        return Result.o(redisTemp.hGet(key, item));
    }

    /**
     * <h3>获取map中指定多个项的值</h3>
     * http://localhost:8080/hash/hMultiGetArray?key=a&items=a1&items=a2&items=a4<br>
     * 存在项a1/a2/a3值为111/222/333 返回[111,222,null]
     */
    @GetMapping("hMultiGetArray")
    public Result<List<Object>> hMultiGet(String key, String[] items) {
        return Result.o(redisTemp.hGetMulti(key, items));
    }

    /**
     * <h3>获取map中指定多个项的值</h3>
     * POST ttp://localhost:8080/hash/hMultiGetList?key=a<br>
     * body JSON ["a1","a2","a4"]<br>
     * 存在项a1/a2/a3值为111/222/333 返回[111,222,null]
     */
    @PostMapping("hMultiGetList")
    public Result<List<Object>> hMultiGet(String key, @RequestBody List<String> items) {
        return Result.o(redisTemp.hGetMulti(key, items));
    }

    /**
     * <h3>map指定项的值递增1</h3>
     * http://localhost:8080/hash/hIncrement1?key=a&item=a1<br>
     * 值为123 返回124，值改变为124<br>
     * 不存在键或项 返回1，值改变为1
     */
    @GetMapping("hIncrement1")
    public Result<Long> hIncrement(String key, String item) {
        return Result.o(redisTemp.hIncrement(key, item));
    }

    /**
     * <h3>map指定项的值递增</h3>
     * http://localhost:8080/hash/hIncrement?key=a&item=a1&delta=2<br>
     * 值为123 返回125，值改变为125<br>
     * 不存在键或项 返回2，值改变为2
     */
    @GetMapping("hIncrement")
    public Result<Long> hIncrement(String key, String item, long delta) {
        return Result.o(redisTemp.hIncrement(key, item, delta));
    }

    /**
     * <h3>map指定项的值递增</h3>
     * http://localhost:8080/hash/hIncrementD?key=a&item=a1&delta=1.2<br>
     * 值为123 返回124.2，值改变为124.2<br>
     * 不存在键或项 返回1.2，值改变为1.2
     */
    @GetMapping("hIncrementD")
    public Result<Double> hIncrementD(String key, String item, double delta) {
        return Result.o(redisTemp.hIncrement(key, item, delta));
    }

    /**
     * <h3>map指定项的值递减1</h3>
     * http://localhost:8080/hash/hDecrement1?key=a&item=a1<br>
     * 值为123 返回122，值改变为122<br>
     * 不存在键或项 返回-1，值改变为-1
     */
    @GetMapping("hDecrement1")
    public Result<Long> hDecrement(String key, String item) {
        return Result.o(redisTemp.hDecrement(key, item));
    }

    /**
     * <h3>map指定项的值递减</h3>
     * http://localhost:8080/hash/hDecrement?key=a&item=a1&delta=2<br>
     * 值为123 返回121，值改变为121<br>
     * 不存在键或项 返回-2，值改变为-2
     */
    @GetMapping("hDecrement")
    public Result<Long> hDecrement(String key, String item, long delta) {
        return Result.o(redisTemp.hDecrement(key, item, delta));
    }

    /**
     * <h3>map指定项的值递减</h3>
     * http://localhost:8080/hash/hDecrementD?key=a&item=a1&delta=1.2<br>
     * 值为123 返回121.8，值改变为121.8<br>
     * 不存在键或项 返回-1.2，值改变为-1.2
     */
    @GetMapping("hDecrementD")
    public Result<Double> hDecrementD(String key, String item, double delta) {
        return Result.o(redisTemp.hDecrement(key, item, delta));
    }

    /**
     * <h3>获取一个随机的项</h3>
     * http://localhost:8080/hash/hRandomItem?key=a<br>
     * 存在项a1/a2/a3 返回a2<br>
     * 不存在键 null
     */
    @GetMapping("hRandomItem")
    public Result<String> hRandomItem(String key) {
        return Result.o(redisTemp.hRandomItem(key));
    }

    /**
     * <h3>获取一个随机的项和值</h3>
     * http://localhost:8080/hash/hRandomMap?key=a<br>
     * 存在项a1/a2/a3 值1/2/3 返回a2/2<br>
     * 不存在键 null
     */
    @GetMapping("hRandomMap")
    public Result<Map.Entry<Object, Object>> hRandomMap(String key) {
        return Result.o(redisTemp.hRandomMap(key));
    }

    /**
     * <h3>获取多个随机的项</h3>
     * http://localhost:8080/hash/hRandomItem2?key=a&count=2<br>
     * 存在项a1/a2/a3 返回a2/a3<br>
     * 不存在键 []
     */
    @GetMapping("hRandomItem2")
    public Result<List<Object>> hRandomItem2(String key, long count) {
        return Result.o(redisTemp.hRandomItem(key, count));
    }

    /**
     * <h3>获取多个随机的项和值</h3>
     * http://localhost:8080/hash/hRandomMap2?key=a&count=2<br>
     * 存在项a1/a2/a3 值1/2/3 返回a2/2 a3/3<br>
     * 不存在键 报错
     */
    @GetMapping("hRandomMap2")
    public Result<Map<Object, Object>> hRandomMap2(String key, long count) {
        return Result.o(redisTemp.hRandomMap(key, count));
    }

    /**
     * <h3>获取项的个数</h3>
     * http://localhost:8080/hash/hLengthOfValue?key=a&item=a1<br>
     * 存在项a1 值123 返回3<br>
     * 不存在键 返回0
     */
    @GetMapping("hLengthOfValue")
    public Result<Long> hLengthOfValue(String key, String item) {
        return Result.o(redisTemp.hLengthOfValue(key, item));
    }

    /**
     * <h3>获取项的个数</h3>
     * http://localhost:8080/hash/hSize?key=a<br>
     * 存在项a1/a2/a3 返回3<br>
     * 不存在键 返回0
     */
    @GetMapping("hSize")
    public Result<Long> hSize(String key) {
        return Result.o(redisTemp.hSize(key));
    }

    /**
     * <h3>设置map的多个键值(项已存在会被覆盖)</h3>
     * POST http://localhost:8080/hash/hPutAll?key=a<br>
     * body JSON {"a":"111","b":"222","c":"333"}
     * Redis中"a":"111","b":"222","c":"333"
     */
    @PostMapping("hPutAll")
    public Result hPutAll(String key, @RequestBody Map<String, Object> map) {
        redisTemp.hSetMulti(key, map);
        return Result.o();
    }

    /**
     * <h3>设置map的1个键值(项已存在会被覆盖)</h3>
     * http://localhost:8080/hash/hPut?key=a&item=a1&value=abc<br>
     * Redis中"a1":"abc"
     */
    @GetMapping("hPut")
    public Result hPut(String key, String item, String value) {
        redisTemp.hSet(key, item, value);
        return Result.o();
    }

    /**
     * <h3>项不存在时，设置map的1个键值</h3>
     * http://localhost:8080/hash/hPutIfAbsent?key=a&item=a1&value=abc<br>
     * 存在key a和item a1 返回false<br>
     * 不存在key a或item a1 返回true
     */
    @GetMapping("hPutIfAbsent")
    public Result<Boolean> hPutIfAbsent(String key, String item, String value) {
        return Result.o(redisTemp.hPutIfAbsent(key, item, value));
    }

    /**
     * <h3>获取map中所有的项</h3>
     * http://localhost:8080/hash/hGetAllItem?key=a<br>
     * 存在项a1/a2/a3值为111/222/333 返回["a1","a2","a3"]
     */
    @GetMapping("hGetAllItem")
    public Result<Set<Object>> hGetAllItem(String key) {
        return Result.o(redisTemp.hGetAllItem(key));
    }

    /**
     * <h3>获取map中所有的值</h3>
     * http://localhost:8080/hash/hGetAllValue?key=a<br>
     * 存在项a1/a2/a3值为111/222/333 返回[111,222,333]
     */
    @GetMapping("hGetAllValue")
    public Result<List<Object>> hGetAllValue(String key) {
        return Result.o(redisTemp.hGetAllValue(key));
    }

    /**
     * <h3>获取map中所有的项和值</h3>
     * http://localhost:8080/hash/hGetAllItemAndValue?key=a<br>
     * 存在项a1/a2/a3值为111/222/333 返回{"a":"111","b":"222","c":"333"}
     */
    @GetMapping("hGetAllItemAndValue")
    public Result<Map<Object, Object>> hGetAllItemAndValue(String key) {
        return Result.o(redisTemp.hGetAllItemAndValue(key));
    }

    /**
     * <h3>模糊查询</h3>
     * 实际存在键a项和值有a 1 /aabbcc 2 /abc 3 /abd 4 /b 5 /bc 6 /bd 7 /c 8 /[] 9<br>
     * http://localhost:8080/hScan?key=a&match=a 不匹配字符[a 1]<br>
     * http://localhost:8080/hScan?key=a&match=b? 右侧匹配1个字符[bc 6,bd 7]<br>
     * http://localhost:8080/hScan?key=a&match=*c 左侧匹配0+个字符[aabbcc 2,abc 3,bc 6,c 8]<br>
     * http://localhost:8080/hScan?key=a&match=*b* 两侧匹配0+个字符[aabbcc 2,abc 3,abd 4,b 5,bc 6,bd 7]<br>
     * http://localhost:8080/hScan?key=a&match=[abd] 匹配1个指定字符[a 1,b 5]<br>
     * http://localhost:8080/hScan?key=a&match=[^abd] 不匹配1个指定字符[c 8]<br>
     * http://localhost:8080/hScan?key=a&match=[A-z] 匹配1个指定字符[a 1,b 5,c 8]<br>
     * http://localhost:8080/hScan?key=a&match=\[* 转义匹配匹配1个指定字符[[] 9]
     */
    @GetMapping("hScan")
    public Result<Map<String, Object>> hScan(String key, String match) {
        return Result.o(redisTemp.hScan(key, match));
    }

}
