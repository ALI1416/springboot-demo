package com.demo.hutool.text;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.text.StrBuilder;
import cn.hutool.core.text.StrSplitter;
import cn.hutool.core.text.UnicodeUtil;
import cn.hutool.core.text.csv.*;
import cn.hutool.core.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.List;

/**
 * <h1>文本操作</h1>
 *
 * <p>
 * createDate 2022/03/10 10:12:41
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Slf4j
public class Main {

    public static void main(String[] args) {
        csvUtil();
        strBuilder();
        unicodeUtil();
        strSplitter();
    }

    /**
     * CSV文件处理工具
     */
    private static void csvUtil() {
        log.info("---------- CSV文件处理工具 ----------");
        File file = new File("E:/testWrite.csv");
        /*生成CSV文件*/
        CsvWriter writer = CsvUtil.getWriter(file, CharsetUtil.CHARSET_GBK);
        writer.write(//
                new String[]{"姓名", "gender", "year"},//
                new String[]{"张三", "男", "2000"},//
                new String[]{"Ali", "1", "1998"},//
                new String[]{"3", "", "-1"}//
        );
        /*读取CSV文件*/
        CsvReader reader = CsvUtil.getReader();
        CsvData data = reader.read(file, CharsetUtil.CHARSET_GBK);
        List<CsvRow> rows = data.getRows();
        for (CsvRow row : rows) {
            log.info("row:" + row.getRawList());
        }
        /*读取为Bean列表*/
        List<User> users = reader.read(FileUtil.getReader(file, CharsetUtil.CHARSET_GBK), User.class);
        for (User user : users) {
            log.info("user:" + user);
        }
    }

    /**
     * 可复用字符串生成器
     */
    private static void strBuilder() {
        log.info("---------- 可复用字符串生成器 ----------");
        StrBuilder builder = StrBuilder.create();
        builder.append("Hello, ").append("World !");
        log.info("old:" + builder);
        builder.reset();
        builder.append("Hi!");
        log.info("new:" + builder);
    }

    /**
     * Unicode编码转换工具
     */
    private static void unicodeUtil() {
        log.info("---------- Unicode编码转换工具 ----------");
        /*字符串转Unicode*/
        //第二个参数true表示跳过ASCII字符（只跳过可见字符）
        String s = UnicodeUtil.toUnicode("aaa123中文", true);
        log.info("字符串转Unicode:" + s);
        /*Unicode转字符串*/
        String str = "aaa\\U4e2d\\u6587\\u111\\uzzzz\\u0026";
        // 由于\\u111为非Unicode字符串，因此原样输出
        String res = UnicodeUtil.toString(str);
        log.info("Unicode转字符串:" + res);
    }

    /**
     * 字符串切割
     */
    private static void strSplitter() {
        log.info("---------- 字符串切割 ----------");
        String str = "a, ,abc,   ddf";
        // 参数：被切分字符串，分隔符逗号，0表示无限制分片数，去除两边空格，忽略空白项
        List<String> split = StrSplitter.split(str, ',', 0, true, true);
        log.info("字符串切割:" + split);
    }

}
