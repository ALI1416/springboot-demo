package com.demo.tool;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <h1>线程池</h1>
 *
 * <p>
 * 线程池不允许关闭！
 * </p>
 *
 * <p>
 * createDate 2020/11/12 20:23:18
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class ThreadPool {

    /**
     * 自定义线程池
     */
    private final static ExecutorService THREAD_POOL = new ThreadPoolExecutor(5, 200, 0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(1024), new NameTreadFactory(), new ThreadPoolExecutor.AbortPolicy());

    /**
     * 执行线程
     *
     * @param command 线程
     */
    public static void execute(Runnable command) {
        THREAD_POOL.execute(command);
    }

    /**
     * 自定义线程名
     */
    private static class NameTreadFactory implements ThreadFactory {
        // 原子整形，防止多线程异常
        private final AtomicInteger threadNumber = new AtomicInteger(0);

        @Override
        public Thread newThread(Runnable runnable) {
            return new Thread(runnable, "ThreadPool-" + threadNumber.getAndIncrement());
        }
    }
}
