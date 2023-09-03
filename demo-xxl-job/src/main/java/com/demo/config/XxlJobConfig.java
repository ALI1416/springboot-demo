package com.demo.config;

import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import groovy.util.logging.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <h1>XxlJob配置</h1>
 *
 * <p>
 * createDate 2023/09/03 14:12:26
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Configuration
@Slf4j
public class XxlJobConfig {

    @Bean
    public XxlJobSpringExecutor xxlJobExecutor() {
        XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
        xxlJobSpringExecutor.setAdminAddresses("http://127.0.0.1:8080/xxl-job-admin");
        xxlJobSpringExecutor.setAppname("xxl-job-executor-sample");
        xxlJobSpringExecutor.setPort(9999);
        xxlJobSpringExecutor.setAccessToken("default_token");
        xxlJobSpringExecutor.setLogPath("/data/applogs/xxl-job/jobhandler");
        xxlJobSpringExecutor.setLogRetentionDays(30);
        return xxlJobSpringExecutor;
    }

}
