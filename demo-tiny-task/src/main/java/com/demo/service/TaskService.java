package com.demo.service;

import cn.z.tinytask.annotation.Task;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
     * 无效
     */
    // @Task
    public void noTask() {
    }

    /**
     * 含参
     */
    // @Task(fixedDelay = 10)
    public void hasParameter(int a) {
    }

    /**
     * initialDelay不合法
     */
    // @Task(initialDelay = -1)
    public void initialDelayError() {
    }

}
