package com.demo.controller;

import cn.z.redis.RedisTemp;
import com.demo.entity.po.User;
import com.demo.entity.pojo.Result;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.connection.BitFieldSubCommands;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <h1>字符串操作</h1>
 *
 * <p>
 * createDate 2021/09/09 10:35:04
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@RestController
@RequestMapping("string")
@AllArgsConstructor
public class StringController {

    private final RedisTemp redisTemp;

    /* ==================== set各种类型 ==================== */
    // region

    /**
     * <h3>放入null</h3>
     * http://localhost:8080/string/nullSet?key=null<br>
     * Redis中的值 (null)<br>
     * get返回值 null
     */
    @GetMapping("nullSet")
    public Result nullSet(String key) {
        redisTemp.set(key);
        return Result.o();
    }

    /**
     * <h3>放入int</h3>
     * http://localhost:8080/string/intSet?key=int&value=12<br>
     * Redis中的值 12<br>
     * get返回值 12
     */
    @GetMapping("intSet")
    public Result intSet(String key, int value) {
        redisTemp.set(key, value);
        return Result.o();
    }

    /**
     * <h3>放入short</h3>
     * http://localhost:8080/string/shortSet?key=short&value=123<br>
     * Redis中的值 123S (尾部带大写字母S)<br>
     * get返回值 123
     */
    @GetMapping("shortSet")
    public Result shortSet(String key, short value) {
        redisTemp.set(key, value);
        return Result.o();
    }

    /**
     * <h3>放入long</h3>
     * http://localhost:8080/string/longSet?key=long&value=1234567890123456789<br>
     * Redis中的值1234567890123456789<br>
     * get返回值 1234567890123456789
     */
    @GetMapping("longSet")
    public Result longSet(String key, long value) {
        redisTemp.set(key, value);
        return Result.o();
    }

    /**
     * <h3>放入boolean</h3>
     * http://localhost:8080/string/booleanSet?key=boolean&value=true<br>
     * Redis中的值 true<br>
     * get返回值 true
     */
    @GetMapping("booleanSet")
    public Result booleanSet(String key, boolean value) {
        redisTemp.set(key, value);
        return Result.o();
    }

    /**
     * <h3>放入byte</h3>
     * http://localhost:8080/string/byteSet?key=byte&value=123<br>
     * Redis中的值 123B (尾部带大写字母B)<br>
     * get返回值 123
     */
    @GetMapping("byteSet")
    public Result byteSet(String key, byte value) {
        redisTemp.set(key, value);
        return Result.o();
    }

    /**
     * <h3>放入char</h3>
     * http://localhost:8080/string/charSet?key=char&value=c<br>
     * Redis中的值 "c"<br>
     * get返回值 "c"
     */
    @GetMapping("charSet")
    public Result charSet(String key, char value) {
        redisTemp.set(key, value);
        return Result.o();
    }

    /**
     * <h3>放入float</h3>
     * http://localhost:8080/string/floatSet?key=float&value=123.45<br>
     * Redis中的值 123.45F (尾部带大写字母F)<br>
     * get返回值 123.45
     */
    @GetMapping("floatSet")
    public Result floatSet(String key, float value) {
        redisTemp.set(key, value);
        return Result.o();
    }

    /**
     * <h3>放入double</h3>
     * http://localhost:8080/string/doubleSet?key=double&value=123.456789012<br>
     * Redis中的值 123.456789012D (尾部带大写字母D)<br>
     * get返回值 123.456789012
     */
    @GetMapping("doubleSet")
    public Result doubleSet(String key, double value) {
        redisTemp.set(key, value);
        return Result.o();
    }

    /**
     * <h3>放入String</h3>
     * http://localhost:8080/string/stringSet?key=string&value=string<br>
     * Redis中的值 "string"<br>
     * get返回值 "string"
     */
    @GetMapping("stringSet")
    public Result stringSet(String key, String value) {
        redisTemp.set(key, value);
        return Result.o();
    }

    /**
     * <h3>放入Integer</h3>
     * http://localhost:8080/string/integerSet?key=integer&value=123<br>
     * Redis中的值 123<br>
     * get返回值 123
     */
    @GetMapping("integerSet")
    public Result integerSet(String key, Integer value) {
        redisTemp.set(key, value);
        return Result.o();
    }

    /**
     * <h3>放入对象User</h3>
     * POST http://localhost:8080/string/userSet?key=user<br>
     * body JSON {"account":"aaa","year":1998,"gender":true,"date":"2021-01-02 12:34:56"}<br>
     * Redis中的值 {"@type":"com.demo.entity.po.User","account":"aaa","date":"2021-01-02 12:34:56","gender":true,
     * "year":1998}<br>
     * get返回值 {"account":"aaa","date":"2021-01-02 12:34:56","gender":"true","year":"1998"}
     */
    @PostMapping("userSet")
    public Result userSet(String key, @RequestBody User user) {
        redisTemp.set(key, user);
        return Result.o();
    }

    /**
     * <h3>放入Map String Object</h3>
     * POST http://localhost:8080/string/mapStringObjectSet?key=mapStringObjectSet<br>
     * body JSON {"account":"aaa","year":1998,"gender":true,"date":"2021-01-02 12:34:56"}<br>
     * Redis中的值 {"@type":"java.util.HashMap","date":"2021-01-02 12:34:56","gender":true,"year":1998,"account":"aaa"}<br>
     * get返回值 {"account":"aaa","date":"2021-01-02 12:34:56","gender":"true","year":"1998"}
     */
    @PostMapping("mapStringObjectSet")
    public Result mapStringObjectSet(String key, @RequestBody Map<String, Object> value) {
        redisTemp.set(key, value);
        return Result.o();
    }

    /**
     * <h3>放入Array Integer</h3>
     * http://localhost:8080/string/arrayIntegerSet?key=arrayIntegerSet&value=123&value=456&value=789<br>
     * Redis中的值 [123,456,789]<br>
     * get返回值 [123,456,789]
     */
    @GetMapping("arrayIntegerSet")
    public Result arrayIntegerSet(String key, Integer[] value) {
        redisTemp.set(key, value);
        return Result.o();
    }

    /**
     * <h3>放入List Integer</h3>
     * POST http://localhost:8080/string/listIntegerSet?key=listIntegerSet<br>
     * body JSON [111,222,333]<br>
     * Redis中的值 [111,222,333]<br>
     * get返回值 [123,456,789]
     */
    @PostMapping("listIntegerSet")
    public Result listIntegerSet(String key, @RequestBody List<Integer> value) {
        redisTemp.set(key, value);
        return Result.o();
    }

    // endregion

    /**
     * <h3>放入，并设置失效时间</h3>
     * http://localhost:8080/string/setExpire?key=a&value=a&timeout=100</h3>
     * 倒计时100秒
     */
    @GetMapping("setExpire")
    public Result setExpire(String key, String value, long timeout) {
        redisTemp.set(key, value, timeout);
        return Result.o();
    }

    /**
     * <h3>如果key不存在，则放入</h3>
     * http://localhost:8080/string/setIfAbsent?key=a&value=a</h3>
     * 存在 false</h3>
     * 不存在 true
     */
    @GetMapping("setIfAbsent")
    public Result<Boolean> setIfAbsent(String key, String value) {
        return Result.o(redisTemp.setIfAbsent(key, value));
    }

    /**
     * <h3>如果key不存在，则放入，并设置失效时间</h3>
     * http://localhost:8080/string/setIfAbsentExpire?key=a&value=a&timeout=100</h3>
     * 存在 false</h3>
     * 不存在 true
     */
    @GetMapping("setIfAbsentExpire")
    public Result<Boolean> setIfAbsent(String key, String value, long timeout) {
        return Result.o(redisTemp.setIfAbsent(key, value, timeout));
    }

    /**
     * <h3>如果key存在，则放入</h3>
     * http://localhost:8080/string/setIfPresent?key=a&value=a</h3>
     * 存在 true</h3>
     * 不存在 false
     */
    @GetMapping("setIfPresent")
    public Result<Boolean> setIfPresent(String key, String value) {
        return Result.o(redisTemp.setIfPresent(key, value));
    }

    /**
     * <h3>如果key存在，则放入，并设置失效时间</h3>
     * http://localhost:8080/string/setIfPresentExpire?key=a&value=a&timeout=100</h3>
     * 存在 true</h3>
     * 不存在 false
     */
    @GetMapping("setIfPresentExpire")
    public Result<Boolean> setIfPresent(String key, String value, long timeout) {
        return Result.o(redisTemp.setIfPresent(key, value, timeout));
    }

    /**
     * <h3>map中的key和value依次放入(键已存在会被覆盖)</h3>
     * POST http://localhost:8080/string/multiSet<br>
     * body JSON {"a":"a","b":"b","c":"c","d":"d"}<br>
     * 存在键b，acd依次放入，b被修改
     */
    @PostMapping("multiSet")
    public Result multiSet(@RequestBody Map<String, Object> map) {
        redisTemp.setMulti(map);
        return Result.o();
    }

    /**
     * <h3>如果map中的key全部不存在，则map中的key和value依次放入</h3>
     * POST http://localhost:8080/string/multiSetIfAbsent<br>
     * body JSON {"a":"a","b":"b","c":"c","d":"d"}<br>
     * abcd都不存在 true<br>
     * 存在1个以上 false
     */
    @PostMapping("multiSetIfAbsent")
    public Result<Boolean> multiSetIfAbsent(@RequestBody Map<String, Object> map) {
        return Result.o(redisTemp.setMultiIfAbsent(map));
    }

    /**
     * <h3>获取</h3>
     * http://localhost:8080/string/get?key=a<br>
     * 存在a a的值<br>
     * 不存在a null
     */
    @GetMapping("get")
    public Result<Object> get(String key) {
        return Result.o(redisTemp.get(key));
    }

    /**
     * <h3>获取并删除</h3>
     * 存在key a 值 123<br>
     * http://localhost:8080/string/getAndDelete?key=a<br>
     * 返回123 a被删除<br>
     * http://localhost:8080/string/getAndDelete?key=b<br>
     * 返回null
     */
    @GetMapping("getAndDelete")
    public Result<Object> getAndDelete(String key) {
        return Result.o(redisTemp.getAndDelete(key));
    }

    /**
     * <h3>获取并设置超时时间</h3>
     * 存在key a 值 123 ttl为-1<br>
     * http://localhost:8080/string/getAndExpire?key=a&timeout=100<br>
     * 返回123 a ttl 100<br>
     * http://localhost:8080/string/getAndExpire?key=b&timeout=100<br>
     * 返回null 无新增
     */
    @GetMapping("getAndExpire")
    public Result<Object> getAndExpire(String key, long timeout) {
        return Result.o(redisTemp.getAndExpire(key, timeout));
    }

    /**
     * <h3>获取并设置为持久数据</h3>
     * 存在key a 值 123 ttl为100<br>
     * http://localhost:8080/string/getAndPersist?key=a<br>
     * 返回123 a ttl -1<br>
     * http://localhost:8080/string/getAndPersist?key=b<br>
     * 返回null 无新增
     */
    @GetMapping("getAndPersist")
    public Result<Object> getAndPersist(String key) {
        return Result.o(redisTemp.getAndPersist(key));
    }

    /**
     * <h3>获取并放入</h3>
     * http://localhost:8080/string/getAndSet?key=a&value=abc<br>
     * 存在a a的值 值被修改<br>
     * 不存在a null 值被修改
     */
    @GetMapping("getAndSet")
    public Result<Object> getAndSet(String key, String value) {
        return Result.o(redisTemp.getAndSet(key, value));
    }

    /**
     * <h3>获取多个</h3>
     * POST http://localhost:8080/string/multiGetLis<br>
     * body JSON ["a","b","c"]<br>
     * 存在a/b ["aaa","bbb",null]
     */
    @PostMapping("multiGetList")
    public Result<List<Object>> multiGet(@RequestBody List<String> keys) {
        return Result.o(redisTemp.getMulti(keys));
    }

    /**
     * <h3>获取多个</h3>
     * http://localhost:8080/string/multiGetLis?keys=a&keys=b&keys=c<br>
     * 存在a/b ["aaa","bbb",null]
     */
    @GetMapping("multiGetArray")
    public Result<List<Object>> multiGet(String[] keys) {
        return Result.o(redisTemp.getMulti(keys));
    }

    /**
     * <h3>整数型递增1(键不存在自动创建并赋值为0后再递增)</h3>
     * http://localhost:8080/string/increment1?key=a<br>
     * 值为123 返回124，值改变为124<br>
     * 不存在键 返回1，值改变为1
     */
    @GetMapping("increment1")
    public Result<Long> increment(String key) {
        return Result.o(redisTemp.increment(key));
    }

    /**
     * <h3>整数型递增(键不存在自动创建并赋值为0后再递增)</h3>
     * http://localhost:8080/string/increment?key=a&delta=2<br>
     * 值为123 返回125，值改变为125<br>
     * 不存在键 返回2，值改变为2
     */
    @GetMapping("increment")
    public Result<Long> increment(String key, long delta) {
        return Result.o(redisTemp.increment(key, delta));
    }

    /**
     * <h3>数字型递增(键不存在自动创建并赋值为0后再递增)</h3>
     * http://localhost:8080/string/incrementD?key=a&delta=1.2<br>
     * 值为1 返回2.2，值改变为2.2<br>
     * 不存在键 返回1.2，值改变为1.2
     */
    @GetMapping("incrementD")
    public Result<Double> incrementD(String key, double delta) {
        return Result.o(redisTemp.increment(key, delta));
    }

    /**
     * <h3>整数型递减1(键不存在自动创建并赋值为0后再递减)</h3>
     * http://localhost:8080/string/decrement1?key=a<br>
     * 值为123 返回122，值改变为122<br>
     * 不存在键 返回-1，值改变为-1
     */
    @GetMapping("decrement1")
    public Result<Long> decrement(String key) {
        return Result.o(redisTemp.decrement(key));
    }

    /**
     * <h3>整数型递减(键不存在自动创建并赋值为0后再递减)</h3>
     * http://localhost:8080/string/decrement?key=a&delta=2<br>
     * 值为123 返回121，值改变为121<br>
     * 不存在键 返回-2，值改变为-2
     */
    @GetMapping("decrement")
    public Result<Long> decrement(String key, long delta) {
        return Result.o(redisTemp.decrement(key, delta));
    }

    /**
     * <h3>整数型递减(键不存在自动创建并赋值为0后再递减)</h3>
     * http://localhost:8080/string/decrementD?key=a&delta=1.2<br>
     * 值为2 返回0.8，值改变为0.8<br>
     * 不存在键 返回-1.2，值改变为-1.2
     */
    @GetMapping("decrementD")
    public Result<Double> decrementD(String key, double delta) {
        return Result.o(redisTemp.decrement(key, delta));
    }

    /**
     * <h3>追加字符串</h3>
     * http://localhost:8080/string/append?key=a&value=2<br>
     * 值为123 返回5，值改变为12345<br>
     * 不存在键 返回2，值改变为45
     */
    @GetMapping("append")
    public Result<Integer> append(String key, String value) {
        return Result.o(redisTemp.append(key, value));
    }

    /**
     * <h3>获取字符串</h3>
     * http://localhost:8080/string/get2?key=a&start=1&end=5<br>
     * 值为12345 返回2345<br>
     * 不存在键 返回空字符串
     */
    @GetMapping("get2")
    public Result<String> get2(String key, long start, long end) {
        return Result.o(redisTemp.get(key, start, end));
    }

    /**
     * <h3>设置字符串</h3>
     * http://localhost:8080/string/setValue?key=a&offset=1&value=678<br>
     * 值为12345 值改变为16785<br>
     * 不存在键 值改变为(空格)678
     */
    @GetMapping("setValue")
    public Result setValue(String key, Integer value, long offset) {
        redisTemp.setValue(key, value, offset);
        return Result.o();
    }

    /**
     * <h3>获取字符串长度</h3>
     * http://localhost:8080/string/size?key=a<br>
     * 值为12345 返回5<br>
     * 不存在键 返回0
     */
    @GetMapping("size")
    public Result<Long> size(String key) {
        return Result.o(redisTemp.size(key));
    }

    /**
     * <h3>设置bit</h3>
     * http://localhost:8080/string/setBit?key=a&offset=6&value=true<br>
     * 值为1(0b00110001) 返回false 值改变为3(0b00110011)<br>
     * 不存在键 返回false 值改变为(0b00000010)
     */
    @GetMapping("setBit")
    public Result<Boolean> setBit(String key, long offset, boolean value) {
        return Result.o(redisTemp.setBit(key, offset, value));
    }

    /**
     * <h3>获取bit</h3>
     * http://localhost:8080/string/getBit?key=a&offset=7<br>
     * 值为1(0b00110001) 返回true<br>
     * 不存在键 返回false
     */
    @GetMapping("getBit")
    public Result<Boolean> getBit(String key, long offset) {
        return Result.o(redisTemp.getBit(key, offset));
    }

    /**
     * <h3>bitField</h3>
     * http://localhost:8080/string/bitField?key=a&offset=0<br>
     * 值为12345 返回[49]<br>
     * 不存在键 返回[0]
     */
    @GetMapping("bitField")
    public Result<List<Long>> bitField(String key, long offset) {
        BitFieldSubCommands.BitFieldGet bitFieldSubCommand = BitFieldSubCommands.BitFieldGet.create(BitFieldSubCommands.BitFieldType.INT_8, BitFieldSubCommands.Offset.offset(offset));
        BitFieldSubCommands subCommands = BitFieldSubCommands.create(bitFieldSubCommand);
        return Result.o(redisTemp.bitField(key, subCommands));
    }

}
