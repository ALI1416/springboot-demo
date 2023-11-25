package com.demo.advanced.reflect;

import java.lang.annotation.*;

/**
 * <h1>注解</h1>
 *
 * <h2>@Target</h2>
 * <h3>表明该注解可以应用的Java元素类型</h3>
 * <table>
 * <tr>
 * <th>ElementType
 * <th>解释
 * <tr>
 * <td>TYPE
 * <td>应用于类、接口(包括注解类型)、枚举
 * <tr>
 * <td>FIELD
 * <td>应用于属性(包括枚举中的常量)
 * <tr>
 * <td>METHOD
 * <td>应用于方法
 * <tr>
 * <td>PARAMETER
 * <td>应用于方法的形参
 * <tr>
 * <td>CONSTRUCTOR
 * <td>应用于构造函数
 * <tr>
 * <td>LOCAL_VARIABLE
 * <td>应用于局部变量
 * <tr>
 * <td>ANNOTATION_TYPE
 * <td>应用于注解类型
 * <tr>
 * <td>PACKAGE
 * <td>应用于包
 * <tr>
 * <td>TYPE_PARAMETER
 * <td>应用于类型变量
 * <tr>
 * <td>TYPE_USE
 * <td>应用于任何使用类型的语句中(例如声明语句、泛型和强制转换语句中的类型)
 * </table>
 *
 * <h2>@Retention<h2>
 * <h3>表明该注解的生命周期</h3>
 * <table>
 * <tr>
 * <th>RetentionPolicy
 * <th>解释
 * <tr>
 * <td>SOURCE
 * <td>编译时被丢弃，不包含在类文件中
 * <tr>
 * <td>CLASS
 * <td>JVM加载时被丢弃，包含在类文件中(默认)
 * <tr>
 * <td>RUNTIME
 * <td>由JVM加载，包含在类文件中，在运行时可以被获取到
 * </table>
 *
 * <h2>@Document<h2>
 * <h3>表明该注解标记的元素可以被Javadoc或类似的工具文档化</h3>
 *
 * <h2>@Inherited<h2>
 * <h3>表明使用了@Inherited注解的注解，所标记的类的子类也会拥有这个注解</h3>
 *
 * <p>
 * createDate 2020/12/05 19:20:09
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Target({ElementType.TYPE, ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Anno {

    /**
     * 字符串
     */
    String[] value() default {};

    /**
     * 布尔
     */
    boolean bool() default false;

}
