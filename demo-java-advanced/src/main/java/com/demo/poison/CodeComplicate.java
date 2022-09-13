package com.demo.poison;

import lombok.extern.slf4j.Slf4j;

/**
 * <h1>代码复杂化</h1>
 *
 * <p>
 * createDate 2022/09/13 10:16:32
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Slf4j
public class CodeComplicate {

    /**
     * 判断一个int类型的符号
     *
     * @param x 数值
     */
    public static void judge(int x) {
        if (x == 0) {
            log.info("零");
        } else if (x > 0) {
            log.info("正数");
        } else {
            log.info("负数");
        }
    }

    /**
     * 判断一个int类型的符号，复杂写法
     *
     * @param x 数值
     */
    public static void judge2(int x) {
        if (x == Integer.MAX_VALUE >> 31 >> 1) {
            // `Integer.MAX_VALUE >> 31 >> 1`等同于`0`
            // 将一个数字无符号右移32位后，二进制的所有位上全部是0，所以最终会得到0
            // 不能一次性右移32位，因为在对int型的数字进行移位操作时，会对操作符右边的参数进行模32的取余运算，因此如果直接写32的话，那么相当于什么都不做，得到的还是原数值
            log.info("零");
        } else if (x >>> 31 == 0) {
            // `x >>> 31 == 0`等同于`x > 0`
            // int类型的数字在无符号右移31位后，其实在前面的31位高位全部是0，剩下的最低位是原来的符号位，因此可以用来判断数字的正负(正数的符号位为0，负数的为1)
            log.info("正数");
        } else {
            log.info("负数");
        }
    }


    public static void main(String[] args) {
        judge(0);
        judge(10);
        judge(-10);
        judge2(0);
        judge2(10);
        judge2(-10);
    }

}
