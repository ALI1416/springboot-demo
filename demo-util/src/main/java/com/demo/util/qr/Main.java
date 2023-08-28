package com.demo.util.qr;

import cn.hutool.extra.qrcode.BufferedImageLuminanceSource;
import cn.z.qrcode.encoder.QRCode;
import com.google.zxing.*;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.encoder.Encoder;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;

/**
 * <h1>二维码生成器</h1>
 *
 * <p>
 * createDate 2023/05/09 09:26:59
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Slf4j
public class Main {

    private static final String content = "爱上对方过后就哭了啊123456789012345678901234567890";
    private static final ErrorCorrectionLevel level = ErrorCorrectionLevel.L;
    private static final String path = "E:/qr3.png";
    private static final String path2 = "E:/qr4.png";

    public static void main(String[] args) throws Exception {
        // 生成ZXing二维码
        EnumMap<EncodeHintType, Object> hints = new EnumMap<>(EncodeHintType.class);
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        com.google.zxing.qrcode.encoder.QRCode qr = Encoder.encode(content, level, hints);
        BufferedImage image = qrMatrix2Image(qr.getMatrix().getArray(), 10);
        saveImage(image, path);
        // 识别二维码
        BinaryBitmap binaryBitmap =
                new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(ImageIO.read(new File(path)))));
        Map<DecodeHintType, Object> hints2 = new EnumMap<>(DecodeHintType.class);
        hints2.put(DecodeHintType.CHARACTER_SET, "UTF-8");
        Result result = new MultiFormatReader().decode(binaryBitmap, hints2);
        log.info(result.getText());
        // 生成二维码
        int level = 0;
        int mode = 3;
        int versionNumber = 4;
        QRCode qr2 = new QRCode(content, level, mode, versionNumber);
        log.info("Mode {}", qr2.Mode);
        log.info("VersionNumber {}", qr2.VersionNumber);
        log.info("MaskPatternNumber {}", qr2.MaskPatternNumber);
        BufferedImage image2 = qrMatrix2Image(qr2.Matrix, 10);
        saveImage(image2, path2);
        // 识别二维码
        BinaryBitmap binaryBitmap2 =
                new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(ImageIO.read(new File(path2)))));
        Result result2 = new MultiFormatReader().decode(binaryBitmap2, hints2);
        log.info(result2.getText());
    }

    /**
     * 二维码boolean[][]转BufferedImage
     *
     * @param bytes     boolean[][](false白 true黑)
     * @param pixelSize 像素尺寸
     * @return BufferedImage
     */
    public static BufferedImage qrMatrix2Image(boolean[][] bytes, int pixelSize) {
        int length = bytes.length;
        int size = (length + 2) * pixelSize;
        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();
        graphics.setColor(Color.BLACK);
        for (int x = 0; x < length; x++) {
            for (int y = 0; y < length; y++) {
                if (bytes[x][y]) {
                    graphics.fillRect((x + 1) * pixelSize, (y + 1) * pixelSize, pixelSize, pixelSize);
                }
            }
        }
        graphics.dispose();
        return image;
    }

    /**
     * 二维码byte[][]转BufferedImage
     *
     * @param bytes     byte[][](0白 1黑)
     * @param pixelSize 像素尺寸
     * @return BufferedImage
     */
    public static BufferedImage qrMatrix2Image(byte[][] bytes, int pixelSize) {
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
