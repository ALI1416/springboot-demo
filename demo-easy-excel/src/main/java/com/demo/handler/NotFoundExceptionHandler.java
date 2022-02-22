package com.demo.handler;

import com.demo.constant.ResultCodeEnum;
import com.demo.entity.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

/**
 * <h1>404异常处理类</h1>
 *
 * <p>
 * createDate 2021/09/10 14:37:57
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@RestController
@Slf4j
public class NotFoundExceptionHandler implements ErrorController {

    /**
     * 请求地址找不到
     */
    @RequestMapping("error")
    public Result error(HttpServletRequest request) {
        log.warn(ResultCodeEnum.NOT_FOUND.getMsg(),
                new IllegalStateException("请求地址找不到：" + new ServletRequestAttributes(request).getAttribute(RequestDispatcher.ERROR_REQUEST_URI, RequestAttributes.SCOPE_REQUEST)));
        return Result.e(ResultCodeEnum.NOT_FOUND);
    }

}
