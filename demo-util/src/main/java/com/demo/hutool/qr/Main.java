package com.demo.hutool.qr;

import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.io.File;
import java.nio.charset.StandardCharsets;

/**
 * <h1>二维码</h1>
 *
 * <p>
 * createDate 2022/03/10 16:08:11
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Slf4j
public class Main {

    private static final String content = "爱上对方过后就哭了啊123456789012345678901234567890";
    private static final ErrorCorrectionLevel level = ErrorCorrectionLevel.M;
    private static final String path1 = "E:/qr1.png";
    private static final String path2 = "E:/qr2.png";

    public static void main(String[] args) {
        /*生成二维码*/
        File file = new File(path1);
        // 默认M和UTF-8
        QrCodeUtil.generate(content, 300, 300, file);
        /*识别二维码*/
        String decode = QrCodeUtil.decode(file);
        log.info("二维码:" + decode);

        /*自定义参数*/
        QrConfig config = new QrConfig(300, 300);
        // 设置边距，既二维码和背景之间的边距
        config.setMargin(1);
        // 设置前景色
        config.setForeColor(new Color(0x66ccff));
        // 设置背景色
        config.setBackColor(Color.BLACK);
        // 设置二维码中的Logo
        config.setImg(file);
        // 设置纠错级别
        config.setErrorCorrection(level);
        // 设置文本编码
        config.setCharset(StandardCharsets.UTF_8);
        // 生成二维码到文件，也可以到流
        QrCodeUtil.generate(content, config, new File(path2));
    }

}
