package com.demo.controller;

import com.demo.entity.po.User;
import com.demo.entity.pojo.Result;
import com.demo.util.RedisUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <h1>首页</h1>
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
     * <h3>获取指定key的value</h3>
     */
    @PostMapping("get")
    public Result stringGet(String key) {
        return Result.o(RedisUtils.get(key));
    }

    /**
     * <h3>放入int</h3>
     * GET http://localhost:8080/intSet?key=int&value=12<br>
     * Redis中的值 12
     */
    @PostMapping("intSet")
    public Result intSet(String key, int value) {
        RedisUtils.set(key, value);
        return Result.o();
    }

    /**
     * <h3>放入short</h3>
     * GET http://localhost:8080/shortSet?key=short&value=123<br>
     * Redis中的值 123S (尾部带大写字母S)
     */
    @PostMapping("shortSet")
    public Result shortSet(String key, short value) {
        RedisUtils.set(key, value);
        return Result.o();
    }

    /**
     * <h3>放入long</h3>
     * GET http://localhost:8080/longSet?key=long&value=1234567890123456789<br>
     * Redis中的值1234567890123456789
     */
    @PostMapping("longSet")
    public Result longSet(String key, long value) {
        RedisUtils.set(key, value);
        return Result.o();
    }

    /**
     * <h3>放入boolean</h3>
     * GET http://localhost:8080/booleanSet?key=boolean&value=true<br>
     * Redis中的值 true
     */
    @PostMapping("booleanSet")
    public Result booleanSet(String key, boolean value) {
        RedisUtils.set(key, value);
        return Result.o();
    }

    /**
     * <h3>放入byte</h3>
     * GET http://localhost:8080/byteSet?key=byte&value=123<br>
     * Redis中的值 123B (尾部带大写字母B)
     */
    @PostMapping("byteSet")
    public Result byteSet(String key, byte value) {
        RedisUtils.set(key, value);
        return Result.o();
    }

    /**
     * <h3>放入char</h3>
     * GET http://localhost:8080/charSet?key=char&value=c<br>
     * Redis中的值 "c"
     */
    @PostMapping("charSet")
    public Result charSet(String key, char value) {
        RedisUtils.set(key, value);
        return Result.o();
    }

    /**
     * <h3>放入float</h3>
     * GET http://localhost:8080/floatSet?key=float&value=123.45<br>
     * Redis中的值 123.45F (尾部带大写字母F)
     */
    @PostMapping("floatSet")
    public Result floatSet(String key, float value) {
        RedisUtils.set(key, value);
        return Result.o();
    }

    /**
     * <h3>放入double</h3>
     * GET http://localhost:8080/doubleSet?key=double&value=123.456789012<br>
     * Redis中的值 123.456789012D (尾部带大写字母D)
     */
    @PostMapping("doubleSet")
    public Result doubleSet(String key, double value) {
        RedisUtils.set(key, value);
        return Result.o();
    }

    /**
     * <h3>放入String</h3>
     * GET http://localhost:8080/stringSet?key=string&value=string<br>
     * Redis中的值 "string"
     */
    @PostMapping("stringSet")
    public Result stringSet(String key, String value) {
        RedisUtils.set(key, value);
        return Result.o();
    }

    /**
     * <h3>放入Integer</h3>
     * GET http://localhost:8080/integerSet?key=integer&value=123<br>
     * Redis中的值 123
     */
    @PostMapping("integerSet")
    public Result integerSet(String key, Integer value) {
        RedisUtils.set(key, value);
        return Result.o();
    }

    /**
     * <h3>放入对象User</h3>
     * GET http://localhost:8080/userSet?key=user<br>
     * body JSON {"account":"aaa","year":1998,"gender":true,"date":"2021-01-02 12:34:56"}<br>
     * Redis中的值 {"@type":"com.demo.entity.po.User","account":"aaa","date":"2021-01-02 12:34:56","gender":"true",
     * "year":"1998"}
     */
    @PostMapping("userSet")
    public Result userSet(String key, @RequestBody User user) {
        RedisUtils.set(key, user);
        return Result.o();
    }

    /**
     * <h3>放入Array Integer</h3>
     * GET http://localhost:8080/arrayIntegerSet?key=arrayIntegerSet&value=123&value=456&value=789<br>
     * Redis中的值 [123,456,789]
     */
    @PostMapping("arrayIntegerSet")
    public Result arrayIntegerSet(String key, Integer[] value) {
        RedisUtils.set(key, value);
        return Result.o();
    }

    /**
     * <h3>放入List Integer</h3>
     * GET http://localhost:8080/listIntegerSet?key=string&value=string<br>
     * body JSON [111,222,333]
     * Redis中的值 [111,222,333]
     */
    @PostMapping("listIntegerSet")
    public Result listIntegerSet(String key, @RequestBody List<Integer> value) {
        RedisUtils.set(key, value);
        return Result.o();
    }

    /**
     * 设置失效时间
     */
    @PostMapping("stringSetExpire")
    public Result stringSetExpire(String key, String value, long time) {
        RedisUtils.set(key, value, time);
        return Result.o();
    }

    @PostMapping("stringIncrement")
    public Result stringIncrement(String key, long delta) {
        return Result.o(RedisUtils.increment(key, delta));
    }

}
