package com.demo.util.hutool.captcha;

import cn.hutool.captcha.*;
import lombok.extern.slf4j.Slf4j;

/**
 * <h1>验证码</h1>
 *
 * <p>
 * createDate 2022/02/22 14:08:01
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Slf4j
public class Main {

    public static void main(String[] args) {
        /* 线段干扰的验证码 */
        // 验证码的长和宽(默认5位验证码，150条干扰线)
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 100);
        // LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 100, 5, 150);
        // 写出到文件
        lineCaptcha.write("E:/lineCaptcha.png");
        // 验证码
        log.info("验证码：" + lineCaptcha.getCode());
        // 有效性
        log.info("有效性：" + lineCaptcha.verify("1234"));
        // 重新生成验证码
        lineCaptcha.createCode();
        // 写出到文件
        lineCaptcha.write("E:/lineCaptcha2.png");
        // 验证码
        log.info("验证码：" + lineCaptcha.getCode());

        /* 圆圈干扰验证码 */
        // 验证码的长和宽(默认5位验证码，15个干扰圆)
        CircleCaptcha circleCaptcha = CaptchaUtil.createCircleCaptcha(200, 100);
        // CircleCaptcha circleCaptcha = CaptchaUtil.createCircleCaptcha(200, 100, 5, 15);
        // 写出到文件
        circleCaptcha.write("E:/circleCaptcha.png");
        // 验证码
        log.info("验证码：" + circleCaptcha.getCode());

        /* 扭曲干扰验证码 */
        // 验证码的长和宽(默认5位验证码，干扰线宽度为4像素)
        ShearCaptcha shearCaptcha = CaptchaUtil.createShearCaptcha(200, 100);
        // ShearCaptcha shearCaptcha = CaptchaUtil.createShearCaptcha(200, 100, 5, 4);
        // 写出到文件
        shearCaptcha.write("E:/shearCaptcha.png");
        // 验证码
        log.info("验证码：" + shearCaptcha.getCode());

        /* GIF验证码 */
        // 验证码的长和宽(默认5位验证码)
        GifCaptcha gifCaptcha = CaptchaUtil.createGifCaptcha(200, 100);
        // GifCaptcha gifCaptcha = CaptchaUtil.createGifCaptcha(200, 100, 5);
        // 写出到文件
        gifCaptcha.write("E:/gifCaptcha.gif");
        // 验证码
        log.info("验证码：" + gifCaptcha.getCode());
    }

}
