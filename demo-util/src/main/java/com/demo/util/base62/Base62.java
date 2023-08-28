package com.demo.util.base62;

import java.nio.charset.StandardCharsets;

/**
 * <h1>Base62</h1>
 *
 * <p>
 * createDate 2020/11/14 21:20:10
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class Base62 {

    /**
     * GMP风格字母表
     */
    public static final char[] GMP = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();
    /**
     * 反转风格字母表
     */
    public static final char[] INVERTED = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    private Base62() {
    }

    /**
     * 编码数字(GMP风格)
     *
     * @param number 数字
     * @return Base62字符串
     * @throws IllegalArgumentException number<0
     */
    public static String encode(long number) {
        return encode(number, GMP);
    }

    /**
     * 编码数字
     *
     * @param number   数字
     * @param alphabet 字母表 {@link #GMP} {@link #INVERTED}
     * @return Base62字符串
     * @throws IllegalArgumentException number<0
     */
    public static String encode(long number, char[] alphabet) {
        if (number < 1) {
            if (number < 0) {
                throw new IllegalArgumentException("不可为负数！");
            }
            return "0";
        }
        StringBuilder sb = new StringBuilder();
        for (; number > 0; number /= 62) {
            sb.append(alphabet[(int) (number % 62)]);
        }
        return sb.reverse().toString();
    }

    /**
     * 编码(GMP风格)
     *
     * @param string 字符串
     * @return Base62字符串
     * @throws IllegalArgumentException string为null或空字符串
     */
    public static String encode(String string) {
        return encode(string, GMP);
    }

    /**
     * 编码
     *
     * @param string   字符串
     * @param alphabet 字母表 {@link #GMP} {@link #INVERTED}
     * @return Base62字符串
     * @throws IllegalArgumentException string为null或空字符串
     */
    public static String encode(String string, char[] alphabet) {
        if (string == null || string.isEmpty()) {
            throw new IllegalArgumentException("不可为null或空字符串！");
        }
        return encode(string.getBytes(StandardCharsets.UTF_8), alphabet);
    }

    /**
     * 编码(GMP风格)
     *
     * @param bytes 数据
     * @return Base62字符串
     * @throws IllegalArgumentException bytes为null或空数组
     */
    public static String encode(byte[] bytes) {
        return encode(bytes, GMP);
    }

    /**
     * 编码
     *
     * @param bytes    数据
     * @param alphabet 字母表 {@link #GMP} {@link #INVERTED}
     * @return Base62字符串
     * @throws IllegalArgumentException bytes为null或空数组
     */
    public static String encode(byte[] bytes, char[] alphabet) {
        if (bytes == null || bytes.length == 0) {
            throw new IllegalArgumentException("不可为null或空数组！");
        }
        return "";
    }

    /**
     * 解码数字(GMP风格)
     *
     * @param string Base62字符串
     * @return 解码后的数字
     * @throws IllegalArgumentException string为null或空字符串
     */
    public static long decodeNumber(String string) {
        return decodeNumber(string, GMP);
    }

    /**
     * 解码数字
     *
     * @param string   Base62字符串
     * @param alphabet 字母表 {@link #GMP} {@link #INVERTED}
     * @return 解码后的数字
     * @throws IllegalArgumentException string为null或空字符串
     */
    public static long decodeNumber(String string, char[] alphabet) {
        if (string == null || string.isEmpty()) {
            throw new IllegalArgumentException("不可为null或空字符串！");
        }
        char[] chars = string.toCharArray();
        int length = chars.length;
        long number = 0;
        long power = 1;
        if (alphabet == GMP) {
            // GMP
            char c;
            for (int i = length - 1; i >= 0; i--, power *= 62) {
                c = chars[i];
                if (c > 96) {
                    // a-z
                    number += (c - 61) * power;
                } else if (c > 64) {
                    // A-Z
                    number += (c - 55) * power;
                } else {
                    // 0-9
                    number += (c - 48) * power;
                }
            }
        } else if (alphabet == INVERTED) {
            // INVERTED
            char c;
            for (int i = length - 1; i >= 0; i--, power *= 62) {
                c = chars[i];
                if (c > 96) {
                    // a-z
                    number += (c - 87) * power;
                } else if (c > 64) {
                    // A-Z
                    number += (c - 29) * power;
                } else {
                    // 0-9
                    number += (c - 48) * power;
                }
            }
        } else {
            // 自定义
            for (int i = 0; i < length; i++, power *= 62) {
                for (int j = 0; j < alphabet.length; j++) {
                    if (alphabet[j] == chars[length - i - 1]) {
                        number += j * power;
                    }
                }
            }
        }
        return number;
    }

    /**
     * 解码(GMP风格)
     *
     * @param string Base62字符串
     * @return 数据
     * @throws IllegalArgumentException string为null或空字符串
     */
    public static byte[] decode(String string) {
        return decode(string, GMP);
    }

    /**
     * 解码
     *
     * @param string   Base62字符串
     * @param alphabet 字母表 {@link #GMP} {@link #INVERTED}
     * @return 数据
     * @throws IllegalArgumentException string为null或空字符串
     */
    public static byte[] decode(String string, char[] alphabet) {
        if (string == null || string.isEmpty()) {
            throw new IllegalArgumentException("不可为null或空字符串！");
        }
        return null;
    }

    /**
     * 解码为字符串(GMP风格)
     *
     * @param string Base62字符串
     * @return 字符串
     * @throws IllegalArgumentException string为null或空字符串
     */
    public static String decodeToString(String string) {
        return new String(decodeToString(string, GMP));
    }

    /**
     * 解码为字符串
     *
     * @param string   Base62字符串
     * @param alphabet 字母表 {@link #GMP} {@link #INVERTED}
     * @return 字符串
     * @throws IllegalArgumentException string为null或空字符串
     */
    public static String decodeToString(String string, char[] alphabet) {
        if (string == null || string.isEmpty()) {
            throw new IllegalArgumentException("不可为null或空字符串！");
        }
        return new String(decode(string, alphabet));
    }

    /**
     * 是合法的Base62字符串
     *
     * @param string Base62字符串
     * @return 是否合法
     */
    public static boolean isValid(String string) {
        if (string == null || string.isEmpty()) {
            return false;
        }
        for (char c : string.toCharArray()) {
            if (!((c >= '0' && c <= '9') || (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z'))) {
                return false;
            }
        }
        return true;
    }

}
