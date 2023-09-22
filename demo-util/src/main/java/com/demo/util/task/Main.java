package com.demo.util.task;

import cn.z.tool.ScheduledThreadPool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.scheduling.support.SimpleTriggerContext;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * <h1>任务</h1>
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
        // 触发器
        trigger();
        // 定时任务
        scheduled();
    }

    public static void trigger() {
        // log.info("固定时间触发器：20秒");
        // SimpleTriggerContext triggerContext = new SimpleTriggerContext();
        // log.info("当前时间：{}", new Date(triggerContext.getClock().millis()));
        // PeriodicTrigger periodicTrigger = new PeriodicTrigger(20000);
        // log.info("没有执行时间和完成时间");
        // log.info("没有初始延迟时间，下一次触发时间：{}", periodicTrigger.nextExecutionTime(triggerContext));
        // periodicTrigger.setInitialDelay(1000);
        // log.info("有初始延迟时间1秒，下一次触发时间：{}", periodicTrigger.nextExecutionTime(triggerContext));
        // log.info("有执行时间2秒后和完成时间5秒后");
        // long millis = triggerContext.getClock().millis();
        // triggerContext.update(new Date(millis + 2000), new Date(millis + 2000), new Date(millis + 5000));
        // log.info("下一次触发时间：{}", periodicTrigger.nextExecutionTime(triggerContext));
        // log.info("使用固定时间间隔");
        // periodicTrigger.setFixedRate(true);
        // log.info("下一次触发时间：{}", periodicTrigger.nextExecutionTime(triggerContext));

        log.info("cron触发器：每分钟的30秒");
        SimpleTriggerContext triggerContext2 = new SimpleTriggerContext();
        log.info("当前时间：{}", new Date(triggerContext2.getClock().millis()));
        CronTrigger cronTrigger = new CronTrigger("30 * * * * *");
        log.info("没有执行时间和完成时间");
        log.info("下一次触发时间：{}", cronTrigger.nextExecutionTime(triggerContext2));
        log.info("有执行时间30秒后和完成时间35秒后");
        long millis2 = triggerContext2.getClock().millis();
        triggerContext2.update(new Date(millis2 + 30000), new Date(millis2 + 30000), new Date(millis2 + 35000));
        log.info("下一次触发时间：{}", cronTrigger.nextExecutionTime(triggerContext2));
    }

    public static void scheduled() {
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
