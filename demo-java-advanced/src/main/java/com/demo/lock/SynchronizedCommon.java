package com.demo.lock;

import lombok.extern.slf4j.Slf4j;

/**
 * <h1>SynchronizedCommon</h1>
 *
 * <p>
 * createDate 2022/04/26 10:31:52
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Slf4j
public class SynchronizedCommon {

    /**
     * 方法锁(锁对象)
     */
    public synchronized void method() {
        log.info("方法锁开始");
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("方法锁结束");
    }

    /**
     * 静态方法锁(锁类)
     */
    public static synchronized void method2() {
        log.info("静态方法锁开始");
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("静态方法锁结束");
    }

    /**
     * 代码块锁this(锁对象)
     */
    public void codeThis() {
        log.info("代码块锁this--前");
        synchronized (this) {
            log.info("代码块锁this开始");
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            log.info("代码块锁this结束");
        }
        log.info("代码块锁this--后");
    }

    /**
     * 代码块锁class(锁类)
     */
    public void codeClass() {
        log.info("代码块锁class--前");
        synchronized (SynchronizedCommon.class) {
            log.info("代码块锁class开始");
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            log.info("代码块锁class结束");
        }
        log.info("代码块锁class--后");
    }

    /**
     * 静态代码块锁class(锁类)
     */
    public static void codeClass2() {
        log.info("静态代码块锁class--前");
        synchronized (SynchronizedCommon.class) {
            log.info("静态代码块锁class开始");
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            log.info("静态代码块锁class结束");
        }
        log.info("静态代码块锁class--后");
    }

    /**
     * 无锁
     */
    public void noLock() {
        log.info("无锁");
    }

    /**
     * 静态无锁
     */
    public static void noLock2() {
        log.info("静态无锁");
    }

    /**
     * 重入锁(递归锁)
     */
    public synchronized void reentrant() {
        log.info("reentrant开始");
        reentrant2();
        log.info("reentrant结束");
    }

    /**
     * 重入锁(递归锁)
     */
    public synchronized void reentrant2() {
        log.info("reentrant2");
    }

}