package com.demo.service;

import cn.z.redis.annotation.ModeEnum;
import cn.z.redis.annotation.Subscribe;
import com.demo.entity.po.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <h1>RedisService</h1>
 *
 * <p>
 * createDate 2023/10/07 17:22:53
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Service
@Slf4j
public class RedisService {

    /**
     * 没有参数
     */
    @Subscribe("noParameter")
    public void noParameter() {
        log.info("noParameter");
    }

    /**
     * 1个参数
     */
    @Subscribe("direct")
    public void direct(String msg) {
        log.info(msg);
    }

    /**
     * 2个参数
     */
    @Subscribe(value = "match*", mode = ModeEnum.MATCH)
    public void match(User user, String topic) {
        log.info("user {} topic {}", user, topic);
    }

    /**
     * 过多参数
     */
    // @Subscribe("moreParameter")
    public void moreParameter(String msg, User user, String topic) {
    }

}
