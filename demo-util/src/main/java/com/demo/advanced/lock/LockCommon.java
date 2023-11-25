package com.demo.advanced.lock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * <h1>LockCommon</h1>
 *
 * <p>
 * createDate 2022/04/26 10:31:52
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Slf4j
public class LockCommon {

    /**
     * 执行上锁和解锁方法
     *
     * @param lock lock
     */
    public static void doLock(Lock lock) {
        try {
            log.info("lock开始");
            lock.lock();
            Thread.sleep(2000);
            log.info("lock结束");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            log.info("unlock开始");
            lock.unlock();
            log.info("unlock结束");
        }
    }

    /**
     * 中断
     *
     * @param lock lock
     */
    public static void interrupt(Lock lock) {
        try {
            log.info("lock开始");
            lock.lock();
            log.info("lock结束");
            Thread.sleep(1000);
            Thread thread = new Thread(() -> {
                try {
                    log.info("线程内lock开始");
                    lock.lock();
                    log.info("线程内lock结束");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    log.info("线程内unlock开始");
                    lock.unlock();
                    log.info("线程内unlock结束");
                }
            });
            thread.start();
            Thread.sleep(1000);
            log.info("线程interrupt");
            thread.interrupt();
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            log.info("unlock开始");
            lock.unlock();
            log.info("unlock结束");
        }
    }

    /**
     * 中断2
     *
     * @param lock lock
     */
    public static void interrupt2(Lock lock) {
        try {
            log.info("lock开始");
            lock.lock();
            log.info("lock结束");
            Thread.sleep(1000);
            Thread thread = new Thread(() -> {
                try {
                    log.info("线程内lock开始");
                    lock.lockInterruptibly();
                    log.info("线程内lock结束");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    log.info("线程内unlock开始");
                    lock.unlock();
                    log.info("线程内unlock结束");
                }
            });
            thread.start();
            Thread.sleep(1000);
            log.info("线程interrupt");
            thread.interrupt();
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            log.info("unlock开始");
            lock.unlock();
            log.info("unlock结束");
        }
    }

    /**
     * 执行尝试上锁方法
     *
     * @param lock lock
     */
    public static void doTryLock(Lock lock) {
        try {
            log.info("tryLock开始");
            boolean tryLock = lock.tryLock();
            log.info("tryLock结束，状态" + tryLock);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            log.info("unlock开始");
            lock.unlock();
            log.info("unlock结束");
        }
    }

    /**
     * 执行尝试上锁方法2
     *
     * @param lock lock
     * @param ms   毫秒
     */
    public static void doTryLock2(Lock lock, int ms) {
        try {
            log.info("tryLock2开始");
            boolean tryLock = lock.tryLock(ms, TimeUnit.MILLISECONDS);
            log.info("tryLock2结束，状态" + tryLock);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            log.info("unlock开始");
            lock.unlock();
            log.info("unlock结束");
        }
    }

    /**
     * 重入
     *
     * @param lock lock
     */
    public static void reentrant(Lock lock) {
        try {
            log.info("lock开始");
            lock.lock();
            reentrant2(lock);
            log.info("lock结束");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            log.info("unlock开始");
            lock.unlock();
            log.info("unlock结束");
        }
    }

    /**
     * 重入2
     *
     * @param lock lock
     */
    public static void reentrant2(Lock lock) {
        try {
            log.info("lock开始2");
            lock.lock();
            log.info("lock结束2");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            log.info("unlock开始2");
            lock.unlock();
            log.info("unlock结束2");
        }
    }

}