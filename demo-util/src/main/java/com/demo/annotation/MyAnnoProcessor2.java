package com.demo.annotation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.MethodIntrospector;
import org.springframework.core.annotation.AnnotatedElementUtils;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * <h1>MyAnnoProcessor2</h1>
 *
 * <p>
 * createDate 2023/09/28 15:10:36
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Configuration
@Slf4j
public class MyAnnoProcessor2 implements ApplicationContextAware, SmartInitializingSingleton, DisposableBean {

    /**
     * ApplicationContext
     */
    private ApplicationContext applicationContext;

    /**
     * ApplicationContextAware
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * SmartInitializingSingleton
     */
    @Override
    public void afterSingletonsInstantiated() {
        if (applicationContext == null) {
            return;
        }
        // 所有Bean
        String[] beanNamesForTypeArray = applicationContext.getBeanNamesForType(Object.class, false, true);
        for (String beanNamesForType : beanNamesForTypeArray) {
            // 跳过@Lazy注解
            if (applicationContext.findAnnotationOnBean(beanNamesForType, Lazy.class) == null) {
                Object bean = applicationContext.getBean(beanNamesForType);
                // 含有@MyAnno注解的方法
                Map<Method, MyAnno> annotatedMethodMap = MethodIntrospector.selectMethods(
                        bean.getClass(),
                        (MethodIntrospector.MetadataLookup<MyAnno>) method ->
                                AnnotatedElementUtils.findMergedAnnotation(method, MyAnno.class));
                annotatedMethodMap.forEach((method, myAnno) -> log.info("方法 {} ，注解 {}", method, myAnno));
            }
        }
    }

    /**
     * DisposableBean
     */
    @Override
    public void destroy() {
        log.info("destroy");
    }

}
