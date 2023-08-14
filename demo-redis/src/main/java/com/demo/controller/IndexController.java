package com.demo.controller;

import com.demo.entity.pojo.Result;
import com.demo.tool.RedisTemp;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.connection.SortParameters;
import org.springframework.data.redis.core.query.SortQuery;
import org.springframework.data.redis.core.query.SortQueryBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Set;

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
@AllArgsConstructor
public class IndexController {

    private final RedisTemp redisTemp;

    /**
     * <h3>拷贝</h3>
     * 实际存在key有a 值为123 b值为45<br>
     * POST /copy?sourceKey=a&targetKey=b&replace=false<br>
     * false<br>
     * POST /copy?sourceKey=a&targetKey=c&replace=false<br>
     * true 新增key c 值123<br>
     * POST /copy?sourceKey=a&targetKey=b&replace=true<br>
     * true key b 值改变为123
     */
    @PostMapping("copy")
    public Result<Boolean> copy(String sourceKey, String targetKey, boolean replace) {
        return Result.o(redisTemp.copy(sourceKey, targetKey, replace));
    }

    /**
     * <h3>key是否存在</h3>
     * POST /exists?key=a<br>
     * 存在 true<br>
     * 不存在 false
     */
    @PostMapping("exists")
    public Result<Boolean> exists(String key) {
        return Result.o(redisTemp.exists(key));
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
    public Result<Long> existsCount(@RequestBody List<String> keys) {
        return Result.o(redisTemp.existsCount(keys));
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
    public Result<Long> existsCount(String[] keys) {
        return Result.o(redisTemp.existsCount(keys));
    }

    /**
     * <h3>删除key</h3>
     * POST /delete?key=a<br>
     * 存在 true<br>
     * 不存在 false
     */
    @PostMapping("delete")
    public Result<Boolean> delete(String key) {
        return Result.o(redisTemp.delete(key));
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
    public Result<Long> delete(@RequestBody List<String> keys) {
        return Result.o(redisTemp.deleteMulti(keys));
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
    public Result<Long> delete(String[] keys) {
        return Result.o(redisTemp.deleteMulti(keys));
    }

    /**
     * <h3>获取失效时间(秒，-1不过期，-2不存在)</h3>
     * POST /getExpire?key=a<br>
     * 不过期 -1<br>
     * 不存在 -2<br>
     * 还有23秒过期 23
     */
    @PostMapping("getExpire")
    public Result<Long> getExpire(String key) {
        return Result.o(redisTemp.getExpire(key));
    }

    /**
     * <h3>dump</h3>
     * POST /dump?key=a<br>
     * 字符串"abc" [0, 5, 34, 97, 98, 99, 34, 9, 0, 1, -3, -98, 28, 120, -86, 35, 37]
     */
    @PostMapping("dump")
    public Result<byte[]> dump(String key) {
        return Result.o(redisTemp.dump(key));
    }

    /**
     * <h3>restore</h3>
     * 实际存在key有a<br>
     * POST /restore?key=a&timeout=0&replace=false<br>
     * 报错<br>
     * POST /restore?key=a&timeout=0&replace=true<br>
     * 内容被替换为字符串"abc"，ttl为-1<br>
     * POST /restore?key=b&timeout=100&replace=false<br>
     * 新建key为b，内容为字符串"abc"，ttl为100
     */
    @PostMapping("restore")
    public Result restore(String key, long timeout, boolean replace) {
        redisTemp.restore(key, new byte[]{0, 5, 34, 97, 98, 99, 34, 9, 0, 1, -3, -98, 28, 120, -86, 35, 37}, timeout, replace);
        return Result.o();
    }

    /**
     * <h3>指定为持久数据(移除失效时间)</h3>
     * key为a类型为list数据有"3","1","d","0"<br>
     * POST /sort?key=a&asc=true<br>
     * ["0","1","3","d"]<br>
     * POST /sort?key=a&asc=false<br>
     * ["d","3","1","0"]
     */
    @PostMapping("sort")
    public Result<List<Object>> sort(String key, boolean asc) {
        SortQuery<String> query = SortQueryBuilder.sort(key).alphabetical(true).order(asc ? SortParameters.Order.ASC : SortParameters.Order.DESC).build();
        return Result.o(redisTemp.sort(query));
    }

    /**
     * <h3>指定为持久数据(移除失效时间)</h3>
     * POST /persist?key=a<br>
     * 还有10秒过期 true<br>
     * 不过期/不存在 false
     */
    @PostMapping("persist")
    public Result<Boolean> persist(String key) {
        return Result.o(redisTemp.persist(key));
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
    public Result<Boolean> expire(String key, long timeout) {
        return Result.o(redisTemp.expire(key, timeout));
    }

    /**
     * <h3>指定失效日期</h3>
     * POST /expireAt?key=a&timeout=2022/01/01 00:00:00<br>
     * 在2022/01/01 00:00:00时失效
     */
    @PostMapping("expireAt")
    public Result<Boolean> expireAt(String key, Date timeout) {
        return Result.o(redisTemp.expireAt(key, timeout));
    }

    /**
     * <h3>非阻塞异步删除key</h3>
     * POST /unlink?key=a<br>
     * 存在 true<br>
     * 不存在 false
     */
    @PostMapping("unlink")
    public Result<Boolean> unlink(String key) {
        return Result.o(redisTemp.unlink(key));
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
    public Result<Long> unlink(List<String> keys) {
        return Result.o(redisTemp.unlinkMulti(keys));
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
    public Result<Long> unlink(String[] keys) {
        return Result.o(redisTemp.unlinkMulti(keys));
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
    public Result<DataType> type(String key) {
        return Result.o(redisTemp.type(key));
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
    public Result<Set<String>> keys(String pattern) {
        return Result.o(redisTemp.keys(pattern));
    }

    /**
     * <h3>返回一个随机的key</h3>
     * POST /randomKey<br>
     * 实际存在key有a/b/c c<br>
     * 不存在任何键 null
     */
    @PostMapping("randomKey")
    public Result<String> randomKey() {
        return Result.o(redisTemp.randomKey());
    }

    /**
     * <h3>重命名key</h3>
     * POST /rename?oldKey=a&newKey=b
     * 存在a，不存在b<br>
     * 存在ab true b被覆盖<br>
     * 不存在a 报错
     */
    @PostMapping("rename")
    public Result rename(String oldKey, String newKey) {
        redisTemp.rename(oldKey, newKey);
        return Result.o();
    }

    /**
     * <h3>重命名key，仅当newKey不存在时</h3>
     * POST /renameIfAbsent?oldKey=a&newKey=b<br>
     * 存在a，不存在b true<br>
     * 存在ab false<br>
     * 不存在a false
     */
    @PostMapping("renameIfAbsent")
    public Result<Boolean> renameIfAbsent(String oldKey, String newKey) {
        return Result.o(redisTemp.renameIfAbsent(oldKey, newKey));
    }

    /**
     * <h3>将key移动到指定索引的数据库</h3>
     * POST /move?key=a&dbIndex=1<br>
     * 存在a和db1 true<br>
     * 不存在a或db1 false
     */
    @PostMapping("move")
    public Result<Boolean> move(String key, int dbIndex) {
        return Result.o(redisTemp.move(key, dbIndex));
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
    @GetMapping("scan")
    public Result<List<String>> scan(String match) {
        return Result.o(redisTemp.scan(match));
    }

}
