package com.demo.util.hutool.thread;

import cn.hutool.core.thread.ConcurrencyTester;
import cn.hutool.core.thread.ExecutorBuilder;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;

/**
 * <h1>线程</h1>
 *
 * <p>
 * createDate 2022/03/10 11:14:58
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Slf4j
public class Main {

    public static void main(String[] args) {
        threadUtil();
        executorBuilder();
        concurrencyTester();
    }

    /**
     * 线程工具
     */
    private static void threadUtil() {
        ThreadUtil.execute(() -> {
            log.info("直接在公共线程池中执行线程");
        });
    }

    /**
     * 自定义线程池
     */
    private static void executorBuilder() {
        ExecutorService executor = ExecutorBuilder.create()//
                .setCorePoolSize(1)//
                .setMaxPoolSize(1)//
                .setKeepAliveTime(0)//
                .build();
        executor.execute(() -> {
            log.info("自定义线程池中执行线程");
        });
    }

    /**
     * 高并发测试
     */
    private static void concurrencyTester() {
        ConcurrencyTester tester = ThreadUtil.concurrencyTest(100, () -> {
            long delay = RandomUtil.randomLong(100, 1000);
            ThreadUtil.sleep(delay);
            log.info("{} test finished, delay: {}", Thread.currentThread().getName(), delay);
        });
        log.info("获取总的执行时间，单位毫秒:" + tester.getInterval());
    }

}
