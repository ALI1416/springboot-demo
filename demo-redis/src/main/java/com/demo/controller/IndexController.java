package com.demo.controller;

import com.demo.entity.pojo.Result;
import com.demo.util.RedisUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * <h1>通用操作</h1>
 *
 * <p>
 * createDate 2021/09/09 10:35:04
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@RestController
public class IndexController {

    /**
     * <h3></h3>
     * POST /copy?sourceKey=a&targetKey=b&replace=false<br>
     * 成功 true<br>
     * 失败 false
     */
    @PostMapping("copy")
    public Result copy(String sourceKey, String targetKey, boolean replace) {
        return Result.o(RedisUtils.copy(sourceKey, targetKey, replace));
    }

    /**
     * <h3>key是否存在</h3>
     * POST /exists?key=a<br>
     * 存在 true<br>
     * 不存在 false
     */
    @PostMapping("exists")
    public Result exists(String key) {
        return Result.o(RedisUtils.exists(key));
    }

    /**
     * <h3>集合中存在的key的数量</h3>
     * 实际存在key有a/b/c<br>
     * POST /existsCount<br>
     * body JSON ["a","b","c"] 3<br>
     * body JSON ["a","b","d"] 2<br>
     * body JSON ["a","b","c","c"] 4<br>
     * body JSON ["d"] 0
     */
    @PostMapping("existsCount")
    public Result existsCount(@RequestBody List<String> keys) {
        return Result.o(RedisUtils.existsCount(keys));
    }

    /**
     * <h3>集合中存在的key的数量</h3>
     * 实际存在key有a/b/c<br>
     * POST /existsCount2?keys=a&keys=b&keys=c 3<br>
     * POST /existsCount2?keys=a&keys=b&keys=d 2<br>
     * POST /existsCount2?keys=a&keys=b&keys=c&keys=c 4<br>
     * POST /existsCount2?keys=d 0
     */
    @PostMapping("existsCount2")
    public Result existsCount(String[] keys) {
        return Result.o(RedisUtils.existsCount(keys));
    }

    /**
     * <h3>删除key</h3>
     * POST /delete?key=a<br>
     * 存在 true<br>
     * 不存在 false
     */
    @PostMapping("delete")
    public Result delete(String key) {
        return Result.o(RedisUtils.delete(key));
    }

    /**
     * <h3>删除多个key</h3>
     * 实际存在key有a/b/c<br>
     * POST /deleteList<br>
     * body JSON ["a","b"] 2<br>
     * body JSON ["a","d"] 1<br>
     * body JSON ["c","c"] 1<br>
     * body JSON ["d","e"] 0
     */
    @PostMapping("deleteList")
    public Result delete(@RequestBody List<String> keys) {
        return Result.o(RedisUtils.deleteMulti(keys));
    }

    /**
     * <h3>删除多个key</h3>
     * 实际存在key有a/b/c<br>
     * POST /deleteArray?keys=a&keys=b 2<br>
     * POST /deleteArray?keys=a&keys=d 1<br>
     * POST /deleteArray?keys=c&keys=c 1<br>
     * POST /deleteArray?keys=d&keys=e 0
     */
    @PostMapping("deleteArray")
    public Result delete(String[] keys) {
        return Result.o(RedisUtils.deleteMulti(keys));
    }

    /**
     * <h3>获取失效时间(秒，-1不过期，-2不存在)</h3>
     * POST /getExpire?key=a<br>
     * 不过期 -1<br>
     * 不存在 -2<br>
     * 还有23秒过期 23
     */
    @PostMapping("getExpire")
    public Result getExpire(String key) {
        return Result.o(RedisUtils.getExpire(key));
    }

    /**
     * <h3>指定为持久数据(移除失效时间)</h3>
     * POST /persist?key=a<br>
     * 还有10秒过期 true<br>
     * 不过期/不存在 false
     */
    @PostMapping("persist")
    public Result persist(String key) {
        return Result.o(RedisUtils.persist(key));
    }

    /**
     * <h3>指定失效时间(秒，<=0删除)</h3>
     * POST /expire?key=a&timeout=100<br>
     * 还有10秒过期/不过期 true<br>
     * 不存在 false<br>
     * POST /expire?key=a&timeout=0<br>
     * POST /expire?key=a&timeout=-2<br>
     * 被删除
     */
    @PostMapping("expire")
    public Result expire(String key, long timeout) {
        return Result.o(RedisUtils.expire(key, timeout));
    }

    /**
     * <h3>指定失效日期</h3>
     * POST /expireAt?key=a&timeout=2022/01/01 00:00:00<br>
     * 在2022/01/01 00:00:00时失效
     */
    @PostMapping("expireAt")
    public Result expireAt(String key, Date timeout) {
        return Result.o(RedisUtils.expireAt(key, timeout));
    }

    /**
     * <h3>非阻塞异步删除key</h3>
     * POST /unlink?key=a<br>
     * 存在 true<br>
     * 不存在 false
     */
    @PostMapping("unlink")
    public Result unlink(String key) {
        return Result.o(RedisUtils.unlink(key));
    }

    /**
     * <h3>非阻塞异步删除多个key</h3>
     * 实际存在key有a/b/c<br>
     * POST /unlinkList<br>
     * body JSON ["a","b"] 2<br>
     * body JSON ["a","d"] 1<br>
     * body JSON ["c","c"] 1<br>
     * body JSON ["d","e"] 0
     */
    @PostMapping("unlinkList")
    public Result unlink(List<String> keys) {
        return Result.o(RedisUtils.unlinkMulti(keys));
    }

    /**
     * <h3>非阻塞异步删除多个key</h3>
     * 实际存在key有a/b/c<br>
     * POST /unlinkArray?keys=a&keys=b 2<br>
     * POST /unlinkArray?keys=a&keys=d 1<br>
     * POST /unlinkArray?keys=c&keys=c 1<br>
     * POST /unlinkArray?keys=d&keys=e 0
     */
    @PostMapping("unlinkArray")
    public Result unlink(String[] keys) {
        return Result.o(RedisUtils.unlinkMulti(keys));
    }

    /**
     * <h3>返回存储的数据类型</h3>
     * POST /type?key=a<br>
     * 是String类型 STRING</h3>
     * 是List类型 LIST</h3>
     * 是Set类型 SET</h3>
     * 是ZSet类型 ZSET</h3>
     * 是Hash类型 HASH</h3>
     * 不存在 NONE
     */
    @PostMapping("type")
    public Result type(String key) {
        return Result.o(RedisUtils.type(key));
    }

    /**
     * <h3>模糊查询</h3>
     * 实际存在key有a/aabbcc/abc/abd/b/bc/bd/c/[]<br>
     * POST /keys?pattern=a 不匹配字符[a]<br>
     * POST /keys?pattern=b? 右侧匹配1个字符[bc,bd]<br>
     * POST /keys?pattern=*c 左侧匹配0+个字符[aabbcc,abc,bc,c]<br>
     * POST /keys?pattern=*b* 两侧匹配0+个字符[aabbcc,abc,abd,b,bc,bd]<br>
     * POST /keys?pattern=[abd] 匹配1个指定字符[a,b]<br>
     * POST /keys?pattern=[^abd] 不匹配1个指定字符[c]<br>
     * POST /keys?pattern=[A-z] 匹配1个指定字符[a,b,c]<br>
     * POST /keys?pattern=\[* 转义匹配匹配1个指定字符[[]]
     */
    @PostMapping("keys")
    public Result keys(String pattern) {
        return Result.o(RedisUtils.keys(pattern));
    }

    /**
     * <h3>返回一个随机的key</h3>
     * POST /randomKey<br>
     * 实际存在key有a/b/c c<br>
     * 不存在任何键 null
     */
    @PostMapping("randomKey")
    public Result randomKey() {
        return Result.o(RedisUtils.randomKey());
    }

    /**
     * <h3>重命名key</h3>
     * POST /rename?oldKey=a&newKey=b
     * 存在a，不存在b true<br>
     * 存在ab true b被覆盖<br>
     * 不存在a false
     */
    @PostMapping("rename")
    public Result rename(String oldKey, String newKey) {
        return Result.o(RedisUtils.rename(oldKey, newKey));
    }

    /**
     * <h3>重命名key，仅当newKey不存在时</h3>
     * POST /renameIfAbsent?oldKey=a&newKey=b<br>
     * 存在a，不存在b true<br>
     * 存在ab false<br>
     * 不存在a false
     */
    @PostMapping("renameIfAbsent")
    public Result renameIfAbsent(String oldKey, String newKey) {
        return Result.o(RedisUtils.renameIfAbsent(oldKey, newKey));
    }

    /**
     * <h3>将key移动到指定索引的数据库</h3>
     * POST /move?key=a&dbIndex=1<br>
     * 存在a和db1 true<br>
     * 不存在a或db1 false
     */
    @PostMapping("move")
    public Result move(String key, int dbIndex) {
        return Result.o(RedisUtils.move(key, dbIndex));
    }

    /**
     * <h3>模糊查询</h3>
     * 实际存在key有a/aabbcc/abc/abd/b/bc/bd/c/[]<br>
     * POST /scan?match=a 不匹配字符[a]<br>
     * POST /scan?match=b? 右侧匹配1个字符[bc,bd]<br>
     * POST /scan?match=*c 左侧匹配0+个字符[aabbcc,abc,bc,c]<br>
     * POST /scan?match=*b* 两侧匹配0+个字符[aabbcc,abc,abd,b,bc,bd]<br>
     * POST /scan?match=[abd] 匹配1个指定字符[a,b]<br>
     * POST /scan?match=[^abd] 不匹配1个指定字符[c]<br>
     * POST /scan?match=[A-z] 匹配1个指定字符[a,b,c]<br>
     * POST /scan?match=\[* 转义匹配匹配1个指定字符[[]]
     */
    @PostMapping("scan")
    public Result scan(String match) {
        return Result.o(RedisUtils.scan(match));
    }

}
