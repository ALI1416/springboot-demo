package com.demo.base;

import com.demo.constant.ResultEnum;
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
     */
    public static Result paramIsError() {
        return Result.e(ResultEnum.PARAM_IS_ERROR);
    }

    /**
     * 是null对象
     *
     * @param object 对象
     * @return 是否为null对象
     */
    public static boolean isNull(Object object) {
        return object == null;
    }

    /**
     * 存在null对象
     *
     * @param objects 对象
     * @return 是存在null对象
     */
    public static boolean existNull(Object... objects) {
        for (Object object : objects) {
            if (object == null) {
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
        return string == null || string.isEmpty();
    }

    /**
     * 存在空字符串
     *
     * @param strings 字符串
     * @return 是否存在空字符串
     */
    public static boolean existEmpty(String... strings) {
        for (String string : strings) {
            if (string == null || string.isEmpty()) {
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
        return string == null || string.trim().isEmpty();
    }

    /**
     * 存在空白字符串
     *
     * @param strings 字符串
     * @return 是否存在空白字符串
     */
    public static boolean existBlack(String... strings) {
        for (String string : strings) {
            if (string == null || string.trim().isEmpty()) {
                return true;
            }
        }
        return false;
    }

}
