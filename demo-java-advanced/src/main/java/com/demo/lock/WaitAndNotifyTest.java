package com.demo.lock;

import lombok.extern.slf4j.Slf4j;

/**
 * <h1>WaitAndNotifyTest</h1>
 *
 * <p>
 * createDate 2022/04/24 16:51:52
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Slf4j
public class WaitAndNotifyTest {

    public static void main(String[] args) {
        // noLock();
        simple();
        // simple2();
        // simple3();
        // simple4();
    }

    /**
     * 没有锁
     */
    private static void noLock() {
        log.info("---------- 没有锁 ----------");
        try {
            // 在调用wait(),notify()或notifyAll()的时候，必须先获得锁，且状态变量须由该锁保护
            // 而固有锁对象与固有条件队列对象又是同一个对象
            // 也就是说，要在某个对象上执行wait(),notify()或notifyAll()，先必须锁定该对象，而对应的状态变量也是由该对象锁保护的
            Object obj = new Object();
            obj.wait();
            obj.notify();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 简单示例
     */
    public static void simple() {
        log.info("---------- 简单示例 ----------");
        WaitAndNotifyCommon obj = new WaitAndNotifyCommon();
        // 调用wait方法不指定时间，会一直等待下去，直到notify方法调用
        new Thread(() -> obj.goWait(0)).start();
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        obj.goNotify();
        log.info("已结束");
    }

    /**
     * 简单示例2
     */
    public static void simple2() {
        log.info("---------- 简单示例2 ----------");
        WaitAndNotifyCommon obj = new WaitAndNotifyCommon();
        // 调用wait方法指定时间，时间结束后自动取消等待，不用调用notify方法
        new Thread(() -> obj.goWait(2000)).start();
        log.info("已结束");
    }

    /**
     * 简单示例3
     */
    public static void simple3() {
        log.info("---------- 简单示例3 ----------");
        WaitAndNotifyCommon obj = new WaitAndNotifyCommon();
        new Thread(() -> obj.goWait(0)).start();
        new Thread(() -> obj.goWait(0)).start();
        new Thread(() -> obj.goWait(0)).start();
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // notify方法只会随机取消一个等待
        obj.goNotify();
        log.info("已结束");
    }

    /**
     * 简单示例4
     */
    public static void simple4() {
        log.info("---------- 简单示例4 ----------");
        WaitAndNotifyCommon obj = new WaitAndNotifyCommon();
        new Thread(() -> obj.goWait(0)).start();
        new Thread(() -> obj.goWait(0)).start();
        new Thread(() -> obj.goWait(0)).start();
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // notifyAll方法会取消所有等待
        obj.goNotifyAll();
        log.info("已结束");
    }

}