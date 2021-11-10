package com.demo.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.RedisSystemException;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
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
@Slf4j
public class RedisUtils {

    public static final TimeUnit SECONDS = TimeUnit.SECONDS;
    public static RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public RedisUtils(RedisConnectionFactory factory) {
        //FastJson序列化
        RedisSerializer<Object> fastJsonRedisSerializer = new RedisSerializer<Object>() {
            @Override
            public byte[] serialize(Object o) throws SerializationException {
                if (o == null) {
                    return new byte[0];
                }
                return JSON.toJSONStringWithDateFormat(//
                        o,//
                        "yyyy-MM-dd HH:mm:ss", // 日期格式化样式
                        SerializerFeature.DisableCircularReferenceDetect, // 禁用对象循环引用：避免$ref
                        SerializerFeature.WriteClassName // 写入类名
                ).getBytes(StandardCharsets.UTF_8);
            }

            @Override
            public Object deserialize(byte[] bytes) throws SerializationException {
                if (bytes == null || bytes.length == 0) {
                    return null;
                }
                return JSON.parseObject(new String(bytes, StandardCharsets.UTF_8), Object.class,
                        Feature.SupportAutoType);
            }
        };
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        //key采用String序列化
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        //value采用FastJson序列化
        redisTemplate.setValueSerializer(fastJsonRedisSerializer);
        redisTemplate.setHashValueSerializer(fastJsonRedisSerializer);
        redisTemplate.afterPropertiesSet();
        RedisUtils.redisTemplate = redisTemplate;
    }

    /* ==================== 通用操作 ==================== */
    //region

    /**
     * 获取失效时间(秒，-1不过期，-2不存在)
     *
     * @param key 键
     * @return 失效时间(管道或事务中使用为null)
     */
    public static Long getExpire(@NonNull String key) {
        return redisTemplate.getExpire(key, SECONDS);
    }

    /**
     * 指定为持久数据(移除失效时间)
     *
     * @param key 键
     * @return 是否成功(管道或事务中使用为null)
     */
    public static Boolean persist(@NonNull String key) {
        return redisTemplate.persist(key);
    }

    /**
     * 指定失效时间(秒，<=0删除)
     *
     * @param key     键
     * @param timeout 失效时间
     * @return 是否成功(管道或事务中使用为null)
     */
    public static Boolean expire(@NonNull String key, long timeout) {
        return redisTemplate.expire(key, timeout, SECONDS);
    }

    /**
     * 指定失效时间
     *
     * @param key     键
     * @param timeout 持续时间
     * @return 是否成功(管道或事务中使用为null)
     */
    public static Boolean expire(@NonNull String key, @NonNull Duration timeout) {
        return redisTemplate.expire(key, timeout);
    }

    /**
     * 指定失效日期
     *
     * @param key  键
     * @param date 失效日期
     * @return 是否成功(管道或事务中使用为null)
     */
    public static Boolean expireAt(@NonNull String key, @NonNull Date date) {
        return redisTemplate.expireAt(key, date);
    }

    /**
     * 指定失效日期
     *
     * @param key      键
     * @param expireAt 失效日期
     * @return 是否成功(管道或事务中使用为null)
     */
    public static Boolean expireAt(@NonNull String key, @NonNull Instant expireAt) {
        return redisTemplate.expireAt(key, expireAt);
    }

    /**
     * key是否存在
     *
     * @param key 键
     * @return 是否存在
     */
    public static Boolean hasKey(@NonNull String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 集合中存在的key的数量
     *
     * @param keys 键
     * @return 存在的数量(key重复会计算多次)
     */
    public static Long countExistingKeys(Collection<String> keys) {
        return redisTemplate.countExistingKeys(keys);
    }

    /**
     * 删除key
     *
     * @param key 键
     * @return 是否成功(管道或事务中使用为null)
     */
    public static Boolean delete(@NonNull String key) {
        return redisTemplate.delete(key);
    }

    /**
     * 删除多个key
     *
     * @param keys 键
     * @return 成功个数(管道或事务中使用为null)
     */
    public static Long delete(@NonNull Collection<String> keys) {
        return redisTemplate.delete(keys);
    }

    /**
     * 删除多个key
     *
     * @param keys 键
     * @return 成功个数(管道或事务中使用为null)
     */
    public static Long delete(@NonNull String... keys) {
        return redisTemplate.delete(Arrays.asList(keys));
    }

    /**
     * 非阻塞异步删除key
     *
     * @param key 键
     * @return 是否成功(管道或事务中使用为null)
     */
    public static Boolean unlink(@NonNull String key) {
        return redisTemplate.unlink(key);
    }

    /**
     * 非阻塞异步删除多个key
     *
     * @param keys 键
     * @return 成功个数(管道或事务中使用为null)
     */
    public static Long unlink(@NonNull Collection<String> keys) {
        return redisTemplate.unlink(keys);
    }

    /**
     * 非阻塞异步删除多个key
     *
     * @param keys 键
     * @return 成功个数(管道或事务中使用为null)
     */
    public static Long unlink(@NonNull String... keys) {
        return redisTemplate.unlink(Arrays.asList(keys));
    }

    /**
     * 返回存储的数据类型
     *
     * @param key 键
     * @return none/string/list/set/zset/hash/stream(管道或事务中使用为null)
     */
    public static DataType type(@NonNull String key) {
        return redisTemplate.type(key);
    }

    /**
     * 模糊查询
     *
     * @param pattern 匹配模式<br>
     *                * : 匹配0+个任意字符<br>
     *                ? : 匹配1个任意字符<br>
     *                [] : 匹配1个指定字符
     * @return 键(管道或事务中使用为null)
     */
    public static Set<String> keys(@NonNull String pattern) {
        return redisTemplate.keys(pattern);
    }

    /**
     * 返回一个随机的key
     *
     * @return 键(管道或事务中使用或不存在任何键为null)
     */
    public static String randomKey() {
        return redisTemplate.randomKey();
    }

    /**
     * 重命名key(新键已存在会被覆盖)
     *
     * @param oldKey 旧键
     * @param newKey 新键
     * @return 是否成功
     */
    public static boolean rename(@NonNull String oldKey, @NonNull String newKey) {
        try {
            redisTemplate.rename(oldKey, newKey);
            return true;
        } catch (RedisSystemException e) {
            log.error("key不存在，重命名失败！", e);
            return false;
        }
    }

    /**
     * 重命名key，仅当newKey不存在时
     *
     * @param oldKey 旧键
     * @param newKey 新键
     * @return 是否成功(管道或事务中使用为null)
     */
    public static Boolean renameIfAbsent(@NonNull String oldKey, @NonNull String newKey) {
        try {
            return redisTemplate.renameIfAbsent(oldKey, newKey);
        } catch (RedisSystemException e) {
            log.error("key不存在，重命名失败！", e);
            return false;
        }
    }

    /**
     * 将key移动到指定索引的数据库
     *
     * @param key     键
     * @param dbIndex 数据库索引
     * @return 是否成功(管道或事务中使用为null)
     */
    public static Boolean move(@NonNull String key, int dbIndex) {
        return redisTemplate.move(key, dbIndex);
    }

    //endregion

    /* ==================== 字符串操作 ==================== */
    //region

    /**
     * 放入
     *
     * @param key   键
     * @param value 值
     */
    public static void set(@NonNull String key, @NonNull Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 放入，并设置失效时间(秒，必须>0)
     *
     * @param key     键
     * @param value   值
     * @param timeout 失效时间
     */
    public static void set(@NonNull String key, @NonNull Object value, long timeout) {
        redisTemplate.opsForValue().set(key, value, timeout, SECONDS);
    }

    /**
     * 放入，并设置持续时间
     *
     * @param key     键
     * @param value   值
     * @param timeout 持续时间
     */
    public static void set(@NonNull String key, @NonNull Object value, @NonNull Duration timeout) {
        redisTemplate.opsForValue().set(key, value, timeout);
    }

    /**
     * 如果key不存在，则放入
     *
     * @param key   键
     * @param value 值
     * @return 是否成功(管道或事务中使用为null)
     */
    public static Boolean setIfAbsent(@NonNull String key, @NonNull Object value) {
        return redisTemplate.opsForValue().setIfAbsent(key, value);
    }

    /**
     * 如果key不存在，则放入，并设置失效时间(秒，必须>0)
     *
     * @param key     键
     * @param value   值
     * @param timeout 失效时间
     * @return 是否成功(管道或事务中使用为null)
     */
    public static Boolean setIfAbsent(@NonNull String key, @NonNull Object value, long timeout) {
        return redisTemplate.opsForValue().setIfAbsent(key, value, timeout, SECONDS);
    }

    /**
     * 如果key不存在，则放入，并设置持续时间
     *
     * @param key     键
     * @param value   值
     * @param timeout 持续时间
     * @return 是否成功(管道或事务中使用为null)
     */
    public static Boolean setIfAbsent(@NonNull String key, @NonNull Object value, @NonNull Duration timeout) {
        return redisTemplate.opsForValue().setIfAbsent(key, value, timeout);
    }

    /**
     * 如果key存在，则放入
     *
     * @param key   键
     * @param value 值
     * @return 是否成功(管道或事务中使用为null)
     */
    public static Boolean setIfPresent(@NonNull String key, @NonNull Object value) {
        return redisTemplate.opsForValue().setIfPresent(key, value);
    }

    /**
     * 如果key存在，则放入，并设置失效时间(秒，必须>0)
     *
     * @param key     键
     * @param value   值
     * @param timeout 失效时间
     * @return 是否成功(管道或事务中使用为null)
     */
    public static Boolean setIfPresent(@NonNull String key, @NonNull Object value, long timeout) {
        return redisTemplate.opsForValue().setIfPresent(key, value, timeout, SECONDS);
    }

    /**
     * 如果key存在，则放入，并设置持续时间
     *
     * @param key     键
     * @param value   值
     * @param timeout 持续时间
     * @return 是否成功(管道或事务中使用为null)
     */
    public static Boolean setIfPresent(@NonNull String key, @NonNull Object value, @NonNull Duration timeout) {
        return redisTemplate.opsForValue().setIfPresent(key, value, timeout);
    }

    /**
     * map中的key和value依次放入(键存在则不会放入)
     *
     * @param map map
     */
    public static void multiSet(@NonNull Map<String, Object> map) {
        redisTemplate.opsForValue().multiSet(map);
    }

    /**
     * 如果map中的key全部不存在，则map中的key和value依次放入
     *
     * @param map map
     */
    public static Boolean multiSetIfAbsent(@NonNull Map<String, Object> map) {
        return redisTemplate.opsForValue().multiSetIfAbsent(map);
    }

    /**
     * 获取
     *
     * @param key 键
     * @return 值(管道或事务中使用或不存在key时为null)
     */
    public static Object get(@NonNull String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 获取并放入
     *
     * @param key   键
     * @param value 值
     * @return 值(管道或事务中使用为null)
     */
    public static Object getAndSet(@NonNull String key, @NonNull Object value) {
        return redisTemplate.opsForValue().getAndSet(key, value);
    }

    /**
     * 获取多个
     *
     * @param keys 多个键
     * @return 多个值(管道或事务中使用为null)
     */
    public static List<Object> multiGet(@NonNull Collection<String> keys) {
        return redisTemplate.opsForValue().multiGet(keys);
    }

    /**
     * 获取多个
     *
     * @param keys 多个键
     * @return 多个值(管道或事务中使用为null)
     */
    public static List<Object> multiGet(@NonNull String... keys) {
        return redisTemplate.opsForValue().multiGet(Arrays.asList(keys));
    }

    /**
     * 递增1，值必须是整数类型
     *
     * @param key 键
     * @return 递增后的值(管道或事务中使用为null)
     */
    public static Long increment(@NonNull String key) {
        return redisTemplate.opsForValue().increment(key);
    }

    /**
     * 递增，值必须是整数类型
     *
     * @param key   键
     * @param delta 增量
     * @return 递增后的值(管道或事务中使用为null)
     */
    public static Long increment(@NonNull String key, long delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 递减1，值必须是整数类型
     *
     * @param key 键
     * @return 递减后的值(管道或事务中使用为null)
     */
    public static Long decrement(@NonNull String key) {
        return redisTemplate.opsForValue().decrement(key);
    }

    /**
     * 递减，值必须是整数类型
     *
     * @param key   键
     * @param delta 减量
     * @return 递减后的值(管道或事务中使用为null)
     */
    public static Long decrement(@NonNull String key, long delta) {
        return redisTemplate.opsForValue().decrement(key, delta);
    }

    //endregion

    /* ==================== 哈希操作 ==================== */
    //region

    /**
     * 删除map中指定多个项
     *
     * @param key   键
     * @param items 多个项
     * @return 删除成功个数(管道或事务中使用为null)。注意：当key中不存在item时，key将被删除。
     */
    public static Long hDelete(@NonNull String key, @NonNull String... items) {
        return redisTemplate.opsForHash().delete(key, Arrays.copyOf(items, items.length, Object[].class));
    }

    /**
     * 删除map中指定多个项
     *
     * @param key   键
     * @param items 多个项
     * @return 删除成功个数(管道或事务中使用为null)。注意：当key中不存在item时，key将被删除。
     */
    public static Long hDelete(@NonNull String key, @NonNull Collection<String> items) {
        return redisTemplate.opsForHash().delete(key, items.toArray());
    }

    /**
     * 判断map中是否有指定项
     *
     * @param key  键
     * @param item 项
     * @return 是否存在(管道或事务中使用为null)
     */
    public static Boolean hHasKey(@NonNull String key, @NonNull String item) {
        return redisTemplate.opsForHash().hasKey(key, item);
    }

    /**
     * 获取map中指定项的值
     *
     * @param key  键
     * @param item 项
     * @return 值(管道或事务中使用或不存在key或不存在item时为null)
     */
    public static Object hGet(@NonNull String key, @NonNull String item) {
        return redisTemplate.opsForHash().get(key, item);
    }

    /**
     * 获取map中指定多个项的值
     *
     * @param key   键
     * @param items 多个项
     * @return 值
     */
    public static List<Object> hMultiGet(@NonNull String key, @NonNull String... items) {
        return redisTemplate.opsForHash().multiGet(key, Arrays.asList(items));
    }

    /**
     * 获取map中指定多个项的值
     *
     * @param key   键
     * @param items 多个项
     * @return 值
     */
    public static List<Object> hMultiGet(@NonNull String key, @NonNull Collection<String> items) {
        return redisTemplate.opsForHash().multiGet(key, Arrays.asList(items.toArray()));
    }

    /**
     * map指定项的值递增1，值必须是整数类型
     *
     * @param key  键
     * @param item 项
     * @return 递增后的值(管道或事务中使用为null)
     */
    public static Long hIncrement(@NonNull String key, @NonNull String item) {
        return redisTemplate.opsForHash().increment(key, item, 1);
    }

    /**
     * map指定项的值递增，值必须是整数类型
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
     * map指定项的值递减1，值必须是整数类型
     *
     * @param key  键
     * @param item 项
     * @return 递减后的值(管道或事务中使用为null)
     */
    public static Long hDecrement(@NonNull String key, @NonNull String item) {
        return redisTemplate.opsForHash().increment(key, item, -1);
    }

    /**
     * map指定项的值递减，值必须是整数类型
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
     * 获取项的个数
     *
     * @param key 键
     * @return 项的个数
     */
    public static Long hSize(@NonNull String key) {
        return redisTemplate.opsForHash().size(key);
    }

    /**
     * 设置map的多个键值
     *
     * @param key 键
     * @param map 多个键值
     */
    public static void hPutAll(@NonNull String key, @NonNull Map<String, Object> map) {
        redisTemplate.opsForHash().putAll(key, map);
    }

    /**
     * 设置map的1个键值
     *
     * @param key   键
     * @param item  项
     * @param value 值
     */
    public static void hPut(@NonNull String key, @NonNull String item, Object value) {
        redisTemplate.opsForHash().put(key, item, value);
    }

    /**
     * 项不存在时，设置map的1个键值
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @return 是否成功(管道或事务中使用为null)
     */
    public static Boolean hPutIfAbsent(@NonNull String key, @NonNull String item, Object value) {
        return redisTemplate.opsForHash().putIfAbsent(key, item, value);
    }

    /**
     * 获取map中所有的项
     *
     * @param key 键
     * @return 项(管道或事务中使用为null)
     */
    public static Set<Object> hGetAllItem(@NonNull String key) {
        return redisTemplate.opsForHash().keys(key);
    }

    /**
     * 获取map中所有的值
     *
     * @param key 键
     * @return 值(管道或事务中使用为null)
     */
    public static List<Object> hGetAllValue(@NonNull String key) {
        return redisTemplate.opsForHash().values(key);
    }

    /**
     * 获取map中所有的项和值
     *
     * @param key 键
     * @return 项和值(管道或事务中使用为null)
     */
    public static Map<Object, Object> hGetAllItemAndValue(@NonNull String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    //endregion

    /* ==================== 列表操作 ==================== */
    //region

    /**
     * 获取指定下标的值
     *
     * @param key   键
     * @param index 下标
     * @return 值(管道或事务中使用为null)
     */
    public static Object lGet(@NonNull String key, long index) {
        return redisTemplate.opsForList().index(key, index);
    }

    /**
     * 获取第一个
     *
     * @param key 键
     * @return 值(管道或事务中使用为null)
     */
    public static Object lGetFirst(@NonNull String key) {
        return redisTemplate.opsForList().index(key, 0);
    }

    /**
     * 获取最后一个
     *
     * @param key 键
     * @return 值(管道或事务中使用为null)
     */
    public static Object lGetLast(@NonNull String key) {
        return redisTemplate.opsForList().index(key, -1);
    }

    /**
     * 获取
     *
     * @param key   键
     * @param start 开始(开头0)
     * @param end   结束(末尾-1)
     * @return 值(管道或事务中使用为null)
     */
    public static List<Object> lGetList(@NonNull String key, long start, long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    /**
     * 获取全部
     *
     * @param key 键
     * @return 值(管道或事务中使用为null)
     */
    public static List<Object> lGetAll(@NonNull String key) {
        return redisTemplate.opsForList().range(key, 0, -1);
    }

    /**
     * 修剪(只要start和end之间的值)
     *
     * @param key   键
     * @param start 开始(开头0)
     * @param end   结束(末尾-1)
     */
    public static void lTrim(@NonNull String key, long start, long end) {
        redisTemplate.opsForList().trim(key, start, end);
    }

    /**
     * 获取列表的长度
     *
     * @param key 键
     * @return 长度(管道或事务中使用为null)
     */
    public static Long lSize(@NonNull String key) {
        return redisTemplate.opsForList().size(key);
    }

    /**
     * 添加到左侧
     *
     * @param key   键
     * @param value 值
     * @return 下标(管道或事务中使用为null)
     */
    public static Long lLeftPush(@NonNull String key, @NonNull Object value) {
        return redisTemplate.opsForList().leftPush(key, value);
    }

    /**
     * 添加到左侧
     *
     * @param key   键
     * @param value 多个值
     * @return 下标(管道或事务中使用为null)
     */
    public static Long lLeftPushAll(@NonNull String key, @NonNull Object[] value) {
        return redisTemplate.opsForList().leftPushAll(key, value);
    }

    /**
     * 添加到左侧
     *
     * @param key   键
     * @param value 多个值
     * @return 下标(管道或事务中使用为null)
     */
    public static Long lLeftPushAll(@NonNull String key, @NonNull Collection<Object> value) {
        return redisTemplate.opsForList().leftPushAll(key, value);
    }

    /**
     * 当列表存在时，添加到左侧
     *
     * @param key   键
     * @param value 值
     * @return 下标(管道或事务中使用为null)
     */
    public static Long lLeftPushIfPresent(@NonNull String key, @NonNull Object value) {
        return redisTemplate.opsForList().leftPushIfPresent(key, value);
    }

    /**
     * 添加到指定值的左侧
     *
     * @param key   键
     * @param pivot 指定值
     * @param value 值
     * @return 下标(管道或事务中使用为null)
     */
    public static Long lLeftPush(@NonNull String key, @NonNull Object pivot, @NonNull Object value) {
        return redisTemplate.opsForList().leftPush(key, pivot, value);
    }

    /**
     * 添加到右侧
     *
     * @param key   键
     * @param value 值
     * @return 下标(管道或事务中使用为null)
     */
    public static Long lRightPush(@NonNull String key, @NonNull Object value) {
        return redisTemplate.opsForList().rightPush(key, value);
    }

    /**
     * 添加到右侧
     *
     * @param key   键
     * @param value 多个值
     * @return 下标(管道或事务中使用为null)
     */
    public static Long lRightPushAll(@NonNull String key, @NonNull Object[] value) {
        return redisTemplate.opsForList().rightPushAll(key, value);
    }

    /**
     * 添加到右侧
     *
     * @param key   键
     * @param value 多个值
     * @return 下标(管道或事务中使用为null)
     */
    public static Long lRightPushAll(@NonNull String key, @NonNull Collection<Object> value) {
        return redisTemplate.opsForList().rightPushAll(key, value);
    }

    /**
     * 当列表存在时，添加到右侧
     *
     * @param key   键
     * @param value 值
     * @return 下标(管道或事务中使用为null)
     */
    public static Long lRightPushIfPresent(@NonNull String key, @NonNull Object value) {
        return redisTemplate.opsForList().rightPushIfPresent(key, value);
    }

    /**
     * 添加到指定值的右侧
     *
     * @param key   键
     * @param pivot 指定值
     * @param value 值
     * @return 下标(管道或事务中使用为null)
     */
    public static Long lRightPush(@NonNull String key, @NonNull Object pivot, @NonNull Object value) {
        return redisTemplate.opsForList().rightPush(key, pivot, value);
    }

    /**
     * 插入到指定位置
     *
     * @param key   键
     * @param index 下标
     * @param value 值
     */
    public static void lSet(@NonNull String key, long index, @NonNull Object value) {
        redisTemplate.opsForList().set(key, index, value);
    }

    /**
     * 删除第count次出现的值
     *
     * @param key   键
     * @param count 第几次
     * @param value 值
     * @return 下标(管道或事务中使用为null)
     */
    public static Long lRemove(@NonNull String key, long count, @NonNull Object value) {
        return redisTemplate.opsForList().remove(key, count, value);
    }

    /**
     * 删除第一次出现的值
     *
     * @param key   键
     * @param value 值
     * @return 下标(管道或事务中使用为null)
     */
    public static Long lRemoveFirst(@NonNull String key, @NonNull Object value) {
        return redisTemplate.opsForList().remove(key, 1, value);
    }

    /**
     * 删除最后一次出现的值
     *
     * @param key   键
     * @param value 值
     * @return 下标(管道或事务中使用为null)
     */
    public static Long lRemoveLast(@NonNull String key, @NonNull Object value) {
        return redisTemplate.opsForList().remove(key, -1, value);
    }

    /**
     * 指定值第一次出现的下标(Redis版本需要6.0.6及以上)
     *
     * @param key   键
     * @param value 值
     * @return 下标(管道或事务中使用或没有指定值为null)
     */
    public static Long lIndexOf(@NonNull String key, @NonNull Object value) {
        return redisTemplate.opsForList().indexOf(key, value);
    }

    /**
     * 指定值最后一次出现的下标(Redis版本需要6.0.6及以上)
     *
     * @param key   键
     * @param value 值
     * @return 下标(管道或事务中使用或没有指定值为null)
     */
    public static Long lLastIndexOf(@NonNull String key, @NonNull Object value) {
        return redisTemplate.opsForList().lastIndexOf(key, value);
    }

    /**
     * 删除并返回左侧的值
     *
     * @param key 键
     * @return 值(管道或事务中使用为null)
     */
    public static Object lLeftPop(@NonNull String key) {
        return redisTemplate.opsForList().leftPop(key);
    }

    /**
     * 删除并返回左侧的值，并阻塞指定时间(秒)
     *
     * @param key     键
     * @param timeout 阻塞时间
     * @return 值(管道或事务中使用为null)
     */
    public static Object lLeftPop(@NonNull String key, long timeout) {
        return redisTemplate.opsForList().leftPop(key, timeout, SECONDS);
    }

    /**
     * 删除并返回左侧的值，并阻塞指定时间(秒)
     *
     * @param key     键
     * @param timeout 阻塞时间
     * @return 值(管道或事务中使用为null)
     */
    public static Object lLeftPop(@NonNull String key, @NonNull Duration timeout) {
        return redisTemplate.opsForList().leftPop(key, timeout);
    }

    /**
     * 删除并返回右侧的值
     *
     * @param key 键
     * @return 值(管道或事务中使用为null)
     */
    public static Object lRightPop(@NonNull String key) {
        return redisTemplate.opsForList().rightPop(key);
    }

    /**
     * 删除并返回右侧的值，并阻塞指定时间(秒)
     *
     * @param key     键
     * @param timeout 阻塞时间
     * @return 值(管道或事务中使用为null)
     */
    public static Object lRightPop(@NonNull String key, long timeout) {
        return redisTemplate.opsForList().rightPop(key, timeout, SECONDS);
    }

    /**
     * 删除并返回右侧的值，并阻塞指定时间
     *
     * @param key     键
     * @param timeout 阻塞时间
     * @return 值(管道或事务中使用为null)
     */
    public static Object lRightPop(@NonNull String key, @NonNull Duration timeout) {
        return redisTemplate.opsForList().rightPop(key, timeout);
    }

    /**
     * 从sourceKey的右侧删除，添加到destinationKey的左侧，并返回这个值
     *
     * @param sourceKey      源键
     * @param destinationKey 目的键
     * @return 值(管道或事务中使用为null)
     */
    public static Object lRightPopAndLeftPush(@NonNull String sourceKey, @NonNull String destinationKey) {
        return redisTemplate.opsForList().rightPopAndLeftPush(sourceKey, destinationKey);
    }

    /**
     * 从sourceKey的右侧删除，添加到destinationKey的左侧，并返回这个值，并阻塞指定时间(秒)
     *
     * @param sourceKey      源键
     * @param destinationKey 目的键
     * @param timeout        阻塞时间
     * @return 值(管道或事务中使用为null)
     */
    public static Object lRightPopAndLeftPush(@NonNull String sourceKey, @NonNull String destinationKey, long timeout) {
        return redisTemplate.opsForList().rightPopAndLeftPush(sourceKey, destinationKey, timeout, SECONDS);
    }

    /**
     * 从sourceKey的右侧删除，添加到destinationKey的左侧，并返回这个值，并阻塞指定时间
     *
     * @param sourceKey      源键
     * @param destinationKey 目的键
     * @param timeout        阻塞时间
     * @return 值(管道或事务中使用为null)
     */
    public static Object lRightPopAndLeftPush(@NonNull String sourceKey, @NonNull String destinationKey,
                                              @NonNull Duration timeout) {
        return redisTemplate.opsForList().rightPopAndLeftPush(sourceKey, destinationKey, timeout);
    }

    //endregion

}
