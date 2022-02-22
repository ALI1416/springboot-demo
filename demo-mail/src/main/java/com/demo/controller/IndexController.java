package com.demo.controller;

import com.demo.entity.pojo.Result;
import com.demo.util.MailUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <h1>首页</h1>
 *
 * <p>
 * createDate 2021/09/09 10:35:04
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@RestController
public class IndexController {

    /**
     * 普通邮件
     */
    @GetMapping("text")
    public Result index() {
        MailUtils.sendMail("1416978277@qq.com", "主题", "内容");
        return Result.o();
    }

    /**
     * HTML邮件
     */
    @GetMapping("html")
    public Result html() {
        MailUtils.sendMailHtml("1416978277@qq.com", "主题Html", "<h1>内容Html</h1>");
        return Result.o();
    }

}
