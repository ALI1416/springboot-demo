package com.demo.controller;

import cn.z.mail.MailTemp;
import com.demo.entity.pojo.Result;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class IndexController {

    private final MailTemp mailTemp;

    /**
     * http://localhost:8080/text <br>
     * 普通邮件
     */
    @GetMapping("text")
    public Result index() {
        mailTemp.sendMail("1416978277@qq.com", "主题", "内容");
        return Result.o();
    }

    /**
     * http://localhost:8080/html <br>
     * HTML邮件
     */
    @GetMapping("html")
    public Result html() {
        mailTemp.sendMailHtmlSync("1416978277@qq.com", "主题HTML", "<h1>内容HTML</h1>");
        return Result.o();
    }

}
