package cn.z.tool;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <h1>定时任务线程池</h1>
 *
 * <p>
 * createDate 2023/09/21 15:11:07
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class ScheduledThreadPool {

    private ScheduledThreadPool() {
    }

    /**
     * 执行一次线程
     *
     * @param command 线程
     * @param delay   初始延迟时间(秒)
     */
    public static void once(Runnable command, long delay) {
        SCHEDULED_THREAD_POOL.schedule(command, delay, TimeUnit.SECONDS);
    }

    /**
     * 执行一次线程
     *
     * @param command 线程
     * @param delay   初始延迟时间
     * @param unit    TimeUnit
     */
    public static void once(Runnable command, long delay, TimeUnit unit) {
        SCHEDULED_THREAD_POOL.schedule(command, delay, unit);
    }

    /**
     * 循环执行线程<br>
     * (下一次执行时间=上一次开始执行时间+循环间隔时间)
     *
     * @param command      线程
     * @param initialDelay 初始延迟时间(秒)
     * @param delay        循环间隔时间(秒)
     */
    public static void loop(Runnable command, long initialDelay, long delay) {
        SCHEDULED_THREAD_POOL.scheduleAtFixedRate(command, initialDelay, delay, TimeUnit.SECONDS);
    }

    /**
     * 循环执行线程<br>
     * (下一次执行时间=上一次开始执行时间+循环间隔时间)
     *
     * @param command      线程
     * @param initialDelay 初始延迟时间
     * @param delay        循环间隔时间
     * @param unit         TimeUnit
     */
    public static void loop(Runnable command, long initialDelay, long delay, TimeUnit unit) {
        SCHEDULED_THREAD_POOL.scheduleAtFixedRate(command, initialDelay, delay, unit);
    }

    /**
     * 增强循环执行线程<br>
     * (下一次执行时间=上一次执行完成时间+循环间隔时间)
     *
     * @param command      线程
     * @param initialDelay 初始延迟时间(秒)
     * @param delay        循环间隔时间(秒)
     */
    public static void loopExpand(Runnable command, long initialDelay, long delay) {
        SCHEDULED_THREAD_POOL.scheduleWithFixedDelay(command, initialDelay, delay, TimeUnit.SECONDS);
    }

    /**
     * 增强循环执行线程<br>
     * (下一次执行时间=上一次执行完成时间+循环间隔时间)
     *
     * @param command      线程
     * @param initialDelay 初始延迟时间
     * @param delay        循环间隔时间
     * @param unit         TimeUnit
     */
    public static void loopExpand(Runnable command, long initialDelay, long delay, TimeUnit unit) {
        SCHEDULED_THREAD_POOL.scheduleWithFixedDelay(command, initialDelay, delay, unit);
    }

    /**
     * 自定义线程池
     */
    private static final ScheduledExecutorService SCHEDULED_THREAD_POOL = new ScheduledThreadPoolExecutor( //
            5, // 核心线程数量
            new NameTreadFactory(), // 自定义线程名
            new ThreadPoolExecutor.AbortPolicy() // 超出最大线程数量或阻塞时抛出拒绝执行异常
    );

    /**
     * 自定义线程名
     */
    private static class NameTreadFactory implements ThreadFactory {

        private final AtomicInteger threadNumber = new AtomicInteger(0);

        @Override
        public Thread newThread(Runnable runnable) {
            return new Thread(runnable, "ScheduledThreadPool-" + threadNumber.getAndIncrement());
        }

    }

}
