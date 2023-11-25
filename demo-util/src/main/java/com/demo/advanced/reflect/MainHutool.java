package com.demo.advanced.reflect;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ReflectUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Set;

/**
 * <h1>MainHutool</h1>
 *
 * <p>
 * createDate 2022/04/22 11:22:58
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Slf4j
public class MainHutool {

    public static final String CLASS_NAME = "com.demo.reflect.User";

    public static void main(String[] args) {
        clazz();
        constructor();
        field();
        method();
        reflect();
    }

    /**
     * 类
     */
    private static void clazz() {
        try {
            log.info("---------- clazz ----------");
            Set<Class<?>> classes = ClassUtil.scanPackage("com.demo");
            log.info("扫描该包路径下所有class文件:" + classes);
            Class<?> clazz = Class.forName(CLASS_NAME);
            log.info("包名:" + ClassUtil.getPackage(clazz));
            log.info("类名:" + ClassUtil.getClassName(clazz, true));
            log.info("包名+类名:" + ClassUtil.getClassName(clazz, false));
            log.info("是公共类:" + ClassUtil.isPublic(clazz));
            log.info("方法列表(Method):" + Arrays.toString(ClassUtil.getDeclaredMethods(clazz)));
            log.info("公共方法列表(Method):" + Arrays.toString(ClassUtil.getPublicMethods(clazz)));
            log.info("字段列表(Field):" + Arrays.toString(ClassUtil.getDeclaredFields(clazz)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 构造函数
     */
    private static void constructor() {
        try {
            log.info("---------- constructor ----------");
            Class<?> clazz = Class.forName(CLASS_NAME);
            log.info("构造函数列表(Constructor):" + Arrays.toString(ReflectUtil.getConstructors(clazz)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 字段
     */
    private static void field() {
        try {
            log.info("---------- field ----------");
            Class<?> clazz = Class.forName(CLASS_NAME);
            log.info("字段列表(Field):" + Arrays.toString(ReflectUtil.getFields(clazz)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 方法
     */
    private static void method() {
        try {
            log.info("---------- method ----------");
            Class<?> clazz = Class.forName(CLASS_NAME);
            log.info("方法列表(Method):" + Arrays.toString(ReflectUtil.getMethods(clazz)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 反射
     */
    private static void reflect() {
        try {
            log.info("---------- reflect ----------");
            Class<?> clazz = Class.forName(CLASS_NAME);
            // 创建无参构造函数
            Object instance = ReflectUtil.newInstance(clazz);
            log.info("无参构造函数:" + instance);
            // 创建含参构造函数并赋值
            Object instance2 = ReflectUtil.newInstance(clazz, 123L, "root", 1);
            log.info("含参构造函数:" + instance2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
