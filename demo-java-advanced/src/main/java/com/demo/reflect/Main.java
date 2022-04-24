package com.demo.reflect;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.*;
import java.util.Arrays;

/**
 * <h1>Main</h1>
 *
 * <p>
 * createDate 2022/04/20 14:53:01
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Slf4j
public class Main {

    public static final String CLASS_NAME = "com.demo.reflect.User";

    public static void main(String[] args) {
        classLoader();
        myClassLoader();
        clazz();
        package1();
        constructor();
        field();
        method();
        parameter();
        reflect();
        annotation();
        primitive();
        array();
    }

    /**
     * 类加载器
     */
    private static void classLoader() {
        try {
            log.info("---------- classLoader ----------");
            ClassLoader classLoader = new ClassLoader() {
            };
            Class<?> clazz = classLoader.loadClass("com.demo.reflect.HelloWorld");
            Object obj = clazz.getConstructor().newInstance();
            Method method = clazz.getMethod("main", String[].class);
            method.invoke(obj, (Object) null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 自定义类加载器
     */
    private static void myClassLoader() {
        try {
            log.info("---------- myClassLoader ----------");
            MyClassLoader myClassLoader = new MyClassLoader();
            Class<?> clazz = myClassLoader.findClass("demo-java-advanced\\src\\main\\resources\\Hello.class");
            Object obj = clazz.getConstructor().newInstance();
            Method method = clazz.getMethod("main", String[].class);
            method.invoke(obj, (Object) null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 类
     */
    private static void clazz() {
        try {
            log.info("---------- clazz ----------");
            Class<?> clazz = Class.forName(CLASS_NAME);
            log.info("包名(Package):" + clazz.getPackage());
            log.info("类名:" + clazz.getSimpleName());
            log.info("包名+类名:" + clazz.getName());
            log.info("规范名:" + clazz.getCanonicalName());
            log.info("类型名:" + clazz.getTypeName());
            log.info("类型(是接口类):" + clazz.isInterface());
            log.info("类型(是基本类型):" + clazz.isPrimitive());
            log.info("类型(是注解类):" + clazz.isAnnotation());
            log.info("类型(是数组类):" + clazz.isArray());
            log.info("类型(是枚举类):" + clazz.isEnum());
            log.info("类型(是匿名类):" + clazz.isAnonymousClass());
            log.info("类型(是内部类):" + clazz.isLocalClass());
            log.info("类型(是成员类):" + clazz.isMemberClass());
            log.info("类型(是合成类):" + clazz.isSynthetic());
            log.info("类型+路径+类名:" + clazz);
            log.info("修饰符(Modifier):" + Modifier.toString(clazz.getModifiers()));
            log.info("修饰符+类型+路径+类名:" + clazz.toGenericString());
            log.info("父类(Class):" + clazz.getSuperclass());
            log.info("内部类列表(Class):" + Arrays.toString(clazz.getDeclaredClasses()));
            log.info("公共内部类列表(Class):" + Arrays.toString(clazz.getClasses()));
            log.info("构造函数列表(Constructor):" + Arrays.toString(clazz.getDeclaredConstructors()));
            log.info("公共构造函数列表(Constructor):" + Arrays.toString(clazz.getConstructors()));
            log.info("方法列表(Method):" + Arrays.toString(clazz.getDeclaredMethods()));
            log.info("公共方法列表(Method):" + Arrays.toString(clazz.getMethods()));
            log.info("字段列表(Field):" + Arrays.toString(clazz.getDeclaredFields()));
            log.info("公共字段列表(Field):" + Arrays.toString(clazz.getFields()));
            log.info("注解列表(Annotation):" + Arrays.toString(clazz.getDeclaredAnnotations()));
            log.info("公共注解列表(Annotation):" + Arrays.toString(clazz.getAnnotations()));
            log.info("注解的接口列表(AnnotatedType):" + Arrays.toString(clazz.getAnnotatedInterfaces()));
            log.info("注解的父类(AnnotatedType):" + clazz.getAnnotatedSuperclass());
            log.info("泛型参数列表(TypeVariable):" + Arrays.toString(clazz.getTypeParameters()));
            log.info("是否为User实例:" + clazz.isInstance(new User()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 包
     */
    private static void package1() {
        try {
            log.info("---------- package1 ----------");
            Class<?> clazz = Class.forName(CLASS_NAME);
            Package package1 = clazz.getPackage();
            log.info("包名:" + package1.getName());
            log.info("package+包名:" + package1);
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
            // 创建含参构造函数
            Constructor<?> constructor = clazz.getConstructor(Long.class, String.class, Integer.class);
            log.info("方法名:" + constructor.getName());
            log.info("修饰符+方法名+参数:" + constructor);
            log.info("通用字符串:" + constructor.toGenericString());
            log.info("注解列表(Annotation):" + Arrays.toString(constructor.getDeclaredAnnotations()));
            log.info("公共注解列表(Annotation):" + Arrays.toString(constructor.getAnnotations()));
            log.info("参数个数:" + constructor.getParameterCount());
            log.info("是不定长参数:" + constructor.isVarArgs());
            log.info("参数列表(Parameter):" + Arrays.toString(constructor.getParameters()));
            log.info("参数的类型列表(Class):" + Arrays.toString(constructor.getParameterTypes()));
            log.info("参数的注解列表(Annotation):" + Arrays.deepToString(constructor.getParameterAnnotations()));
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
            Field field = clazz.getDeclaredField("id");
            log.info("字段名:" + field.getName());
            log.info("修饰符+通用字符串:" + field);
            log.info("通用字符串:" + field.toGenericString());
            log.info("类型(Class):" + field.getType());
            log.info("类型(Type):" + field.getGenericType());
            log.info("是枚举常量:" + field.isEnumConstant());
            log.info("注解列表(Annotation):" + Arrays.toString(field.getDeclaredAnnotations()));
            log.info("公共注解列表(Annotation):" + Arrays.toString(field.getAnnotations()));
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
            Method method = clazz.getDeclaredMethod("setId", Long.class);
            log.info("方法名:" + method.getName());
            log.info("修饰符+方法名+参数:" + method);
            log.info("通用字符串:" + method.toGenericString());
            log.info("返回类型(Class):" + method.getReturnType());
            log.info("注解列表(Annotation):" + Arrays.toString(method.getDeclaredAnnotations()));
            log.info("公共注解列表(Annotation):" + Arrays.toString(method.getAnnotations()));
            log.info("参数个数:" + method.getParameterCount());
            log.info("是不定长参数:" + method.isVarArgs());
            log.info("参数列表(Parameter):" + Arrays.toString(method.getParameters()));
            log.info("参数的类型列表(Class):" + Arrays.toString(method.getParameterTypes()));
            log.info("参数的注解列表(Annotation):" + Arrays.deepToString(method.getParameterAnnotations()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 参数
     */
    private static void parameter() {
        try {
            log.info("---------- parameter ----------");
            Class<?> clazz = Class.forName(CLASS_NAME);
            Method method = clazz.getDeclaredMethod("setId", Long.class);
            Parameter parameter = method.getParameters()[0];
            log.info("参数名:" + parameter.getName());
            log.info("类名+参数名:" + parameter);
            log.info("类型+类名:" + parameter.getType());
            log.info("是不定长参数:" + parameter.isVarArgs());
            log.info("修饰符(Modifier):" + Modifier.toString(parameter.getModifiers()));
            log.info("注解列表(Annotation):" + Arrays.toString(parameter.getDeclaredAnnotations()));
            log.info("公共注解列表(Annotation):" + Arrays.toString(parameter.getAnnotations()));
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
            Object instance = clazz.getConstructor().newInstance();
            log.info("无参构造函数:" + instance);
            // 创建含参构造函数并赋值
            Object instance2 = clazz.getConstructor(Long.class, String.class, Integer.class) //
                    .newInstance(123L, "root", 1);
            log.info("含参构造函数:" + instance2);
            // 获取方法
            Method methodSetGender = clazz.getDeclaredMethod("setGender", Integer.class);
            // 调用方法
            methodSetGender.invoke(instance2, 3);
            // 调用方法并获取返回值
            log.info("gender:" + clazz.getDeclaredMethod("getGender").invoke(instance2).toString());
            // 获取字段
            Field fieldAccount = clazz.getDeclaredField("account");
            // 设置字段可访问
            fieldAccount.setAccessible(true);
            // 设置字段值
            fieldAccount.set(instance2, "admin");
            // 获取字段值
            log.info("account:" + fieldAccount.get(instance2));
            // 设置字段不可访问
            fieldAccount.setAccessible(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 注解
     */
    private static void annotation() {
        try {
            log.info("---------- annotation ----------");
            Class<?> clazz = Class.forName(CLASS_NAME);
            log.info("类的注解:" + clazz.getAnnotation(Anno.class));
            Constructor<?> constructor = clazz.getConstructor();
            log.info("构造函数的注解:" + constructor.getAnnotation(Anno.class));
            Field field = clazz.getDeclaredField("id");
            log.info("字段的注解:" + field.getAnnotation(Anno.class));
            Method method = clazz.getDeclaredMethod("setId", Long.class);
            log.info("方法的注解:" + method.getAnnotation(Anno.class));
            Parameter parameter = method.getParameters()[0];
            log.info("参数的注解:" + parameter.getAnnotation(Anno.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 基本类型
     */
    private static void primitive() {
        try {
            log.info("---------- primitive ----------");
            log.info("void:" + void.class);
            log.info("boolean:" + boolean.class);
            log.info("byte:" + byte.class);
            log.info("char:" + char.class);
            log.info("int:" + int.class);
            log.info("short:" + short.class);
            log.info("long:" + long.class);
            log.info("float:" + float.class);
            log.info("double:" + double.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 数组
     */
    private static void array() {
        try {
            log.info("---------- array ----------");
            log.info("boolean[]:" + Class.forName("[Z"));
            log.info("byte[][]:" + Class.forName("[[B"));
            log.info("char[][][]:" + Class.forName("[[[C"));
            log.info("int[][][][]:" + Class.forName("[[[[I"));
            log.info("short[]:" + short[].class);
            log.info("long[][]:" + long[][].class);
            log.info("float[][][]:" + float[][][].class);
            log.info("double[][][][]:" + double[][][][].class);
            log.info("User[]:" + Class.forName("[Lcom.demo.reflect.User;"));
            log.info("Anno[][]:" + Anno[][].class);
            // 创建数组实例
            Object instance = Array.newInstance(int.class, 3);
            Array.set(instance, 0, 123);
            Array.setInt(instance, 2, 234);
            log.info("[0]:" + Array.get(instance, 0));
            log.info("[1]:" + Array.getInt(instance, 1));
            log.info("[2]:" + Array.getInt(instance, 2));
            log.info("length:" + Array.getLength(instance));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
