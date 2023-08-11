package com.demo.handler;

import com.demo.constant.ResultCodeEnum;
import com.demo.entity.pojo.GlobalException;
import com.demo.entity.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.io.IOException;

/**
 * <h1>全局异常处理类(404异常除外)</h1>
 *
 * <p>
 * 捕获处理所有未被捕获的异常(404异常除外)
 * </p>
 *
 * <p>
 * createDate 2020/12/22 19:56:13
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 请求方法不支持异常
     *
     * @see HttpRequestMethodNotSupportedException
     */
    @Order(1)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result requestMethodNotSupportedHandler() {
        return Result.e(ResultCodeEnum.REQUEST_METHOD_NOT_SUPPORTED);
    }

    /**
     * 媒体类型不支持异常
     *
     * @see HttpMediaTypeNotSupportedException
     */
    @Order(1)
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public Result mediaTypeNotSupportedHandler() {
        return Result.e(ResultCodeEnum.MEDIA_TYPE_NOT_SUPPORTED);
    }

    /**
     * <h2>参数异常</h2>
     * <h3>Params</h3>
     * --{@linkplain IllegalStateException IllegalStateException}：拆箱类型为null错误<br>
     * --{@linkplain MethodArgumentTypeMismatchException MethodArgumentTypeMismatchException}：参数类型错误<br>
     * --{@linkplain BindException BindException}：对象的属性类型错误<br>
     * <h3>Body</h3>
     * --raw<br>
     * ----JSON<br>
     * ------{@linkplain HttpMessageNotReadableException HttpMessageNotReadableException}：为null、不是JSON格式、参数类型错误<br>
     * ----XML<br>
     * ----HTML<br>
     * ----Text<br>
     * --form-data<br>
     * ----等同于Params
     * --x-www-form-urlencoded<br>
     * --binary<br>
     *
     * @see IllegalStateException
     * @see MethodArgumentTypeMismatchException
     * @see BindException
     * @see HttpMessageNotReadableException
     */
    @Order(1)
    @ExceptionHandler({ //
            IllegalStateException.class, //
            MethodArgumentTypeMismatchException.class, //
            BindException.class,  //
            HttpMessageNotReadableException.class //
    })
    public Result paramErrorHandler() {
        return Result.e(ResultCodeEnum.PARAM_IS_ERROR);
    }

    /**
     * 全局异常
     *
     * @see GlobalException
     */
    @Order(1)
    @ExceptionHandler(GlobalException.class)
    public Result globalExceptionHandler(GlobalException e) {
        return Result.e(e.getResultCodeEnum(), e.getMsg());
    }

    /**
     * 运行时异常
     *
     * @see RuntimeException
     */
    @Order(2)
    @ExceptionHandler(RuntimeException.class)
    public Result runtimeExceptionHandler(RuntimeException e) {
        log.error("RuntimeException", e);
        return Result.e(ResultCodeEnum.SYSTEM_INNER_ERROR, "RuntimeException");
    }

    /**
     * IO异常
     *
     * @see IOException
     */
    @Order(2)
    @ExceptionHandler(IOException.class)
    public Result ioExceptionHandler(IOException e) {
        log.error("IOException", e);
        return Result.e(ResultCodeEnum.SYSTEM_INNER_ERROR, "IOException");
    }

    /**
     * 异常
     *
     * @see Exception
     */
    @Order(3)
    @ExceptionHandler(Exception.class)
    public Result exceptionHandler(Exception e) {
        log.error("Exception", e);
        return Result.e(ResultCodeEnum.SYSTEM_INNER_ERROR, "Exception");
    }

}
