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
     * 立即执行，10秒循环
     */
    @Task(fixedDelay = 10)
    public void test1() {
        log.info("测试1");
    }

    /**
     * 1秒后执行，10秒增强循环
     */
    @Task(initialDelay = 1, fixedRateDuration = "PT10S")
    public void test2() {
        log.info("测试2");
    }

    /**
     * cron表达式，10秒循环
     */
    @Task("*/10 * * * * *")
    public void test3() {
        log.info("测试3");
    }

    /**
     * 立即执行，10秒循环
     */
    @Scheduled(fixedDelay = 10, timeUnit = TimeUnit.SECONDS)
    public void test4() {
        log.info("测试4");
    }

    /**
     * 1秒后执行，10秒增强循环
     */
    @Scheduled(initialDelay = 1, fixedRateString = "PT10S")
    public void test5() {
        log.info("测试5");
    }

    /**
     * cron表达式，10秒循环
     */
    @Scheduled(cron = "*/10 * * * * *")
    public void test6() {
        log.info("测试6");
    }

}
