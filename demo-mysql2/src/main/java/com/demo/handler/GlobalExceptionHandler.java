package com.demo.handler;

import com.demo.constant.ResultCodeEnum;
import com.demo.entity.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.io.IOException;

/**
 * <h1>全局异常处理</h1>
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
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public Result notSupportedHandler(Exception e) {
        log.warn(ResultCodeEnum.NOT_SUPPORTED.getMsg(), e);
        return Result.e(ResultCodeEnum.NOT_SUPPORTED);
    }

    /**
     * <h2>参数异常</h2>
     * <h3>Params</h3>
     * --{@linkplain IllegalStateException IllegalStateException}：拆箱类型不能为null<br>
     * --{@linkplain MethodArgumentTypeMismatchException MethodArgumentTypeMismatchException}：参数类型错误<br>
     * <h3>Body</h3>
     * --form-data<br>
     * --x-www-form-urlencoded<br>
     * ----{@linkplain BindException BindException}：参数类型错误<br>
     * --raw<br>
     * ----JSON<br>
     * ------{@linkplain HttpMessageNotReadableException HttpMessageNotReadableException}：为null、不是JSON格式、参数类型错误<br>
     * ----XML<br>
     * ----HTML<br>
     * ----Text<br>
     * --binary<br>
     *
     * @see IllegalStateException
     * @see MethodArgumentTypeMismatchException
     * @see BindException
     * @see HttpMessageNotReadableException
     */
    @Order(1)
    @ExceptionHandler({IllegalStateException.class, MethodArgumentTypeMismatchException.class, BindException.class,
            HttpMessageNotReadableException.class})
    public Result paramErrorHandler(Exception e) {
        log.warn(ResultCodeEnum.PARAM_IS_ERROR.getMsg(), e);
        return Result.e(ResultCodeEnum.PARAM_IS_ERROR);
    }

    /**
     * RuntimeException 运行异常
     *
     * @see RuntimeException
     */
    @Order(2)
    @ExceptionHandler(RuntimeException.class)
    public Result runtimeExceptionHandler(Exception e) {
        log.error("RuntimeException", e);
        return Result.e(ResultCodeEnum.ERROR, "RuntimeException");
    }

    /**
     * IOException IO异常
     *
     * @see IOException
     */
    @Order(2)
    @ExceptionHandler(IOException.class)
    public Result ioExceptionHandler(Exception e) {
        log.error("IOException", e);
        return Result.e(ResultCodeEnum.ERROR, "IOException");
    }

    /**
     * Exception 异常
     *
     * @see Exception
     */
    @Order(3)
    @ExceptionHandler(Exception.class)
    public Result exceptionHandler(Exception e) {
        System.out.println("exceptionHandler");
        log.error("Exception", e);
        return Result.e(ResultCodeEnum.ERROR, "Exception");
    }
}
