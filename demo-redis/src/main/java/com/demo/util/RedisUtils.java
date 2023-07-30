package com.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.BitFieldSubCommands;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.ConvertingCursor;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.query.SortQuery;
import org.springframework.stereotype.Component;

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

    private static final TimeUnit SECONDS = TimeUnit.SECONDS;
    private static RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public RedisUtils(RedisTemplate<String, Object> redisTemplate) {
        RedisUtils.redisTemplate = redisTemplate;
    }

    /* ==================== 通用操作 ==================== */
    // region 通用操作

    /**
     * TODO 拷贝(copy)
     *
     * @return 是否成功
     */
    public static Boolean copy(String sourceKey, String targetKey, boolean replace) {
        return redisTemplate.copy(sourceKey, targetKey, replace);
    }

    /**
     * key是否存在(exists)
     *
     * @param key 键
     * @return 是否存在
     */
    public static Boolean exists(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 集合中存在的key的数量(exists)
     *
     * @param keys 键
     * @return 存在的数量(key重复会计算多次)
     */
    public static Long existsCount(Collection<String> keys) {
        return redisTemplate.countExistingKeys(keys);
    }

    /**
     * 集合中存在的key的数量(exists)
     *
     * @param keys 键
     * @return 存在的数量(key重复会计算多次)
     */
    public static Long existsCount(String... keys) {
        return redisTemplate.countExistingKeys(Arrays.asList(keys));
    }

    /**
     * 删除key(del)
     *
     * @param key 键
     * @return 是否成功
     */
    public static Boolean delete(String key) {
        return redisTemplate.delete(key);
    }

    /**
     * 删除多个key(del)
     *
     * @param keys 键
     * @return 成功个数
     */
    public static Long deleteMulti(Collection<String> keys) {
        return redisTemplate.delete(keys);
    }

    /**
     * 删除多个key(del)
     *
     * @param keys 键
     * @return 成功个数
     */
    public static Long deleteMulti(String... keys) {
        return redisTemplate.delete(Arrays.asList(keys));
    }

    /**
     * 非阻塞异步删除key(unlink)
     *
     * @param key 键
     * @return 是否成功
     */
    public static Boolean unlink(String key) {
        return redisTemplate.unlink(key);
    }

    /**
     * 非阻塞异步删除多个key(unlink)
     *
     * @param keys 键
     * @return 成功个数
     */
    public static Long unlinkMulti(Collection<String> keys) {
        return redisTemplate.unlink(keys);
    }

    /**
     * 非阻塞异步删除多个key(unlink)
     *
     * @param keys 键
     * @return 成功个数
     */
    public static Long unlinkMulti(String... keys) {
        return redisTemplate.unlink(Arrays.asList(keys));
    }

    /**
     * 获取存储的数据类型(type)
     *
     * @param key 键
     * @return none/string/list/set/zset/hash/stream
     */
    public static DataType type(String key) {
        return redisTemplate.type(key);
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
     * @return 键列表
     */
    public static Set<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
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
     * @return 键列表
     */
    public static Set<String> scan(String match) {
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
     * @return 键列表
     */
    public static Set<String> scan(String match, long count) {
        Set<String> keys = new HashSet<>();
        Cursor<String> cursor = (Cursor<String>) redisTemplate.executeWithStickyConnection( //
                connection -> new ConvertingCursor<>( //
                        connection.scan(ScanOptions.scanOptions().match(match).count(count).build()), //
                        redisTemplate.getKeySerializer()::deserialize));
        if (cursor == null) {
            return keys;
        }
        while (cursor.hasNext()) {
            keys.add(cursor.next());
        }
        return keys;
    }

    /**
     * 获取一个随机的key(randomKey)
     *
     * @return 键(不存在任何键返回null)
     */
    public static String randomKey() {
        return redisTemplate.randomKey();
    }

    /**
     * 重命名key(rename)<br>
     * (新键已存在会被覆盖)
     *
     * @param oldKey 旧键
     * @param newKey 新键
     */
    public static void rename(String oldKey, String newKey) {
        redisTemplate.rename(oldKey, newKey);
    }

    /**
     * 重命名key，仅当newKey不存在时(renameNX)
     *
     * @param oldKey 旧键
     * @param newKey 新键
     * @return 是否成功
     */
    public static Boolean renameIfAbsent(String oldKey, String newKey) {
        return redisTemplate.renameIfAbsent(oldKey, newKey);
    }

    /**
     * 指定超时时间(expire)
     *
     * @param key     键
     * @param timeout 超时时间(秒，<=0删除)
     * @return 是否成功
     */
    public static Boolean expire(String key, long timeout) {
        return redisTemplate.expire(key, timeout, SECONDS);
    }

    /**
     * 指定失效日期(expire)
     *
     * @param key  键
     * @param date 失效日期
     * @return 是否成功
     */
    public static Boolean expireAt(String key, Date date) {
        return redisTemplate.expireAt(key, date);
    }

    /**
     * 指定为持久数据(persist)
     *
     * @param key 键
     * @return 是否成功
     */
    public static Boolean persist(String key) {
        return redisTemplate.persist(key);
    }

    /**
     * 将key移动到指定索引的数据库(move)
     *
     * @param key     键
     * @param dbIndex 数据库索引
     * @return 是否成功
     */
    public static Boolean move(String key, int dbIndex) {
        return redisTemplate.move(key, dbIndex);
    }

    /**
     * 导出
     *
     * @param key 键
     * @return byte[]
     */
    public static byte[] dump(String key) {
        return redisTemplate.dump(key);
    }

    /**
     * 导入
     *
     * @param key     键
     * @param value   byte[]
     * @param timeout 超时时间(秒，必须>=0，0不过期)
     * @param replace key已存在也执行操作
     */
    public static void restore(String key, byte[] value, long timeout, boolean replace) {
        redisTemplate.restore(key, value, timeout, SECONDS, replace);
    }

    /**
     * 获取超时时间(ttl)
     *
     * @param key 键
     * @return 超时时间(秒 ， - 1不过期 ， - 2不存在)
     */
    public static Long getExpire(String key) {
        return redisTemplate.getExpire(key);
    }

    /**
     * 排序
     *
     * @param query SortQuery
     * @return 排序后的列表
     */
    public static List<Object> sort(SortQuery<String> query) {
        return redisTemplate.sort(query);
    }

    // endregion

    /* ==================== 字符串Value操作 ==================== */
    // region 字符串Value操作

    /**
     * 放入(set)
     *
     * @param <T>   指定数据类型
     * @param key   键
     * @param value 值
     */
    public static <T> void set(String key, T value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 放入，并设置超时时间(setEX)
     *
     * @param <T>     指定数据类型
     * @param key     键
     * @param value   值
     * @param timeout 超时时间(秒，必须>0)
     */
    public static <T> void set(String key, T value, long timeout) {
        redisTemplate.opsForValue().set(key, value, timeout, SECONDS);
    }

    /**
     * 如果key不存在，则放入(setNX)
     *
     * @param <T>   指定数据类型
     * @param key   键
     * @param value 值
     * @return 是否成功
     */
    public static <T> Boolean setIfAbsent(String key, T value) {
        return redisTemplate.opsForValue().setIfAbsent(key, value);
    }

    /**
     * 如果key不存在，则放入，并设置超时时间(set)
     *
     * @param <T>     指定数据类型
     * @param key     键
     * @param value   值
     * @param timeout 超时时间(秒，必须>0)
     * @return 是否成功
     */
    public static <T> Boolean setIfAbsent(String key, T value, long timeout) {
        return redisTemplate.opsForValue().setIfAbsent(key, value, timeout, SECONDS);
    }

    /**
     * 如果key存在，则放入(set)
     *
     * @param <T>   指定数据类型
     * @param key   键
     * @param value 值
     * @return 是否成功
     */
    public static <T> Boolean setIfPresent(String key, T value) {
        return redisTemplate.opsForValue().setIfPresent(key, value);
    }

    /**
     * 如果key存在，则放入，并设置超时时间(set)
     *
     * @param <T>     指定数据类型
     * @param key     键
     * @param value   值
     * @param timeout 超时时间(秒，必须>0)
     * @return 是否成功
     */
    public static <T> Boolean setIfPresent(String key, T value, long timeout) {
        return redisTemplate.opsForValue().setIfPresent(key, value, timeout, SECONDS);
    }

    /**
     * map中的key和value依次放入(mSet)<br>
     * (键存在则不会放入)
     *
     * @param <T> 指定数据类型
     * @param map Map
     */
    public static <T> void setMulti(Map<String, T> map) {
        redisTemplate.opsForValue().multiSet(map);
    }

    /**
     * 如果map中的key全部不存在，则map中的key和value依次放入(mSetNX)
     *
     * @param <T> 指定数据类型
     * @param map Map
     */
    public static <T> Boolean setMultiIfAbsent(Map<String, T> map) {
        return redisTemplate.opsForValue().multiSetIfAbsent(map);
    }

    /**
     * 获取(get)
     *
     * @param key 键
     * @return 值
     */
    public static Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * TODO 获取并删除(getDel)
     *
     * @param key 键
     * @return 值
     */
    public static Object getAndDelete(String key) {
        return redisTemplate.opsForValue().getAndDelete(key);
    }

    /**
     * TODO 获取并设置超时时间(getEx)
     *
     * @param key     键
     * @param timeout 超时时间(秒，必须>0)
     * @return 值
     */
    public static Object getAndExpire(String key, long timeout) {
        return redisTemplate.opsForValue().getAndExpire(key, timeout, SECONDS);
    }

    /**
     * TODO 获取并设置为持久数据(getEx)
     *
     * @param key 键
     * @return 值
     */
    public static Object getAndPersist(String key) {
        return redisTemplate.opsForValue().getAndPersist(key);
    }

    /**
     * 获取并放入(getSet)
     *
     * @param <T>   指定数据类型
     * @param key   键
     * @param value 值
     * @return 值
     */
    public static <T> Object getAndSet(String key, T value) {
        return redisTemplate.opsForValue().getAndSet(key, value);
    }

    /**
     * 获取多个(mGet)
     *
     * @param keys 多个键
     * @return 多个值
     */
    public static List<Object> getMulti(Collection<String> keys) {
        return redisTemplate.opsForValue().multiGet(keys);
    }

    /**
     * 获取多个(mGet)
     *
     * @param keys 多个键
     * @return 多个值
     */
    public static List<Object> getMulti(String... keys) {
        return redisTemplate.opsForValue().multiGet(Arrays.asList(keys));
    }

    /**
     * 递增1，值必须是整数类型(incr)<br>
     * (键不存在自动创建并赋值为0后再递增)
     *
     * @param key 键
     * @return 递增后的值
     */
    public static Long increment(String key) {
        return redisTemplate.opsForValue().increment(key);
    }

    /**
     * 递增，值必须是整数类型(incrBy)<br>
     * (键不存在自动创建并赋值为0后再递增)
     *
     * @param key   键
     * @param delta 增量
     * @return 递增后的值
     */
    public static Long increment(String key, long delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 递增，值必须是数字(redis值中不能带字母)类型(incrByFloat)<br>
     * (键不存在自动创建并赋值为0后再递增)<br>
     * 注意：慎用
     *
     * @param key   键
     * @param delta 增量
     * @return 递增后的值
     */
    public static Double increment(String key, double delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 递减1，值必须是整数类型(decr)<br>
     * (键不存在自动创建并赋值为0后再递减)
     *
     * @param key 键
     * @return 递减后的值
     */
    public static Long decrement(String key) {
        return redisTemplate.opsForValue().decrement(key);
    }

    /**
     * 递减，值必须是整数类型(decrBy)<br>
     * (键不存在自动创建并赋值为0后再递减)
     *
     * @param key   键
     * @param delta 减量
     * @return 递减后的值
     */
    public static Long decrement(String key, long delta) {
        return redisTemplate.opsForValue().decrement(key, delta);
    }

    /**
     * 递减，值必须是数字(redis值中不能带字母)类型(incrByFloat)<br>
     * (键不存在自动创建并赋值为0后再递减)<br>
     * 注意：慎用
     *
     * @param key   键
     * @param delta 减量
     * @return 递减后的值
     */
    public static Double decrement(String key, double delta) {
        return redisTemplate.opsForValue().increment(key, -delta);
    }

    /**
     * 追加字符串(append)<br>
     * (键不存在自动创建并赋值为空字符串后再追加)<br>
     * 注意：慎用
     *
     * @param key   键
     * @param value 字符串
     * @return 追加后的长度
     */
    public static Integer append(String key, String value) {
        return redisTemplate.opsForValue().append(key, value);
    }

    /**
     * 获取字符串(getRange)<br>
     * 注意：慎用
     *
     * @param key   键
     * @param start 起始下标
     * @param end   结束下标
     * @return 字符串
     */
    public static String get(String key, long start, long end) {
        return redisTemplate.opsForValue().get(key, start, end);
    }

    /**
     * 设置字符串(setRange)<br>
     * 注意：慎用
     *
     * @param <T>    指定数据类型
     * @param key    键
     * @param value  值
     * @param offset 下标
     */
    public static <T> void setValue(String key, T value, long offset) {
        redisTemplate.opsForValue().set(key, value, offset);
    }

    /**
     * 获取字符串长度(strLen)<br>
     * 注意：慎用
     *
     * @param key 键
     * @return 长度
     */
    public static Long size(String key) {
        return redisTemplate.opsForValue().size(key);
    }

    /**
     * 设置bit(setBit)<br>
     * 注意：慎用
     *
     * @param key    键
     * @param offset 下标
     * @param value  值
     * @return 是否成功
     */
    public static Boolean setBit(String key, long offset, boolean value) {
        return redisTemplate.opsForValue().setBit(key, offset, value);
    }

    /**
     * 获取bit(getBit)<br>
     * 注意：慎用
     *
     * @param key    键
     * @param offset 下标
     * @return bit
     */
    public static Boolean getBit(String key, long offset) {
        return redisTemplate.opsForValue().getBit(key, offset);
    }

    /**
     * bitField<br>
     * 注意：慎用
     *
     * @param key         键
     * @param subCommands BitFieldSubCommands
     * @return List<Long>
     */
    public static List<Long> bitField(String key, BitFieldSubCommands subCommands) {
        return redisTemplate.opsForValue().bitField(key, subCommands);
    }

    // endregion

    /* ==================== 哈希Hash操作 ==================== */
    // region 哈希Hash操作

    /**
     * 删除map中指定项(hDel)
     *
     * @param key  键
     * @param item 项
     * @return 删除成功个数<br>
     * 注意：当key中不存在item时，key将被删除
     */
    public static Long hDelete(String key, String item) {
        return redisTemplate.opsForHash().delete(key, item);
    }

    /**
     * 删除map中指定多个项(hDel)
     *
     * @param key   键
     * @param items 多个项
     * @return 删除成功个数<br>
     * 注意：当key中不存在item时，key将被删除
     */
    public static Long hDeleteMulti(String key, Collection<String> items) {
        return redisTemplate.opsForHash().delete(key, items.toArray());
    }

    /**
     * 删除map中指定多个项(hDel)
     *
     * @param key   键
     * @param items 多个项
     * @return 删除成功个数<br>
     * 注意：当key中不存在item时，key将被删除
     */
    public static Long hDeleteMulti(String key, String... items) {
        return redisTemplate.opsForHash().delete(key, Arrays.copyOf(items, items.length, Object[].class));
    }

    /**
     * 判断map中是否有指定项(hExists)
     *
     * @param key  键
     * @param item 项
     * @return 是否存在
     */
    public static Boolean hExists(String key, String item) {
        return redisTemplate.opsForHash().hasKey(key, item);
    }

    /**
     * 获取map中指定项的值(hGet)
     *
     * @param key  键
     * @param item 项
     * @return 值(不存在key或不存在item时为null)
     */
    public static Object hGet(String key, String item) {
        return redisTemplate.opsForHash().get(key, item);
    }

    /**
     * 获取map中指定多个项的值(hMGet)
     *
     * @param key   键
     * @param items 多个项
     * @return 值
     */
    public static List<Object> hGetMulti(String key, Collection<String> items) {
        return redisTemplate.opsForHash().multiGet(key, Arrays.asList(items.toArray()));
    }

    /**
     * 获取map中指定多个项的值(hMGet)
     *
     * @param key   键
     * @param items 多个项
     * @return 值
     */
    public static List<Object> hGetMulti(String key, String... items) {
        return redisTemplate.opsForHash().multiGet(key, Arrays.asList(items));
    }

    /**
     * map指定项的值递增1，值必须是整数类型(hIncrBy)<br>
     * (键或项不存在自动创建并赋值为0后再递增)
     *
     * @param key  键
     * @param item 项
     * @return 递增后的值
     */
    public static Long hIncrement(String key, String item) {
        return redisTemplate.opsForHash().increment(key, item, 1);
    }

    /**
     * map指定项的值递增，值必须是整数类型(hIncrBy)<br>
     * (键或项不存在自动创建并赋值为0后再递增)
     *
     * @param key   键
     * @param item  项
     * @param delta 增量
     * @return 递增后的值
     */
    public static Long hIncrement(String key, String item, long delta) {
        return redisTemplate.opsForHash().increment(key, item, delta);
    }

    /**
     * map指定项的值递减1，值必须是整数类型(hIncrBy)<br>
     * (键或项不存在自动创建并赋值为0后再递减)
     *
     * @param key  键
     * @param item 项
     * @return 递减后的值
     */
    public static Long hDecrement(String key, String item) {
        return redisTemplate.opsForHash().increment(key, item, -1);
    }

    /**
     * map指定项的值递减，值必须是整数类型(hIncrBy)<br>
     * (键或项不存在自动创建并赋值为0后再递减)
     *
     * @param key   键
     * @param item  项
     * @param delta 减量
     * @return 递减后的值
     */
    public static Long hDecrement(String key, String item, long delta) {
        return redisTemplate.opsForHash().increment(key, item, -delta);
    }

    // TODO increment
    // TODO randomKey
    // TODO randomEntry
    // TODO randomEntries

    /**
     * 获取map中所有的项(hKeys)
     *
     * @param key 键
     * @return 项
     */
    public static Set<Object> hGetAllItem(String key) {
        return redisTemplate.opsForHash().keys(key);
    }

    // TODO lengthOfValue

    /**
     * 获取项的个数(hLen)
     *
     * @param key 键
     * @return 项的个数(键不存在返回0)
     */
    public static Long hSize(String key) {
        return redisTemplate.opsForHash().size(key);
    }

    /**
     * 设置map的多个键值(hMSet)<br>
     * (项已存在会被覆盖)
     *
     * @param <T> 指定数据类型
     * @param key 键
     * @param map 多个键值
     */
    public static <T> void hSetMulti(String key, Map<String, T> map) {
        redisTemplate.opsForHash().putAll(key, map);
    }

    /**
     * 设置map的1个键值(hSet)<br>
     * (项已存在会被覆盖)
     *
     * @param <T>   指定数据类型
     * @param key   键
     * @param item  项
     * @param value 值
     */
    public static <T> void hSet(String key, String item, T value) {
        redisTemplate.opsForHash().put(key, item, value);
    }

    /**
     * 项不存在时，设置map的1个键值(hSetNX)
     *
     * @param <T>   指定数据类型
     * @param key   键
     * @param item  项
     * @param value 值
     * @return 是否成功
     */
    public static <T> Boolean hPutIfAbsent(String key, String item, T value) {
        return redisTemplate.opsForHash().putIfAbsent(key, item, value);
    }

    /**
     * 获取map中所有的值(hVals)
     *
     * @param key 键
     * @return 值
     */
    public static List<Object> hGetAllValue(String key) {
        return redisTemplate.opsForHash().values(key);
    }

    /**
     * 获取map中所有的项和值(hGetAll)
     *
     * @param key 键
     * @return 项和值
     */
    public static Map<Object, Object> hGetAllItemAndValue(String key) {
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
     * @return 项和值
     */
    public static Map<String, Object> hScan(String key, String match) {
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
     * @return 项和值
     */
    public static Map<String, Object> hScan(String key, String match, long count) {
        Map<String, Object> map = new HashMap<>();
        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan( //
                key, ScanOptions.scanOptions().match(match).count(count).build());
        while (cursor.hasNext()) {
            Map.Entry<Object, Object> next = cursor.next();
            map.put((String) next.getKey(), next.getValue());
        }
        return map;
    }

    // endregion

    /* ==================== 列表List操作 ==================== */
    // region 列表List操作

    /**
     * 获取(lRange)
     *
     * @param key   键
     * @param start 开始(开头0)
     * @param end   结束(末尾-1)
     * @return 值(键或项不存在为[])
     */
    public static List<Object> lGetMulti(String key, long start, long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    /**
     * 获取全部(lRange)
     *
     * @param key 键
     * @return 值(键或项不存在为[])
     */
    public static List<Object> lGetAll(String key) {
        return redisTemplate.opsForList().range(key, 0, -1);
    }

    /**
     * 修剪(只要start和end之间的值)(lTrim)
     *
     * @param key   键
     * @param start 开始(开头0)
     * @param end   结束(末尾-1)
     */
    public static void lTrim(String key, long start, long end) {
        redisTemplate.opsForList().trim(key, start, end);
    }

    /**
     * 获取列表的长度(lLen)
     *
     * @param key 键
     * @return 列表长度
     */
    public static Long lSize(String key) {
        return redisTemplate.opsForList().size(key);
    }

    /**
     * 获取指定下标的值(lIndex)
     *
     * @param key   键
     * @param index 下标
     * @return 值(键不存在或下标不存在为null)
     */
    public static Object lGet(String key, long index) {
        return redisTemplate.opsForList().index(key, index);
    }

    /**
     * 获取第一个(lIndex)
     *
     * @param key 键
     * @return 值(键不存在或下标不存在为null)
     */
    public static Object lGetFirst(String key) {
        return redisTemplate.opsForList().index(key, 0);
    }

    /**
     * 获取最后一个(lIndex)
     *
     * @param key 键
     * @return 值(键不存在或下标不存在为null)
     */
    public static Object lGetLast(String key) {
        return redisTemplate.opsForList().index(key, -1);
    }


    /**
     * 添加到指定值的左侧(lInsert before)
     *
     * @param <T>   指定数据类型
     * @param key   键
     * @param pivot 指定值
     * @param value 值
     * @return 列表长度(不存在返回 - 1)
     */
    public static <T> Long lInsertLeft(String key, T pivot, T value) {
        return redisTemplate.opsForList().leftPush(key, pivot, value);
    }

    /**
     * 添加到指定值的右侧(lInsert after)
     *
     * @param <T>   指定数据类型
     * @param key   键
     * @param pivot 指定值
     * @param value 值
     * @return 列表长度(不存在返回 - 1)
     */
    public static <T> Long lInsertRight(String key, T pivot, T value) {
        return redisTemplate.opsForList().rightPush(key, pivot, value);
    }

    /**
     * 添加到左侧(lPush)
     *
     * @param <T>   指定数据类型
     * @param key   键
     * @param value 值
     * @return 列表长度
     */
    public static <T> Long lLeftPush(String key, T value) {
        return redisTemplate.opsForList().leftPush(key, value);
    }

    /**
     * 添加到左侧(多个值依次添加a/b/c变成c/b/a)(lPush)
     *
     * @param key   键
     * @param value 多个值
     * @return 列表长度
     */
    public static Long lLeftPushMulti(String key, Collection<Object> value) {
        return redisTemplate.opsForList().leftPushAll(key, value);
    }

    /**
     * 添加到左侧(多个值依次添加a/b/c变成c/b/a)(lPush)
     *
     * @param <T>   指定数据类型
     * @param key   键
     * @param value 多个值
     * @return 列表长度
     */
    public static <T> Long lLeftPushMultiArray(String key, T[] value) {
        return redisTemplate.opsForList().leftPushAll(key, value);
    }

    /**
     * 当列表存在时，添加到左侧(lPushX)
     *
     * @param <T>   指定数据类型
     * @param key   键
     * @param value 值
     * @return 列表长度(不存在返回0)
     */
    public static <T> Long lLeftPushIfPresent(String key, T value) {
        return redisTemplate.opsForList().leftPushIfPresent(key, value);
    }

    /**
     * 添加到右侧(rPush)
     *
     * @param <T>   指定数据类型
     * @param key   键
     * @param value 值
     * @return 列表长度
     */
    public static <T> Long lRightPush(String key, T value) {
        return redisTemplate.opsForList().rightPush(key, value);
    }

    /**
     * 添加到右侧(多个值依次添加a/b/c还是a/b/c)(rPush)
     *
     * @param key   键
     * @param value 多个值
     * @return 列表长度
     */
    public static Long lRightPushMulti(String key, Collection<Object> value) {
        return redisTemplate.opsForList().rightPushAll(key, value);
    }

    /**
     * 添加到右侧(多个值依次添加a/b/c还是a/b/c)(rPush)
     *
     * @param <T>   指定数据类型
     * @param key   键
     * @param value 多个值
     * @return 列表长度
     */
    public static <T> Long lRightPushMultiArray(String key, T[] value) {
        return redisTemplate.opsForList().rightPushAll(key, value);
    }

    /**
     * 当列表存在时，添加到右侧(rPushX)
     *
     * @param <T>   指定数据类型
     * @param key   键
     * @param value 值
     * @return 列表长度
     */
    public static <T> Long lRightPushIfPresent(String key, T value) {
        return redisTemplate.opsForList().rightPushIfPresent(key, value);
    }

    /**
     * 删除并返回左侧的值(lPop)
     *
     * @param key 键
     * @return 值(不存在键时为null)
     */
    public static Object lLeftPop(String key) {
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
    public static Object lLeftPop(String key, long timeout) {
        return redisTemplate.opsForList().leftPop(key, timeout, SECONDS);
    }

    /**
     * 删除并返回右侧的值(rPop)
     *
     * @param key 键
     * @return 值(不存在键时为null)
     */
    public static Object lRightPop(String key) {
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
    public static Object lRightPop(String key, long timeout) {
        return redisTemplate.opsForList().rightPop(key, timeout, SECONDS);
    }

    /**
     * 指定值第一次出现的下标(Redis版本需要6.0.6及以上)(lPos)
     *
     * @param <T>   指定数据类型
     * @param key   键
     * @param value 值
     * @return 下标(没有指定值为null)
     */
    public static <T> Long lIndexOf(String key, T value) {
        return redisTemplate.opsForList().indexOf(key, value);
    }

    /**
     * 指定值最后一次出现的下标(Redis版本需要6.0.6及以上)(lPos)
     *
     * @param <T>   指定数据类型
     * @param key   键
     * @param value 值
     * @return 下标(没有指定值为null)
     */
    public static <T> Long lLastIndexOf(String key, T value) {
        return redisTemplate.opsForList().lastIndexOf(key, value);
    }

    /**
     * 插入到指定位置(会替换该位置的值)(lSet)
     *
     * @param <T>   指定数据类型
     * @param key   键
     * @param index 下标
     * @param value 值
     */
    public static <T> void lSet(String key, long index, T value) {
        redisTemplate.opsForList().set(key, index, value);
    }

    /**
     * 删除第count次出现的值(正数从左边删，负数从右边删，0全部删除)(lRem)
     *
     * @param <T>   指定数据类型
     * @param key   键
     * @param count 第几次
     * @param value 值
     * @return 删除成功的个数(未删除返回0)
     */
    public static <T> Long lDelete(String key, long count, T value) {
        return redisTemplate.opsForList().remove(key, count, value);
    }

    /**
     * 删除左侧第一次出现的值(lRem)
     *
     * @param <T>   指定数据类型
     * @param key   键
     * @param value 值
     * @return 删除成功的个数(未删除返回0)
     */
    public static <T> Long lDeleteLeft(String key, T value) {
        return redisTemplate.opsForList().remove(key, 1, value);
    }

    /**
     * 删除右侧第一次出现的值(lRem)
     *
     * @param <T>   指定数据类型
     * @param key   键
     * @param value 值
     * @return 删除成功的个数(删除返回0)
     */
    public static <T> Long lDeleteRight(String key, T value) {
        return redisTemplate.opsForList().remove(key, -1, value);
    }

    /**
     * 删除全部出现的值(lRem)
     *
     * @param <T>   指定数据类型
     * @param key   键
     * @param value 值
     * @return 删除成功的个数(未删除返回0)
     */
    public static <T> Long lDeleteAll(String key, T value) {
        return redisTemplate.opsForList().remove(key, 0, value);
    }

    /**
     * 从sourceKey的右侧删除，添加到destinationKey的左侧，并返回这个值(rPopLPush)
     *
     * @param sourceKey      源键
     * @param destinationKey 目的键
     * @return 值(sourceKey不存在时为null)
     */
    public static Object lRightPopAndLeftPush(String sourceKey, String destinationKey) {
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
    public static Object lRightPopAndLeftPush(String sourceKey, String destinationKey, long timeout) {
        return redisTemplate.opsForList().rightPopAndLeftPush(sourceKey, destinationKey, timeout, SECONDS);
    }

    // TODO move

    // endregion

    /* ==================== 集合Set操作 ==================== */
    // region 集合Set操作

    /**
     * 添加(sAdd)
     *
     * @param <T>   指定数据类型
     * @param key   键
     * @param value 值
     * @return 列表长度
     */
    public static <T> Long sAdd(String key, T value) {
        return redisTemplate.opsForSet().add(key, value);
    }

    /**
     * 添加多个(sAdd)
     *
     * @param <T>   指定数据类型
     * @param key   键
     * @param value 值
     * @return 列表长度
     */
    public static <T> Long sAddMulti(String key, Collection<T> value) {
        return redisTemplate.opsForSet().add(key, value.toArray());
    }

    /**
     * 添加多个(sAdd)
     *
     * @param <T>   指定数据类型
     * @param key   键
     * @param value 值
     * @return 列表长度
     */
    @SafeVarargs
    public static <T> Long sAddMultiArray(String key, T... value) {
        return redisTemplate.opsForSet().add(key, value);
    }

    /**
     * 删除值(sRem)
     *
     * @param <T>   指定数据类型
     * @param key   键
     * @param value 值
     * @return 列表长度
     */
    public static <T> Long sDelete(String key, T value) {
        return redisTemplate.opsForSet().remove(key, value);
    }

    /**
     * 删除多个值(sRem)
     *
     * @param <T>   指定数据类型
     * @param key   键
     * @param value 值
     * @return 列表长度
     */
    public static <T> Long sDeleteMulti(String key, Collection<T> value) {
        return redisTemplate.opsForSet().remove(key, value.toArray());
    }

    /**
     * 删除多个值(sRem)
     *
     * @param <T>   指定数据类型
     * @param key   键
     * @param value 值
     * @return 列表长度
     */
    @SafeVarargs
    public static <T> Long sDeleteMultiArray(String key, T... value) {
        return redisTemplate.opsForSet().remove(key, Arrays.copyOf(value, value.length, Object[].class));
    }

    /**
     * 返回并删除1个随机值(sPop)
     *
     * @param key 键
     * @return 值
     */
    public static Object sPop(String key) {
        return redisTemplate.opsForSet().pop(key);
    }

    /**
     * 返回并删除多个随机值(sPop)
     *
     * @param key   键
     * @param count 数量
     * @return 值
     */
    public static List<Object> sPopMulti(String key, long count) {
        return redisTemplate.opsForSet().pop(key, count);
    }

    /**
     * 移动元素到目的键(sMove)
     *
     * @param key     键
     * @param value   值
     * @param destKey 目的键
     * @return 是否成功
     */
    public static <T> Boolean sMove(String key, T value, String destKey) {
        return redisTemplate.opsForSet().move(key, value, destKey);
    }

    /**
     * 元素个数(sCard)
     *
     * @param key 键
     * @return 个数
     */
    public static Long sSize(String key) {
        return redisTemplate.opsForSet().size(key);
    }

    /**
     * 是否存在元素(sIsMember)
     *
     * @param key   键
     * @param value 值
     * @return 是否存在
     */
    public static <T> Boolean sIsMember(String key, T value) {
        return redisTemplate.opsForSet().isMember(key, value);
    }

    /**
     * 是否存在多个元素(Redis版本需要6.2.0及以上)(sMIsMember)
     *
     * @param key   键
     * @param value 值
     * @return Map
     */
    public static <T> Map<Object, Boolean> sIsMultiMember(String key, Collection<T> value) {
        return redisTemplate.opsForSet().isMember(key, value.toArray());
    }

    /**
     * 是否存在多个元素(Redis版本需要6.2.0及以上)(sMIsMember)
     *
     * @param key   键
     * @param value 值
     * @return Map
     */
    @SafeVarargs
    public static <T> Map<Object, Boolean> sIsMultiMemberArray(String key, T... value) {
        return redisTemplate.opsForSet().isMember(key, Arrays.copyOf(value, value.length, Object[].class));
    }

    /**
     * 所有元素(sMembers)
     *
     * @param key 键
     * @return 元素
     */
    public static Set<Object> sMembers(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 随机获取1个元素(sRandMember)
     *
     * @param key 键
     * @return 元素
     */
    public static Object sRandomMember(String key) {
        return redisTemplate.opsForSet().randomMember(key);
    }

    /**
     * 随机获取多个元素(sRandMember)
     *
     * @param key   键
     * @param count 个数(大于0)
     * @return 元素(key不存在返回空List)
     */
    public static List<Object> sRandomMultiMember(String key, long count) {
        return redisTemplate.opsForSet().randomMembers(key, count);
    }

    /**
     * 随机获取多个不重复元素(sRandMember)
     *
     * @param key   键
     * @param count 个数(大于0)
     * @return 元素(key不存在返回空Set)
     */
    public static Set<Object> sRandomMultiDistinctMember(String key, long count) {
        return redisTemplate.opsForSet().distinctRandomMembers(key, count);
    }

    /**
     * 两键交集(sInter)
     *
     * @param key      键
     * @param otherKey 另一个键
     * @return 交集
     */
    public static Set<Object> sIntersect(String key, String otherKey) {
        return redisTemplate.opsForSet().intersect(key, otherKey);
    }

    /**
     * 键与其他键的交集(sInter)
     *
     * @param key      键
     * @param otherKey 其他键
     * @return 交集
     */
    public static Set<Object> sIntersectMulti(String key, Collection<String> otherKey) {
        return redisTemplate.opsForSet().intersect(key, otherKey);
    }

    /**
     * 所有键的交集(sInter)
     *
     * @param keys 键
     * @return 交集
     */
    public static Set<Object> sIntersectAll(Collection<String> keys) {
        return redisTemplate.opsForSet().intersect(keys);
    }

    /**
     * 两键交集并储存(sInterStore)
     *
     * @param key      键
     * @param otherKey 另一个键
     * @return 交集个数
     */
    public static Long sIntersectAndStore(String key, String otherKey, String destKey) {
        return redisTemplate.opsForSet().intersectAndStore(key, otherKey, destKey);
    }

    /**
     * 键与其他键的交集并储存(sInterStore)
     *
     * @param key      键
     * @param otherKey 其他键
     * @return 交集个数
     */
    public static Long sIntersectMultiAndStore(String key, Collection<String> otherKey,
                                               String destKey) {
        return redisTemplate.opsForSet().intersectAndStore(key, otherKey, destKey);
    }

    /**
     * 所有键的交集并储存(sInterStore)
     *
     * @param keys 键
     * @return 交集个数
     */
    public static Long sIntersectAllAndStore(Collection<String> keys, String destKey) {
        return redisTemplate.opsForSet().intersectAndStore(keys, destKey);
    }

    /**
     * 两键并集(sUnion)
     *
     * @param key      键
     * @param otherKey 另一个键
     * @return 并集
     */
    public static Set<Object> sUnion(String key, String otherKey) {
        return redisTemplate.opsForSet().union(key, otherKey);
    }

    /**
     * 键与其他键的并集(sUnion)
     *
     * @param key      键
     * @param otherKey 其他键
     * @return 并集
     */
    public static Set<Object> sUnionMulti(String key, Collection<String> otherKey) {
        return redisTemplate.opsForSet().union(key, otherKey);
    }

    /**
     * 所有键的并集(sUnion)
     *
     * @param keys 键
     * @return 并集
     */
    public static Set<Object> sUnionAll(Collection<String> keys) {
        return redisTemplate.opsForSet().union(keys);
    }

    /**
     * 两键并集并储存(sUnionStore)
     *
     * @param key      键
     * @param otherKey 另一个键
     * @return 并集个数
     */
    public static Long sUnionAndStore(String key, String otherKey, String destKey) {
        return redisTemplate.opsForSet().unionAndStore(key, otherKey, destKey);
    }

    /**
     * 键与其他键的并集并储存(sUnionStore)
     *
     * @param key      键
     * @param otherKey 其他键
     * @return 并集个数
     */
    public static Long sUnionMultiAndStore(String key, Collection<String> otherKey,
                                           String destKey) {
        return redisTemplate.opsForSet().unionAndStore(key, otherKey, destKey);
    }

    /**
     * 所有键的并集并储存(sUnionStore)
     *
     * @param keys 键
     * @return 并集个数
     */
    public static Long sUnionAllAndStore(Collection<String> keys, String destKey) {
        return redisTemplate.opsForSet().unionAndStore(keys, destKey);
    }

    /**
     * 两键差集(sDiff)
     *
     * @param key      键
     * @param otherKey 另一个键
     * @return 差集
     */
    public static Set<Object> sDifference(String key, String otherKey) {
        return redisTemplate.opsForSet().difference(key, otherKey);
    }

    /**
     * 键与其他键的差集(sDiff)
     *
     * @param key      键
     * @param otherKey 其他键
     * @return 差集
     */
    public static Set<Object> sDifferenceMulti(String key, Collection<String> otherKey) {
        return redisTemplate.opsForSet().difference(key, otherKey);
    }

    /**
     * 所有键的差集(sDiff)
     *
     * @param keys 键
     * @return 差集
     */
    public static Set<Object> sDifferenceAll(Collection<String> keys) {
        return redisTemplate.opsForSet().difference(keys);
    }

    /**
     * 两键差集并储存(sDiffStore)
     *
     * @param key      键
     * @param otherKey 另一个键
     * @return 差集个数
     */
    public static Long sDifferenceAndStore(String key, String otherKey, String destKey) {
        return redisTemplate.opsForSet().differenceAndStore(key, otherKey, destKey);
    }

    /**
     * 键与其他键的差集并储存(sDiffStore)
     *
     * @param key      键
     * @param otherKey 其他键
     * @return 差集个数
     */
    public static Long sDifferenceMultiAndStore(String key, Collection<String> otherKey,
                                                String destKey) {
        return redisTemplate.opsForSet().differenceAndStore(key, otherKey, destKey);
    }

    /**
     * 所有键的差集并储存(sDiffStore)
     *
     * @param keys 键
     * @return 差集个数
     */
    public static Long sDifferenceAllAndStore(Collection<String> keys, String destKey) {
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
     * @return 值
     */
    public static Set<Object> sScan(String key, String match) {
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
     * @return 值
     */
    public static Set<Object> sScan(String key, String match, long count) {
        Set<Object> values = new HashSet<>();
        Cursor<Object> cursor = redisTemplate.opsForSet().scan( //
                key, ScanOptions.scanOptions().match(match).count(count).build());
        while (cursor.hasNext()) {
            Object next = cursor.next();
            values.add(next);
        }
        return values;
    }

    // endregion

}
