package com.demo.handler;

import com.demo.constant.ResultEnum;
import com.demo.entity.pojo.Result;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <h1>404异常处理</h1>
 *
 * <p>
 * createDate 2021/09/10 14:37:57
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@RestController
public class PageNotFoundExceptionHandler implements ErrorController {

    /**
     * 页面找不到
     */
    @RequestMapping("error")
    public Result error() {
        return Result.e(ResultEnum.PAGE_NOT_FOUND);
    }

}
