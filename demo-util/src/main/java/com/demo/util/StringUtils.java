package com.demo.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * <h1>字符串工具</h1>
 *
 * <p>
 * createDate 2020/11/18 10:29:56
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class StringUtils {

    /**
     * 数字:{@value}
     */
    public static final String NUMBER = "0123456789";
    /**
     * 大写字母:{@value}
     */
    public static final String UPPER_LETTER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    /**
     * 小写字母:{@value}
     */
    public static final String LOWER_LETTER = "abcdefghijklmnopqrstuvwxyz";
    /**
     * 全部字母:{@value}
     */
    public static final String ALL_LETTER = UPPER_LETTER + LOWER_LETTER;
    /**
     * 数字+大写字母:{@value}
     */
    public static final String NUMBER_UPPER_LETTER = NUMBER + UPPER_LETTER;
    /**
     * 数字+小写字母:{@value}
     */
    public static final String NUMBER_LOWER_LETTER = NUMBER + LOWER_LETTER;
    /**
     * 数字+全部字母:{@value}
     */
    public static final String NUMBER_ALL_LETTER = NUMBER + ALL_LETTER;
    /**
     * 64进制:{@value}
     */
    public static final String BASE64_ALPHABET = NUMBER + ALL_LETTER + "+/";
    /**
     * 随机数实例
     */
    private static final Random RANDOM = new Random();

    /**
     * 获取随机字符串
     *
     * @param base 源字符串
     * @param len  长度
     * @see #NUMBER
     * @see #UPPER_LETTER
     * @see #LOWER_LETTER
     * @see #ALL_LETTER
     * @see #NUMBER_UPPER_LETTER
     * @see #NUMBER_LOWER_LETTER
     * @see #NUMBER_ALL_LETTER
     * @see #BASE64_ALPHABET
     */
    public static String getRandom(String base, int len) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < len; i++) {
            s.append(base.charAt(RANDOM.nextInt(base.length())));
        }
        return s.toString();
    }

    /**
     * 获取随机数字字符串
     *
     * @param len 长度
     * @return 指定长度随机数字字符串
     */
    public static String getRandomNum(int len) {
        return getRandom(NUMBER, len);
    }

    /**
     * 获取4位随机数字字符串
     *
     * @return 4位随机数字字符串
     */
    public static String getRandomNum4() {
        return getRandom(NUMBER, 4);
    }

    /**
     * 获取6位随机数字字符串
     *
     * @return 6位随机数字字符串
     */
    public static String getRandomNum6() {
        return getRandom(NUMBER, 6);
    }

    /**
     * 获取8位随机数字字符串
     *
     * @return 6位随机数字字符串
     */
    public static String getRandomNum8() {
        return getRandom(NUMBER, 8);
    }

    /**
     * 是null对象
     *
     * @param obj 对象
     * @return 是否为null对象
     */
    public static boolean isNull(Object obj) {
        return obj == null;
    }

    /**
     * 存在null对象
     *
     * @param objs 对象
     * @return 是存在null对象
     */
    public static boolean existNull(Object... objs) {
        for (Object obj : objs) {
            if (obj == null) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是空字符串
     *
     * @param string 字符串
     * @return 是否为空字符串
     */
    public static boolean isEmpty(String string) {
        return string == null || string.length() == 0;
    }

    /**
     * 存在空字符串
     *
     * @param strings 字符串
     * @return 是否存在空字符串
     */
    public static boolean existEmpty(String... strings) {
        for (String string : strings) {
            if (string == null || string.length() == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是空白字符串
     *
     * @param string 字符串
     * @return 是否为空白字符串
     */
    public static boolean isBlack(String string) {
        return string == null || string.trim().length() == 0;
    }

    /**
     * 存在空白字符串
     *
     * @param strings 字符串
     * @return 是否存在空白字符串
     */
    public static boolean existBlack(String... strings) {
        for (String string : strings) {
            if (string == null || string.trim().length() == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 字符串打码
     *
     * @param string 原始字符串
     * @param mask   马赛克字符串
     * @param start  字符串保留首部长度
     * @param end    字符串保留尾部长度
     * @return 打码后的字符串
     */
    public static String getMask(String string, String mask, int start, int end) {
        StringBuilder s = new StringBuilder(string);
        // 字符串长度
        int l = s.length();
        if (l <= start + end) {
            // 字符串长度<=首部保留长度+尾部保留长度
            return mask;
        } else {
            return s.replace(start, l - end, mask).toString();
        }
    }

    /**
     * 字符串打码(保留首尾1位，马赛克字符串是"***")
     *
     * @param string 原始字符串
     * @return 打码后的字符串
     */
    public static String getMask(String string) {
        return getMask(string, "***", 1, 1);
    }

    /**
     * 获取文件后缀
     *
     * @param fileName 文件名
     * @return 文件后缀
     */
    public static String getSuffix(String fileName) {
        int index = fileName.lastIndexOf(".") + 1;
        return index == 0 ? "" : fileName.substring(index);
    }

    /**
     * 重命名字符串
     *
     * @param stringList 字符串列表
     * @return 重命名后的字符串
     */
    public static List<String> duplicateRenameString(List<String> stringList) {
        return duplicateRename(stringList, false);
    }

    /**
     * 重命名文件名
     *
     * @param fileNameList 文件名列表
     * @return 重命名后的文件名
     */
    public static List<String> duplicateRenameFile(List<String> fileNameList) {
        return duplicateRename(fileNameList, true);
    }

    /**
     * 重命名
     *
     * @param stringList 字符串列表
     * @param isFile     是文件
     * @return 重命名后的字符串
     */
    public static List<String> duplicateRename(List<String> stringList, boolean isFile) {
        // 临时
        List<String> stringTemp = new ArrayList<>();
        // 结果
        List<String> stringResult = new ArrayList<>();
        // 个数
        List<Integer> stringCount = new ArrayList<>();
        // 遍历字符串列表
        for (String s : stringList) {
            // 结果中不包含该字符串
            if (!stringResult.contains(s)) {
                // 临时和结果添加该字符串，个数为1
                stringTemp.add(s);
                stringResult.add(s);
                stringCount.add(1);
            } else {
                // 结果中包含该字符串
                // 找到下标和个数
                int index = stringTemp.indexOf(s);
                int count = stringCount.get(index) + 1;
                // 拼接的个数字符串
                String countString;
                // 如果是文件，从.前添加个数字符串
                if (isFile) {
                    int dotIndex = s.lastIndexOf(".");
                    if (dotIndex == -1) {
                        countString = s + " (" + count + ")";
                    } else {
                        countString = s.substring(0, dotIndex) + " (" + count + ")" + s.substring(dotIndex);
                    }
                } else {
                    // 如果不是文件，从尾部添加个数字符串
                    countString = s + " (" + count + ")";
                }
                // 个数+1
                stringCount.set(index, count);
                // 结果添加拼接后的字符串
                stringResult.add(countString);
            }
        }
        // 没有重复的
        if (stringList.size() == stringTemp.size()) {
            return stringList;
        }
        return stringResult;
    }

}
