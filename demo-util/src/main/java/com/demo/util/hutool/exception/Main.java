package com.demo.util.hutool.exception;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.io.IORuntimeException;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * <h1>异常</h1>
 *
 * <p>
 * createDate 2022/03/10 11:04:29
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Slf4j
public class Main {

    public static void main(String[] args) {
        /*包装异常*/
        IORuntimeException e = ExceptionUtil.wrap(new IOException("error"), IORuntimeException.class);
        log.info("包装异常:", e);
        /*获取入口方法*/
        StackTraceElement ele = ExceptionUtil.getRootStackElement();
        log.info("获取入口方法:" + ele.getMethodName());
        /*异常转换*/
        IOException ioException = new IOException("ee");
        IllegalArgumentException argumentException = new IllegalArgumentException(ioException);
        IOException ioException1 = ExceptionUtil.convertFromOrSuppressedThrowable(argumentException,
                IOException.class, true);
        log.info("异常转换:", ioException1);
    }

}
