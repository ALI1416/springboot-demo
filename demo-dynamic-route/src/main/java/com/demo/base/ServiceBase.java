package com.demo.base;

import java.util.function.BooleanSupplier;

/**
 * <h1>Service层基类</h1>
 *
 * <p>
 * createDate 2021/10/27 14:33:27
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class ServiceBase {

    /**
     * 连续执行，当前面返回false时，后面不再执行
     *
     * @param functionArray 函数数组
     * @return 是否全部返回true
     */
    public static boolean execute(BooleanSupplier... functionArray) {
        for (BooleanSupplier function : functionArray) {
            if (!function.getAsBoolean()) {
                return false;
            }
        }
        return true;
    }

}
