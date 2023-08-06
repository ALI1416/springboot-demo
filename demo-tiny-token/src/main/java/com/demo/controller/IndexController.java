package com.demo.controller;

import cn.z.tinytoken.T4s;
import com.demo.entity.pojo.Result;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <h1>首页</h1>
 *
 * <p>
 * createDate 2023/08/04 17:51:17
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@RestController
@AllArgsConstructor
public class IndexController {

    private final T4s t4s;

    /**
     * 设置token(token使用UUID 过期时间使用默认值)<br>
     * http://localhost:8080/setToken?id=123
     */
    @GetMapping("setToken")
    public Result setToken(long id) {
        return Result.o(t4s.setToken(id));
    }

    /**
     * 设置token(token使用UUID)<br>
     * http://localhost:8080/setToken2?id=1234&timeout=100
     */
    @GetMapping("setToken2")
    public Result setToken(long id, long timeout) {
        return Result.o(t4s.setToken(id, timeout));
    }

    /**
     * 设置token(过期时间使用默认值)<br>
     * http://localhost:8080/setToken3?id=12345&token=qwe
     */
    @GetMapping("setToken3")
    public Result setToken(long id, String token) {
        t4s.setToken(id, token);
        return Result.o();
    }

    /**
     * 设置token<br>
     * http://localhost:8080/setToken4?id=12346&token=qwe&timeout=100
     */
    @GetMapping("setToken4")
    public Result setToken(long id, String token, long timeout) {
        t4s.setToken(id, token, timeout);
        return Result.o();
    }

    /**
     * 设置token<br>
     * http://localhost:8080/setToken5?id=123467&token=qwe&extra=asd&timeout=100
     */
    @GetMapping("setToken5")
    public Result setToken(long id, String token, String extra, long timeout) {
        t4s.setToken(id, token, extra, timeout);
        return Result.o();
    }

    /**
     * 获取token(当前Context 不判断是否有效)<br>
     * http://localhost:8080/getToken
     */
    @GetMapping("getToken")
    public Result getToken() {
        return Result.o(t4s.getToken());
    }

    /**
     * 获取token(当前Context 判断是否有效)<br>
     * http://localhost:8080/getTokenValid
     */
    @GetMapping("getTokenValid")
    public Result getTokenValid() {
        return Result.o(t4s.getTokenValid());
    }

    /**
     * 获取token列表<br>
     * http://localhost:8080/getToken2?id=123
     */
    @GetMapping("getToken2")
    public Result getToken(long id) {
        return Result.o(t4s.getToken(id));
    }

    /**
     * 获取id(当前Context)<br>
     * http://localhost:8080/getId
     */
    @GetMapping("getId")
    public Result getId() {
        return Result.o(t4s.getId());
    }

    /**
     * 获取id<br>
     * http://localhost:8080/getId2?token=qwe
     */
    @GetMapping("getId2")
    public Result getId(String token) {
        return Result.o(t4s.getId(token));
    }

    /**
     * token是否存在(当前Context)<br>
     * http://localhost:8080/existByToken
     */
    @GetMapping("existByToken")
    public Result existByToken() {
        return Result.o(t4s.existByToken());
    }

    /**
     * token是否存在<br>
     * http://localhost:8080/existByToken2?token=qwe
     */
    @GetMapping("existByToken2")
    public Result existByToken(String token) {
        return Result.o(t4s.existByToken(token));
    }

    /**
     * id是否存在<br>
     * http://localhost:8080/existById?id=123
     */
    @GetMapping("existById")
    public Result existById(long id) {
        return Result.o(t4s.existById(id));
    }

    /**
     * 删除(当前Context)<br>
     * http://localhost:8080/deleteByToken
     */
    @GetMapping("deleteByToken")
    public Result deleteByToken() {
        return Result.o(t4s.deleteByToken());
    }

    /**
     * 删除<br>
     * http://localhost:8080/deleteByToken2?token=qwe
     */
    @GetMapping("deleteByToken2")
    public Result deleteByToken(String token) {
        return Result.o(t4s.deleteByToken(token));
    }

    /**
     * 删除<br>
     * http://localhost:8080/deleteById?id=123
     */
    @GetMapping("deleteById")
    public Result deleteById(long id) {
        return Result.o(t4s.deleteById(id));
    }

    /**
     * 设置过期时间(当前Context)<br>
     * http://localhost:8080/expire?timeout=100
     */
    @GetMapping("expire")
    public Result expire(long timeout) {
        return Result.o(t4s.expire(timeout));
    }

    /**
     * 设置过期时间<br>
     * http://localhost:8080/expire2?token=qwe&timeout=100
     */
    @GetMapping("expire2")
    public Result expire(String token, long timeout) {
        return Result.o(t4s.expire(token, timeout));
    }

    /**
     * 设置永不过期(当前Context)<br>
     * http://localhost:8080/persist
     */
    @GetMapping("persist")
    public Result persist() {
        return Result.o(t4s.persist());
    }

    /**
     * 设置永不过期<br>
     * http://localhost:8080/persist2?token=qwe
     */
    @GetMapping("persist2")
    public Result persist(String token) {
        return Result.o(t4s.persist(token));
    }

    /**
     * 获取信息(当前Context)<br>
     * http://localhost:8080/getInfoByToken
     */
    @GetMapping("getInfoByToken")
    public Result getInfoByToken() {
        return Result.o(t4s.getInfoByToken());
    }

    /**
     * 获取信息<br>
     * http://localhost:8080/getInfoByToken2?token=qwe
     */
    @GetMapping("getInfoByToken2")
    public Result getInfoByToken(String token) {
        return Result.o(t4s.getInfoByToken(token));
    }

    /**
     * 获取信息列表<br>
     * http://localhost:8080/getInfoById?id=123
     */
    @GetMapping("getInfoById")
    public Result getInfoById(long id) {
        return Result.o(t4s.getInfoById(id));
    }

    /**
     * 获取拓展信息(当前Context)<br>
     * http://localhost:8080/getInfoExtraByToken
     */
    @GetMapping("getInfoExtraByToken")
    public Result getInfoExtraByToken() {
        return Result.o(t4s.getInfoExtraByToken());
    }

    /**
     * 获取拓展信息<br>
     * http://localhost:8080/getInfoExtraByToken2?token=qwe
     */
    @GetMapping("getInfoExtraByToken2")
    public Result getInfoExtraByToken(String token) {
        return Result.o(t4s.getInfoExtraByToken(token));
    }

    /**
     * 获取信息拓展列表<br>
     * http://localhost:8080/getInfoExtraById?id=123
     */
    @GetMapping("getInfoExtraById")
    public Result getInfoExtraById(long id) {
        return Result.o(t4s.getInfoExtraById(id));
    }

}
