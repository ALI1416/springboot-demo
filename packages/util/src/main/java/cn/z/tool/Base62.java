package cn.z.tool;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

/**
 * <h1>Base62</h1>
 *
 * <p>
 * 根据<a href="https://github.com/seruco/base62/blob/0.1.3/src/main/java/io/seruco/encoding/base62/Base62.java">seruco/base62</a>重构
 * </p>
 *
 * <p>
 * createDate 2020/11/14 21:20:10
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class Base62 {

    /* ==================== 常量 ==================== */
    // region 常量

    /**
     * GMP风格字母表
     */
    public static final String GMP = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    /**
     * 反转风格字母表
     */
    public static final String INVERTED = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    /**
     * GMP风格字母表
     */
    public static final byte[] GMP_ALPHABET = GMP.getBytes(StandardCharsets.UTF_8);
    /**
     * 反转风格字母表
     */
    public static final byte[] INVERTED_ALPHABET = INVERTED.getBytes(StandardCharsets.UTF_8);
    /**
     * GMP风格反向映射表
     */
    public static final byte[] GMP_LOOKUP = lookup(GMP_ALPHABET);
    /**
     * 反转风格反向映射表
     */
    public static final byte[] INVERTED_LOOKUP = lookup(INVERTED_ALPHABET);

    // endregion

    private Base62() {
    }

    /* ==================== 编码数字类型 ==================== */
    // region 编码数字类型

    /**
     * 编码int型数字(GMP风格)
     *
     * @param number int型数字
     * @return Base62字符串
     * @throws IllegalArgumentException number {@value #NEGATIVE}
     */
    public static String encode(int number) {
        checkNegative(number);
        return encodeInner(number, GMP_ALPHABET);
    }

    /**
     * 编码int型数字
     *
     * @param number   int型数字
     * @param alphabet 字母表 {@link #GMP_ALPHABET} {@link #INVERTED_ALPHABET}
     * @return Base62字符串
     * @throws IllegalArgumentException number {@value #NEGATIVE}
     */
    public static String encode(int number, byte[] alphabet) {
        checkNegative(number);
        return encodeInner(number, alphabet);
    }

    /**
     * 编码int型数字(内部)
     *
     * @param number   int型数字(>=0)
     * @param alphabet 字母表 {@link #GMP_ALPHABET} {@link #INVERTED_ALPHABET}
     * @return Base62字符串
     */
    private static String encodeInner(int number, byte[] alphabet) {
        if (number == 0) {
            return String.valueOf((char) alphabet[0]);
        }
        StringBuilder sb = new StringBuilder();
        for (; number > 0; number /= 62) {
            sb.append((char) alphabet[(number % 62)]);
        }
        return sb.reverse().toString();
    }

    /**
     * 编码long型数字(GMP风格)
     *
     * @param number long型数字
     * @return Base62字符串
     * @throws IllegalArgumentException number {@value #NEGATIVE}
     */
    public static String encode(long number) {
        checkNegative(number);
        return encodeInner(number, GMP_ALPHABET);
    }

    /**
     * 编码long型数字
     *
     * @param number   long型数字
     * @param alphabet 字母表 {@link #GMP_ALPHABET} {@link #INVERTED_ALPHABET}
     * @return Base62字符串
     * @throws IllegalArgumentException number {@value #NEGATIVE}
     */
    public static String encode(long number, byte[] alphabet) {
        checkNegative(number);
        return encodeInner(number, alphabet);
    }

    /**
     * 编码long型数字(内部)
     *
     * @param number   long型数字(>=0)
     * @param alphabet 字母表 {@link #GMP_ALPHABET} {@link #INVERTED_ALPHABET}
     * @return Base62字符串
     */
    private static String encodeInner(long number, byte[] alphabet) {
        if (number == 0) {
            return String.valueOf((char) alphabet[0]);
        }
        StringBuilder sb = new StringBuilder();
        for (; number > 0; number /= 62) {
            sb.append((char) alphabet[(int) (number % 62)]);
        }
        return sb.reverse().toString();
    }

    // endregion

    /* ==================== 编码字符串和byte[]类型 ==================== */
    // region 编码字符串和byte[]类型

    /**
     * 编码为Base62字符串(GMP风格)
     *
     * @param string 原始字符串
     * @return Base62字符串
     * @throws IllegalArgumentException string {@value EMPTY_STRING}
     */
    public static String encodeToString(String string) {
        checkEmptyString(string);
        return new String(encodeInner(string.getBytes(StandardCharsets.UTF_8), GMP_ALPHABET), StandardCharsets.UTF_8);
    }

    /**
     * 编码为Base62字符串
     *
     * @param string   原始字符串
     * @param alphabet 字母表 {@link #GMP_ALPHABET} {@link #INVERTED_ALPHABET}
     * @return Base62字符串
     * @throws IllegalArgumentException string {@value EMPTY_STRING}
     */
    public static String encodeToString(String string, byte[] alphabet) {
        checkEmptyString(string);
        return new String(encodeInner(string.getBytes(StandardCharsets.UTF_8), alphabet), StandardCharsets.UTF_8);
    }

    /**
     * 编码为Base62字符串(GMP风格)
     *
     * @param bytes 原始数据
     * @return Base62字符串
     * @throws IllegalArgumentException bytes {@value EMPTY_BYTES}
     */
    public static String encodeToString(byte[] bytes) {
        checkEmptyBytes(bytes);
        return new String(encodeInner(bytes, GMP_ALPHABET), StandardCharsets.UTF_8);
    }

    /**
     * 编码为Base62字符串
     *
     * @param bytes    原始数据
     * @param alphabet 字母表 {@link #GMP_ALPHABET} {@link #INVERTED_ALPHABET}
     * @return Base62字符串
     * @throws IllegalArgumentException bytes {@value EMPTY_BYTES}
     */
    public static String encodeToString(byte[] bytes, byte[] alphabet) {
        checkEmptyBytes(bytes);
        return new String(encodeInner(bytes, alphabet), StandardCharsets.UTF_8);
    }

    /**
     * 编码(GMP风格)
     *
     * @param string 原始字符串
     * @return Base62数据
     * @throws IllegalArgumentException string {@value EMPTY_STRING}
     */
    public static byte[] encode(String string) {
        checkEmptyString(string);
        return encodeInner(string.getBytes(StandardCharsets.UTF_8), GMP_ALPHABET);
    }

    /**
     * 编码
     *
     * @param string   原始字符串
     * @param alphabet 字母表 {@link #GMP_ALPHABET} {@link #INVERTED_ALPHABET}
     * @return Base62数据
     * @throws IllegalArgumentException string {@value EMPTY_STRING}
     */
    public static byte[] encode(String string, byte[] alphabet) {
        checkEmptyString(string);
        return encodeInner(string.getBytes(StandardCharsets.UTF_8), alphabet);
    }

    /**
     * 编码(GMP风格)
     *
     * @param bytes 原始数据
     * @return Base62数据
     * @throws IllegalArgumentException bytes {@value EMPTY_BYTES}
     */
    public static byte[] encode(byte[] bytes) {
        checkEmptyBytes(bytes);
        return encodeInner(bytes, GMP_ALPHABET);
    }

    /**
     * 编码
     *
     * @param bytes    原始数据
     * @param alphabet 字母表 {@link #GMP_ALPHABET} {@link #INVERTED_ALPHABET}
     * @return Base62数据
     * @throws IllegalArgumentException bytes {@value EMPTY_BYTES}
     */
    public static byte[] encode(byte[] bytes, byte[] alphabet) {
        checkEmptyBytes(bytes);
        return encodeInner(bytes, alphabet);
    }

    /**
     * 编码
     *
     * @param bytes    原始数据
     * @param alphabet 字母表 {@link #GMP_ALPHABET} {@link #INVERTED_ALPHABET}
     * @return Base62数据
     */
    private static byte[] encodeInner(byte[] bytes, byte[] alphabet) {
        byte[] convert = convert(bytes, 256, 62);
        int length = convert.length;
        byte[] data = new byte[length];
        for (int i = 0; i < length; i++) {
            data[i] = alphabet[convert[i]];
        }
        return data;
    }

    // endregion

    /* ==================== 解码数字类型 ==================== */
    // region 解码数字类型

    /**
     * 解码int型数字(GMP风格)
     *
     * @param string Base62字符串
     * @return int型数字
     * @throws IllegalArgumentException string {@value EMPTY_STRING}
     */
    public static int decodeInt(String string) {
        checkEmptyString(string);
        return decodeIntInner(string, GMP_LOOKUP);
    }

    /**
     * 解码int型数字
     *
     * @param string Base62字符串
     * @param lookup 反向映射表 {@link #GMP_LOOKUP} {@link #INVERTED_LOOKUP}
     * @return int型数字
     * @throws IllegalArgumentException string {@value EMPTY_STRING}
     */
    public static int decodeInt(String string, byte[] lookup) {
        checkEmptyString(string);
        return decodeIntInner(string, lookup);
    }

    /**
     * 解码int型数字(内部)
     *
     * @param string Base62字符串
     * @param lookup 反向映射表 {@link #GMP_LOOKUP} {@link #INVERTED_LOOKUP}
     * @return int型数字
     */
    private static int decodeIntInner(String string, byte[] lookup) {
        char[] chars = string.toCharArray();
        int length = chars.length;
        int number = 0;
        int power = 1;
        for (int i = 0; i < length; i++, power *= 62) {
            number += lookup[chars[length - i - 1]] * power;
        }
        return number;
    }

    /**
     * 解码long型数字(GMP风格)
     *
     * @param string Base62字符串
     * @return long型数字
     * @throws IllegalArgumentException string {@value EMPTY_STRING}
     */
    public static long decodeLong(String string) {
        checkEmptyString(string);
        return decodeLongInner(string, GMP_LOOKUP);
    }

    /**
     * 解码long型数字
     *
     * @param string Base62字符串
     * @param lookup 反向映射表 {@link #GMP_LOOKUP} {@link #INVERTED_LOOKUP}
     * @return long型数字
     * @throws IllegalArgumentException string {@value EMPTY_STRING}
     */
    public static long decodeLong(String string, byte[] lookup) {
        checkEmptyString(string);
        return decodeLongInner(string, lookup);
    }

    /**
     * 解码long型数字(内部)
     *
     * @param string Base62字符串
     * @param lookup 反向映射表 {@link #GMP_LOOKUP} {@link #INVERTED_LOOKUP}
     * @return long型数字
     */
    private static long decodeLongInner(String string, byte[] lookup) {
        char[] chars = string.toCharArray();
        int length = chars.length;
        long number = 0;
        long power = 1;
        for (int i = 0; i < length; i++, power *= 62) {
            number += lookup[chars[length - i - 1]] * power;
        }
        return number;
    }

    // endregion

    /* ==================== 解码字符串和byte[]类型 ==================== */
    // region 解码字符串和byte[]类型

    /**
     * 解码为字符串(GMP风格)
     *
     * @param string Base62字符串
     * @return 字符串
     * @throws IllegalArgumentException string {@value EMPTY_STRING}
     */
    public static String decodeToString(String string) {
        checkEmptyString(string);
        return new String(decodeInner(string.getBytes(StandardCharsets.UTF_8), GMP_LOOKUP), StandardCharsets.UTF_8);
    }

    /**
     * 解码为字符串
     *
     * @param string Base62字符串
     * @param lookup 反向映射表 {@link #GMP_LOOKUP} {@link #INVERTED_LOOKUP}
     * @return 字符串
     * @throws IllegalArgumentException string {@value EMPTY_STRING}
     */
    public static String decodeToString(String string, byte[] lookup) {
        checkEmptyString(string);
        return new String(decodeInner(string.getBytes(StandardCharsets.UTF_8), lookup), StandardCharsets.UTF_8);
    }

    /**
     * 解码(GMP风格)
     *
     * @param string Base62字符串
     * @return 数据
     * @throws IllegalArgumentException string {@value EMPTY_STRING}
     */
    public static byte[] decode(String string) {
        checkEmptyString(string);
        return decodeInner(string.getBytes(StandardCharsets.UTF_8), GMP_LOOKUP);
    }

    /**
     * 解码
     *
     * @param string Base62字符串
     * @param lookup 反向映射表 {@link #GMP_LOOKUP} {@link #INVERTED_LOOKUP}
     * @return 数据
     * @throws IllegalArgumentException string {@value EMPTY_STRING}
     */
    public static byte[] decode(String string, byte[] lookup) {
        checkEmptyString(string);
        return decodeInner(string.getBytes(StandardCharsets.UTF_8), lookup);
    }

    /**
     * 解码(GMP风格)
     *
     * @param bytes Base62数据
     * @return 数据
     * @throws IllegalArgumentException bytes {@value EMPTY_BYTES}
     */
    public static byte[] decode(byte[] bytes) {
        checkEmptyBytes(bytes);
        return decodeInner(bytes, GMP_LOOKUP);
    }

    /**
     * 解码
     *
     * @param bytes  Base62数据
     * @param lookup 反向映射表 {@link #GMP_LOOKUP} {@link #INVERTED_LOOKUP}
     * @return 数据
     * @throws IllegalArgumentException bytes {@value EMPTY_BYTES}
     */
    public static byte[] decode(byte[] bytes, byte[] lookup) {
        checkEmptyBytes(bytes);
        return decodeInner(bytes, lookup);
    }

    /**
     * 解码
     *
     * @param bytes  Base62数据
     * @param lookup 反向映射表 {@link #GMP_LOOKUP} {@link #INVERTED_LOOKUP}
     * @return 数据
     * @throws IllegalArgumentException bytes {@value EMPTY_BYTES}
     */
    private static byte[] decodeInner(byte[] bytes, byte[] lookup) {
        int length = bytes.length;
        byte[] data = new byte[length];
        for (int i = 0; i < length; i++) {
            data[i] = lookup[bytes[i]];
        }
        return convert(data, 62, 256);
    }

    // endregion

    /* ==================== 工具 ==================== */
    // region 工具

    /**
     * 是合法的Base62数据
     *
     * @param bytes Base62数据
     * @return 是否合法
     */
    public static boolean isValid(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return false;
        }
        for (byte b : bytes) {
            if (!((b >= '0' && b <= '9') || (b >= 'A' && b <= 'Z') || (b >= 'a' && b <= 'z'))) {
                return false;
            }
        }
        return true;
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

    /**
     * 基准转换
     *
     * @param bytes      数据
     * @param sourceBase 源基准长度
     * @param targetBase 目标基准长度
     * @return 转换后的数据
     */
    public static byte[] convert(byte[] bytes, int sourceBase, int targetBase) {
        int byteLength = bytes.length;
        // 数据转换后的长度
        int outLength = (int) Math.ceil((Math.log(sourceBase) / Math.log(targetBase)) * byteLength);
        // 转换数据
        ByteArrayOutputStream out = new ByteArrayOutputStream(outLength);
        byte[] source = bytes;
        while (source.length > 0) {
            ByteArrayOutputStream quotient = new ByteArrayOutputStream(source.length);
            int remainder = 0;
            for (byte b : source) {
                int accumulator = (b & 0xFF) + remainder * sourceBase;
                int digit = (accumulator - (accumulator % targetBase)) / targetBase;
                remainder = accumulator % targetBase;
                if (quotient.size() > 0 || digit > 0) {
                    quotient.write(digit);
                }
            }
            out.write(remainder);
            source = quotient.toByteArray();
        }
        for (int i = 0; i < byteLength - 1 && bytes[i] == 0; i++) {
            out.write(0);
        }
        // 反转数据
        byte[] data = out.toByteArray();
        int dataLength = data.length;
        byte[] reverse = new byte[dataLength];
        for (int i = 0; i < dataLength; i++) {
            reverse[dataLength - i - 1] = data[i];
        }
        return reverse;
    }

    /**
     * 获取反向映射表
     *
     * @param alphabet 字母表 {@link #GMP_ALPHABET} {@link #INVERTED_ALPHABET}
     * @return 映射表
     */
    public static byte[] lookup(byte[] alphabet) {
        byte[] lookup = new byte[123];
        for (int i = 0; i < alphabet.length; i++) {
            lookup[alphabet[i]] = (byte) i;
        }
        return lookup;
    }

    // endregion

    /* ==================== 私有 ==================== */
    // region 私有

    /**
     * 负数
     */
    private static final String NEGATIVE = "不可为负数！";
    /**
     * 空字符串
     */
    private static final String EMPTY_STRING = "不可为null或空字符串！";
    /**
     * 空数组
     */
    private static final String EMPTY_BYTES = "不可为null或空数组！";

    /**
     * 检查int型数字是否为负数
     *
     * @param number int型数字
     * @throws IllegalArgumentException number {@value #NEGATIVE}
     */
    private static void checkNegative(int number) {
        if (number < 0) {
            throw new IllegalArgumentException(NEGATIVE);
        }
    }

    /**
     * 检查long型数字是否为负数
     *
     * @param number long型数字
     * @throws IllegalArgumentException number {@value #NEGATIVE}
     */
    private static void checkNegative(long number) {
        if (number < 0) {
            throw new IllegalArgumentException(NEGATIVE);
        }
    }

    /**
     * 检查是否为null或空字符串
     *
     * @param string 字符串
     * @throws IllegalArgumentException string {@value EMPTY_STRING}
     */
    private static void checkEmptyString(String string) {
        if (string == null || string.isEmpty()) {
            throw new IllegalArgumentException(EMPTY_STRING);
        }
    }

    /**
     * 检查是否为null或空数组
     *
     * @param bytes byte[]
     * @throws IllegalArgumentException bytes {@value EMPTY_BYTES}
     */
    private static void checkEmptyBytes(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            throw new IllegalArgumentException(EMPTY_BYTES);
        }
    }

    // endregion

}
