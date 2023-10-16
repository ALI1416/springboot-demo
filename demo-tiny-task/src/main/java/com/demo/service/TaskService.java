package com.demo.service;

import cn.z.tinytask.annotation.Task;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * <h1>TaskService</h1>
 *
 * <p>
 * createDate 2023/10/04 21:45:41
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Service
@Slf4j
public class TaskService {

    /**
     * 每分钟的0秒执行一次
     */
    @Task("0/10 * * * * *")
    public void test() {
        log.info("@Task测试");
    }

    /**
     * 每分钟的0秒执行一次
     */
    @Scheduled(cron = "0/10 * * * * *")
    public void test2() {
        log.info("@Scheduled测试");
    }

    /**
     * 立即执行，10秒循环
     */
    @Scheduled(fixedDelay = 10, timeUnit = TimeUnit.SECONDS)
    public void test3() {
        log.info("fixedDelay测试");
    }

    /**
     * 1秒后执行，10秒增强循环
     */
    @Scheduled(initialDelay = 1, fixedRateString = "PT10S")
    public void test4() {
        log.info("initialDelay/fixedRateString测试");
    }

}
