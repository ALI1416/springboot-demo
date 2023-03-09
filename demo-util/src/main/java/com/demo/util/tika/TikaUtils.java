package com.demo.util.tika;

import org.apache.tika.Tika;
import org.apache.tika.detect.AutoDetectReader;
import org.apache.tika.langdetect.tika.LanguageIdentifier;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.BodyContentHandler;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * <h1>Tika工具</h1>
 *
 * <p>
 * createDate 2022/04/11 17:27:37
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class TikaUtils {

    /**
     * 获取媒体类型
     *
     * @param filepath 文件路径
     * @return 媒体类型
     */
    public static String mediaType(String filepath) {
        try {
            return mediaType(new FileInputStream(filepath));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取媒体类型
     *
     * @param inputStream 输入流
     * @return 媒体类型
     */
    public static String mediaType(InputStream inputStream) {
        Tika tika = new Tika();
        try {
            return tika.detect(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取文本内容
     *
     * @param filepath 文件路径
     * @return 文本内容
     */
    public static String textContent(String filepath) {
        try {
            return textContent(new FileInputStream(filepath));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取文本内容
     *
     * @param inputStream 输入流
     * @return 文本内容
     */
    public static String textContent(InputStream inputStream) {
        Tika tika = new Tika();
        try {
            return tika.parseToString(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取Metadata
     *
     * @param filepath 文件路径
     * @return Metadata
     */
    public static Metadata metadata(String filepath) {
        try {
            return metadata(new FileInputStream(filepath));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取Metadata
     *
     * @param inputStream 输入流
     * @return Metadata
     */
    public static Metadata metadata(InputStream inputStream) {
        AutoDetectParser parser = new AutoDetectParser();
        Metadata metadata = new Metadata();
        try {
            parser.parse(inputStream, new BodyContentHandler(), metadata);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return metadata;
    }

    /**
     * 获取文本编码
     *
     * @param filepath 文件路径
     * @return 编码
     */
    public static Charset contentEncoding(String filepath) {
        try {
            return contentEncoding(new FileInputStream(filepath));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取文本编码
     *
     * @param inputStream 输入流
     * @return 编码
     */
    public static Charset contentEncoding(InputStream inputStream) {
        try {
            return new AutoDetectReader(inputStream).getCharset();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取语言
     *
     * @param content 文本
     * @return 语言
     */
    public static String language(String content) {
        return new LanguageIdentifier(content).getLanguage();
    }

}
