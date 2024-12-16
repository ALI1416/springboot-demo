package com.demo.handler;

import com.demo.constant.ResultCode;
import com.demo.entity.pojo.GlobalException;
import com.demo.entity.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.core.annotation.Order;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.io.IOException;

/**
 * <h1>全局异常处理</h1>
 *
 * <p>
 * 捕获处理所有未被捕获的异常
 * </p>
 *
 * <p>
 * createDate 2020/12/22 19:56:13
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler implements ErrorController {

    /**
     * 页面未找到
     */
    @RequestMapping("error")
    public Result error() {
        return Result.e(ResultCode.PAGE_NOT_FOUND);
    }

    /**
     * 页面未找到异常
     *
     * @see NoResourceFoundException
     */
    @Order(1)
    @ExceptionHandler(NoResourceFoundException.class)
    public Result noResourceFoundHandler() {
        return Result.e(ResultCode.PAGE_NOT_FOUND);
    }

    /**
     * 请求方法不支持异常
     *
     * @see HttpRequestMethodNotSupportedException
     */
    @Order(1)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result requestMethodNotSupportedHandler() {
        return Result.e(ResultCode.REQUEST_METHOD_NOT_SUPPORTED);
    }

    /**
     * 媒体类型不支持异常
     *
     * @see HttpMediaTypeNotSupportedException
     */
    @Order(1)
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public Result mediaTypeNotSupportedHandler() {
        return Result.e(ResultCode.MEDIA_TYPE_NOT_SUPPORTED);
    }

    /**
     * <h2>参数异常</h2>
     * <h3>params、form-data、x-www-form-urlencoded</h3>
     * <ul>
     * <li>{@link IllegalStateException} 拆箱类型为null错误</li>
     * <li>{@link MethodArgumentTypeMismatchException} 参数类型错误</li>
     * <li>{@link MethodArgumentNotValidException} 对象属性类型错误</li>
     * </ul>
     * <h3>JSON</h3>
     * <ul>
     * <li>{@link HttpMessageNotReadableException} 为null、不是JSON格式、参数类型错误</li>
     * </ul>
     *
     * @see IllegalStateException
     * @see MethodArgumentTypeMismatchException
     * @see MethodArgumentNotValidException
     * @see HttpMessageNotReadableException
     */
    @Order(1)
    @ExceptionHandler({
            IllegalStateException.class,
            MethodArgumentTypeMismatchException.class,
            MethodArgumentNotValidException.class,
            HttpMessageNotReadableException.class
    })
    public Result paramErrorHandler() {
        return Result.e(ResultCode.PARAM_ERROR);
    }

    /**
     * 全局异常
     *
     * @see GlobalException
     */
    @Order(1)
    @ExceptionHandler(GlobalException.class)
    public Result globalExceptionHandler(GlobalException e) {
        log.error("GlobalException: [{}:{}]", e.getResultCode(), e.getResultCode().getMessage(), e);
        return Result.e(e.getResultCode(), e.getMessage());
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
        return Result.e(ResultCode.SYSTEM_INNER_ERROR);
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
        return Result.e(ResultCode.SYSTEM_INNER_ERROR);
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
        return Result.e(ResultCode.SYSTEM_INNER_ERROR);
    }

}
