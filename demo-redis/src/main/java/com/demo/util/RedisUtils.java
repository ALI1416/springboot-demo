package com.demo.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.RedisSystemException;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.BoundListOperations;
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

    public static RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public RedisUtils(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        //key采用String序列化
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        //value采用FastJson序列化
        RedisSerializer<Object> fastJsonRedisSerializer = new RedisSerializer<Object>() {
            @Override
            public byte[] serialize(Object o) throws SerializationException {
                if (o == null) {
                    return new byte[0];
                }
                return JSON.toJSONStringWithDateFormat(//
                        o,//
                        "yyyy-MM-dd HH:mm:ss", //日期格式化样式
                        SerializerFeature.DisableCircularReferenceDetect, //禁用对象循环引用：避免$ref
                        SerializerFeature.WriteNonStringValueAsString, //非String转为String：防止long丢失精度
                        SerializerFeature.WriteClassName //写入类名
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
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
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
        return redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
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
        redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
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
        return redisTemplate.opsForValue().setIfAbsent(key, value, timeout, TimeUnit.SECONDS);
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
        return redisTemplate.opsForValue().setIfPresent(key, value, timeout, TimeUnit.SECONDS);
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
     * 递增1，值必须是Long类型
     *
     * @param key 键
     * @return 递增后的值(管道或事务中使用为null)
     */
    public static Long increment(@NonNull String key) {
        return redisTemplate.opsForValue().increment(key);
    }

    /**
     * 递增，值必须是Long类型
     *
     * @param key   键
     * @param delta 增量
     * @return 递增后的值(管道或事务中使用为null)
     */
    public static Long increment(@NonNull String key, long delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 递增，值必须是Double类型
     *
     * @param key   键
     * @param delta 增量
     * @return 递增后的值(管道或事务中使用为null)
     */
    public static Double increment(@NonNull String key, double delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 递减1，值必须是Long类型
     *
     * @param key 键
     * @return 递增后的值(管道或事务中使用为null)
     */
    public static Long decrement(@NonNull String key) {
        return redisTemplate.opsForValue().decrement(key);
    }

    /**
     * 递减，值必须是Long类型
     *
     * @param key   键
     * @param delta 增量
     * @return 递增后的值(管道或事务中使用为null)
     */
    public static Long decrement(@NonNull String key, long delta) {
        return redisTemplate.opsForValue().decrement(key, delta);
    }

    /**
     * 追加字符串
     *
     * @param key   键
     * @param value 追加的字符串
     * @return (管道或事务中使用为null)
     */
    public static Integer append(@NonNull String key, @NonNull String value) {
        return redisTemplate.opsForValue().append(key, value);
    }

    /**
     * 截取字符串
     *
     * @param key   键
     * @param start 起始位置
     * @param end   结束位置
     * @return 截取的字符串
     */
    public static String substring(@NonNull String key, long start, long end) {
        return redisTemplate.opsForValue().get(key, start, end);
    }

    /**
     * 插入
     *
     * @param key    键
     * @param value  值
     * @param offset 插入位置
     */
    public static void insert(@NonNull String key, @NonNull Object value, long offset) {
        redisTemplate.opsForValue().set(key, value, offset);
    }

    /**
     * 获取长度
     *
     * @param key 键
     * @return 长度(管道或事务中使用为null)
     */
    public static Long size(@NonNull String key) {
        return redisTemplate.opsForValue().size(key);
    }

    /**
     * 指定位置设置1个bit
     *
     * @param key    键
     * @param offset 指定位置
     * @param value  true/false
     * @return 是否成功(管道或事务中使用为null)
     */
    public static Boolean setBit(@NonNull String key, long offset, boolean value) {
        return redisTemplate.opsForValue().setBit(key, offset, value);
    }

    /**
     * 指定位置获取1个bit
     *
     * @param key    键
     * @param offset 指定位置
     * @return 值(管道或事务中使用为null)
     */
    public static Boolean getBit(@NonNull String key, long offset) {
        return redisTemplate.opsForValue().getBit(key, offset);
    }

    //endregion

    /* ==================== 哈希操作 ==================== */
    //region

    /**
     * 删除map中指定项的值
     *
     * @param key   键
     * @param items 多个项
     * @return 删除成功个数(管道或事务中使用为null)
     */
    public static Long hDelete(@NonNull String key, @NonNull Object... items) {
        return redisTemplate.opsForHash().delete(key, items);
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
     * @return 值
     */
    public static Object hGet(@NonNull String key, @NonNull String item) {
        return redisTemplate.opsForHash().get(key, item);
    }

    /**
     * 获取map中指定项的值
     *
     * @param key   键
     * @param items 多个项
     * @return 值
     */
    public static List<Object> hMultiGet(@NonNull String key, @NonNull Collection<String> items) {
        return redisTemplate.opsForHash().multiGet(key, Collections.singleton(items));
    }

    /**
     * map项的值递增
     *
     * @param key   键
     * @param item  项
     * @param delta 增量
     */
    public static Long hIncrement(@NonNull String key, @NonNull String item, long delta) {
        return redisTemplate.opsForHash().increment(key, item, delta);
    }

    /**
     * map项的值递增
     *
     * @param key   键
     * @param item  项
     * @param delta 增量
     */
    public static Double hIncrement(@NonNull String key, @NonNull String item, double delta) {
        return redisTemplate.opsForHash().increment(key, item, delta);
    }

    /**
     * 获取map项中值的长度
     *
     * @param key  键
     * @param item 项
     * @return 长度(不存在为0)
     */
    public static Long hLengthOfValue(@NonNull String key, @NonNull String item) {
        return redisTemplate.opsForHash().lengthOfValue(key, item);
    }

    /**
     * 获取大小
     *
     * @param key 键
     * @return 大小
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
     * 获取map中所有的项和值到Set中
     *
     * @param key 键
     * @return 项和值
     */
    public static Set<Object> hGetAll2Set(@NonNull String key) {
        return redisTemplate.opsForHash().keys(key);
    }

    /**
     * 获取map中所有的项和值到List中
     *
     * @param key 键
     * @return 项和值
     */
    public static List<Object> hGetAll2List(@NonNull String key) {
        return redisTemplate.opsForHash().values(key);
    }

    /**
     * 获取map中所有的项和值到Map中
     *
     * @param key 键
     * @return 项和值
     */
    public static Map<Object, Object> hGetAll2Map(@NonNull String key) {
        return redisTemplate.opsForHash().entries(key);
    }
    //endregion


    /* ==================== 集合操作 ==================== */
    //region

    /**
     * 获取
     *
     * @param key 键
     */
    public static Set<Object> setGet(@NonNull String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 根据value从一个set中查询是否存在
     *
     * @param key   键
     * @param value 值
     */
    public static Boolean setHas(@NonNull String key, @NonNull Object value) {
        return redisTemplate.opsForSet().isMember(key, value);
    }

    /**
     * 将数据放入set缓存
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public static Long setSet(@NonNull String key, Object... values) {
        return redisTemplate.opsForSet().add(key, values);
    }

    /**
     * 将set数据放入缓存
     *
     * @param key     键
     * @param timeout 时间(秒)
     * @param values  值 可以是多个
     * @return 成功个数
     */
    public static Long setSet(@NonNull String key, long timeout, Object... values) {
        Long count = redisTemplate.opsForSet().add(key, values);
        if (count != null && count > 0) {
            expire(key, timeout);
        }
        return count;
    }

    /**
     * 获取set缓存的长度
     *
     * @param key 键
     */
    public static Long setSize(@NonNull String key) {
        return redisTemplate.opsForSet().size(key);
    }

    /**
     * 移除值为value的
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 移除的个数
     */
    public static Long setRemove(@NonNull String key, Object... values) {
        return redisTemplate.opsForSet().remove(key, values);
    }
    //endregion

    /* ==================== 列表操作 ==================== */
    //region

    /**
     * 获取list缓存的内容
     *
     * @param key   键
     * @param start 开始
     * @param end   结束 0 到 -1代表所有值
     */
    public static List<Object> listGet(@NonNull String key, long start, long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    /**
     * 获取list缓存的长度
     *
     * @param key 键
     */
    public static Long listSize(@NonNull String key) {
        return redisTemplate.opsForList().size(key);
    }

    /**
     * 通过索引 获取list中的值
     *
     * @param key   键
     * @param index 索引 index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     */
    public static Object listIndex(@NonNull String key, long index) {
        return redisTemplate.opsForList().index(key, index);
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     */
    public static Long listSet(@NonNull String key, Object value) {
        return redisTemplate.opsForList().rightPush(key, value);
    }

    /**
     * 将list放入缓存
     *
     * @param key     键
     * @param value   值
     * @param timeout 时间(秒)
     */
    public static Long listSet(@NonNull String key, Object value, long timeout) {
        Long count = redisTemplate.opsForList().rightPush(key, value);
        if (count != null && count > 0) {
            expire(key, timeout);
        }
        return count;
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     */
    public static Long listSet(@NonNull String key, List<Object> value) {
        return redisTemplate.opsForList().rightPushAll(key, value);
    }

    /**
     * 将list放入缓存
     *
     * @param key     键
     * @param value   值
     * @param timeout 时间(秒)
     */
    public static Long listSet(@NonNull String key, List<Object> value, long timeout) {
        Long count = redisTemplate.opsForList().rightPushAll(key, value);
        if (count != null && count > 0) {
            expire(key, timeout);
        }
        return count;
    }

    /**
     * 根据索引修改list中的某条数据
     *
     * @param key   键
     * @param index 索引
     * @param value 值
     */
    public static void listUpdate(@NonNull String key, long index, Object value) {
        redisTemplate.opsForList().set(key, index, value);
    }

    /**
     * 移除N个值为value
     *
     * @param key   键
     * @param count 移除多少个
     * @param value 值
     */
    public static Long listRemove(@NonNull String key, long count, Object value) {
        return redisTemplate.opsForList().remove(key, count, value);
    }
    //endregion

    // =========BoundListOperations用法============

    /**
     * 将数据添加到Redis的list中（从右边添加）
     *
     * @param listKey key列表
     * @param timeout 过期时间(秒)
     * @param values  待添加的数据
     */
    public static void addToListRight(@NonNull String listKey, long timeout, Object... values) {
        // 绑定操作
        BoundListOperations<String, Object> boundValueOperations = redisTemplate.boundListOps(listKey);
        // 插入数据
        boundValueOperations.rightPushAll(values);
        // 设置过期时间
        boundValueOperations.expire(timeout, TimeUnit.SECONDS);
    }

    /**
     * 根据起始结束序号遍历Redis中的list
     *
     * @param listKey key列表
     * @param start   起始序号
     * @param end     结束序号
     */
    public static List<Object> rangeList(@NonNull String listKey, long start, long end) {
        // 绑定操作--查询数据
        return redisTemplate.boundListOps(listKey).range(start, end);
    }

    /**
     * 弹出右边的值 --- 并且移除这个值
     *
     * @param listKey key列表
     */
    public static Object rightPop(@NonNull String listKey) {
        // 绑定操作
        return redisTemplate.boundListOps(listKey).rightPop();
    }

}
