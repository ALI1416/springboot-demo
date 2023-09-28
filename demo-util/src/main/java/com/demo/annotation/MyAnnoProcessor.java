package com.demo.annotation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.*;

/**
 * <h1>MyAnnoProcessor</h1>
 *
 * <p>
 * createDate 2023/09/14 16:54:51
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Configuration
@Slf4j
public class MyAnnoProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> clazz = bean.getClass();
        scanClass(clazz);
        scanField(clazz);
        scanConstructor(clazz);
        scanMethod(clazz);
        return bean;
    }

    /**
     * 扫描类
     */
    private void scanClass(Class<?> clazz) {
        MyAnno myAnno = clazz.getAnnotation(MyAnno.class);
        if (myAnno != null) {
            log.info("类 {} {}", clazz, myAnno);
        }
    }

    /**
     * 扫描字段
     */
    private void scanField(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            MyAnno myAnno = field.getAnnotation(MyAnno.class);
            if (myAnno != null) {
                log.info("字段 {} {}", field, myAnno);
            }
        }
    }

    /**
     * 扫描构造函数
     */
    private void scanConstructor(Class<?> clazz) {
        Constructor<?>[] constructors = clazz.getDeclaredConstructors();
        for (Constructor<?> constructor : constructors) {
            MyAnno myAnno = constructor.getAnnotation(MyAnno.class);
            if (myAnno != null) {
                log.info("构造函数 {} {}", constructor, myAnno);
            }
            scanParameter(constructor);
        }
    }

    /**
     * 扫描方法
     */
    private void scanMethod(Class<?> clazz) {
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            MyAnno myAnno = method.getAnnotation(MyAnno.class);
            if (myAnno != null) {
                log.info("方法 {} {}", method, myAnno);
            }
            scanParameter(method);
        }
    }

    /**
     * 扫描参数
     */
    private void scanParameter(Executable executable) {
        Parameter[] parameters = executable.getParameters();
        for (Parameter parameter : parameters) {
            MyAnno myAnno = parameter.getAnnotation(MyAnno.class);
            if (myAnno != null) {
                log.info("参数 {} {}", parameter, myAnno);
            }
        }
    }

}
