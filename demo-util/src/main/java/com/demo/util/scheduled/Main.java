package com.demo.util.scheduled;

import cn.z.tool.ScheduledThreadPool;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * <h1>定时任务</h1>
 *
 * <p>
 * createDate 2023/09/21 15:11:07
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Slf4j
public class Main {

    public static void main(String[] args) {
        // log.info("2秒后执行一次");
        // ScheduledThreadPool.once(() -> log.info("1执行"), 2, TimeUnit.SECONDS);

        // log.info("0秒后执行，每隔2秒执行一次");
        // ScheduledThreadPool.loop(() -> log.info("2执行"), 0, 2, TimeUnit.SECONDS);

        // log.info("如果抛出异常，下一次则不会执行");
        // ScheduledThreadPool.loop(() -> {
        //     log.info("3执行");
        //     int a = 1 / 0;
        // }, 0, 2, TimeUnit.SECONDS);

        // log.info("捕获异常");
        // ScheduledThreadPool.loop(() -> {
        //     try {
        //         log.info("4执行");
        //         int a = 1 / 0;
        //     } catch (Exception e) {
        //         log.info("异常", e);
        //     }
        // }, 0, 2, TimeUnit.SECONDS);

        // log.info("执行时间超出任务间隔时间，本次任务执行完成后立即执行下一次");
        // ScheduledThreadPool.loopExpand(() -> {
        //     try {
        //         log.info("5执行开始");
        //         Thread.sleep(2000);
        //         log.info("5执行结束");
        //     } catch (Exception e) {
        //         log.info("异常", e);
        //     }
        // }, 0, 1, TimeUnit.SECONDS);

        log.info("本次任务执行完成后，等待任务间隔时间，再执行下一次");
        ScheduledThreadPool.loopExpand(() -> {
            try {
                log.info("6执行开始");
                Thread.sleep(2000);
                log.info("6执行结束");
            } catch (Exception e) {
                log.info("异常", e);
            }
        }, 0, 1, TimeUnit.SECONDS);

    }

}
