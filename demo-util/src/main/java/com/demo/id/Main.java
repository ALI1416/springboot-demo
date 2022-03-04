package com.demo.id;

import cn.z.clock.Clock;
import cn.z.id.Id;
import lombok.extern.slf4j.Slf4j;

/**
 * <h1>雪花ID</h1>
 *
 * <p>
 * createDate 2022/03/04 10:31:06
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Slf4j
public class Main {

    public static void main(String[] args) {
        Id.init(0, 8, 12);
        log.info("Id.next():" + Id.next());
        log.info("Id.next():" + Id.next());
        log.info("Id.next():" + Id.next());
        log.info("Clock.now():" + Clock.now());
        log.info("Clock.date():" + Clock.date());
        log.info("Clock.timestamp():" + Clock.timestamp());
    }

}
