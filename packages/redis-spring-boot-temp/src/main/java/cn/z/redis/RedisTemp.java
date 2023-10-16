package cn.z.redis;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.JSONWriter;
import org.springframework.data.redis.connection.BitFieldSubCommands;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisListCommands;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.query.SortQuery;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * <h1>Redis模板</h1>
 *
 * <p>
 * createDate 2020/12/04 15:57:36
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class RedisTemp {

    /**
     * Redis模板
     */
    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * 构造函数(自动注入)
     *
     * @param factory RedisConnectionFactory
     */
    public RedisTemp(RedisConnectionFactory factory) {
        redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        // key使用String序列化
        RedisSerializer<String> stringRedisSerializer = StringRedisSerializer.UTF_8;
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        // value使用FastJson序列化
        RedisSerializer<Object> fastJsonRedisSerializer = new RedisSerializer<Object>() {
            @Override
            public byte[] serialize(Object object) throws SerializationException {
                if (object == null) {
                    return new byte[0];
                }
                // 写类名
                return JSON.toJSONBytes(object, "yyyy-MM-dd HH:mm:ss", JSONWriter.Feature.WriteClassName);
            }

            @Override
            public Object deserialize(byte[] bytes) throws SerializationException {
                // 类型自动探测
                return JSON.parseObject(bytes, Object.class, JSONReader.Feature.SupportAutoType);
            }
        };
        redisTemplate.setValueSerializer(fastJsonRedisSerializer);
        redisTemplate.setHashValueSerializer(fastJsonRedisSerializer);
        redisTemplate.afterPropertiesSet();
    }

    /* ==================== 通用操作 ==================== */
    // region 通用操作

    /**
     * 拷贝(copy)
     *
     * @param sourceKey 源键
     * @param targetKey 目标键(不存在将创建)
     * @param replace   targetKey已存在也执行操作
     * @return 是否成功
     */
    public Boolean copy(String sourceKey, String targetKey, boolean replace) {
        return redisTemplate.copy(sourceKey, targetKey, replace);
    }

    /**
     * key是否存在(exists)
     *
     * @param key 键
     * @return 是否存在
     */
    public Boolean exists(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 集合中存在的key的数量(exists)
     *
     * @param keys 键(重复会计算多次)
     * @return 数量
     */
    public Long existsCount(Collection<String> keys) {
        return redisTemplate.countExistingKeys(keys);
    }

    /**
     * 集合中存在的key的数量(exists)
     *
     * @param keys 键(重复会计算多次)
     * @return 数量
     */
    public Long existsCount(String... keys) {
        return redisTemplate.countExistingKeys(Arrays.asList(keys));
    }

    /**
     * 删除key(del)
     *
     * @param key 键
     * @return 是否成功
     */
    public Boolean delete(String key) {
        return redisTemplate.delete(key);
    }

    /**
     * 删除多个key(del)
     *
     * @param keys 键
     * @return 成功个数
     */
    public Long deleteMulti(Collection<String> keys) {
        return redisTemplate.delete(keys);
    }

    /**
     * 删除多个key(del)
     *
     * @param keys 键
     * @return 成功个数
     */
    public Long deleteMulti(String... keys) {
        return redisTemplate.delete(Arrays.asList(keys));
    }

    /**
     * 非阻塞异步删除key(unlink)
     *
     * @param key 键
     * @return 是否成功
     */
    public Boolean unlink(String key) {
        return redisTemplate.unlink(key);
    }

    /**
     * 非阻塞异步删除多个key(unlink)
     *
     * @param keys 键
     * @return 成功个数
     */
    public Long unlinkMulti(Collection<String> keys) {
        return redisTemplate.unlink(keys);
    }

    /**
     * 非阻塞异步删除多个key(unlink)
     *
     * @param keys 键
     * @return 成功个数
     */
    public Long unlinkMulti(String... keys) {
        return redisTemplate.unlink(Arrays.asList(keys));
    }

    /**
     * 获取存储的数据类型(type)
     *
     * @param key 键
     * @return none/string/list/set/zset/hash/stream
     */
    public DataType type(String key) {
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
    public Set<String> keys(String pattern) {
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
    public List<String> scan(String match) {
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
    public List<String> scan(String match, long count) {
        List<String> list = new ArrayList<>();
        try (Cursor<String> cursor = redisTemplate.scan(ScanOptions.scanOptions().match(match).count(count).build())) {
            while (cursor.hasNext()) {
                list.add(cursor.next());
            }
        }
        return list;
    }

    /**
     * 获取一个随机的key(randomKey)
     *
     * @return 键(不存在任何key返回null)
     */
    public String randomKey() {
        return redisTemplate.randomKey();
    }

    /**
     * 重命名key(rename)
     *
     * @param oldKey 旧键(不存在报错)
     * @param newKey 新键(已存在会被覆盖)
     */
    public void rename(String oldKey, String newKey) {
        redisTemplate.rename(oldKey, newKey);
    }

    /**
     * 重命名key，仅当newKey不存在时(renameNX)
     *
     * @param oldKey 旧键
     * @param newKey 新键
     * @return 是否成功
     */
    public Boolean renameIfAbsent(String oldKey, String newKey) {
        return redisTemplate.renameIfAbsent(oldKey, newKey);
    }

    /**
     * 指定超时时间(expire)
     *
     * @param key     键
     * @param timeout 超时时间(秒，<=0删除)
     * @return 是否成功
     */
    public Boolean expire(String key, long timeout) {
        return redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 指定失效日期(expire)
     *
     * @param key  键
     * @param date 失效日期
     * @return 是否成功
     */
    public Boolean expireAt(String key, Date date) {
        return redisTemplate.expireAt(key, date);
    }

    /**
     * 指定为持久数据(persist)
     *
     * @param key 键
     * @return 是否成功
     */
    public Boolean persist(String key) {
        return redisTemplate.persist(key);
    }

    /**
     * 获取超时时间(ttl)
     *
     * @param key 键
     * @return 超时时间(秒 ， - 1不过期 ， - 2不存在)
     */
    public Long getExpire(String key) {
        return redisTemplate.getExpire(key);
    }

    /**
     * 获取超时时间(ttl)
     *
     * @param key  键
     * @param unit TimeUnit
     * @return 超时时间(- 1不过期 ， - 2不存在)
     */
    public Long getExpire(String key, TimeUnit unit) {
        return redisTemplate.getExpire(key, unit);
    }

    /**
     * 将key移动到指定索引的数据库(move)
     *
     * @param key     键
     * @param dbIndex 数据库索引
     * @return 是否成功
     */
    public Boolean move(String key, int dbIndex) {
        return redisTemplate.move(key, dbIndex);
    }

    /**
     * 导出(dump)
     *
     * @param key 键
     * @return byte[]
     */
    public byte[] dump(String key) {
        return redisTemplate.dump(key);
    }

    /**
     * 导入(restore)
     *
     * @param key     键(已存在并且replace为false时报错)
     * @param value   byte[]
     * @param timeout 超时时间(秒，必须>=0，0不过期)
     * @param replace key已存在也执行操作
     */
    public void restore(String key, byte[] value, long timeout, boolean replace) {
        redisTemplate.restore(key, value, timeout, TimeUnit.SECONDS, replace);
    }

    /**
     * 导入(restore)
     *
     * @param key     键(已存在并且replace为false时报错)
     * @param value   byte[]
     * @param timeout 超时时间(必须>=0，0不过期)
     * @param unit    TimeUnit
     * @param replace key已存在也执行操作
     */
    public void restore(String key, byte[] value, long timeout, TimeUnit unit, boolean replace) {
        redisTemplate.restore(key, value, timeout, unit, replace);
    }

    /**
     * 排序(sort)
     *
     * @param query SortQuery
     * @return 排序后的列表
     */
    public List<Object> sort(SortQuery<String> query) {
        return redisTemplate.sort(query);
    }

    // endregion

    /* ==================== 字符串Value操作 ==================== */
    // region 字符串Value操作

    /**
     * 放入(set)
     *
     * @param key 键(已存在会被覆盖)
     */
    public void set(String key) {
        redisTemplate.opsForValue().set(key, null);
    }

    /**
     * 放入(set)
     *
     * @param <T>   数据类型
     * @param key   键(已存在会被覆盖)
     * @param value 值
     */
    public <T> void set(String key, T value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 放入，并设置超时时间(setEX)
     *
     * @param key     键(已存在会被覆盖)
     * @param timeout 超时时间(秒，必须>0)
     */
    public void set(String key, long timeout) {
        redisTemplate.opsForValue().set(key, null, timeout, TimeUnit.SECONDS);
    }

    /**
     * 放入，并设置超时时间(setEX)
     *
     * @param key     键(已存在会被覆盖)
     * @param timeout 超时时间(必须>0)
     * @param unit    TimeUnit
     */
    public void set(String key, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, null, timeout, unit);
    }

    /**
     * 放入，并设置超时时间(setEX)
     *
     * @param <T>     数据类型
     * @param key     键(已存在会被覆盖)
     * @param value   值
     * @param timeout 超时时间(秒，必须>0)
     */
    public <T> void set(String key, T value, long timeout) {
        redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
    }

    /**
     * 放入，并设置超时时间(setEX)
     *
     * @param <T>     数据类型
     * @param key     键(已存在会被覆盖)
     * @param value   值
     * @param timeout 超时时间(必须>0)
     * @param unit    TimeUnit
     */
    public <T> void set(String key, T value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    /**
     * 如果key不存在，则放入(setNX)
     *
     * @param key 键
     * @return 是否成功
     */
    public Boolean setIfAbsent(String key) {
        return redisTemplate.opsForValue().setIfAbsent(key, null);
    }

    /**
     * 如果key不存在，则放入(setNX)
     *
     * @param <T>   数据类型
     * @param key   键
     * @param value 值
     * @return 是否成功
     */
    public <T> Boolean setIfAbsent(String key, T value) {
        return redisTemplate.opsForValue().setIfAbsent(key, value);
    }

    /**
     * 如果key不存在，则放入，并设置超时时间(set)
     *
     * @param key     键
     * @param timeout 超时时间(秒，必须>0)
     * @return 是否成功
     */
    public Boolean setIfAbsent(String key, long timeout) {
        return redisTemplate.opsForValue().setIfAbsent(key, null, timeout, TimeUnit.SECONDS);
    }

    /**
     * 如果key不存在，则放入，并设置超时时间(set)
     *
     * @param key     键
     * @param timeout 超时时间(必须>0)
     * @param unit    TimeUnit
     * @return 是否成功
     */
    public Boolean setIfAbsent(String key, long timeout, TimeUnit unit) {
        return redisTemplate.opsForValue().setIfAbsent(key, null, timeout, unit);
    }

    /**
     * 如果key不存在，则放入，并设置超时时间(set)
     *
     * @param <T>     数据类型
     * @param key     键
     * @param value   值
     * @param timeout 超时时间(秒，必须>0)
     * @return 是否成功
     */
    public <T> Boolean setIfAbsent(String key, T value, long timeout) {
        return redisTemplate.opsForValue().setIfAbsent(key, value, timeout, TimeUnit.SECONDS);
    }

    /**
     * 如果key不存在，则放入，并设置超时时间(set)
     *
     * @param <T>     数据类型
     * @param key     键
     * @param value   值
     * @param timeout 超时时间(必须>0)
     * @param unit    TimeUnit
     * @return 是否成功
     */
    public <T> Boolean setIfAbsent(String key, T value, long timeout, TimeUnit unit) {
        return redisTemplate.opsForValue().setIfAbsent(key, value, timeout, unit);
    }

    /**
     * 如果key存在，则放入(set)
     *
     * @param key 键
     * @return 是否成功
     */
    public Boolean setIfPresent(String key) {
        return redisTemplate.opsForValue().setIfPresent(key, null);
    }

    /**
     * 如果key存在，则放入(set)
     *
     * @param <T>   数据类型
     * @param key   键
     * @param value 值
     * @return 是否成功
     */
    public <T> Boolean setIfPresent(String key, T value) {
        return redisTemplate.opsForValue().setIfPresent(key, value);
    }

    /**
     * 如果key存在，则放入，并设置超时时间(set)
     *
     * @param key     键
     * @param timeout 超时时间(秒，必须>0)
     * @return 是否成功
     */
    public Boolean setIfPresent(String key, long timeout) {
        return redisTemplate.opsForValue().setIfPresent(key, null, timeout, TimeUnit.SECONDS);
    }

    /**
     * 如果key存在，则放入，并设置超时时间(set)
     *
     * @param key     键
     * @param timeout 超时时间(必须>0)
     * @param unit    TimeUnit
     * @return 是否成功
     */
    public Boolean setIfPresent(String key, long timeout, TimeUnit unit) {
        return redisTemplate.opsForValue().setIfPresent(key, null, timeout, unit);
    }

    /**
     * 如果key存在，则放入，并设置超时时间(set)
     *
     * @param <T>     数据类型
     * @param key     键
     * @param value   值
     * @param timeout 超时时间(秒，必须>0)
     * @return 是否成功
     */
    public <T> Boolean setIfPresent(String key, T value, long timeout) {
        return redisTemplate.opsForValue().setIfPresent(key, value, timeout, TimeUnit.SECONDS);
    }

    /**
     * 如果key存在，则放入，并设置超时时间(set)
     *
     * @param <T>     数据类型
     * @param key     键
     * @param value   值
     * @param timeout 超时时间(必须>0)
     * @param unit    TimeUnit
     * @return 是否成功
     */
    public <T> Boolean setIfPresent(String key, T value, long timeout, TimeUnit unit) {
        return redisTemplate.opsForValue().setIfPresent(key, value, timeout, unit);
    }

    /**
     * 键和值依次放入(mSet)
     *
     * @param <T> 数据类型
     * @param map 键和值(key已存在会被覆盖)
     */
    public <T> void setMulti(Map<String, T> map) {
        redisTemplate.opsForValue().multiSet(map);
    }

    /**
     * 如果键和值全部不存在，则依次放入(mSetNX)
     *
     * @param <T> 数据类型
     * @param map 键和值
     */
    public <T> Boolean setMultiIfAbsent(Map<String, T> map) {
        return redisTemplate.opsForValue().multiSetIfAbsent(map);
    }

    /**
     * 获取(get)
     *
     * @param key 键(不存在返回null)
     * @return 值
     */
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 获取并删除(getDel)
     *
     * @param key 键(不存在返回null)
     * @return 值
     */
    public Object getAndDelete(String key) {
        return redisTemplate.opsForValue().getAndDelete(key);
    }

    /**
     * 获取并设置超时时间(getEx)
     *
     * @param key     键(不存在返回null)
     * @param timeout 超时时间(秒，必须>0)
     * @return 值
     */
    public Object getAndExpire(String key, long timeout) {
        return redisTemplate.opsForValue().getAndExpire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 获取并设置超时时间(getEx)
     *
     * @param key     键(不存在返回null)
     * @param timeout 超时时间(秒，必须>0)
     * @param unit    TimeUnit
     * @return 值
     */
    public Object getAndExpire(String key, long timeout, TimeUnit unit) {
        return redisTemplate.opsForValue().getAndExpire(key, timeout, unit);
    }

    /**
     * 获取并设置为持久数据(getEx)
     *
     * @param key 键(不存在返回null)
     * @return 值
     */
    public Object getAndPersist(String key) {
        return redisTemplate.opsForValue().getAndPersist(key);
    }

    /**
     * 获取并放入(getSet)
     *
     * @param key 键(不存在返回null)
     * @return 值
     */
    public Object getAndSet(String key) {
        return redisTemplate.opsForValue().getAndSet(key, null);
    }

    /**
     * 获取并放入(getSet)
     *
     * @param <T>   数据类型
     * @param key   键(不存在返回null)
     * @param value 值
     * @return 值
     */
    public <T> Object getAndSet(String key, T value) {
        return redisTemplate.opsForValue().getAndSet(key, value);
    }

    /**
     * 获取多个(mGet)
     *
     * @param keys 多个键(不存在返回null)
     * @return 值列表
     */
    public List<Object> getMulti(Collection<String> keys) {
        return redisTemplate.opsForValue().multiGet(keys);
    }

    /**
     * 获取多个(mGet)
     *
     * @param keys 多个键(不存在返回null)
     * @return 值列表
     */
    public List<Object> getMulti(String... keys) {
        return redisTemplate.opsForValue().multiGet(Arrays.asList(keys));
    }

    /**
     * 递增1，值必须是整数类型(incr)
     *
     * @param key 键(不存在自动创建并赋值为0后再执行操作)
     * @return 递增后的值
     */
    public Long increment(String key) {
        return redisTemplate.opsForValue().increment(key);
    }

    /**
     * 递增，值必须是整数类型(incrBy)
     *
     * @param key   键(不存在自动创建并赋值为0后再执行操作)
     * @param delta 增量
     * @return 递增后的值
     */
    public Long increment(String key, long delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 递增，值必须是数字(redis值中不能带字母)类型(incrByFloat)<br>
     * 注意：慎用
     *
     * @param key   键(不存在自动创建并赋值为0后再执行操作)
     * @param delta 增量
     * @return 递增后的值
     */
    public Double increment(String key, double delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 递减1，值必须是整数类型(decr)
     *
     * @param key 键(不存在自动创建并赋值为0后再执行操作)
     * @return 递减后的值
     */
    public Long decrement(String key) {
        return redisTemplate.opsForValue().decrement(key);
    }

    /**
     * 递减，值必须是整数类型(decrBy)
     *
     * @param key   键(不存在自动创建并赋值为0后再执行操作)
     * @param delta 减量
     * @return 递减后的值
     */
    public Long decrement(String key, long delta) {
        return redisTemplate.opsForValue().decrement(key, delta);
    }

    /**
     * 递减，值必须是数字(redis值中不能带字母)类型(incrByFloat)<br>
     * 注意：慎用
     *
     * @param key   键(不存在自动创建并赋值为0后再执行操作)
     * @param delta 减量
     * @return 递减后的值
     */
    public Double decrement(String key, double delta) {
        return redisTemplate.opsForValue().increment(key, -delta);
    }

    /**
     * 追加字符串(append)<br>
     * 注意：慎用
     *
     * @param key   键(键不存在自动创建并赋值为空字符串后再执行操作)
     * @param value 字符串
     * @return 追加后的长度
     */
    public Integer append(String key, String value) {
        return redisTemplate.opsForValue().append(key, value);
    }

    /**
     * 获取字符串(getRange)<br>
     * 注意：慎用
     *
     * @param key   键(不存在返回空字符串)
     * @param start 起始下标
     * @param end   结束下标
     * @return 字符串
     */
    public String get(String key, long start, long end) {
        return redisTemplate.opsForValue().get(key, start, end);
    }

    /**
     * 设置字符串(setRange)<br>
     * 注意：慎用
     *
     * @param <T>    数据类型
     * @param key    键(不存在将创建)
     * @param value  值
     * @param offset 下标
     */
    public <T> void setValue(String key, T value, long offset) {
        redisTemplate.opsForValue().set(key, value, offset);
    }

    /**
     * 获取字符串长度(strLen)<br>
     * 注意：慎用
     *
     * @param key 键(不存在返回0)
     * @return 长度
     */
    public Long size(String key) {
        return redisTemplate.opsForValue().size(key);
    }

    /**
     * 设置bit(setBit)<br>
     * 注意：慎用
     *
     * @param key    键(不存在将创建)
     * @param offset 下标
     * @param value  值
     * @return 是否成功
     */
    public Boolean setBit(String key, long offset, boolean value) {
        return redisTemplate.opsForValue().setBit(key, offset, value);
    }

    /**
     * 获取bit(getBit)<br>
     * 注意：慎用
     *
     * @param key    键(不存在返回false)
     * @param offset 下标
     * @return bit
     */
    public Boolean getBit(String key, long offset) {
        return redisTemplate.opsForValue().getBit(key, offset);
    }

    /**
     * bitField(bitField)<br>
     * 注意：慎用
     *
     * @param key         键
     * @param subCommands BitFieldSubCommands
     * @return List
     */
    public List<Long> bitField(String key, BitFieldSubCommands subCommands) {
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
    public Long hDelete(String key, String item) {
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
    public Long hDeleteMulti(String key, Collection<String> items) {
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
    public Long hDeleteMulti(String key, String... items) {
        return redisTemplate.opsForHash().delete(key, Arrays.copyOf(items, items.length, Object[].class));
    }

    /**
     * 判断map中是否有指定项(hExists)
     *
     * @param key  键
     * @param item 项
     * @return 是否存在
     */
    public Boolean hExists(String key, String item) {
        return redisTemplate.opsForHash().hasKey(key, item);
    }

    /**
     * 获取map中指定项的值(hGet)
     *
     * @param key  键(不存在返回null)
     * @param item 项(不存在返回null)
     * @return 值
     */
    public Object hGet(String key, String item) {
        return redisTemplate.opsForHash().get(key, item);
    }

    /**
     * 获取map中指定多个项的值(hMGet)
     *
     * @param key   键(不存在返回null)
     * @param items 多个项(不存在返回null)
     * @return 值列表
     */
    public List<Object> hGetMulti(String key, Collection<String> items) {
        return redisTemplate.opsForHash().multiGet(key, Arrays.asList(items.toArray()));
    }

    /**
     * 获取map中指定多个项的值(hMGet)
     *
     * @param key   键(不存在返回null)
     * @param items 多个项(不存在返回null)
     * @return 值列表
     */
    public List<Object> hGetMulti(String key, String... items) {
        return redisTemplate.opsForHash().multiGet(key, Arrays.asList(items));
    }

    /**
     * map指定项的值递增1，值必须是整数类型(hIncrBy)
     *
     * @param key  键(不存在自动创建并赋值为0后再执行操作)
     * @param item 项(不存在自动创建并赋值为0后再执行操作)
     * @return 递增后的值
     */
    public Long hIncrement(String key, String item) {
        return redisTemplate.opsForHash().increment(key, item, 1);
    }

    /**
     * map指定项的值递增，值必须是整数类型(hIncrBy)
     *
     * @param key   键(不存在自动创建并赋值为0后再执行操作)
     * @param item  项(不存在自动创建并赋值为0后再执行操作)
     * @param delta 增量
     * @return 递增后的值
     */
    public Long hIncrement(String key, String item, long delta) {
        return redisTemplate.opsForHash().increment(key, item, delta);
    }

    /**
     * map指定项的值递增，值必须是数字(redis值中不能带字母)类型(hIncrByFloat)<br>
     * 注意：慎用
     *
     * @param key   键(不存在自动创建并赋值为0后再执行操作)
     * @param item  项(不存在自动创建并赋值为0后再执行操作)
     * @param delta 增量
     * @return 递增后的值
     */
    public Double hIncrement(String key, String item, double delta) {
        return redisTemplate.opsForHash().increment(key, item, delta);
    }

    /**
     * map指定项的值递减1，值必须是整数类型(hIncrBy)
     *
     * @param key  键(不存在自动创建并赋值为0后再执行操作)
     * @param item 项(不存在自动创建并赋值为0后再执行操作)
     * @return 递减后的值
     */
    public Long hDecrement(String key, String item) {
        return redisTemplate.opsForHash().increment(key, item, -1);
    }

    /**
     * map指定项的值递减，值必须是整数类型(hIncrBy)
     *
     * @param key   键(不存在自动创建并赋值为0后再执行操作)
     * @param item  项(不存在自动创建并赋值为0后再执行操作)
     * @param delta 减量
     * @return 递减后的值
     */
    public Long hDecrement(String key, String item, long delta) {
        return redisTemplate.opsForHash().increment(key, item, -delta);
    }

    /**
     * map指定项的值递减，值必须是数字(redis值中不能带字母)类型(hIncrByFloat)<br>
     * 注意：慎用
     *
     * @param key   键(不存在自动创建并赋值为0后再执行操作)
     * @param item  项(不存在自动创建并赋值为0后再执行操作)
     * @param delta 减量
     * @return 递减后的值
     */
    public Double hDecrement(String key, String item, double delta) {
        return redisTemplate.opsForHash().increment(key, item, -delta);
    }

    /**
     * 获取一个随机的项(hRandField)
     *
     * @param key 键(不存在返回null)
     * @return 项
     */
    public String hRandomItem(String key) {
        return (String) redisTemplate.opsForHash().randomKey(key);
    }

    /**
     * 获取一个随机的项和值(hRandField)
     *
     * @param key 键(不存在返回null)
     * @return 项和值
     */
    public Map.Entry<Object, Object> hRandomMap(String key) {
        return redisTemplate.opsForHash().randomEntry(key);
    }

    /**
     * 获取多个随机的项(hRandField)
     *
     * @param key 键(不存在返回[])
     * @return 项列表
     */
    public List<Object> hRandomItem(String key, long count) {
        return redisTemplate.opsForHash().randomKeys(key, count);
    }

    /**
     * 获取多个随机的项和值(hRandField)
     *
     * @param key 键(不存在报错)
     * @return 项和值列表
     */
    public Map<Object, Object> hRandomMap(String key, long count) {
        return redisTemplate.opsForHash().randomEntries(key, count);
    }

    /**
     * 获取map中指定项的长度(hStrLen)<br>
     * 注意：慎用
     *
     * @param key  键(不存在返回0)
     * @param item 项(不存在返回0)
     * @return 长度
     */
    public Long hLengthOfValue(String key, String item) {
        return redisTemplate.opsForHash().lengthOfValue(key, item);
    }

    /**
     * 获取项的数量(hLen)
     *
     * @param key 键(不存在返回0)
     * @return 数量
     */
    public Long hSize(String key) {
        return redisTemplate.opsForHash().size(key);
    }

    /**
     * 设置map的1个键值(hSet)
     *
     * @param <T>   数据类型
     * @param key   键
     * @param item  项(已存在会被覆盖)
     * @param value 值
     */
    public <T> void hSet(String key, String item, T value) {
        redisTemplate.opsForHash().put(key, item, value);
    }

    /**
     * 设置map的多个键值(hMSet)
     *
     * @param <T> 数据类型
     * @param key 键
     * @param map 多个键值(已存在会被覆盖)
     */
    public <T> void hSetMulti(String key, Map<String, T> map) {
        redisTemplate.opsForHash().putAll(key, map);
    }

    /**
     * 项不存在时，设置map的1个键值(hSetNX)
     *
     * @param <T>   数据类型
     * @param key   键
     * @param item  项
     * @param value 值
     * @return 是否成功
     */
    public <T> Boolean hPutIfAbsent(String key, String item, T value) {
        return redisTemplate.opsForHash().putIfAbsent(key, item, value);
    }

    /**
     * 获取map中所有的项(hKeys)
     *
     * @param key 键(不存在返回[])
     * @return 项列表
     */
    public Set<Object> hGetAllItem(String key) {
        return redisTemplate.opsForHash().keys(key);
    }

    /**
     * 获取map中所有的值(hVals)
     *
     * @param key 键(不存在返回[])
     * @return 值列表
     */
    public List<Object> hGetAllValue(String key) {
        return redisTemplate.opsForHash().values(key);
    }

    /**
     * 获取map中所有的项和值(hGetAll)
     *
     * @param key 键(不存在返回{})
     * @return 项和值列表
     */
    public Map<Object, Object> hGetAllItemAndValue(String key) {
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
     * @return 项和值Map
     */
    public Map<String, Object> hScan(String key, String match) {
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
     * @return 项和值Map
     */
    public Map<String, Object> hScan(String key, String match, long count) {
        Map<String, Object> map = new HashMap<>();
        try (Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(key, ScanOptions.scanOptions().match(match).count(count).build())) {
            while (cursor.hasNext()) {
                Map.Entry<Object, Object> entry = cursor.next();
                map.put((String) entry.getKey(), entry.getValue());
            }
        }
        return map;
    }

    // endregion

    /* ==================== 列表List操作 ==================== */
    // region 列表List操作

    /**
     * 获取指定下标的值(lIndex)
     *
     * @param key   键(不存在返回null)
     * @param index 下标(不存在返回null)
     * @return 值
     */
    public Object lGet(String key, long index) {
        return redisTemplate.opsForList().index(key, index);
    }

    /**
     * 获取第一个(lIndex)
     *
     * @param key 键(不存在返回null)
     * @return 值
     */
    public Object lGetFirst(String key) {
        return redisTemplate.opsForList().index(key, 0);
    }

    /**
     * 获取最后一个(lIndex)
     *
     * @param key 键(不存在返回null)
     * @return 值
     */
    public Object lGetLast(String key) {
        return redisTemplate.opsForList().index(key, -1);
    }

    /**
     * 获取(lRange)
     *
     * @param key   键(不存在返回[])
     * @param start 起始下标
     * @param end   结束下标
     * @return 值列表
     */
    public List<Object> lGetMulti(String key, long start, long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    /**
     * 获取全部(lRange)
     *
     * @param key 键(不存在返回[])
     * @return 值列表
     */
    public List<Object> lGetAll(String key) {
        return redisTemplate.opsForList().range(key, 0, -1);
    }

    /**
     * 修剪(只保留下标为[start,end]的值)(lTrim)
     *
     * @param key   键
     * @param start 起始下标
     * @param end   结束下标
     */
    public void lTrim(String key, long start, long end) {
        redisTemplate.opsForList().trim(key, start, end);
    }

    /**
     * 获取列表的长度(lLen)
     *
     * @param key 键(不存在返回0)
     * @return 长度
     */
    public Long lSize(String key) {
        return redisTemplate.opsForList().size(key);
    }

    /**
     * 添加到指定值的左侧(lInsert before)
     *
     * @param <T>   数据类型
     * @param key   键(不存在返回0)
     * @param pivot 指定值(不存在返回 - 1)
     * @param value 值
     * @return 列表长度
     */
    public <T> Long lInsertLeft(String key, T pivot, T value) {
        return redisTemplate.opsForList().leftPush(key, pivot, value);
    }

    /**
     * 添加到指定值的右侧(lInsert after)
     *
     * @param <T>   数据类型
     * @param key   键(不存在返回0)
     * @param pivot 指定值(不存在返回 - 1)
     * @param value 值
     * @return 列表长度
     */
    public <T> Long lInsertRight(String key, T pivot, T value) {
        return redisTemplate.opsForList().rightPush(key, pivot, value);
    }

    /**
     * 添加到左侧(lPush)
     *
     * @param <T>   数据类型
     * @param key   键(不存在将新建)
     * @param value 值
     * @return 列表长度
     */
    public <T> Long lLeftPush(String key, T value) {
        return redisTemplate.opsForList().leftPush(key, value);
    }

    /**
     * 添加到左侧(多个值依次添加a/b/c变成c/b/a)(lPush)
     *
     * @param key   键(不存在将新建)
     * @param value 多个值
     * @return 列表长度
     */
    public Long lLeftPushMulti(String key, Collection<Object> value) {
        return redisTemplate.opsForList().leftPushAll(key, value);
    }

    /**
     * 添加到左侧(多个值依次添加a/b/c变成c/b/a)(lPush)
     *
     * @param <T>   数据类型
     * @param key   键(不存在将新建)
     * @param value 多个值
     * @return 列表长度
     */
    public <T> Long lLeftPushMulti(String key, T[] value) {
        return redisTemplate.opsForList().leftPushAll(key, value);
    }

    /**
     * 当key存在时，添加到左侧(lPushX)
     *
     * @param <T>   数据类型
     * @param key   键(不存在返回0)
     * @param value 值
     * @return 列表长度
     */
    public <T> Long lLeftPushIfPresent(String key, T value) {
        return redisTemplate.opsForList().leftPushIfPresent(key, value);
    }

    /**
     * 添加到右侧(rPush)
     *
     * @param <T>   数据类型
     * @param key   键(不存在将新建)
     * @param value 值
     * @return 列表长度
     */
    public <T> Long lRightPush(String key, T value) {
        return redisTemplate.opsForList().rightPush(key, value);
    }

    /**
     * 添加到右侧(多个值依次添加a/b/c还是a/b/c)(rPush)
     *
     * @param key   键(不存在将新建)
     * @param value 多个值
     * @return 列表长度
     */
    public Long lRightPushMulti(String key, Collection<Object> value) {
        return redisTemplate.opsForList().rightPushAll(key, value);
    }

    /**
     * 添加到右侧(多个值依次添加a/b/c还是a/b/c)(rPush)
     *
     * @param <T>   数据类型
     * @param key   键(不存在将新建)
     * @param value 多个值
     * @return 列表长度
     */
    public <T> Long lRightPushMulti(String key, T[] value) {
        return redisTemplate.opsForList().rightPushAll(key, value);
    }

    /**
     * 当列表存在时，添加到右侧(rPushX)
     *
     * @param <T>   数据类型
     * @param key   键(不存在返回0)
     * @param value 值
     * @return 列表长度
     */
    public <T> Long lRightPushIfPresent(String key, T value) {
        return redisTemplate.opsForList().rightPushIfPresent(key, value);
    }

    /**
     * 删除并返回左侧的值(lPop)
     *
     * @param key 键(不存在返回null)
     * @return 值
     */
    public Object lLeftPop(String key) {
        return redisTemplate.opsForList().leftPop(key);
    }

    /**
     * 删除并返回左侧的值，并阻塞指定时间(bLPop)<br>
     * 当指定键不存在值时，客户端将等待指定时间查询数据，查询到了返回数据，查询不到返回null
     *
     * @param key     键
     * @param timeout 阻塞时间(秒)
     * @return 值
     */
    public Object lLeftPop(String key, long timeout) {
        return redisTemplate.opsForList().leftPop(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 删除并返回左侧的值，并阻塞指定时间(bLPop)<br>
     * 当指定键不存在值时，客户端将等待指定时间查询数据，查询到了返回数据，查询不到返回null
     *
     * @param key     键
     * @param timeout 阻塞时间
     * @param unit    TimeUnit
     * @return 值
     */
    public Object lLeftPop(String key, long timeout, TimeUnit unit) {
        return redisTemplate.opsForList().leftPop(key, timeout, unit);
    }

    /**
     * 删除并返回右侧的值(rPop)
     *
     * @param key 键(不存在返回null)
     * @return 值
     */
    public Object lRightPop(String key) {
        return redisTemplate.opsForList().rightPop(key);
    }

    /**
     * 删除并返回右侧的值，并阻塞指定时间(bRPop)<br>
     * 当指定键不存在值时，客户端将等待指定时间查询数据，查询到了返回数据，查询不到返回null
     *
     * @param key     键
     * @param timeout 阻塞时间(秒)
     * @return 值
     */
    public Object lRightPop(String key, long timeout) {
        return redisTemplate.opsForList().rightPop(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 删除并返回右侧的值，并阻塞指定时间(bRPop)<br>
     * 当指定键不存在值时，客户端将等待指定时间查询数据，查询到了返回数据，查询不到返回null
     *
     * @param key     键
     * @param timeout 阻塞时间
     * @param unit    TimeUnit
     * @return 值
     */
    public Object lRightPop(String key, long timeout, TimeUnit unit) {
        return redisTemplate.opsForList().rightPop(key, timeout, unit);
    }

    /**
     * 指定值第一次出现的下标(lPos)
     *
     * @param <T>   数据类型
     * @param key   键
     * @param value 值(不存在返回null)
     * @return 下标
     */
    public <T> Long lIndexOfFirst(String key, T value) {
        return redisTemplate.opsForList().indexOf(key, value);
    }

    /**
     * 指定值最后一次出现的下标(lPos)
     *
     * @param <T>   数据类型
     * @param key   键
     * @param value 值(不存在返回null)
     * @return 下标
     */
    public <T> Long lIndexOfLast(String key, T value) {
        return redisTemplate.opsForList().lastIndexOf(key, value);
    }

    /**
     * 插入到指定位置(会替换该位置的值)(lSet)
     *
     * @param <T>   数据类型
     * @param key   键
     * @param index 下标(越界报错)
     * @param value 值
     */
    public <T> void lSet(String key, long index, T value) {
        redisTemplate.opsForList().set(key, index, value);
    }

    /**
     * 删除第count次出现的值(正数从左边删，负数从右边删，0全部删除)(lRem)
     *
     * @param <T>   数据类型
     * @param key   键
     * @param count 第几次
     * @param value 值
     * @return 删除成功的个数(未删除返回0)
     */
    public <T> Long lDelete(String key, long count, T value) {
        return redisTemplate.opsForList().remove(key, count, value);
    }

    /**
     * 删除左侧第一次出现的值(lRem)
     *
     * @param <T>   数据类型
     * @param key   键
     * @param value 值
     * @return 删除成功的个数(未删除返回0)
     */
    public <T> Long lDeleteLeft(String key, T value) {
        return redisTemplate.opsForList().remove(key, 1, value);
    }

    /**
     * 删除右侧第一次出现的值(lRem)
     *
     * @param <T>   数据类型
     * @param key   键
     * @param value 值
     * @return 删除成功的个数(删除返回0)
     */
    public <T> Long lDeleteRight(String key, T value) {
        return redisTemplate.opsForList().remove(key, -1, value);
    }

    /**
     * 删除全部出现的值(lRem)
     *
     * @param <T>   数据类型
     * @param key   键
     * @param value 值
     * @return 删除成功的个数(未删除返回0)
     */
    public <T> Long lDeleteAll(String key, T value) {
        return redisTemplate.opsForList().remove(key, 0, value);
    }

    /**
     * 从sourceKey的右侧删除，添加到destinationKey的左侧，并返回这个值(rPopLPush)
     *
     * @param sourceKey      源键(不存在返回null)
     * @param destinationKey 目的键
     * @return 值
     */
    public Object lRightPopAndLeftPush(String sourceKey, String destinationKey) {
        return redisTemplate.opsForList().rightPopAndLeftPush(sourceKey, destinationKey);
    }

    /**
     * 从sourceKey的右侧删除，添加到destinationKey的左侧，并返回这个值，并阻塞指定时间(bRPopLPush)<br>
     * 当指定键不存在值时，客户端将等待指定时间查询数据，查询到了返回数据，查询不到返回null
     *
     * @param sourceKey      源键(不存在返回null)
     * @param destinationKey 目的键
     * @param timeout        阻塞时间(秒)
     * @return 值
     */
    public Object lRightPopAndLeftPush(String sourceKey, String destinationKey, long timeout) {
        return redisTemplate.opsForList().rightPopAndLeftPush(sourceKey, destinationKey, timeout, TimeUnit.SECONDS);
    }

    /**
     * 从sourceKey的右侧删除，添加到destinationKey的左侧，并返回这个值，并阻塞指定时间(bRPopLPush)<br>
     * 当指定键不存在值时，客户端将等待指定时间查询数据，查询到了返回数据，查询不到返回null
     *
     * @param sourceKey      源键(不存在返回null)
     * @param destinationKey 目的键
     * @param timeout        阻塞时间
     * @param unit           TimeUnit
     * @return 值
     */
    public Object lRightPopAndLeftPush(String sourceKey, String destinationKey, long timeout, TimeUnit unit) {
        return redisTemplate.opsForList().rightPopAndLeftPush(sourceKey, destinationKey, timeout, unit);
    }

    /**
     * 从sourceKey的左侧/右侧，添加到destinationKey的左侧/右侧(lMove)
     *
     * @param sourceKey      源键(不存在返回null)
     * @param from           从源键的左侧(true)/右侧(false)
     * @param destinationKey 目的键
     * @param to             到目的键左侧(true)/右侧(false)
     * @return 值
     */
    public Object lMove(String sourceKey, boolean from, String destinationKey, boolean to) {
        return redisTemplate.opsForList().move(sourceKey, from ? RedisListCommands.Direction.LEFT : RedisListCommands.Direction.RIGHT, destinationKey, to ? RedisListCommands.Direction.LEFT : RedisListCommands.Direction.RIGHT);
    }

    /**
     * 从sourceKey的左侧/右侧，添加到destinationKey的左侧/右侧，并返回这个值，并阻塞指定时间(bLMove)<br>
     * 当指定键不存在值时，客户端将等待指定时间查询数据，查询到了返回数据，查询不到返回null
     *
     * @param sourceKey      源键(不存在返回null)
     * @param from           从源键的左侧(true)/右侧(false)
     * @param destinationKey 目的键
     * @param to             到目的键左侧(true)/右侧(false)
     * @param timeout        阻塞时间(秒)
     * @return 值
     */
    public Object lMove(String sourceKey, boolean from, String destinationKey, boolean to, long timeout) {
        return redisTemplate.opsForList().move(sourceKey, from ? RedisListCommands.Direction.LEFT : RedisListCommands.Direction.RIGHT, destinationKey, to ? RedisListCommands.Direction.LEFT : RedisListCommands.Direction.RIGHT, timeout, TimeUnit.SECONDS);
    }

    /**
     * 从sourceKey的左侧/右侧，添加到destinationKey的左侧/右侧，并返回这个值，并阻塞指定时间(bLMove)<br>
     * 当指定键不存在值时，客户端将等待指定时间查询数据，查询到了返回数据，查询不到返回null
     *
     * @param sourceKey      源键(不存在返回null)
     * @param from           从源键的左侧(true)/右侧(false)
     * @param destinationKey 目的键
     * @param to             到目的键左侧(true)/右侧(false)
     * @param timeout        阻塞时间
     * @param unit           TimeUnit
     * @return 值
     */
    public Object lMove(String sourceKey, boolean from, String destinationKey, boolean to, long timeout, TimeUnit unit) {
        return redisTemplate.opsForList().move(sourceKey, from ? RedisListCommands.Direction.LEFT : RedisListCommands.Direction.RIGHT, destinationKey, to ? RedisListCommands.Direction.LEFT : RedisListCommands.Direction.RIGHT, timeout, unit);
    }

    // endregion

    /* ==================== 集合Set操作 ==================== */
    // region 集合Set操作

    /**
     * 添加(sAdd)
     *
     * @param <T>   数据类型
     * @param key   键
     * @param value 值
     * @return 列表长度
     */
    public <T> Long sAdd(String key, T value) {
        return redisTemplate.opsForSet().add(key, value);
    }

    /**
     * 添加多个(sAdd)
     *
     * @param <T>   数据类型
     * @param key   键
     * @param value 值
     * @return 列表长度
     */
    public <T> Long sAddMulti(String key, Collection<T> value) {
        return redisTemplate.opsForSet().add(key, value.toArray());
    }

    /**
     * 添加多个(sAdd)
     *
     * @param <T>   数据类型
     * @param key   键
     * @param value 值
     * @return 列表长度
     */
    public <T> Long sAddMulti(String key, T... value) {
        return redisTemplate.opsForSet().add(key, value);
    }

    /**
     * 删除值(sRem)
     *
     * @param <T>   数据类型
     * @param key   键
     * @param value 值
     * @return 列表长度
     */
    public <T> Long sDelete(String key, T value) {
        return redisTemplate.opsForSet().remove(key, value);
    }

    /**
     * 删除多个值(sRem)
     *
     * @param <T>   数据类型
     * @param key   键
     * @param value 值
     * @return 列表长度
     */
    public <T> Long sDeleteMulti(String key, Collection<T> value) {
        return redisTemplate.opsForSet().remove(key, value.toArray());
    }

    /**
     * 删除多个值(sRem)
     *
     * @param <T>   数据类型
     * @param key   键
     * @param value 值
     * @return 列表长度
     */
    public <T> Long sDeleteMulti(String key, T... value) {
        return redisTemplate.opsForSet().remove(key, Arrays.copyOf(value, value.length, Object[].class));
    }

    /**
     * 返回并删除1个随机值(sPop)
     *
     * @param key 键
     * @return 值
     */
    public Object sPop(String key) {
        return redisTemplate.opsForSet().pop(key);
    }

    /**
     * 返回并删除多个随机值(sPop)
     *
     * @param key   键
     * @param count 数量
     * @return 值列表
     */
    public List<Object> sPopMulti(String key, long count) {
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
    public <T> Boolean sMove(String key, T value, String destKey) {
        return redisTemplate.opsForSet().move(key, value, destKey);
    }

    /**
     * 元素数量(sCard)
     *
     * @param key 键
     * @return 数量
     */
    public Long sSize(String key) {
        return redisTemplate.opsForSet().size(key);
    }

    /**
     * 是否存在元素(sIsMember)
     *
     * @param key   键
     * @param value 值
     * @return 是否存在
     */
    public <T> Boolean sIsMember(String key, T value) {
        return redisTemplate.opsForSet().isMember(key, value);
    }

    /**
     * 是否存在多个元素(sMIsMember)
     *
     * @param key   键
     * @param value 值
     * @return Map
     */
    public <T> Map<Object, Boolean> sIsMemberMulti(String key, Collection<T> value) {
        return redisTemplate.opsForSet().isMember(key, value.toArray());
    }

    /**
     * 是否存在多个元素(sMIsMember)
     *
     * @param key   键(不存在返回false)
     * @param value 值
     * @return Map
     */
    public <T> Map<Object, Boolean> sIsMemberMulti(String key, T... value) {
        return redisTemplate.opsForSet().isMember(key, Arrays.copyOf(value, value.length, Object[].class));
    }

    /**
     * 所有元素(sMembers)
     *
     * @param key 键
     * @return 元素列表
     */
    public Set<Object> sMembers(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 随机获取1个元素(sRandMember)
     *
     * @param key 键
     * @return 元素
     */
    public Object sRandomMember(String key) {
        return redisTemplate.opsForSet().randomMember(key);
    }

    /**
     * 随机获取多个元素(sRandMember)
     *
     * @param key   键(不存在返回[])
     * @param count 个数(大于0)
     * @return 元素列表
     */
    public List<Object> sRandomMemberMulti(String key, long count) {
        return redisTemplate.opsForSet().randomMembers(key, count);
    }

    /**
     * 随机获取多个不重复元素(sRandMember)
     *
     * @param key   键(不存在返回[])
     * @param count 个数(大于0)
     * @return 元素列表
     */
    public Set<Object> sRandomMemberMultiDistinct(String key, long count) {
        return redisTemplate.opsForSet().distinctRandomMembers(key, count);
    }

    /**
     * 两键交集(sInter)
     *
     * @param key      键
     * @param otherKey 另一个键
     * @return 交集列表
     */
    public Set<Object> sIntersect(String key, String otherKey) {
        return redisTemplate.opsForSet().intersect(key, otherKey);
    }

    /**
     * 键与其他键的交集(sInter)
     *
     * @param key      键
     * @param otherKey 其他键
     * @return 交集列表
     */
    public Set<Object> sIntersectMulti(String key, Collection<String> otherKey) {
        return redisTemplate.opsForSet().intersect(key, otherKey);
    }

    /**
     * 所有键的交集(sInter)
     *
     * @param keys 键
     * @return 交集列表
     */
    public Set<Object> sIntersectAll(Collection<String> keys) {
        return redisTemplate.opsForSet().intersect(keys);
    }

    /**
     * 两键交集并储存(sInterStore)
     *
     * @param key      键
     * @param otherKey 另一个键
     * @return 交集个数
     */
    public Long sIntersectAndStore(String key, String otherKey, String destKey) {
        return redisTemplate.opsForSet().intersectAndStore(key, otherKey, destKey);
    }

    /**
     * 键与其他键的交集并储存(sInterStore)
     *
     * @param key      键
     * @param otherKey 其他键
     * @return 交集个数
     */
    public Long sIntersectAndStoreMulti(String key, Collection<String> otherKey,
                                        String destKey) {
        return redisTemplate.opsForSet().intersectAndStore(key, otherKey, destKey);
    }

    /**
     * 所有键的交集并储存(sInterStore)
     *
     * @param keys 键
     * @return 交集个数
     */
    public Long sIntersectAndStoreAll(Collection<String> keys, String destKey) {
        return redisTemplate.opsForSet().intersectAndStore(keys, destKey);
    }

    /**
     * 两键并集(sUnion)
     *
     * @param key      键
     * @param otherKey 另一个键
     * @return 并集列表
     */
    public Set<Object> sUnion(String key, String otherKey) {
        return redisTemplate.opsForSet().union(key, otherKey);
    }

    /**
     * 键与其他键的并集(sUnion)
     *
     * @param key      键
     * @param otherKey 其他键
     * @return 并集列表
     */
    public Set<Object> sUnionMulti(String key, Collection<String> otherKey) {
        return redisTemplate.opsForSet().union(key, otherKey);
    }

    /**
     * 所有键的并集(sUnion)
     *
     * @param keys 键
     * @return 并集列表
     */
    public Set<Object> sUnionAll(Collection<String> keys) {
        return redisTemplate.opsForSet().union(keys);
    }

    /**
     * 两键并集并储存(sUnionStore)
     *
     * @param key      键
     * @param otherKey 另一个键
     * @return 并集个数
     */
    public Long sUnionAndStore(String key, String otherKey, String destKey) {
        return redisTemplate.opsForSet().unionAndStore(key, otherKey, destKey);
    }

    /**
     * 键与其他键的并集并储存(sUnionStore)
     *
     * @param key      键
     * @param otherKey 其他键
     * @return 并集个数
     */
    public Long sUnionAndStoreMulti(String key, Collection<String> otherKey,
                                    String destKey) {
        return redisTemplate.opsForSet().unionAndStore(key, otherKey, destKey);
    }

    /**
     * 所有键的并集并储存(sUnionStore)
     *
     * @param keys 键
     * @return 并集个数
     */
    public Long sUnionAndStoreAll(Collection<String> keys, String destKey) {
        return redisTemplate.opsForSet().unionAndStore(keys, destKey);
    }

    /**
     * 两键差集(sDiff)
     *
     * @param key      键
     * @param otherKey 另一个键
     * @return 差集列表
     */
    public Set<Object> sDifference(String key, String otherKey) {
        return redisTemplate.opsForSet().difference(key, otherKey);
    }

    /**
     * 键与其他键的差集(sDiff)
     *
     * @param key      键
     * @param otherKey 其他键
     * @return 差集列表
     */
    public Set<Object> sDifferenceMulti(String key, Collection<String> otherKey) {
        return redisTemplate.opsForSet().difference(key, otherKey);
    }

    /**
     * 所有键的差集(sDiff)
     *
     * @param keys 键
     * @return 差集列表
     */
    public Set<Object> sDifferenceAll(Collection<String> keys) {
        return redisTemplate.opsForSet().difference(keys);
    }

    /**
     * 两键差集并储存(sDiffStore)
     *
     * @param key      键
     * @param otherKey 另一个键
     * @return 差集个数
     */
    public Long sDifferenceAndStore(String key, String otherKey, String destKey) {
        return redisTemplate.opsForSet().differenceAndStore(key, otherKey, destKey);
    }

    /**
     * 键与其他键的差集并储存(sDiffStore)
     *
     * @param key      键
     * @param otherKey 其他键
     * @return 差集个数
     */
    public Long sDifferenceAndStoreMulti(String key, Collection<String> otherKey,
                                         String destKey) {
        return redisTemplate.opsForSet().differenceAndStore(key, otherKey, destKey);
    }

    /**
     * 所有键的差集并储存(sDiffStore)
     *
     * @param keys 键
     * @return 差集个数
     */
    public Long sDifferenceAndStoreAll(Collection<String> keys, String destKey) {
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
     * @return 值列表
     */
    public List<Object> sScan(String key, String match) {
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
     * @return 值列表
     */
    public List<Object> sScan(String key, String match, long count) {
        List<Object> list = new ArrayList<>();
        try (Cursor<Object> cursor = redisTemplate.opsForSet().scan(key, ScanOptions.scanOptions().match(match).count(count).build())) {
            while (cursor.hasNext()) {
                list.add(cursor.next());
            }
        }
        return list;
    }

    // endregion

    /* ==================== 发布订阅 ==================== */
    // region 发布订阅

    /**
     * 广播
     *
     * @param topic 主题
     */
    public void broadcast(String topic) {
        redisTemplate.convertAndSend(topic, null);
    }

    /**
     * 广播
     *
     * @param topic 主题
     * @param data  数据
     */
    public void broadcast(String topic, Object data) {
        redisTemplate.convertAndSend(topic, data);
    }

    // endregion

}
