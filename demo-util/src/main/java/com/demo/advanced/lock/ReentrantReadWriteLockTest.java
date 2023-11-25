package com.demo.advanced.lock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * <h1>ReentrantReadWriteLockTest</h1>
 *
 * <p>
 * createDate 2022/04/27 11:19:34
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Slf4j
public class ReentrantReadWriteLockTest {

    private static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public static void main(String[] args) {
        // readAll();
        // writeAll();
        // readAndWrite();
        // readAndWrite2();
        readAndWrite3();
    }

    /**
     * 全读
     */
    private static void readAll() {
        log.info("---------- 全读 ----------");
        // 互不影响
        new Thread(() -> read()).start();
        new Thread(() -> read()).start();
        new Thread(() -> read()).start();
    }

    /**
     * 全写
     */
    private static void writeAll() {
        log.info("---------- 全写 ----------");
        // 依次进行
        new Thread(() -> write()).start();
        new Thread(() -> write()).start();
        new Thread(() -> write()).start();
    }

    /**
     * 读和写
     */
    private static void readAndWrite() {
        log.info("---------- 读和写 ----------");
        try {
            // 读-写-读
            new Thread(() -> read()).start();
            Thread.sleep(200);
            new Thread(() -> write()).start();
            Thread.sleep(200);
            new Thread(() -> read()).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 读和写2
     */
    private static void readAndWrite2() {
        log.info("---------- 读和写2 ----------");
        try {
            // 读-写
            new Thread(() -> read()).start();
            Thread.sleep(200);
            new Thread(() -> read()).start();
            Thread.sleep(200);
            new Thread(() -> write()).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 读和写3
     */
    private static void readAndWrite3() {
        log.info("---------- 读和写3 ----------");
        try {
            // 写-读
            new Thread(() -> write()).start();
            Thread.sleep(200);
            new Thread(() -> read()).start();
            Thread.sleep(200);
            new Thread(() -> read()).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 读操作
     */
    private static void read() {
        LockCommon.doLock(lock.readLock());
    }

    /**
     * 写操作
     */
    private static void write() {
        LockCommon.doLock(lock.writeLock());
    }

}
