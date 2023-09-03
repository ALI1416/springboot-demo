package com.demo.job;

import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * <h1>TestJob</h1>
 *
 * <p>
 * createDate 2023/09/03 14:12:26
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Component
public class TestJob {

    /**
     * 简单任务(Bean模式)
     */
    @XxlJob("demoJobHandler")
    public void demoJobHandler() throws InterruptedException {
        XxlJobHelper.log("XXL-JOB, Hello World.");
        for (int i = 0; i < 5; i++) {
            XxlJobHelper.log("beat at:" + i);
            TimeUnit.SECONDS.sleep(2);
        }
    }

}
