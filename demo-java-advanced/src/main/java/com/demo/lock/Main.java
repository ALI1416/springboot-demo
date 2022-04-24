package com.demo.lock;

import lombok.extern.slf4j.Slf4j;

/**
 * <h1>Main</h1>
 *
 * <p>
 * createDate 2022/04/24 16:51:52
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Slf4j
public class Main {

    public static void main(String[] args) {
        noLock();
        noGetLock();
        simple();
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
     * 没有获得对象的锁
     */
    private static void noGetLock() {
        log.info("---------- 没有获得对象的锁 ----------");
        try {
            // 可能会造成死锁
            Object obj = new Object();
            Object lock = new Object();
            synchronized (lock) {
                obj.wait();
                obj.notifyAll();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 简单示例
     */
    private static void simple() {
        log.info("---------- 简单示例 ----------");
        ThreadA t1 = new ThreadA("t1");
        log.info("获得锁...");
        // 锁定t1(获得t1的监视器)
        synchronized (t1) {
            try {
                log.info("启动线程...");
                t1.start();
                // 主线程等待t1通过notify唤醒
                // 不是使t1线程等待，而是当前执行wait的线程等待
                log.info("释放锁，并进入等待状态...");
                t1.wait();
                log.info("已结束");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

@Slf4j
class ThreadA extends Thread {
    public ThreadA(String name) {
        super(name);
    }

    @Override
    public void run() {
        synchronized (this) {
            log.info("已进入线程");
            try {
                // 使当前线程阻塞1秒，确保主程序的t1.wait()执行之后再执行notify()
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            log.info("唤醒当前的wait线程");
            this.notify();
        }
    }
}
