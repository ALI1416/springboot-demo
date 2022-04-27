package com.demo.lock;

import lombok.extern.slf4j.Slf4j;

/**
 * <h1>SynchronizedTest</h1>
 *
 * <p>
 * createDate 2022/04/26 10:23:31
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Slf4j
public class SynchronizedTest {

    public static void main(String[] args) {
        // method();
        // method2();
        // method3();
        // code();
        // code2();
        // code3();
        // code4();
        // code5();
        // mix();
        // mix2();
        // diff();
        // diff2();
        // special();
        // special2();
        reentrant();
    }

    /**
     * 方法锁
     */
    private static void method() {
        log.info("---------- 方法锁 ----------");
        SynchronizedCommon obj = new SynchronizedCommon();
        // 相同对象加锁(锁对象)
        // 依次执行
        new Thread(() -> obj.method()).start();
        new Thread(() -> obj.method()).start();
    }

    /**
     * 方法锁2
     */
    private static void method2() {
        log.info("---------- 方法锁2 ----------");
        SynchronizedCommon obj = new SynchronizedCommon();
        SynchronizedCommon obj2 = new SynchronizedCommon();
        // 不同对象加锁(锁对象)
        // 互不影响
        new Thread(() -> obj.method()).start();
        new Thread(() -> obj2.method()).start();
    }

    /**
     * 静态方法锁
     */
    private static void method3() {
        log.info("---------- 静态方法锁 ----------");
        // (锁类)
        // 依次执行
        new Thread(() -> SynchronizedCommon.method2()).start();
        new Thread(() -> SynchronizedCommon.method2()).start();
    }

    /**
     * 代码块锁
     */
    private static void code() {
        log.info("---------- 代码块锁 ----------");
        SynchronizedCommon obj = new SynchronizedCommon();
        // 相同对象使用synchronized (this)加锁(锁对象)
        // synchronized前的代码异步执行，内的代码同步执行，后的代码的内的代码执行完毕后异步执行
        new Thread(() -> obj.codeThis()).start();
        new Thread(() -> obj.codeThis()).start();
    }

    /**
     * 代码块锁2
     */
    private static void code2() {
        log.info("---------- 代码块锁2 ----------");
        SynchronizedCommon obj = new SynchronizedCommon();
        SynchronizedCommon obj2 = new SynchronizedCommon();
        // 不同对象使用synchronized (this)加锁(锁对象)
        // 互不影响
        new Thread(() -> obj.codeThis()).start();
        new Thread(() -> obj2.codeThis()).start();
    }

    /**
     * 代码块锁3
     */
    private static void code3() {
        log.info("---------- 代码块锁3 ----------");
        SynchronizedCommon obj = new SynchronizedCommon();
        // 相同对象使用synchronized (class)加锁(锁类)
        // 效果同code
        new Thread(() -> obj.codeClass()).start();
        new Thread(() -> obj.codeClass()).start();
    }

    /**
     * 代码块锁4
     */
    private static void code4() {
        log.info("---------- 代码块锁4 ----------");
        SynchronizedCommon obj = new SynchronizedCommon();
        SynchronizedCommon obj2 = new SynchronizedCommon();
        // 不同对象使用synchronized (class)加锁(锁类)
        // 效果同code
        new Thread(() -> obj.codeClass()).start();
        new Thread(() -> obj2.codeClass()).start();
    }

    /**
     * 静态代码块锁5
     */
    private static void code5() {
        log.info("---------- 静态代码块锁5 ----------");
        // (锁类)
        // 效果同code
        new Thread(() -> SynchronizedCommon.codeClass2()).start();
        new Thread(() -> SynchronizedCommon.codeClass2()).start();
    }

    /**
     * 无锁和有锁混合
     */
    private static void mix() {
        log.info("---------- 无锁和有锁混合 ----------");
        SynchronizedCommon obj = new SynchronizedCommon();
        // (锁对象)
        // 互不影响
        new Thread(() -> obj.method()).start();
        new Thread(() -> obj.noLock()).start();
    }

    /**
     * 无锁和有锁混合2
     */
    private static void mix2() {
        log.info("---------- 无锁和有锁混合2 ----------");
        // (锁类)
        // 互不影响
        new Thread(() -> SynchronizedCommon.method2()).start();
        new Thread(() -> SynchronizedCommon.noLock2()).start();
    }

    /**
     * 不同锁
     */
    private static void diff() {
        log.info("---------- 不同锁 ----------");
        SynchronizedCommon obj = new SynchronizedCommon();
        // (锁对象)
        // 不同方法不会同时执行synchronized内的代码
        new Thread(() -> obj.method()).start();
        new Thread(() -> obj.codeThis()).start();
    }

    /**
     * 不同锁2
     */
    private static void diff2() {
        log.info("---------- 不同锁2 ----------");
        // (锁类)
        // 不同方法不会同时执行synchronized内的代码
        new Thread(() -> SynchronizedCommon.method2()).start();
        new Thread(() -> SynchronizedCommon.codeClass2()).start();
    }

    /**
     * 特殊
     */
    private static void special() {
        log.info("---------- 特殊 ----------");
        SynchronizedCommon obj = new SynchronizedCommon();
        // (先锁对象后锁类)
        // 互不影响
        new Thread(() -> obj.method()).start();
        new Thread(() -> SynchronizedCommon.method2()).start();
    }

    /**
     * 特殊2
     */
    private static void special2() {
        log.info("---------- 特殊2 ----------");
        SynchronizedCommon obj = new SynchronizedCommon();
        // (先锁类后锁对象)
        // 互不影响
        new Thread(() -> SynchronizedCommon.method2()).start();
        new Thread(() -> obj.method()).start();
    }

    /**
     * 重入(递归)
     */
    private static void reentrant() {
        log.info("---------- 重入(递归) ----------");
        SynchronizedCommon obj = new SynchronizedCommon();
        // 重入锁也叫做递归锁，指的是同一线程的外层函数获得锁之后，内层递归函数仍然有获取该锁的代码，但不受影响
        obj.reentrant();
    }

}
