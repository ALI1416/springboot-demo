package com.demo.controller;

import cn.z.redis.RedisTemp;
import com.demo.entity.pojo.Result;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
@AllArgsConstructor
public class ListController {

    private final RedisTemp redisTemp;

    /**
     * <h3>获取指定下标的值</h3>
     * 存在键 a 值 [111,222,333,444,555]
     * http://localhost:8080/list/lGet?key=a&index=0 返回111<br>
     * http://localhost:8080/list/lGet?key=a&index=2 返回333<br>
     * http://localhost:8080/list/lGet?key=a&index=-1 返回555<br>
     * http://localhost:8080/list/lGet?key=a&index=-2 返回444<br>
     * http://localhost:8080/list/lGet?key=a&index=10 返回null<br>
     * http://localhost:8080/list/lGet?key=b&index=0 返回null
     */
    @GetMapping("lGet")
    public Result<Object> lGet(String key, long index) {
        return Result.o(redisTemp.lGet(key, index));
    }

    /**
     * <h3>获取第一个</h3>
     * 存在键 a 值 [111,222,333,444,555]<br>
     * http://localhost:8080/list/lGetFirst?key=a 返回111<br>
     * http://localhost:8080/list/lGetFirst?key=b 返回null
     */
    @GetMapping("lGetFirst")
    public Result<Object> lGetFirst(String key) {
        return Result.o(redisTemp.lGetFirst(key));
    }

    /**
     * <h3>获取最后一个</h3>
     * 存在键 a 值 [111,222,333,444,555]<br>
     * http://localhost:8080/list/lGetLast?key=a 返回555<br>
     * http://localhost:8080/list/lGetFirst?key=b 返回null
     */
    @GetMapping("lGetLast")
    public Result<Object> lGetLast(String key) {
        return Result.o(redisTemp.lGetLast(key));
    }

    /**
     * <h3>获取指定下标的值</h3>
     * 存在键 a 值 [111,222,333,444,555]<br>
     * http://localhost:8080/list/lGetList?key=a&start=1&end=3 返回[222,333,444]<br>
     * http://localhost:8080/list/lGetList?key=a&start=1&end=-1 返回[222,333,444,555]<br>
     * http://localhost:8080/list/lGetList?key=a&start=1&end=100 返回[222,333,444,555]<br>
     * http://localhost:8080/list/lGetList?key=b&start=1&end=2 返回[]
     */
    @GetMapping("lGetList")
    public Result<List<Object>> lGetList(String key, long start, long end) {
        return Result.o(redisTemp.lGetMulti(key, start, end));
    }

    /**
     * <h3>获取全部</h3>
     * 存在值 [111,222,333,444,555]<br>
     * http://localhost:8080/list/lGetAll?key=a 返回[111,222,333,444,555]
     * http://localhost:8080/list/lGetAll?key=b 返回[]
     */
    @GetMapping("lGetAll")
    public Result<List<Object>> lGetAll(String key) {
        return Result.o(redisTemp.lGetAll(key));
    }

    /**
     * <h3>修剪(只要start和end之间的值)</h3>
     * http://localhost:8080/list/lTrim?key=a&start=1&end=3<br>
     * 存在值 [111,222,333,444,555]<br>
     * 还剩下 [222,333,444]<br>
     * http://localhost:8080/list/lTrim?key=a&start=3&end=-1<br>
     * 存在值 [111,222,333,444,555]<br>
     * 还剩下 [444,555]
     */
    @GetMapping("lTrim")
    public Result lTrim(String key, long start, long end) {
        redisTemp.lTrim(key, start, end);
        return Result.o();
    }

    /**
     * <h3>获取列表的长度</h3>
     * http://localhost:8080/list/lSize?key=a<br>
     * 存在值 [111,222,333,444,555] 5<br>
     */
    @GetMapping("lSize")
    public Result<Long> lSize(String key) {
        return Result.o(redisTemp.lSize(key));
    }

    /**
     * <h3>添加到左侧</h3>
     * http://localhost:8080/list/lLeftPush?key=a&value=abcd<br>
     * 存在键 a 值 [111,222,333,444,555]<br>
     * 变成 ["abcd",111,222,333,444,555]<br>
     * 返回 6<br>
     * http://localhost:8080/list/lLeftPush?key=b&value=abcd<br>
     * 不存在键 b<br>
     * 变成 ["abcd"]<br>
     * 返回 1
     */
    @GetMapping("lLeftPush")
    public Result<Long> lLeftPush(String key, String value) {
        return Result.o(redisTemp.lLeftPush(key, value));
    }

    /**
     * <h3>添加到左侧</h3>
     * http://localhost:8080/list/lLeftPushAllArray?key=a&value=a&value=b&value=c<br>
     * 存在值 [111,222,333,444,555]<br>
     * 变成 ["c","b","a",111,222,333,444,555]<br>
     * 返回 8
     */
    @GetMapping("lLeftPushAllArray")
    public Result<Long> lLeftPushAll(String key, String[] value) {
        return Result.o(redisTemp.lLeftPushMulti(key, value));
    }

    /**
     * <h3>添加到左侧</h3>
     * POST http://localhost:8080/list/lLeftPushAllList?key=a<br>
     * body JSON ["a","b","c"]<br>
     * 存在值 [111,222,333,444,555]<br>
     * 变成 ["c","b","a",111,222,333,444,555]<br>
     * 返回 8
     */
    @PostMapping("lLeftPushAllList")
    public Result<Long> lLeftPushAll(String key, @RequestBody List<Object> value) {
        return Result.o(redisTemp.lLeftPushMulti(key, value));
    }

    /**
     * <h3>当列表存在时，添加到左侧</h3>
     * http://localhost:8080/list/lLeftPushIfPresent?key=a&value=a<br>
     * 存在键 a 值 [111,222,333,444,555]<br>
     * 变成 ["a",111,222,333,444,555]<br>
     * 返回 6<br>
     * http://localhost:8080/list/lLeftPushIfPresent?key=b&value=a<br>
     * 不存在键 b<br>
     * 返回 0
     */
    @GetMapping("lLeftPushIfPresent")
    public Result<Long> lLeftPushIfPresent(String key, String value) {
        return Result.o(redisTemp.lLeftPushIfPresent(key, value));
    }

    /**
     * <h3>添加到指定值的左侧</h3>
     * 存在值 [111,222,333,444,333]<br>
     * http://localhost:8080/list/lLeftPushOfValue?key=a&pivot=333&value=dd<br>
     * 变成 [111,222,"dd",333,444,333]<br>
     * 返回 6<br>
     * http://localhost:8080/list/lLeftPushOfValue?key=a&pivot=888&value=dd<br>
     * 不变<br>
     * 返回 -1
     */
    @GetMapping("lLeftPushOfValue")
    public Result<Long> lLeftPush(String key, int pivot, String value) {
        return Result.o(redisTemp.lInsertLeft(key, pivot, value));
    }

    /**
     * <h3>添加到右侧</h3>
     * http://localhost:8080/list/lRightPush?key=a&value=asd<br>
     * 存在值 [111,222,333,444,555]<br>
     * 变成 [111,222,333,444,555,"asd"]<br>
     * 返回 6
     */
    @GetMapping("lRightPush")
    public Result<Long> lRightPush(String key, String value) {
        return Result.o(redisTemp.lRightPush(key, value));
    }

    /**
     * <h3>添加到右侧</h3>
     * http://localhost:8080/list/lRightPushAllArray?key=a&value=a&value=b&value=c<br>
     * 存在值 [111,222,333,444,555]<br>
     * 变成 [111,222,333,444,555,"a","b","c"]<br>
     * 返回 8
     */
    @GetMapping("lRightPushAllArray")
    public Result<Long> lRightPushAll(String key, String[] value) {
        return Result.o(redisTemp.lRightPushMulti(key, value));
    }

    /**
     * <h3>添加到右侧</h3>
     * POST http://localhost:8080/list/lRightPushAllList?key=a<br>
     * body JSON ["a","b","c"]
     * 存在值 [111,222,333,444,555]<br>
     * 变成 [111,222,333,444,555,"a","b","c"]<br>
     * 返回 8
     */
    @PostMapping("lRightPushAllList")
    public Result<Long> lRightPushAll(String key, @RequestBody List<Object> value) {
        return Result.o(redisTemp.lRightPushMulti(key, value));
    }

    /**
     * <h3>当列表存在时，添加到右侧</h3>
     * http://localhost:8080/list/lRightPushIfPresent?key=a&value=ss<br>
     * 存在值 [111,222,333,444,555]<br>
     * 变成 [111,222,333,444,555,"ss"]<br>
     * 返回 6
     */
    @GetMapping("lRightPushIfPresent")
    public Result<Long> lRightPushIfPresent(String key, String value) {
        return Result.o(redisTemp.lRightPushIfPresent(key, value));
    }

    /**
     * <h3>添加到指定值的右侧</h3>
     * http://localhost:8080/list/lRightPushOfValue?key=a&pivot=333&value=dd<br>
     * 存在值 [111,222,333,444,555]<br>
     * 变成 [111,222,333,"dd",444,555]<br>
     * 返回 6<br>
     * http://localhost:8080/list/lLeftPushOfValue?key=a&pivot=888&value=dd<br>
     * 不变<br>
     * 返回 -1
     */
    @GetMapping("lRightPushOfValue")
    public Result<Long> lRightPush(String key, int pivot, String value) {
        return Result.o(redisTemp.lInsertRight(key, pivot, value));
    }

    /**
     * <h3>插入到指定位置</h3>
     * 存在值 [111,222,333,444,555]<br>
     * http://localhost:8080/list/lSet?key=a&index=0&value=aa<br>
     * 变成 ["aa",222,333,444,555]<br>
     * http://localhost:8080/list/lSet?key=a&index=2&value=aa<br>
     * 变成[111,222,"aa",444,555]<br>
     * http://localhost:8080/list/lSet?key=a&index=-1&value=aa<br>
     * 变成[111,222,333,444,"aa"]<br>
     * http://localhost:8080/list/lSet?key=a&index=5&value=aa<br>
     * 报错
     */
    @GetMapping("lSet")
    public Result lSet(String key, long index, String value) {
        redisTemp.lSet(key, index, value);
        return Result.o();
    }

    /**
     * <h3>删除第count次出现的值</h3>
     * 存在值 [111,222,333,222,444,222]<br>
     * http://localhost:8080/list/lRemove?key=a&count=0&value=222
     * 变成 [111,333,444] 全部删除<br>
     * 返回 3<br>
     * http://localhost:8080/list/lRemove?key=a&count=1&value=222<br>
     * 变成 [111,333,222,444,222] 左边删1个<br>
     * 返回 1<br>
     * http://localhost:8080/list/lRemove?key=a&count=2&value=222<br>
     * 变成 [111,333,444,222] 左边删2个<br>
     * 返回 1<br>
     * http://localhost:8080/list/lRemove?key=a&count=-1&value=222<br>
     * 变成 [111,222,333,222,444] 右边删1个<br>
     * 返回 1<br>
     * http://localhost:8080/list/lRemove?key=a&count=-2&value=222<br>
     * 变成 [111,222,333,444] 右边删2个<br>
     * 返回 2<br>
     * http://localhost:8080/list/lRemove?key=a&count=-2&value=666<br>
     * 不变<br>
     * 返回 0
     */
    @GetMapping("lRemove")
    public Result<Long> lRemove(String key, long count, int value) {
        return Result.o(redisTemp.lDelete(key, count, value));
    }

    /**
     * <h3>删除左侧第一次出现的值</h3>
     * 存在值 [111,222,333,222,444,222]<br>
     * http://localhost:8080/list/lRemoveLeft?key=a&value=222<br>
     * 变成 [111,333,222,444,222]<br>
     * 返回 1
     */
    @GetMapping("lRemoveLeft")
    public Result<Long> lRemoveLeft(String key, int value) {
        return Result.o(redisTemp.lDeleteLeft(key, value));
    }

    /**
     * <h3>删除右侧第一次出现的值</h3>
     * 存在值 [111,222,333,222,444,222]<br>
     * http://localhost:8080/list/lRemoveRight?key=a&value=222<br>
     * 变成 [111,222,333,222,444]<br>
     * 返回 1
     */
    @GetMapping("lRemoveRight")
    public Result<Long> lRemoveLast(String key, int value) {
        return Result.o(redisTemp.lDeleteRight(key, value));
    }

    /**
     * <h3>删除全部出现的值</h3>
     * 存在值 [111,222,333,222,444,222]<br>
     * http://localhost:8080/list/lRemoveAll?key=a&value=222<br>
     * 变成 [111,333,444]<br>
     * 返回 3<br>
     */
    @GetMapping("lRemoveAll")
    public Result<Long> lRemoveAll(String key, int value) {
        return Result.o(redisTemp.lDeleteAll(key, value));
    }

    /**
     * <h3>指定值第一次出现的下标</h3>
     * 存在值 ["a","b","a","c",]<br>
     * http://localhost:8080/list/lIndexOf?key=a&value=a<br>
     * 返回 0<br>
     * http://localhost:8080/list/lIndexOf?key=a&value=d<br>
     * 返回 null<br>
     */
    @GetMapping("lIndexOf")
    public Result<Long> lIndexOf(String key, String value) {
        return Result.o(redisTemp.lIndexOfFirst(key, value));
    }

    /**
     * <h3>指定值最后一次出现的下标</h3>
     * 存在值 ["a","b","a","c",]<br>
     * http://localhost:8080/list/lIndexOf?key=a&value=a<br>
     * 返回 2<br>
     * http://localhost:8080/list/lIndexOf?key=a&value=d<br>
     * 返回 null<br>
     */
    @GetMapping("lLastIndexOf")
    public Result<Long> lLastIndexOf(String key, String value) {
        return Result.o(redisTemp.lIndexOfLast(key, value));
    }

    /**
     * <h3>删除并返回左侧的值</h3>
     * http://localhost:8080/list/lLeftPop?key=a<br>
     * 存在键 a 值 [111,222,333,444,555]<br>
     * 变成 [222,333,444,555]<br>
     * 返回 111<br>
     * http://localhost:8080/list/lLeftPop?key=b<br>
     * 返回 null
     */
    @GetMapping("lLeftPop")
    public Result<Object> lLeftPop(String key) {
        return Result.o(redisTemp.lLeftPop(key));
    }

    /**
     * <h3>删除并返回左侧的值，并阻塞指定时间(秒)</h3>
     * 当指定键不存在值时，客户端将等待指定时间查询数据，查询到了返回数据，查询不到返回null
     * http://localhost:8080/list/lLeftPopSecond?key=a&timeout=20<br>
     */
    @GetMapping("lLeftPopSecond")
    public Result<Object> lLeftPop(String key, long timeout) {
        return Result.o(redisTemp.lLeftPop(key, timeout));
    }

    /**
     * <h3>删除并返回右侧的值</h3>
     * http://localhost:8080/list/lRightPop?key=a<br>
     * 存在值 [111,222,333,444,555]<br>
     * 变成 [111,222,333,444]<br>
     * 返回 555
     */
    @GetMapping("lRightPop")
    public Result<Object> lRightPop(String key) {
        return Result.o(redisTemp.lRightPop(key));
    }

    /**
     * <h3>删除并返回右侧的值，并阻塞指定时间(秒)</h3>
     * 当指定键不存在值时，客户端将等待指定时间查询数据，查询到了返回数据，查询不到返回null
     * http://localhost:8080/list/lRightPopSecond?key=a&timeout=20<br>
     */
    @GetMapping("lRightPopSecond")
    public Result<Object> lRightPop(String key, long timeout) {
        return Result.o(redisTemp.lRightPop(key, timeout));
    }

    /**
     * <h3>从sourceKey的右侧删除，添加到destinationKey的左侧，并返回这个值</h3>
     * http://localhost:8080/list/lRightPopAndLeftPush?sourceKey=a&destinationKey=b<br>
     * 存在键 a 值 [111,222,333]<br>
     * 存在键 b 值 [444,555,666]<br>
     * 变成键 a 值 [111,222]<br>
     * 变成键 b 值 [333,444,555,666]<br>
     * 返回 333
     */
    @GetMapping("lRightPopAndLeftPush")
    public Result<Object> lRightPopAndLeftPush(String sourceKey, String destinationKey) {
        return Result.o(redisTemp.lRightPopAndLeftPush(sourceKey, destinationKey));
    }

    /**
     * <h3>从sourceKey的右侧删除，添加到destinationKey的左侧，并返回这个值，并阻塞指定时间(秒)</h3>
     * 当指定键不存在值时，客户端将等待指定时间查询数据，查询到了返回数据，查询不到返回null
     * http://localhost:8080/list/lRightPopAndLeftPushSecond?sourceKey=a&destinationKey=b&timeout=5<br>
     */
    @GetMapping("lRightPopAndLeftPushSecond")
    public Result<Object> lRightPopAndLeftPush(String sourceKey, String destinationKey, long timeout) {
        return Result.o(redisTemp.lRightPopAndLeftPush(sourceKey, destinationKey, timeout));
    }

    /**
     * <h3>从sourceKey的右侧删除，添加到destinationKey的右侧，并返回这个值</h3>
     * http://localhost:8080/list/lMove?sourceKey=a&destinationKey=b<br>
     * 存在键 a 值 [111,222,333]<br>
     * 存在键 b 值 [444,555,666]<br>
     * 变成键 a 值 [111,222]<br>
     * 变成键 b 值 [444,555,666,333]<br>
     * 返回 333
     */
    @GetMapping("lMove")
    public Result<Object> lMove(String sourceKey, String destinationKey) {
        return Result.o(redisTemp.lMove(sourceKey, false, destinationKey, false));
    }

    /**
     * <h3>从sourceKey的右侧删除，添加到destinationKey的右侧，并返回这个值，并阻塞指定时间(秒)</h3>
     * 当指定键不存在值时，客户端将等待指定时间查询数据，查询到了返回数据，查询不到返回null
     * http://localhost:8080/list/lMove2?sourceKey=a&destinationKey=b&timeout=5<br>
     */
    @GetMapping("lMove2")
    public Result<Object> lMove2(String sourceKey, String destinationKey, long timeout) {
        return Result.o(redisTemp.lMove(sourceKey, false, destinationKey, false, timeout));
    }

}
