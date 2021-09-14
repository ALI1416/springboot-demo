package com.demo.base;

import com.demo.constant.ResultCodeEnum;
import com.demo.entity.pojo.Result;

/**
 * <h1>ResultBase</h1>
 *
 * <p>
 * createDate 2021/09/14 14:42:03
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class ResultBase {

    /**
     * 请求参数错误
     */
    public static Result paramIsError() {
        return Result.e(ResultCodeEnum.PARAM_IS_ERROR);
    }

}
