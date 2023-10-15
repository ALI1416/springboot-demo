package com.demo.controller;

import cn.z.tinytoken.T4s;
import cn.z.tinytoken.entity.TokenInfo;
import cn.z.tinytoken.entity.TokenInfoExtra;
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
     * 设置token(token使用Base62编码的雪花ID 过期时间使用默认值)<br>
     * http://localhost:8080/setToken?id=123
     */
    @GetMapping("setToken")
    public String setToken(long id) {
        return t4s.setToken(id);
    }

    /**
     * 获取token(当前Context 不判断是否有效)<br>
     * http://localhost:8080/getToken
     */
    @GetMapping("getToken")
    public String getToken() {
        return t4s.getToken();
    }

    /**
     * 获取token(当前Context 判断是否有效)<br>
     * http://localhost:8080/getTokenValid
     */
    @GetMapping("getTokenValid")
    public String getTokenValid() {
        return t4s.getTokenValid();
    }

    /**
     * 获取id(当前Context)<br>
     * http://localhost:8080/getId
     */
    @GetMapping("getId")
    public Long getId() {
        return t4s.getId();
    }

    /**
     * 删除(当前Context)<br>
     * http://localhost:8080/deleteByToken
     */
    @GetMapping("deleteByToken")
    public Boolean deleteByToken() {
        return t4s.deleteByToken();
    }

    /**
     * 设置过期时间(当前Context)<br>
     * http://localhost:8080/expire?timeout=100
     */
    @GetMapping("expire")
    public Boolean expire(long timeout) {
        return t4s.expire(timeout);
    }

    /**
     * 设置永不过期(当前Context)<br>
     * http://localhost:8080/persist
     */
    @GetMapping("persist")
    public Boolean persist() {
        return t4s.persist();
    }

    /**
     * 获取信息(当前Context)<br>
     * http://localhost:8080/getInfoByToken
     */
    @GetMapping("getInfoByToken")
    public TokenInfo getInfoByToken() {
        return t4s.getInfoByToken();
    }

    /**
     * 获取拓展信息(当前Context)<br>
     * http://localhost:8080/getInfoExtraByToken
     */
    @GetMapping("getInfoExtraByToken")
    public TokenInfoExtra getInfoExtraByToken() {
        return t4s.getInfoExtraByToken();
    }

}
