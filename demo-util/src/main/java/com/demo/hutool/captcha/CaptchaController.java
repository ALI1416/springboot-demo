package com.demo.hutool.captcha;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;

/**
 * <h1>验证码</h1>
 *
 * <p>
 * createDate 2022/02/22 13:59:51
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Controller
@Slf4j
public class CaptchaController {

    @GetMapping("/captcha")
    public void captcha(HttpServletResponse response) throws Exception {
        /* 设置请求头为输出图片类型 */
        response.setContentType("image/png");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        /* 输出验证码图片 */
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 100);
        lineCaptcha.write(response.getOutputStream());
        log.info("验证码:" + lineCaptcha.getCode());
    }

}
