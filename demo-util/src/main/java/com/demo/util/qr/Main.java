package com.demo.util.qr;

import com.google.zxing.EncodeHintType;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.encoder.Encoder;
import com.google.zxing.qrcode.encoder.QRCode;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.EnumMap;

/**
 * <h1>二维码</h1>
 *
 * <p>
 * createDate 2023/05/09 09:26:59
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class Main {

    private static final String content = "爱上对方过后就哭了啊123456789012345678901234567890";
    private static final ErrorCorrectionLevel level = ErrorCorrectionLevel.M;
    private static final String path = "E:/qr3.png";

    public static void main(String[] args) throws Exception {
        EnumMap<EncodeHintType, Object> hints = new EnumMap<>(EncodeHintType.class);
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        QRCode qr = Encoder.encode(content, level);
        BufferedImage image = qrBytes2Image(qr.getMatrix().getArray(), 10);
        saveImage(image, path);
    }

    /**
     * 二维码byte[][]转BufferedImage
     *
     * @param bytes     byte[][](0白 1黑)
     * @param pixelSize 像素尺寸
     * @return BufferedImage
     */
    public static BufferedImage qrBytes2Image(byte[][] bytes, int pixelSize) {
        int length = bytes.length;
        int size = (length + 2) * pixelSize;
        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();
        graphics.setColor(Color.BLACK);
        for (int y = 0; y < length; y++) {
            for (int x = 0; x < length; x++) {
                if (bytes[y][x] == 1) {
                    graphics.fillRect((x + 1) * pixelSize, (y + 1) * pixelSize, pixelSize, pixelSize);
                }
            }
        }
        graphics.dispose();
        return image;
    }

    /**
     * 保存为PNG图片
     *
     * @param image BufferedImage
     * @param path  路径
     */
    public static void saveImage(BufferedImage image, String path) throws IOException {
        ImageIO.write(image, "png", new File(path));
    }

}
