package com.demo.base;

import com.demo.constant.ResultCode;
import com.demo.entity.pojo.Result;

/**
 * <h1>控制层基类</h1>
 *
 * <p>
 * createDate 2020/11/11 11:11:11
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class ControllerBase {

    /**
     * 请求参数错误
     *
     * @return 请求参数错误
     */
    public static Result paramError() {
        return Result.e(ResultCode.PARAM_ERROR);
    }

    /**
     * 是null对象
     *
     * @param object 对象
     * @return 是null对象
     */
    public static boolean isNull(Object object) {
        return object == null;
    }

    /**
     * 存在null对象
     *
     * @param objects 多个对象
     * @return 存在null对象
     */
    public static boolean existNull(Object... objects) {
        if (objects == null) {
            return true;
        }
        for (Object object : objects) {
            if (object == null) {
                return true;
            }
        }
        return false;
    }

    /**
     * 全是null对象
     *
     * @param objects 多个对象
     * @return 全是null对象
     */
    public static boolean allNull(Object... objects) {
        if (objects == null) {
            return true;
        }
        for (Object object : objects) {
            if (object != null) {
                return false;
            }
        }
        return true;
    }

    /**
     * 是空字符串
     *
     * @param string 字符串
     * @return 是空字符串
     */
    public static boolean isEmpty(String string) {
        return string == null || string.isEmpty();
    }

    /**
     * 存在空字符串
     *
     * @param strings 多个字符串
     * @return 存在空字符串
     */
    public static boolean existEmpty(String... strings) {
        if (strings == null) {
            return true;
        }
        for (String string : strings) {
            if (string == null || string.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 全是空字符串
     *
     * @param strings 多个字符串
     * @return 全是空字符串
     */
    public static boolean allEmpty(String... strings) {
        if (strings == null) {
            return true;
        }
        for (String string : strings) {
            if (string != null && !string.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    /**
     * 是空白字符串
     *
     * @param string 字符串
     * @return 是空白字符串
     */
    public static boolean isBlack(String string) {
        return string == null || string.isBlank();
    }

    /**
     * 存在空白字符串
     *
     * @param strings 多个字符串
     * @return 存在空白字符串
     */
    public static boolean existBlack(String... strings) {
        if (strings == null) {
            return true;
        }
        for (String string : strings) {
            if (string == null || string.isBlank()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 全是空白字符串
     *
     * @param strings 多个字符串
     * @return 全是空白字符串
     */
    public static boolean allBlack(String... strings) {
        if (strings == null) {
            return true;
        }
        for (String string : strings) {
            if (string != null && !string.isBlank()) {
                return false;
            }
        }
        return true;
    }

}
