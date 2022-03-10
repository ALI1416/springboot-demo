package com.demo.hutool.qr;

import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.io.File;

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

    public static void main(String[] args) {
        String s = "https://hutool.cn/";
        File file = new File("E:/qr.png");
        /*生成二维码*/
        QrCodeUtil.generate(s, 300, 300, file);

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
        config.setErrorCorrection(ErrorCorrectionLevel.H);
        // 生成二维码到文件，也可以到流
        QrCodeUtil.generate(s, config, new File("E:/qr2.png"));

        /*识别二维码*/
        String decode = QrCodeUtil.decode(file);
        log.info("二维码:" + decode);
    }

}
