package com.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.RedisSystemException;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.ConvertingCursor;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * <h1>Redis工具类</h1>
 *
 * <p>
 * createDate 2020/12/04 15:57:36
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Component
public class RedisUtils {

    public static final TimeUnit SECONDS = TimeUnit.SECONDS;
    public static RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public RedisUtils(RedisTemplate<String, Object> redisTemplate) {
        RedisUtils.redisTemplate = redisTemplate;
    }

    /* ==================== 通用操作 ==================== */
    //region 通用操作

    /**
     * 删除key(del)
     *
     * @param key 键
     * @return 是否成功(管道或事务中使用为null)
     */
    public static Boolean delete(@NonNull String key) {
        return redisTemplate.delete(key);
    }

    /**
     * 删除多个key(del)
     *
     * @param keys 键
     * @return 成功个数(管道或事务中使用为null)
     */
    public static Long deleteMulti(@NonNull Collection<String> keys) {
        return redisTemplate.delete(keys);
    }

    /**
     * 删除多个key(del)
     *
     * @param keys 键
     * @return 成功个数(管道或事务中使用为null)
     */
    public static Long deleteMultiArray(@NonNull String... keys) {
        return redisTemplate.delete(Arrays.asList(keys));
    }

    /**
     * 非阻塞异步删除key(unlink)
     *
     * @param key 键
     * @return 是否成功(管道或事务中使用为null)
     */
    public static Boolean unlink(@NonNull String key) {
        return redisTemplate.unlink(key);
    }

    /**
     * 非阻塞异步删除多个key(unlink)
     *
     * @param keys 键
     * @return 成功个数(管道或事务中使用为null)
     */
    public static Long unlinkMulti(@NonNull Collection<String> keys) {
        return redisTemplate.unlink(keys);
    }

    /**
     * 非阻塞异步删除多个key(unlink)
     *
     * @param keys 键
     * @return 成功个数(管道或事务中使用为null)
     */
    public static Long unlinkMultiArray(@NonNull String... keys) {
        return redisTemplate.unlink(Arrays.asList(keys));
    }

    /**
     * key是否存在(exists)
     *
     * @param key 键
     * @return 是否存在
     */
    public static Boolean exists(@NonNull String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 集合中存在的key的数量(exists)
     *
     * @param keys 键
     * @return 存在的数量(key重复会计算多次)
     */
    public static Long countExistingKeys(Collection<String> keys) {
        return redisTemplate.countExistingKeys(keys);
    }

    /**
     * 集合中存在的key的数量(exists)
     *
     * @param keys 键
     * @return 存在的数量(key重复会计算多次)
     */
    public static Long countExistingKeysArray(String... keys) {
        return redisTemplate.countExistingKeys(Arrays.asList(keys));
    }

    /**
     * 指定失效时间(秒，<=0删除)(expire)
     *
     * @param key     键
     * @param timeout 失效时间
     * @return 是否成功(管道或事务中使用为null)
     */
    public static Boolean expire(@NonNull String key, long timeout) {
        return redisTemplate.expire(key, timeout, SECONDS);
    }

    /**
     * 指定失效时间(expire)
     *
     * @param key     键
     * @param timeout 持续时间
     * @return 是否成功(管道或事务中使用为null)
     */
    public static Boolean expire(@NonNull String key, @NonNull Duration timeout) {
        return redisTemplate.expire(key, timeout);
    }

    /**
     * 指定失效日期(expire)
     *
     * @param key  键
     * @param date 失效日期
     * @return 是否成功(管道或事务中使用为null)
     */
    public static Boolean expireAt(@NonNull String key, @NonNull Date date) {
        return redisTemplate.expireAt(key, date);
    }

    /**
     * 指定失效日期(expire)
     *
     * @param key      键
     * @param expireAt 失效日期
     * @return 是否成功(管道或事务中使用为null)
     */
    public static Boolean expireAt(@NonNull String key, @NonNull Instant expireAt) {
        return redisTemplate.expireAt(key, expireAt);
    }

    /**
     * 获取失效时间(秒，-1不过期，-2不存在)(ttl)
     *
     * @param key 键
     * @return 失效时间(管道或事务中使用为null)
     */
    public static Long getExpire(@NonNull String key) {
        return redisTemplate.getExpire(key, SECONDS);
    }

    /**
     * 指定为持久数据(移除失效时间)(persist)
     *
     * @param key 键
     * @return 是否成功(管道或事务中使用为null)
     */
    public static Boolean persist(@NonNull String key) {
        return redisTemplate.persist(key);
    }

    /**
     * 模糊查询(keys)<br>
     * 在正式环境中不推荐使用，他会查询所有的键，会造成阻塞<br>
     * 推荐使用scan命令
     *
     * @param pattern 匹配模式<br>
     *                * : 匹配0+个任意字符<br>
     *                ? : 匹配1个任意字符<br>
     *                [abc] : 匹配1个指定字符(括号内字符abc)<br>
     *                [^abc] : 不匹配1个指定字符(括号内字符abc)<br>
     *                [A-z] : 匹配1个指定字符(括号内字符A-z)<br>
     *                \ : 转义(字符*?[]^-\等)
     * @return 键(管道或事务中使用为null)
     */
    public static Set<String> keys(@NonNull String pattern) {
        return redisTemplate.keys(pattern);
    }

    /**
     * 重命名key(新键已存在会被覆盖)(rename)
     *
     * @param oldKey 旧键
     * @param newKey 新键
     * @return 是否成功
     */
    public static boolean rename(@NonNull String oldKey, @NonNull String newKey) {
        try {
            redisTemplate.rename(oldKey, newKey);
            return true;
        } catch (RedisSystemException ignore) {
            return false;
        }
    }

    /**
     * 重命名key，仅当newKey不存在时(renameNX)
     *
     * @param oldKey 旧键
     * @param newKey 新键
     * @return 是否成功(管道或事务中使用为null)
     */
    public static Boolean renameIfAbsent(@NonNull String oldKey, @NonNull String newKey) {
        try {
            return redisTemplate.renameIfAbsent(oldKey, newKey);
        } catch (RedisSystemException ignore) {
            return false;
        }
    }

    /**
     * 返回存储的数据类型(type)
     *
     * @param key 键
     * @return none/string/list/set/zset/hash/stream(管道或事务中使用为null)
     */
    public static DataType type(@NonNull String key) {
        return redisTemplate.type(key);
    }

    /**
     * 返回一个随机的key(randomKey)
     *
     * @return 键(管道或事务中使用或不存在任何键为null)
     */
    public static String randomKey() {
        return redisTemplate.randomKey();
    }

    /**
     * 将key移动到指定索引的数据库(move)
     *
     * @param key     键
     * @param dbIndex 数据库索引
     * @return 是否成功(管道或事务中使用为null)
     */
    public static Boolean move(@NonNull String key, int dbIndex) {
        return redisTemplate.move(key, dbIndex);
    }

    /**
     * 模糊查询，一次扫描1000条(scan)
     *
     * @param match 匹配模式<br>
     *              * : 匹配0+个任意字符<br>
     *              ? : 匹配1个任意字符<br>
     *              [abc] : 匹配1个指定字符(括号内字符abc)<br>
     *              [^abc] : 不匹配1个指定字符(括号内字符abc)<br>
     *              [A-z] : 匹配1个指定字符(括号内字符A-z)<br>
     *              \ : 转义(字符*?[]^-\等)
     * @return 键(管道或事务中使用为null)
     */
    public static Set<String> scan(@NonNull String match) {
        return scan(match, 1000);
    }

    /**
     * 模糊查询(scan)
     *
     * @param match 匹配模式<br>
     *              * : 匹配0+个任意字符<br>
     *              ? : 匹配1个任意字符<br>
     *              [abc] : 匹配1个指定字符(括号内字符abc)<br>
     *              [^abc] : 不匹配1个指定字符(括号内字符abc)<br>
     *              [A-z] : 匹配1个指定字符(括号内字符A-z)<br>
     *              \ : 转义(字符*?[]^-\等)
     * @param count 一次扫描条数
     * @return 键(管道或事务中使用为null)
     */
    @SuppressWarnings("unchecked")
    public static Set<String> scan(@NonNull String match, long count) {
        Set<String> keys = new HashSet<>();
        ScanOptions options = ScanOptions.scanOptions().match(match).count(count).build();
        Cursor<String> cursor = (Cursor<String>) redisTemplate.executeWithStickyConnection(//
                connection -> new ConvertingCursor<>(connection.scan(options), //
                        redisTemplate.getKeySerializer()::deserialize));
        if (cursor == null) {
            return keys;
        }
        while (cursor.hasNext()) {
            keys.add(cursor.next());
        }
        return keys;
    }

    //endregion

    /* ==================== 字符串Value操作 ==================== */
    //region 字符串Value操作

    /**
     * 递减1，值必须是整数类型(键不存在自动创建并赋值为0后再递减)(decr)
     *
     * @param key 键
     * @return 递减后的值(管道或事务中使用为null)
     */
    public static Long decrement(@NonNull String key) {
        return redisTemplate.opsForValue().decrement(key);
    }

    /**
     * 递减，值必须是整数类型(键不存在自动创建并赋值为0后再递减)(decrBy)
     *
     * @param key   键
     * @param delta 减量
     * @return 递减后的值(管道或事务中使用为null)
     */
    public static Long decrement(@NonNull String key, long delta) {
        return redisTemplate.opsForValue().decrement(key, delta);
    }

    /**
     * 递增1，值必须是整数类型(键不存在自动创建并赋值为0后再递增)(incr)
     *
     * @param key 键
     * @return 递增后的值(管道或事务中使用为null)
     */
    public static Long increment(@NonNull String key) {
        return redisTemplate.opsForValue().increment(key);
    }

    /**
     * 递增，值必须是整数类型(键不存在自动创建并赋值为0后再递增)(incrBy)
     *
     * @param key   键
     * @param delta 增量
     * @return 递增后的值(管道或事务中使用为null)
     */
    public static Long increment(@NonNull String key, long delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 获取(get)
     *
     * @param key 键
     * @return 值(管道或事务中使用或不存在key时为null)
     */
    public static Object get(@NonNull String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 获取并放入(getSet)
     *
     * @param <T>   指定数据类型
     * @param key   键
     * @param value 值
     * @return 值(管道或事务中使用为null)
     */
    public static <T> Object getAndSet(@NonNull String key, @NonNull T value) {
        return redisTemplate.opsForValue().getAndSet(key, value);
    }

    /**
     * 获取多个(mGet)
     *
     * @param keys 多个键
     * @return 多个值(管道或事务中使用为null)
     */
    public static List<Object> getMulti(@NonNull Collection<String> keys) {
        return redisTemplate.opsForValue().multiGet(keys);
    }

    /**
     * 获取多个(mGet)
     *
     * @param keys 多个键
     * @return 多个值(管道或事务中使用为null)
     */
    public static List<Object> getMultiArray(@NonNull String... keys) {
        return redisTemplate.opsForValue().multiGet(Arrays.asList(keys));
    }

    /**
     * 放入(set)
     *
     * @param <T>   指定数据类型
     * @param key   键
     * @param value 值
     */
    public static <T> void set(@NonNull String key, @NonNull T value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 如果key不存在，则放入(setNX)
     *
     * @param <T>   指定数据类型
     * @param key   键
     * @param value 值
     * @return 是否成功(管道或事务中使用为null)
     */
    public static <T> Boolean setIfAbsent(@NonNull String key, @NonNull T value) {
        return redisTemplate.opsForValue().setIfAbsent(key, value);
    }

    /**
     * 如果key存在，则放入(setNX)
     *
     * @param <T>   指定数据类型
     * @param key   键
     * @param value 值
     * @return 是否成功(管道或事务中使用为null)
     */
    public static <T> Boolean setIfPresent(@NonNull String key, @NonNull T value) {
        return redisTemplate.opsForValue().setIfPresent(key, value);
    }

    /**
     * 放入，并设置失效时间(秒，必须>0)(setEX)
     *
     * @param <T>     指定数据类型
     * @param key     键
     * @param value   值
     * @param timeout 失效时间
     */
    public static <T> void set(@NonNull String key, @NonNull T value, long timeout) {
        redisTemplate.opsForValue().set(key, value, timeout, SECONDS);
    }

    /**
     * 放入，并设置持续时间(setEX)
     *
     * @param <T>     指定数据类型
     * @param key     键
     * @param value   值
     * @param timeout 持续时间
     */
    public static <T> void set(@NonNull String key, @NonNull T value, @NonNull Duration timeout) {
        redisTemplate.opsForValue().set(key, value, timeout);
    }

    /**
     * 如果key不存在，则放入，并设置失效时间(秒，必须>0)(setNX,setEX)
     *
     * @param <T>     指定数据类型
     * @param key     键
     * @param value   值
     * @param timeout 失效时间
     * @return 是否成功(管道或事务中使用为null)
     */
    public static <T> Boolean setIfAbsent(@NonNull String key, @NonNull T value, long timeout) {
        return redisTemplate.opsForValue().setIfAbsent(key, value, timeout, SECONDS);
    }

    /**
     * 如果key不存在，则放入，并设置持续时间(setNX,setEX)
     *
     * @param <T>     指定数据类型
     * @param key     键
     * @param value   值
     * @param timeout 持续时间
     * @return 是否成功(管道或事务中使用为null)
     */
    public static <T> Boolean setIfAbsent(@NonNull String key, @NonNull T value, @NonNull Duration timeout) {
        return redisTemplate.opsForValue().setIfAbsent(key, value, timeout);
    }

    /**
     * 如果key存在，则放入，并设置失效时间(秒，必须>0)(setNX,setEX)
     *
     * @param <T>     指定数据类型
     * @param key     键
     * @param value   值
     * @param timeout 失效时间
     * @return 是否成功(管道或事务中使用为null)
     */
    public static <T> Boolean setIfPresent(@NonNull String key, @NonNull T value, long timeout) {
        return redisTemplate.opsForValue().setIfPresent(key, value, timeout, SECONDS);
    }

    /**
     * 如果key存在，则放入，并设置持续时间(setNX,setEX)
     *
     * @param <T>     指定数据类型
     * @param key     键
     * @param value   值
     * @param timeout 持续时间
     * @return 是否成功(管道或事务中使用为null)
     */
    public static <T> Boolean setIfPresent(@NonNull String key, @NonNull T value, @NonNull Duration timeout) {
        return redisTemplate.opsForValue().setIfPresent(key, value, timeout);
    }

    /**
     * map中的key和value依次放入(键存在则不会放入)(mSet)
     *
     * @param <T> 指定数据类型
     * @param map map
     */
    public static <T> void setMulti(@NonNull Map<String, T> map) {
        redisTemplate.opsForValue().multiSet(map);
    }

    /**
     * 如果map中的key全部不存在，则map中的key和value依次放入(mSetNX)
     *
     * @param <T> 指定数据类型
     * @param map map
     */
    public static <T> Boolean setMultiIfAbsent(@NonNull Map<String, T> map) {
        return redisTemplate.opsForValue().multiSetIfAbsent(map);
    }

    //endregion

    /* ==================== 哈希Hash操作 ==================== */
    //region 哈希Hash操作

    /**
     * 删除map中指定多个项(hDel)
     *
     * @param key   键
     * @param items 多个项
     * @return 删除成功个数(管道或事务中使用为null)。注意：当key中不存在item时，key将被删除。
     */
    public static Long hDeleteMulti(@NonNull String key, @NonNull Collection<String> items) {
        return redisTemplate.opsForHash().delete(key, items.toArray());
    }

    /**
     * 删除map中指定多个项(hDel)
     *
     * @param key   键
     * @param items 多个项
     * @return 删除成功个数(管道或事务中使用为null)。注意：当key中不存在item时，key将被删除。
     */
    public static Long hDeleteMultiArray(@NonNull String key, @NonNull String... items) {
        return redisTemplate.opsForHash().delete(key, Arrays.copyOf(items, items.length, Object[].class));
    }

    /**
     * 判断map中是否有指定项(hExists)
     *
     * @param key  键
     * @param item 项
     * @return 是否存在(管道或事务中使用为null)
     */
    public static Boolean hExists(@NonNull String key, @NonNull String item) {
        return redisTemplate.opsForHash().hasKey(key, item);
    }

    /**
     * 获取map中指定项的值(hGet)
     *
     * @param key  键
     * @param item 项
     * @return 值(管道或事务中使用或不存在key或不存在item时为null)
     */
    public static Object hGet(@NonNull String key, @NonNull String item) {
        return redisTemplate.opsForHash().get(key, item);
    }

    /**
     * 获取map中指定多个项的值(hMGet)
     *
     * @param key   键
     * @param items 多个项
     * @return 值
     */
    public static List<Object> hGetMulti(@NonNull String key, @NonNull Collection<String> items) {
        return redisTemplate.opsForHash().multiGet(key, Arrays.asList(items.toArray()));
    }

    /**
     * 获取map中指定多个项的值(hMGet)
     *
     * @param key   键
     * @param items 多个项
     * @return 值
     */
    public static List<Object> hGetMultiArray(@NonNull String key, @NonNull String... items) {
        return redisTemplate.opsForHash().multiGet(key, Arrays.asList(items));
    }

    /**
     * map指定项的值递增1，值必须是整数类型(键或项不存在自动创建并赋值为0后再递增)(hIncrBy)
     *
     * @param key  键
     * @param item 项
     * @return 递增后的值(管道或事务中使用为null)
     */
    public static Long hIncrement(@NonNull String key, @NonNull String item) {
        return redisTemplate.opsForHash().increment(key, item, 1);
    }

    /**
     * map指定项的值递增，值必须是整数类型(键或项不存在自动创建并赋值为0后再递增)(hIncrBy)
     *
     * @param key   键
     * @param item  项
     * @param delta 增量
     * @return 递增后的值(管道或事务中使用为null)
     */
    public static Long hIncrement(@NonNull String key, @NonNull String item, long delta) {
        return redisTemplate.opsForHash().increment(key, item, delta);
    }

    /**
     * map指定项的值递减1，值必须是整数类型(键或项不存在自动创建并赋值为0后再递减)(hIncrBy)
     *
     * @param key  键
     * @param item 项
     * @return 递减后的值(管道或事务中使用为null)
     */
    public static Long hDecrement(@NonNull String key, @NonNull String item) {
        return redisTemplate.opsForHash().increment(key, item, -1);
    }

    /**
     * map指定项的值递减，值必须是整数类型(键或项不存在自动创建并赋值为0后再递减)(hIncrBy)
     *
     * @param key   键
     * @param item  项
     * @param delta 减量
     * @return 递减后的值(管道或事务中使用为null)
     */
    public static Long hDecrement(@NonNull String key, @NonNull String item, long delta) {
        return redisTemplate.opsForHash().increment(key, item, -delta);
    }

    /**
     * 设置map的1个键值(项已存在会被覆盖)(hMSet)
     *
     * @param <T>   指定数据类型
     * @param key   键
     * @param item  项
     * @param value 值
     */
    public static <T> void hSet(@NonNull String key, @NonNull String item, T value) {
        redisTemplate.opsForHash().put(key, item, value);
    }

    /**
     * 设置map的多个键值(项已存在会被覆盖)(hMSet)
     *
     * @param <T> 指定数据类型
     * @param key 键
     * @param map 多个键值
     */
    public static <T> void hSetMulti(@NonNull String key, @NonNull Map<String, T> map) {
        redisTemplate.opsForHash().putAll(key, map);
    }

    /**
     * 项不存在时，设置map的1个键值(hSetNX)
     *
     * @param <T>   指定数据类型
     * @param key   键
     * @param item  项
     * @param value 值
     * @return 是否成功(管道或事务中使用为null)
     */
    public static <T> Boolean hPutIfAbsent(@NonNull String key, @NonNull String item, T value) {
        return redisTemplate.opsForHash().putIfAbsent(key, item, value);
    }

    /**
     * 获取项的个数(键不存在返回0)(hLen)
     *
     * @param key 键
     * @return 项的个数
     */
    public static Long hSize(@NonNull String key) {
        return redisTemplate.opsForHash().size(key);
    }

    /**
     * 获取map中所有的项(hKeys)
     *
     * @param key 键
     * @return 项(管道或事务中使用为null)
     */
    public static Set<Object> hGetAllItem(@NonNull String key) {
        return redisTemplate.opsForHash().keys(key);
    }

    /**
     * 获取map中所有的值(hVals)
     *
     * @param key 键
     * @return 值(管道或事务中使用为null)
     */
    public static List<Object> hGetAllValue(@NonNull String key) {
        return redisTemplate.opsForHash().values(key);
    }

    /**
     * 获取map中所有的项和值(hGetAll)
     *
     * @param key 键
     * @return 项和值(管道或事务中使用为null)
     */
    public static Map<Object, Object> hGetAllItemAndValue(@NonNull String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 模糊查询，一次扫描100条(hScan)
     *
     * @param key   键
     * @param match 匹配模式<br>
     *              * : 匹配0+个任意字符<br>
     *              ? : 匹配1个任意字符<br>
     *              [abc] : 匹配1个指定字符(括号内字符abc)<br>
     *              [^abc] : 不匹配1个指定字符(括号内字符abc)<br>
     *              [A-z] : 匹配1个指定字符(括号内字符A-z)<br>
     *              \ : 转义(字符*?[]^-\等)
     * @return 项和值(管道或事务中使用为null)
     */
    public static Map<String, Object> hScan(@NonNull String key, @NonNull String match) {
        return hScan(key, match, 100);
    }

    /**
     * 模糊查询(hScan)
     *
     * @param key   键
     * @param match 匹配模式<br>
     *              * : 匹配0+个任意字符<br>
     *              ? : 匹配1个任意字符<br>
     *              [abc] : 匹配1个指定字符(括号内字符abc)<br>
     *              [^abc] : 不匹配1个指定字符(括号内字符abc)<br>
     *              [A-z] : 匹配1个指定字符(括号内字符A-z)<br>
     *              \ : 转义(字符*?[]^-\等)
     * @param count 一次扫描条数
     * @return 项和值(管道或事务中使用为null)
     */
    public static Map<String, Object> hScan(@NonNull String key, @NonNull String match, long count) {
        Map<String, Object> map = new HashMap<>();
        ScanOptions options = ScanOptions.scanOptions().match(match).count(count).build();
        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(key, options);
        while (cursor.hasNext()) {
            Map.Entry<Object, Object> next = cursor.next();
            map.put((String) next.getKey(), next.getValue());
        }
        return map;
    }

    //endregion

    /* ==================== 列表List操作 ==================== */
    //region 列表List操作

    /**
     * 获取指定下标的值(lIndex)
     *
     * @param key   键
     * @param index 下标
     * @return 值(管道或事务中使用或键不存在或下标不存在为null)
     */
    public static Object lGet(@NonNull String key, long index) {
        return redisTemplate.opsForList().index(key, index);
    }

    /**
     * 获取第一个(lIndex)
     *
     * @param key 键
     * @return 值(管道或事务中使用或键不存在或下标不存在为null)
     */
    public static Object lGetFirst(@NonNull String key) {
        return redisTemplate.opsForList().index(key, 0);
    }

    /**
     * 获取最后一个(lIndex)
     *
     * @param key 键
     * @return 值(管道或事务中使用或键不存在或下标不存在为null)
     */
    public static Object lGetLast(@NonNull String key) {
        return redisTemplate.opsForList().index(key, -1);
    }

    /**
     * 获取(lRange)
     *
     * @param key   键
     * @param start 开始(开头0)
     * @param end   结束(末尾-1)
     * @return 值(管道或事务中使用为null ， 键或项不存在为[])
     */
    public static List<Object> lGetMulti(@NonNull String key, long start, long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    /**
     * 获取全部(lRange)
     *
     * @param key 键
     * @return 值(管道或事务中使用为null ， 键或项不存在为[])
     */
    public static List<Object> lGetAll(@NonNull String key) {
        return redisTemplate.opsForList().range(key, 0, -1);
    }

    /**
     * 添加到指定值的左侧(lInsert before)
     *
     * @param <T>   指定数据类型
     * @param key   键
     * @param pivot 指定值
     * @param value 值
     * @return 列表长度(管道或事务中使用为null ， 不存在返回 - 1)
     */
    public static <T> Long lInsertLeft(@NonNull String key, @NonNull T pivot, @NonNull T value) {
        return redisTemplate.opsForList().leftPush(key, pivot, value);
    }

    /**
     * 添加到指定值的右侧(lInsert after)
     *
     * @param <T>   指定数据类型
     * @param key   键
     * @param pivot 指定值
     * @param value 值
     * @return 列表长度(管道或事务中使用为null ， 不存在返回 - 1)
     */
    public static <T> Long lInsertRight(@NonNull String key, @NonNull T pivot, @NonNull T value) {
        return redisTemplate.opsForList().rightPush(key, pivot, value);
    }

    /**
     * 添加到左侧(lPush)
     *
     * @param <T>   指定数据类型
     * @param key   键
     * @param value 值
     * @return 列表长度(管道或事务中使用为null)
     */
    public static <T> Long lLeftPush(@NonNull String key, @NonNull T value) {
        return redisTemplate.opsForList().leftPush(key, value);
    }

    /**
     * 添加到左侧(多个值依次添加a/b/c变成c/b/a)(lPush)
     *
     * @param key   键
     * @param value 多个值
     * @return 列表长度(管道或事务中使用为null)
     */
    public static Long lLeftPushMulti(@NonNull String key, @NonNull Collection<Object> value) {
        return redisTemplate.opsForList().leftPushAll(key, value);
    }

    /**
     * 添加到左侧(多个值依次添加a/b/c变成c/b/a)(lPush)
     *
     * @param <T>   指定数据类型
     * @param key   键
     * @param value 多个值
     * @return 列表长度(管道或事务中使用为null)
     */
    public static <T> Long lLeftPushMultiArray(@NonNull String key, @NonNull T[] value) {
        return redisTemplate.opsForList().leftPushAll(key, value);
    }

    /**
     * 当列表存在时，添加到左侧(lPushX)
     *
     * @param <T>   指定数据类型
     * @param key   键
     * @param value 值
     * @return 列表长度(管道或事务中使用为null ， 不存在返回0)
     */
    public static <T> Long lLeftPushIfPresent(@NonNull String key, @NonNull T value) {
        return redisTemplate.opsForList().leftPushIfPresent(key, value);
    }

    /**
     * 添加到右侧(rPush)
     *
     * @param <T>   指定数据类型
     * @param key   键
     * @param value 值
     * @return 列表长度(管道或事务中使用为null)
     */
    public static <T> Long lRightPush(@NonNull String key, @NonNull T value) {
        return redisTemplate.opsForList().rightPush(key, value);
    }

    /**
     * 添加到右侧(多个值依次添加a/b/c还是a/b/c)(rPush)
     *
     * @param key   键
     * @param value 多个值
     * @return 列表长度(管道或事务中使用为null)
     */
    public static Long lRightPushMulti(@NonNull String key, @NonNull Collection<Object> value) {
        return redisTemplate.opsForList().rightPushAll(key, value);
    }

    /**
     * 添加到右侧(多个值依次添加a/b/c还是a/b/c)(rPush)
     *
     * @param <T>   指定数据类型
     * @param key   键
     * @param value 多个值
     * @return 列表长度(管道或事务中使用为null)
     */
    public static <T> Long lRightPushMultiArray(@NonNull String key, @NonNull T[] value) {
        return redisTemplate.opsForList().rightPushAll(key, value);
    }

    /**
     * 当列表存在时，添加到右侧(rPushX)
     *
     * @param <T>   指定数据类型
     * @param key   键
     * @param value 值
     * @return 列表长度(管道或事务中使用为null)
     */
    public static <T> Long lRightPushIfPresent(@NonNull String key, @NonNull T value) {
        return redisTemplate.opsForList().rightPushIfPresent(key, value);
    }

    /**
     * 删除并返回左侧的值(lPop)
     *
     * @param key 键
     * @return 值(不存在键时为null)
     */
    public static Object lLeftPop(@NonNull String key) {
        return redisTemplate.opsForList().leftPop(key);
    }

    /**
     * 删除并返回左侧的值，并阻塞指定时间(秒)(bLPop)<br>
     * 当指定键不存在值时，客户端将等待指定时间查询数据，查询到了返回数据，查询不到返回null
     *
     * @param key     键
     * @param timeout 阻塞时间
     * @return 值(超过指定时间该键还是为空时为null)
     */
    public static Object lLeftPop(@NonNull String key, long timeout) {
        return redisTemplate.opsForList().leftPop(key, timeout, SECONDS);
    }

    /**
     * 删除并返回左侧的值，并阻塞指定时间(bLPop)<br>
     * 当指定键不存在值时，客户端将等待指定时间查询数据，查询到了返回数据，查询不到返回null
     *
     * @param key     键
     * @param timeout 阻塞时间
     * @return 值(超过指定时间该键还是为空时为null)
     */
    public static Object lLeftPop(@NonNull String key, @NonNull Duration timeout) {
        return redisTemplate.opsForList().leftPop(key, timeout);
    }

    /**
     * 删除并返回右侧的值(rPop)
     *
     * @param key 键
     * @return 值(不存在键时为null)
     */
    public static Object lRightPop(@NonNull String key) {
        return redisTemplate.opsForList().rightPop(key);
    }

    /**
     * 删除并返回右侧的值，并阻塞指定时间(秒)(bRPop)<br>
     * 当指定键不存在值时，客户端将等待指定时间查询数据，查询到了返回数据，查询不到返回null
     *
     * @param key     键
     * @param timeout 阻塞时间
     * @return 值(超过指定时间该键还是为空时为null)
     */
    public static Object lRightPop(@NonNull String key, long timeout) {
        return redisTemplate.opsForList().rightPop(key, timeout, SECONDS);
    }

    /**
     * 删除并返回右侧的值，并阻塞指定时间(bRPop)<br>
     * 当指定键不存在值时，客户端将等待指定时间查询数据，查询到了返回数据，查询不到返回null
     *
     * @param key     键
     * @param timeout 阻塞时间
     * @return 值(超过指定时间该键还是为空时为null)
     */
    public static Object lRightPop(@NonNull String key, @NonNull Duration timeout) {
        return redisTemplate.opsForList().rightPop(key, timeout);
    }

    /**
     * 获取列表的长度(lLen)
     *
     * @param key 键
     * @return 列表长度(管道或事务中使用为null)
     */
    public static Long lSize(@NonNull String key) {
        return redisTemplate.opsForList().size(key);
    }

    /**
     * 指定值第一次出现的下标(Redis版本需要6.0.6及以上)(lPos)
     *
     * @param <T>   指定数据类型
     * @param key   键
     * @param value 值
     * @return 下标(管道或事务中使用或没有指定值为null)
     */
    public static <T> Long lIndexOf(@NonNull String key, @NonNull T value) {
        return redisTemplate.opsForList().indexOf(key, value);
    }

    /**
     * 指定值最后一次出现的下标(Redis版本需要6.0.6及以上)(lPos)
     *
     * @param <T>   指定数据类型
     * @param key   键
     * @param value 值
     * @return 下标(管道或事务中使用或没有指定值为null)
     */
    public static <T> Long lLastIndexOf(@NonNull String key, @NonNull T value) {
        return redisTemplate.opsForList().lastIndexOf(key, value);
    }

    /**
     * 修剪(只要start和end之间的值)(lTrim)
     *
     * @param key   键
     * @param start 开始(开头0)
     * @param end   结束(末尾-1)
     */
    public static void lTrim(@NonNull String key, long start, long end) {
        redisTemplate.opsForList().trim(key, start, end);
    }

    /**
     * 插入到指定位置(会替换该位置的值)(lSet)
     *
     * @param <T>   指定数据类型
     * @param key   键
     * @param index 下标
     * @param value 值
     * @return 是否成功
     */
    public static <T> boolean lSet(@NonNull String key, long index, @NonNull T value) {
        try {
            redisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (RedisSystemException ignore) {
            return false;
        }
    }

    /**
     * 删除第count次出现的值(正数从左边删，负数从右边删，0全部删除)(lRem)
     *
     * @param <T>   指定数据类型
     * @param key   键
     * @param count 第几次
     * @param value 值
     * @return 删除成功的个数(管道或事务中使用为null ， 未删除返回0)
     */
    public static <T> Long lDelete(@NonNull String key, long count, @NonNull T value) {
        return redisTemplate.opsForList().remove(key, count, value);
    }

    /**
     * 删除左侧第一次出现的值(lRem)
     *
     * @param <T>   指定数据类型
     * @param key   键
     * @param value 值
     * @return 删除成功的个数(管道或事务中使用为null ， 未删除返回0)
     */
    public static <T> Long lDeleteLeft(@NonNull String key, @NonNull T value) {
        return redisTemplate.opsForList().remove(key, 1, value);
    }

    /**
     * 删除右侧第一次出现的值(lRem)
     *
     * @param <T>   指定数据类型
     * @param key   键
     * @param value 值
     * @return 删除成功的个数(管道或事务中使用为null ， 未删除返回0)
     */
    public static <T> Long lDeleteRight(@NonNull String key, @NonNull T value) {
        return redisTemplate.opsForList().remove(key, -1, value);
    }

    /**
     * 删除全部出现的值(lRem)
     *
     * @param <T>   指定数据类型
     * @param key   键
     * @param value 值
     * @return 删除成功的个数(管道或事务中使用为null ， 未删除返回0)
     */
    public static <T> Long lDeleteAll(@NonNull String key, @NonNull T value) {
        return redisTemplate.opsForList().remove(key, 0, value);
    }

    /**
     * 从sourceKey的右侧删除，添加到destinationKey的左侧，并返回这个值(rPopLPush)
     *
     * @param sourceKey      源键
     * @param destinationKey 目的键
     * @return 值(sourceKey不存在时为null)
     */
    public static Object lRightPopAndLeftPush(@NonNull String sourceKey, @NonNull String destinationKey) {
        return redisTemplate.opsForList().rightPopAndLeftPush(sourceKey, destinationKey);
    }

    /**
     * 从sourceKey的右侧删除，添加到destinationKey的左侧，并返回这个值，并阻塞指定时间(秒)(bRPopLPush)
     *
     * @param sourceKey      源键
     * @param destinationKey 目的键
     * @param timeout        阻塞时间
     * @return 值(sourceKey不存在时为null)
     */
    public static Object lRightPopAndLeftPush(@NonNull String sourceKey, @NonNull String destinationKey, long timeout) {
        return redisTemplate.opsForList().rightPopAndLeftPush(sourceKey, destinationKey, timeout, SECONDS);
    }

    /**
     * 从sourceKey的右侧删除，添加到destinationKey的左侧，并返回这个值，并阻塞指定时间(bRPopLPush)
     *
     * @param sourceKey      源键
     * @param destinationKey 目的键
     * @param timeout        阻塞时间
     * @return 值(sourceKey不存在时为null)
     */
    public static Object lRightPopAndLeftPush(@NonNull String sourceKey, @NonNull String destinationKey,
                                              @NonNull Duration timeout) {
        return redisTemplate.opsForList().rightPopAndLeftPush(sourceKey, destinationKey, timeout);
    }

    //endregion

    /* ==================== 集合Set操作 ==================== */
    //region 集合Set操作

    /**
     * 添加(sAdd)
     *
     * @param <T>   指定数据类型
     * @param key   键
     * @param value 值
     * @return 列表长度(管道或事务中使用为null)
     */
    public static <T> Long sAdd(@NonNull String key, @NonNull T value) {
        return redisTemplate.opsForSet().add(key, value);
    }

    /**
     * 添加多个(sAdd)
     *
     * @param <T>   指定数据类型
     * @param key   键
     * @param value 值
     * @return 列表长度(管道或事务中使用为null)
     */
    public static <T> Long sAddMulti(@NonNull String key, @NonNull Collection<T> value) {
        return redisTemplate.opsForSet().add(key, value.toArray());
    }

    /**
     * 添加多个(sAdd)
     *
     * @param <T>   指定数据类型
     * @param key   键
     * @param value 值
     * @return 列表长度(管道或事务中使用为null)
     */
    @SafeVarargs
    public static <T> Long sAddMultiArray(@NonNull String key, @NonNull T... value) {
        return redisTemplate.opsForSet().add(key, value);
    }

    /**
     * 删除值(sRem)
     *
     * @param <T>   指定数据类型
     * @param key   键
     * @param value 值
     * @return 列表长度(管道或事务中使用为null)
     */
    public static <T> Long sDelete(@NonNull String key, @NonNull T value) {
        return redisTemplate.opsForSet().remove(key, value);
    }

    /**
     * 删除多个值(sRem)
     *
     * @param <T>   指定数据类型
     * @param key   键
     * @param value 值
     * @return 列表长度(管道或事务中使用为null)
     */
    public static <T> Long sDeleteMulti(@NonNull String key, @NonNull Collection<T> value) {
        return redisTemplate.opsForSet().remove(key, value.toArray());
    }

    /**
     * 删除多个值(sRem)
     *
     * @param <T>   指定数据类型
     * @param key   键
     * @param value 值
     * @return 列表长度(管道或事务中使用为null)
     */
    @SafeVarargs
    public static <T> Long sDeleteMultiArray(@NonNull String key, @NonNull T... value) {
        return redisTemplate.opsForSet().remove(key, Arrays.copyOf(value, value.length, Object[].class));
    }

    /**
     * 返回并删除1个随机值(sPop)
     *
     * @param key 键
     * @return 值(管道或事务中使用为null)
     */
    public static Object sPop(@NonNull String key) {
        return redisTemplate.opsForSet().pop(key);
    }

    /**
     * 返回并删除多个随机值(sPop)
     *
     * @param key   键
     * @param count 数量
     * @return 值(管道或事务中使用为null)
     */
    public static List<Object> sPopMulti(@NonNull String key, long count) {
        return redisTemplate.opsForSet().pop(key, count);
    }

    /**
     * 移动元素到目的键(sMove)
     *
     * @param key     键
     * @param value   值
     * @param destKey 目的键
     * @return 是否成功(管道或事务中使用为null)
     */
    public static <T> Boolean sMove(@NonNull String key, @NonNull T value, @NonNull String destKey) {
        return redisTemplate.opsForSet().move(key, value, destKey);
    }

    /**
     * 元素个数(sCard)
     *
     * @param key 键
     * @return 个数(管道或事务中使用为null)
     */
    public static Long sSize(@NonNull String key) {
        return redisTemplate.opsForSet().size(key);
    }

    /**
     * 是否存在元素(sIsMember)
     *
     * @param key   键
     * @param value 值
     * @return 是否存在(管道或事务中使用为null)
     */
    public static <T> Boolean sIsMember(@NonNull String key, @NonNull T value) {
        return redisTemplate.opsForSet().isMember(key, value);
    }

    /**
     * 是否存在多个元素(Redis版本需要6.2.0及以上)(sMIsMember)
     *
     * @param key   键
     * @param value 值
     * @return Map(管道或事务中使用为null)
     */
    public static <T> Map<Object, Boolean> sIsMultiMember(@NonNull String key, @NonNull Collection<T> value) {
        return redisTemplate.opsForSet().isMember(key, value.toArray());
    }

    /**
     * 是否存在多个元素(Redis版本需要6.2.0及以上)(sMIsMember)
     *
     * @param key   键
     * @param value 值
     * @return Map(管道或事务中使用为null)
     */
    @SafeVarargs
    public static <T> Map<Object, Boolean> sIsMultiMemberArray(@NonNull String key, @NonNull T... value) {
        return redisTemplate.opsForSet().isMember(key, Arrays.copyOf(value, value.length, Object[].class));
    }

    /**
     * 所有元素(sMembers)
     *
     * @param key 键
     * @return 元素(管道或事务中使用为null)
     */
    public static Set<Object> sMembers(@NonNull String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 随机获取1个元素(sRandMember)
     *
     * @param key 键
     * @return 元素管道或事务中使用为null)
     */
    public static Object sRandomMember(@NonNull String key) {
        return redisTemplate.opsForSet().randomMember(key);
    }

    /**
     * 随机获取多个元素(sRandomMultiMember)
     *
     * @param key   键
     * @param count 个数(大于0)
     * @return 元素(key不存在返回空List, 管道或事务中使用为null)
     */
    public static List<Object> sRandomMultiMember(@NonNull String key, long count) {
        return redisTemplate.opsForSet().randomMembers(key, count);
    }

    /**
     * 随机获取多个元素(不重复)(sRandomMultiDistinctMember)
     *
     * @param key   键
     * @param count 个数(大于0)
     * @return 元素(key不存在返回空Set)
     */
    public static Set<Object> sRandomMultiDistinctMember(@NonNull String key, long count) {
        return redisTemplate.opsForSet().distinctRandomMembers(key, count);
    }

    /**
     * 两键交集(sInter)
     *
     * @param key      键
     * @param otherKey 另一个键
     * @return 交集(管道或事务中使用为null)
     */
    public static Set<Object> sIntersect(@NonNull String key, @NonNull String otherKey) {
        return redisTemplate.opsForSet().intersect(key, otherKey);
    }

    /**
     * 键与其他键的交集(sInter)
     *
     * @param key      键
     * @param otherKey 其他键
     * @return 交集(管道或事务中使用为null)
     */
    public static Set<Object> sIntersectMulti(@NonNull String key, @NonNull Collection<String> otherKey) {
        return redisTemplate.opsForSet().intersect(key, otherKey);
    }

    /**
     * 所有键的交集(sInter)
     *
     * @param keys 键
     * @return 交集(管道或事务中使用为null)
     */
    public static Set<Object> sIntersectAll(@NonNull Collection<String> keys) {
        return redisTemplate.opsForSet().intersect(keys);
    }

    /**
     * 两键交集并储存(sInterStore)
     *
     * @param key      键
     * @param otherKey 另一个键
     * @return 交集个数(管道或事务中使用为null)
     */
    public static Long sIntersectAndStore(@NonNull String key, @NonNull String otherKey, @NonNull String destKey) {
        return redisTemplate.opsForSet().intersectAndStore(key, otherKey, destKey);
    }

    /**
     * 键与其他键的交集并储存(sInterStore)
     *
     * @param key      键
     * @param otherKey 其他键
     * @return 交集个数(管道或事务中使用为null)
     */
    public static Long sIntersectMultiAndStore(@NonNull String key, @NonNull Collection<String> otherKey,
                                               @NonNull String destKey) {
        return redisTemplate.opsForSet().intersectAndStore(key, otherKey, destKey);
    }

    /**
     * 所有键的交集并储存(sInterStore)
     *
     * @param keys 键
     * @return 交集个数(管道或事务中使用为null)
     */
    public static Long sIntersectAllAndStore(@NonNull Collection<String> keys, @NonNull String destKey) {
        return redisTemplate.opsForSet().intersectAndStore(keys, destKey);
    }

    /**
     * 两键并集(sUnion)
     *
     * @param key      键
     * @param otherKey 另一个键
     * @return 并集(管道或事务中使用为null)
     */
    public static Set<Object> sUnion(@NonNull String key, @NonNull String otherKey) {
        return redisTemplate.opsForSet().union(key, otherKey);
    }

    /**
     * 键与其他键的并集(sUnion)
     *
     * @param key      键
     * @param otherKey 其他键
     * @return 并集(管道或事务中使用为null)
     */
    public static Set<Object> sUnionMulti(@NonNull String key, @NonNull Collection<String> otherKey) {
        return redisTemplate.opsForSet().union(key, otherKey);
    }

    /**
     * 所有键的并集(sUnion)
     *
     * @param keys 键
     * @return 并集(管道或事务中使用为null)
     */
    public static Set<Object> sUnionAll(@NonNull Collection<String> keys) {
        return redisTemplate.opsForSet().union(keys);
    }

    /**
     * 两键并集并储存(sUnionStore)
     *
     * @param key      键
     * @param otherKey 另一个键
     * @return 并集个数(管道或事务中使用为null)
     */
    public static Long sUnionAndStore(@NonNull String key, @NonNull String otherKey, @NonNull String destKey) {
        return redisTemplate.opsForSet().unionAndStore(key, otherKey, destKey);
    }

    /**
     * 键与其他键的并集并储存(sUnionStore)
     *
     * @param key      键
     * @param otherKey 其他键
     * @return 并集个数(管道或事务中使用为null)
     */
    public static Long sUnionMultiAndStore(@NonNull String key, @NonNull Collection<String> otherKey,
                                           @NonNull String destKey) {
        return redisTemplate.opsForSet().unionAndStore(key, otherKey, destKey);
    }

    /**
     * 所有键的并集并储存(sUnionStore)
     *
     * @param keys 键
     * @return 并集个数(管道或事务中使用为null)
     */
    public static Long sUnionAllAndStore(@NonNull Collection<String> keys, @NonNull String destKey) {
        return redisTemplate.opsForSet().unionAndStore(keys, destKey);
    }

    /**
     * 两键差集(sDiff)
     *
     * @param key      键
     * @param otherKey 另一个键
     * @return 差集(管道或事务中使用为null)
     */
    public static Set<Object> sDifference(@NonNull String key, @NonNull String otherKey) {
        return redisTemplate.opsForSet().difference(key, otherKey);
    }

    /**
     * 键与其他键的差集(sDiff)
     *
     * @param key      键
     * @param otherKey 其他键
     * @return 差集(管道或事务中使用为null)
     */
    public static Set<Object> sDifferenceMulti(@NonNull String key, @NonNull Collection<String> otherKey) {
        return redisTemplate.opsForSet().difference(key, otherKey);
    }

    /**
     * 所有键的差集(sDiff)
     *
     * @param keys 键
     * @return 差集(管道或事务中使用为null)
     */
    public static Set<Object> sDifferenceAll(@NonNull Collection<String> keys) {
        return redisTemplate.opsForSet().difference(keys);
    }

    /**
     * 两键差集并储存(sDiffStore)
     *
     * @param key      键
     * @param otherKey 另一个键
     * @return 差集个数(管道或事务中使用为null)
     */
    public static Long sDifferenceAndStore(@NonNull String key, @NonNull String otherKey, @NonNull String destKey) {
        return redisTemplate.opsForSet().differenceAndStore(key, otherKey, destKey);
    }

    /**
     * 键与其他键的差集并储存(sDiffStore)
     *
     * @param key      键
     * @param otherKey 其他键
     * @return 差集个数(管道或事务中使用为null)
     */
    public static Long sDifferenceMultiAndStore(@NonNull String key, @NonNull Collection<String> otherKey,
                                                @NonNull String destKey) {
        return redisTemplate.opsForSet().differenceAndStore(key, otherKey, destKey);
    }

    /**
     * 所有键的差集并储存(sDiffStore)
     *
     * @param keys 键
     * @return 差集个数(管道或事务中使用为null)
     */
    public static Long sDifferenceAllAndStore(@NonNull Collection<String> keys, @NonNull String destKey) {
        return redisTemplate.opsForSet().differenceAndStore(keys, destKey);
    }

    /**
     * 模糊查询，一次扫描100条(sScan)
     *
     * @param key   键
     * @param match 匹配模式<br>
     *              * : 匹配0+个任意字符<br>
     *              ? : 匹配1个任意字符<br>
     *              [abc] : 匹配1个指定字符(括号内字符abc)<br>
     *              [^abc] : 不匹配1个指定字符(括号内字符abc)<br>
     *              [A-z] : 匹配1个指定字符(括号内字符A-z)<br>
     *              \ : 转义(字符*?[]^-\等)
     * @return 值(管道或事务中使用为null)
     */
    public static Set<Object> sScan(@NonNull String key, @NonNull String match) {
        return sScan(key, match, 100);
    }

    /**
     * 模糊查询(sScan)
     *
     * @param key   键
     * @param match 匹配模式<br>
     *              * : 匹配0+个任意字符<br>
     *              ? : 匹配1个任意字符<br>
     *              [abc] : 匹配1个指定字符(括号内字符abc)<br>
     *              [^abc] : 不匹配1个指定字符(括号内字符abc)<br>
     *              [A-z] : 匹配1个指定字符(括号内字符A-z)<br>
     *              \ : 转义(字符*?[]^-\等)
     * @param count 一次扫描条数
     * @return 值(管道或事务中使用为null)
     */
    public static Set<Object> sScan(@NonNull String key, @NonNull String match, long count) {
        Set<Object> values = new HashSet<>();
        ScanOptions options = ScanOptions.scanOptions().match(match).count(count).build();
        Cursor<Object> cursor = redisTemplate.opsForSet().scan(key, options);
        while (cursor.hasNext()) {
            Object next = cursor.next();
            values.add(next);
        }
        return values;
    }

    //endregion

    /* ==================== 有序集合ZSet操作 ==================== */
    //region 有序集合ZSet操作
    //endregion

}
