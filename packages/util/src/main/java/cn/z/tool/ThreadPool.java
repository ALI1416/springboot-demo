package cn.z.tool;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <h1>线程池</h1>
 *
 * <p>
 * createDate 2020/11/12 20:23:18
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class ThreadPool {

    private ThreadPool() {
    }

    /**
     * 执行线程
     *
     * @param command 线程
     */
    public static void execute(Runnable command) {
        THREAD_POOL.execute(command);
    }

    /**
     * 自定义线程池
     */
    private static final ExecutorService THREAD_POOL = new ThreadPoolExecutor( //
            5, // 核心线程数量
            200, // 最大线程数量
            0L, // 保活时间
            TimeUnit.MILLISECONDS, // 保活时间单位
            new LinkedBlockingQueue<>(1024), // 保存任务队列
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
            return new Thread(runnable, "ThreadPool-" + threadNumber.getAndIncrement());
        }

    }

}
