package com.demo.controller;

import com.demo.entity.pojo.Result;
import com.demo.util.RedisUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
     * 存在键 a 值 [111,222,333,444,555]
     * POST /list/lGet?key=a&index=0 返回111<br>
     * POST /list/lGet?key=a&index=2 返回333<br>
     * POST /list/lGet?key=a&index=-1 返回555<br>
     * POST /list/lGet?key=a&index=-2 返回444<br>
     * POST /list/lGet?key=a&index=10 返回null<br>
     * POST /list/lGet?key=b&index=0 返回null
     */
    @PostMapping("lGet")
    public Result lGet(String key, long index) {
        return Result.o(RedisUtils.lGet(key, index));
    }

    /**
     * <h3>获取第一个</h3>
     * 存在键 a 值 [111,222,333,444,555]<br>
     * POST /list/lGetFirst?key=a 返回111<br>
     * POST /list/lGetFirst?key=b 返回null
     */
    @PostMapping("lGetFirst")
    public Result lGetFirst(String key) {
        return Result.o(RedisUtils.lGetFirst(key));
    }

    /**
     * <h3>获取最后一个</h3>
     * 存在键 a 值 [111,222,333,444,555]<br>
     * POST /list/lGetLast?key=a 返回555<br>
     * POST /list/lGetFirst?key=b 返回null
     */
    @PostMapping("lGetLast")
    public Result lGetLast(String key) {
        return Result.o(RedisUtils.lGetLast(key));
    }

    /**
     * <h3>获取指定下标的值</h3>
     * 存在键 a 值 [111,222,333,444,555]<br>
     * POST /list/lGetList?key=a&start=1&end=3 返回[222,333,444]<br>
     * POST /list/lGetList?key=a&start=1&end=-1 返回[222,333,444,555]<br>
     * POST /list/lGetList?key=a&start=1&end=100 返回[222,333,444,555]<br>
     * POST /list/lGetList?key=b&start=1&end=2 返回[]
     */
    @PostMapping("lGetList")
    public Result lGetList(String key, long start, long end) {
        return Result.o(RedisUtils.lGetMulti(key, start, end));
    }

    /**
     * <h3>获取全部</h3>
     * 存在值 [111,222,333,444,555]<br>
     * POST /list/lGetAll?key=a 返回[111,222,333,444,555]
     * POST /list/lGetAll?key=b 返回[]
     */
    @PostMapping("lGetAll")
    public Result lGetAll(String key) {
        return Result.o(RedisUtils.lGetAll(key));
    }

    /**
     * <h3>修剪(只要start和end之间的值)</h3>
     * POST /list/lTrim?key=a&start=1&end=3<br>
     * 存在值 [111,222,333,444,555]<br>
     * 还剩下 [222,333,444]<br>
     * POST /list/lTrim?key=a&start=3&end=-1<br>
     * 存在值 [111,222,333,444,555]<br>
     * 还剩下 [444,555]
     */
    @PostMapping("lTrim")
    public Result lTrim(String key, long start, long end) {
        RedisUtils.lTrim(key, start, end);
        return Result.o();
    }

    /**
     * <h3>获取列表的长度</h3>
     * POST /list/lSize?key=a<br>
     * 存在值 [111,222,333,444,555] 5<br>
     */
    @PostMapping("lSize")
    public Result lSize(String key) {
        return Result.o(RedisUtils.lSize(key));
    }

    /**
     * <h3>添加到左侧</h3>
     * POST /list/lLeftPush?key=a&value=abcd<br>
     * 存在键 a 值 [111,222,333,444,555]<br>
     * 变成 ["abcd",111,222,333,444,555]<br>
     * 返回 6<br>
     * POST /list/lLeftPush?key=b&value=abcd<br>
     * 不存在键 b<br>
     * 变成 ["abcd"]<br>
     * 返回 1
     */
    @PostMapping("lLeftPush")
    public Result lLeftPush(String key, String value) {
        return Result.o(RedisUtils.lLeftPush(key, value));
    }

    /**
     * <h3>添加到左侧</h3>
     * POST /list/lLeftPushAllArray?key=a&value=a&value=b&value=c<br>
     * 存在值 [111,222,333,444,555]<br>
     * 变成 ["c","b","a",111,222,333,444,555]<br>
     * 返回 8
     */
    @PostMapping("lLeftPushAllArray")
    public Result lLeftPushAll(String key, String[] value) {
        return Result.o(RedisUtils.lLeftPushMultiArray(key, value));
    }

    /**
     * <h3>添加到左侧</h3>
     * POST /list/lLeftPushAllList?key=a<br>
     * body JSON ["a","b","c"]<br>
     * 存在值 [111,222,333,444,555]<br>
     * 变成 ["c","b","a",111,222,333,444,555]<br>
     * 返回 8
     */
    @PostMapping("lLeftPushAllList")
    public Result lLeftPushAll(String key, @RequestBody List<Object> value) {
        return Result.o(RedisUtils.lLeftPushMulti(key, value));
    }

    /**
     * <h3>当列表存在时，添加到左侧</h3>
     * POST /list/lLeftPushIfPresent?key=a&value=a<br>
     * 存在键 a 值 [111,222,333,444,555]<br>
     * 变成 ["a",111,222,333,444,555]<br>
     * 返回 6<br>
     * POST /list/lLeftPushIfPresent?key=b&value=a<br>
     * 不存在键 b<br>
     * 返回 0
     */
    @PostMapping("lLeftPushIfPresent")
    public Result lLeftPushIfPresent(String key, String value) {
        return Result.o(RedisUtils.lLeftPushIfPresent(key, value));
    }

    /**
     * <h3>添加到指定值的左侧</h3>
     * 存在值 [111,222,333,444,333]<br>
     * POST /list/lLeftPushOfValue?key=a&pivot=333&value=dd<br>
     * 变成 [111,222,"dd",333,444,333]<br>
     * 返回 6<br>
     * POST /list/lLeftPushOfValue?key=a&pivot=888&value=dd<br>
     * 不变<br>
     * 返回 -1
     */
    @PostMapping("lLeftPushOfValue")
    public Result lLeftPush(String key, int pivot, String value) {
        return Result.o(RedisUtils.lInsertLeft(key, pivot, value));
    }

    /**
     * <h3>添加到右侧</h3>
     * POST /list/lRightPush?key=a&value=asd<br>
     * 存在值 [111,222,333,444,555]<br>
     * 变成 [111,222,333,444,555,"asd"]<br>
     * 返回 6
     */
    @PostMapping("lRightPush")
    public Result lRightPush(String key, String value) {
        return Result.o(RedisUtils.lRightPush(key, value));
    }

    /**
     * <h3>添加到右侧</h3>
     * POST /list/lRightPushAllArray?key=a&value=a&value=b&value=c<br>
     * 存在值 [111,222,333,444,555]<br>
     * 变成 [111,222,333,444,555,"a","b","c"]<br>
     * 返回 8
     */
    @PostMapping("lRightPushAllArray")
    public Result lRightPushAll(String key, String[] value) {
        return Result.o(RedisUtils.lRightPushMultiArray(key, value));
    }

    /**
     * <h3>添加到右侧</h3>
     * POST /list/lRightPushAllList?key=a<br>
     * body JSON ["a","b","c"]
     * 存在值 [111,222,333,444,555]<br>
     * 变成 [111,222,333,444,555,"a","b","c"]<br>
     * 返回 8
     */
    @PostMapping("lRightPushAllList")
    public Result lRightPushAll(String key, @RequestBody List<Object> value) {
        return Result.o(RedisUtils.lRightPushMulti(key, value));
    }

    /**
     * <h3>当列表存在时，添加到右侧</h3>
     * POST /list/lRightPushIfPresent?key=a&value=ss<br>
     * 存在值 [111,222,333,444,555]<br>
     * 变成 [111,222,333,444,555,"ss"]<br>
     * 返回 6
     */
    @PostMapping("lRightPushIfPresent")
    public Result lRightPushIfPresent(String key, String value) {
        return Result.o(RedisUtils.lRightPushIfPresent(key, value));
    }

    /**
     * <h3>添加到指定值的右侧</h3>
     * POST /list/lRightPushOfValue?key=a&pivot=333&value=dd<br>
     * 存在值 [111,222,333,444,555]<br>
     * 变成 [111,222,333,"dd",444,555]<br>
     * 返回 6<br>
     * POST /list/lLeftPushOfValue?key=a&pivot=888&value=dd<br>
     * 不变<br>
     * 返回 -1
     */
    @PostMapping("lRightPushOfValue")
    public Result lRightPush(String key, int pivot, String value) {
        return Result.o(RedisUtils.lInsertRight(key, pivot, value));
    }

    /**
     * <h3>插入到指定位置</h3>
     * 存在值 [111,222,333,444,555]<br>
     * POST /list/lSet?key=a&index=0&value=aa<br>
     * 变成 ["aa",222,333,444,555]<br>
     * 返回 true<br>
     * POST /list/lSet?key=a&index=2&value=aa<br>
     * 变成[111,222,"aa",444,555]<br>
     * 返回 true<br>
     * POST /list/lSet?key=a&index=-1&value=aa<br>
     * 变成[111,222,333,444,"aa"]<br>
     * 返回 true<br>
     * POST /list/lSet?key=a&index=5&value=aa<br>
     * 返回 false
     */
    @PostMapping("lSet")
    public Result lSet(String key, long index, String value) {
        return Result.o(RedisUtils.lSet(key, index, value));
    }

    /**
     * <h3>删除第count次出现的值</h3>
     * 存在值 [111,222,333,222,444,222]<br>
     * POST /list/lRemove?key=a&count=0&value=222
     * 变成 [111,333,444] 全部删除<br>
     * 返回 3<br>
     * POST /list/lRemove?key=a&count=1&value=222<br>
     * 变成 [111,333,222,444,222] 左边删1个<br>
     * 返回 1<br>
     * POST /list/lRemove?key=a&count=2&value=222<br>
     * 变成 [111,333,444,222] 左边删2个<br>
     * 返回 1<br>
     * POST /list/lRemove?key=a&count=-1&value=222<br>
     * 变成 [111,222,333,222,444] 右边删1个<br>
     * 返回 1<br>
     * POST /list/lRemove?key=a&count=-2&value=222<br>
     * 变成 [111,222,333,444] 右边删2个<br>
     * 返回 2<br>
     * POST /list/lRemove?key=a&count=-2&value=666<br>
     * 不变<br>
     * 返回 0
     */
    @PostMapping("lRemove")
    public Result lRemove(String key, long count, int value) {
        return Result.o(RedisUtils.lDelete(key, count, value));
    }

    /**
     * <h3>删除左侧第一次出现的值</h3>
     * 存在值 [111,222,333,222,444,222]<br>
     * POST /list/lRemoveLeft?key=a&value=222<br>
     * 变成 [111,333,222,444,222]<br>
     * 返回 1
     */
    @PostMapping("lRemoveLeft")
    public Result lRemoveLeft(String key, int value) {
        return Result.o(RedisUtils.lDeleteLeft(key, value));
    }

    /**
     * <h3>删除右侧第一次出现的值</h3>
     * 存在值 [111,222,333,222,444,222]<br>
     * POST /list/lRemoveRight?key=a&value=222<br>
     * 变成 [111,222,333,222,444]<br>
     * 返回 1
     */
    @PostMapping("lRemoveRight")
    public Result lRemoveLast(String key, int value) {
        return Result.o(RedisUtils.lDeleteRight(key, value));
    }

    /**
     * <h3>删除全部出现的值</h3>
     * 存在值 [111,222,333,222,444,222]<br>
     * POST /list/lRemoveAll?key=a&value=222<br>
     * 变成 [111,333,444]<br>
     * 返回 3<br>
     */
    @PostMapping("lRemoveAll")
    public Result lRemoveAll(String key, int value) {
        return Result.o(RedisUtils.lDeleteAll(key, value));
    }

    /**
     * <h3>指定值第一次出现的下标(Redis版本需要6.0.6及以上)</h3>
     * POST /list/lIndexOf?key=a&value=a<br>
     * 无法测试
     */
    @PostMapping("lIndexOf")
    public Result lIndexOf(String key, String value) {
        return Result.o(RedisUtils.lIndexOf(key, value));
    }

    /**
     * <h3>指定值最后一次出现的下标(Redis版本需要6.0.6及以上)</h3>
     * POST /list/lLastIndexOf?key=a&value=a<br>
     * 无法测试
     */
    @PostMapping("lLastIndexOf")
    public Result lLastIndexOf(String key, String value) {
        return Result.o(RedisUtils.lLastIndexOf(key, value));
    }

    /**
     * <h3>删除并返回左侧的值</h3>
     * POST /list/lLeftPop?key=a<br>
     * 存在键 a 值 [111,222,333,444,555]<br>
     * 变成 [222,333,444,555]<br>
     * 返回 111<br>
     * POST /list/lLeftPop?key=b<br>
     * 返回 null
     */
    @PostMapping("lLeftPop")
    public Result lLeftPop(String key) {
        return Result.o(RedisUtils.lLeftPop(key));
    }

    /**
     * <h3>删除并返回左侧的值，并阻塞指定时间(秒)</h3>
     * 当指定键不存在值时，客户端将等待指定时间查询数据，查询到了返回数据，查询不到返回null
     * POST /list/lLeftPopSecond?key=a&timeout=20<br>
     */
    @PostMapping("lLeftPopSecond")
    public Result lLeftPop(String key, long timeout) {
        return Result.o(RedisUtils.lLeftPop(key, timeout));
    }

    /**
     * <h3>删除并返回右侧的值</h3>
     * POST /list/lRightPop?key=a<br>
     * 存在值 [111,222,333,444,555]<br>
     * 变成 [111,222,333,444]<br>
     * 返回 555
     */
    @PostMapping("lRightPop")
    public Result lRightPop(String key) {
        return Result.o(RedisUtils.lRightPop(key));
    }

    /**
     * <h3>删除并返回右侧的值，并阻塞指定时间(秒)</h3>
     * 当指定键不存在值时，客户端将等待指定时间查询数据，查询到了返回数据，查询不到返回null
     * POST /list/lRightPopSecond?key=a&timeout=20<br>
     */
    @PostMapping("lRightPopSecond")
    public Result lRightPop(String key, long timeout) {
        return Result.o(RedisUtils.lRightPop(key, timeout));
    }

    /**
     * <h3>从sourceKey的右侧删除，添加到destinationKey的左侧，并返回这个值</h3>
     * POST /list/lRightPopAndLeftPush?sourceKey=a&destinationKey=b<br>
     * 存在键 a 值 [111,222,333]<br>
     * 存在键 b 值 [444,555,666]<br>
     * 变成键 a 值 [111,222]<br>
     * 变成键 b 值 [333,444,555,666]<br>
     * 返回 333
     */
    @PostMapping("lRightPopAndLeftPush")
    public Result lRightPopAndLeftPush(String sourceKey, String destinationKey) {
        return Result.o(RedisUtils.lRightPopAndLeftPush(sourceKey, destinationKey));
    }

    /**
     * <h3>从sourceKey的右侧删除，添加到destinationKey的左侧，并返回这个值，并阻塞指定时间(秒)</h3>
     * 当指定键不存在值时，客户端将等待指定时间查询数据，查询到了返回数据，查询不到返回null
     * POST /list/lRightPopAndLeftPushSecond?sourceKey=a&destinationKey=b&timeout=20<br>
     */
    @PostMapping("lRightPopAndLeftPushSecond")
    public Result lRightPopAndLeftPush(String sourceKey, String destinationKey, long timeout) {
        return Result.o(RedisUtils.lRightPopAndLeftPush(sourceKey, destinationKey, timeout));
    }

}
