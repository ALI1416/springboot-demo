package com.demo.util;

/**
 * <h1>Base62工具类</h1>
 *
 * <p>
 * createDate 2020/11/14 21:20:10
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class Base62Utils {

    /**
     * base62字母表
     */
    private static final String BASE62_ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    /**
     * 编码
     *
     * @param n Long
     * @return 编码后的数字
     */
    public static String encoder(long n) {
        // 负数和0都编码为0
        if (n < 1) {
            return "0";
        }
        StringBuilder s = new StringBuilder();
        for (; n > 0; n /= 62) {
            s.append(BASE62_ALPHABET.charAt((int) (n % 62)));
        }
        return s.reverse().toString();
    }

    /**
     * 编码<br>
     * 此方法速度较慢，不推荐使用<br>
     * 请使用{@link #encoder(long)}
     *
     * @param n Long
     * @return 编码后的数字
     */
    @Deprecated
    public static String encoder2(long n) {
        // 负数和0都编码为0
        if (n < 1) {
            return "0";
        }
        StringBuilder s = new StringBuilder();
        // 余数
        long a;
        for (; n > 0; n /= 62) {
            a = (n % 62);
            if (a < 10) {
                // 0-9
                s.append((char) (a + 48));
            } else if (a < 36) {
                // A-Z
                s.append((char) (a + 55));
            } else {
                // a-z
                s.append((char) (a + 61));
            }
        }
        return s.reverse().toString();
    }

    /**
     * 解码
     *
     * @param s Long
     * @return 解码后的数字
     */
    public static long decoder(String s) {
        // 空字符串解码为0
        if (s == null || s.isEmpty()) {
            return 0;
        }
        // 解码后的值
        long n = 0;
        // 62的幂
        long p = 1;
        // 字符
        char c;
        for (int i = s.length() - 1; i >= 0; i--, p *= 62) {
            c = s.charAt(i);
            if (c > 96) {
                // a-z
                n += (c - 61) * p;
            } else if (c > 64) {
                // A-Z
                n += (c - 55) * p;
            } else {
                // 0-9
                n += (c - 48) * p;
            }
        }
        return n;
    }

    /**
     * 解码<br>
     * 此方法速度较慢，不推荐使用<br>
     * 请使用{@link #decoder(String)}
     *
     * @param s Long
     * @return 解码后的数字
     */
    @Deprecated
    public static long decoder2(String s) {
        // 空字符串解码为0
        if (s == null || s.isEmpty()) {
            return 0;
        }
        // 字符串长度
        int l = s.length();
        // 解码后的值
        long n = 0;
        // 62的幂
        long p = 1;
        for (int i = 0; i < l; i++, p *= 62) {
            n += BASE62_ALPHABET.indexOf(s.charAt(l - i - 1)) * p;
        }
        return n;
    }

}
