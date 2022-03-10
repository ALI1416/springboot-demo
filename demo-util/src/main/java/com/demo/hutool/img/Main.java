package com.demo.hutool.img;

import cn.hutool.core.img.Img;
import cn.hutool.core.img.ImgUtil;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import java.awt.*;
import java.io.File;

/**
 * <h1>图片</h1>
 *
 * <p>
 * createDate 2022/03/10 11:34:31
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class Main {

    public static void main(String[] args) {
        try {
            /*图片工具-ImgUtil*/
            File file = new File("E:/创建图片.png");
            // 创建图片
            ImgUtil.createImage("Hello, World !", new Font("Consolas", Font.PLAIN, 100), Color.WHITE,
                    new Color(0x66ccff), new FileImageOutputStream(file));
            // 缩放图片
            ImgUtil.scale(file, new File("E:/缩放图片.png"), 0.5f);
            // 剪裁图片
            ImgUtil.cut(file, new File("E:/剪裁图片.png"), new Rectangle(0, 20, 110, 100));
            // 图像切片
            ImgUtil.slice(file, new File("E:/图像切片/"), 200, 70);
            // 图片类型转换
            ImgUtil.convert(file, new File("E:/图片类型转换.jpg"));
            // 彩色转为黑白
            ImgUtil.gray(file, new File("E:/彩色转为黑白.png"));
            // 添加文字水印
            ImgUtil.pressText(//
                    file, //
                    new File("E:/添加文字水印.png"),//
                    "版权所有", Color.BLACK, //文字
                    new Font("黑体", Font.BOLD, 100), //字体
                    0, //x坐标修正值。 默认在中间，偏移量相对于中间偏移
                    60, //y坐标修正值。 默认在中间，偏移量相对于中间偏移
                    0.8f//透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
            );
            // 添加图片水印
            ImgUtil.pressImage(file, //
                    new File("E:/添加图片水印.png"),//
                    ImgUtil.read(new File("E:/剪裁图片.png")), //水印图片
                    0, //x坐标修正值。 默认在中间，偏移量相对于中间偏移
                    0, //y坐标修正值。 默认在中间，偏移量相对于中间偏移
                    0.8f);
            // 旋转图片
            Image image = ImgUtil.rotate(ImageIO.read(file), 30);
            ImgUtil.write(image, new File("E:/旋转图片.png"));
            // 水平翻转图片
            ImgUtil.flip(file, new File("E:/水平翻转图片.png"));
            /*图片编辑器-Img*/
            Img.from(new File("E:/图片类型转换.jpg"))//
                    .setQuality(0.5)//压缩比率
                    .write(new File("E:/压缩.jpg"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
