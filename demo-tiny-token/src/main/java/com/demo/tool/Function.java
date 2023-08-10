package com.demo.tool;

/**
 * <h1>自定义函数</h1>
 *
 * <p>
 * createDate 2020/11/28 20:25:11
 * </p>
 *
 * @author ALI[1416978277@qq.com]
 * @since 1.0.0
 **/
@FunctionalInterface
public interface Function<T> {

    /**
     * 执行
     *
     * @return T
     */
    T run();

}
