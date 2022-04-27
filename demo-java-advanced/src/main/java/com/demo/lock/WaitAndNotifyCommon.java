package com.demo.lock;

import lombok.extern.slf4j.Slf4j;

/**
 * <h1>WaitAndNotifyCommon</h1>
 *
 * <p>
 * createDate 2022/04/26 10:31:52
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Slf4j
public class WaitAndNotifyCommon {

    /**
     * 调用wait方法
     *
     * @param ms 等待毫秒(0为永久等待)
     */
    public synchronized void goWait(long ms) {
        try {
            log.info("wait开始");
            this.wait(ms);
            log.info("wait结束");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 调用notify方法
     */
    public synchronized void goNotify() {
        log.info("notify开始");
        this.notify();
        log.info("notify结束");
    }

    /**
     * 调用notifyAll方法
     */
    public synchronized void goNotifyAll() {
        log.info("notifyAll开始");
        this.notifyAll();
        log.info("notifyAll结束");
    }

}
