package com.demo.datasource;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * <h1>数据源切换</h1>
 *
 * <p>
 * createDate 2020/11/11 11:11:11
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 */
@Aspect
@Component
public class DataSourceSwitchAspect {

    /**
     * 环绕通知
     */
    @Around("@annotation(targetDataSource)")
    public Object around(ProceedingJoinPoint joinPoint, TargetDataSource targetDataSource) throws Throwable {
        DataSourceContextHolder.setDataSource(targetDataSource.value());
        try {
            return joinPoint.proceed();
        } finally {
            DataSourceContextHolder.clearDataSource();
        }
    }

}
