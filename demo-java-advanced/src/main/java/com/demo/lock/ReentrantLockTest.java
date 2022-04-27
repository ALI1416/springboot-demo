package com.demo.lock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

/**
 * <h1>ReentrantLockTest</h1>
 *
 * <p>
 * createDate 2022/04/26 14:48:30
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Slf4j
public class ReentrantLockTest {

    private static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        // multi();
        // interrupt();
        // interrupt2();
        // tryLock();
        // tryLock2();
        // tryLock3();
        // tryLock4();
        // tryLock5();
        // reentrant();
        otherTest();
    }

    /**
     * 多线程访问
     */
    private static void multi() {
        log.info("---------- 多线程访问 ----------");
        // 依次执行
        new Thread(() -> LockCommon.doLock(lock)).start();
        new Thread(() -> LockCommon.doLock(lock)).start();
        new Thread(() -> LockCommon.doLock(lock)).start();
    }

    /**
     * 中断
     */
    private static void interrupt() {
        log.info("---------- 中断 ----------");
        // 忽视中断，继续获取锁
        LockCommon.interrupt(lock);
    }

    /**
     * 中断2
     */
    private static void interrupt2() {
        log.info("---------- 中断2 ----------");
        // 响应中断，不获取锁
        LockCommon.interrupt2(lock);
    }

    /**
     * 尝试上锁
     */
    private static void tryLock() {
        log.info("---------- 尝试上锁 ----------");
        // 上锁失败返回false
        new Thread(() -> LockCommon.doLock(lock)).start();
        new Thread(() -> LockCommon.doTryLock(lock)).start();
    }

    /**
     * 尝试上锁2
     */
    private static void tryLock2() {
        log.info("---------- 尝试上锁2 ----------");
        // 上锁成功返回true
        LockCommon.doTryLock(lock);
    }

    /**
     * 尝试上锁3
     */
    private static void tryLock3() {
        log.info("---------- 尝试上锁3 ----------");
        new Thread(() -> LockCommon.doLock(lock)).start();
        try {
            Thread.sleep(200);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 1000毫秒内上锁失败返回false
        new Thread(() -> LockCommon.doTryLock2(lock, 1000)).start();
    }

    /**
     * 尝试上锁4
     */
    private static void tryLock4() {
        log.info("---------- 尝试上锁4 ----------");
        new Thread(() -> LockCommon.doLock(lock)).start();
        try {
            Thread.sleep(200);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 3000毫秒内上锁成功返回true
        new Thread(() -> LockCommon.doTryLock2(lock, 3000)).start();
    }

    /**
     * 尝试上锁5
     */
    private static void tryLock5() {
        log.info("---------- 尝试上锁5 ----------");
        // 1000毫秒内上锁成功返回true
        LockCommon.doTryLock2(lock, 1000);
    }

    /**
     * 重入
     */
    private static void reentrant() {
        log.info("---------- 重入 ----------");
        // 可以嵌套上锁
        LockCommon.reentrant(lock);
    }

    /**
     * 其他
     */
    private static void otherTest() {
        new Thread(() -> other()).start();
        new Thread(() -> other()).start();
        new Thread(() -> other()).start();
    }

    /**
     * 其他
     */
    private static void other() {
        try {
            print();
            log.info("lock开始");
            lock.lock();
            log.info("lock结束");
            Thread.sleep(200);
            print();
            log.info("lock开始");
            lock.lock();
            log.info("lock结束");
            Thread.sleep(300);
            print();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            log.info("unlock开始");
            lock.unlock();
            log.info("unlock结束");
            print();
            log.info("unlock开始");
            lock.unlock();
            log.info("unlock结束");
            print();
        }
    }

    /**
     * 打印
     */
    private static void print() {
        log.info("是否已上锁:{},等待线程数量{},当前线程持有锁的数量{},当前线程是否等待获取锁{},当前线程是否持有锁{}", //
                lock.isLocked(), lock.getQueueLength(), lock.getHoldCount(), lock.hasQueuedThreads(),
                lock.isHeldByCurrentThread());
    }

}
