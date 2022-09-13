package com.demo.poison;

import lombok.extern.java.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * <h1>Boolean反转(测试jdk8有效，jdk17无效)</h1>
 *
 * <p>
 * createDate 2022/09/13 10:42:36
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Log
public class BooleanReverse {

    // 通过反射，修改Boolean的定义(隐蔽位置放入以下代码)
    static {
        try {
            // 1、拿到Boolean类TRUE变量，代码：`public static final Boolean TRUE = new Boolean(true);`
            Field trueField = Boolean.class.getDeclaredField("TRUE");
            // 2、拿到Field类modifiers变量，代码：`private int modifiers;`
            Field modifiersField = Field.class.getDeclaredField("modifiers");
            // 3、修改Field类modifiers变量修饰符private为public
            modifiersField.setAccessible(true);
            // 4、去掉Boolean类TRUE变量的final修饰符
            modifiersField.setInt(trueField, trueField.getModifiers() & ~Modifier.FINAL);
            // 5、修改Boolean类TRUE变量的值为false
            trueField.set(null, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // 当使用Boolean去定义变量时，会自动装箱，调用以下方法：
        // public static Boolean valueOf(boolean b) {
        //     return (b ? TRUE : FALSE);
        // }
        // 这时的b为true，而TRUE实际上是false，因此不满足第一个表达式，最终会返回false
        Boolean reality = true;
        if (reality) {
            log.info("true");
        } else {
            log.info("false");
        }
    }

}
