package com.demo.poison;

import lombok.extern.slf4j.Slf4j;

/**
 * <h1>使用Unicode转义注释</h1>
 *
 * <p>
 * createDate 2022/09/13 09:57:21
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Slf4j
public class UnicodeComment {

    public static void main(String[] args) throws Exception {

        // \u000d // 就是一个unicode转义字符，它所表示的是一个换行符
        // 而java中的编译器，不仅会编译代码，还会解析unicode编码将它替换成对应的字符
        /* 1、直接在后面写代码 */
        // \u000d log.info("coder Hydra");

        /* 2、代码也用unicode转义 */
        int a = 1;
        // 以下代码等同于`a++;`
        // \u000d \u0061\u002b\u002b\u003b
        log.info(String.valueOf(a));

        // 以下代码等同于`Thread.sleep(99);`
        //\u000d \u0054\u0068\u0072\u0065\u0061\u0064\u002e\u0073\u006c\u0065\u0065\u0070\u0028\u0039\u0039\u0029\u003b
        log.info("延迟99ms");
    }

}
