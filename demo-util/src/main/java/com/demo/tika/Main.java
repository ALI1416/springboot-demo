package com.demo.tika;

import cn.hutool.core.io.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;
import org.apache.tika.langdetect.tika.LanguageIdentifier;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.mime.MediaTypeRegistry;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.BodyContentHandler;

import java.io.BufferedInputStream;
import java.util.Arrays;
import java.util.SortedSet;

/**
 * <h1>tika</h1>
 *
 * <a href="https://svn.apache.org/repos/asf/tika/trunk/tika-example/src/main/java/org/apache/tika/example/">官方示例</a>
 *
 * <p>
 * createDate 2022/03/11 11:29:13
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Slf4j
public class Main {

    private static final BufferedInputStream STREAM = FileUtil.getInputStream("E:/1.docx");

    public static void main(String[] args) {
        try {
            advancedTypeDetector();
            contentHandlerExample();
            language();
            mediaTypeExample();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 类型检测
     */
    private static void advancedTypeDetector() throws Exception {
        Tika tika = new Tika();
        log.info("类型检测:" + tika.detect(STREAM));
    }

    /**
     * 媒体类型
     */
    private static void mediaTypeExample() {
        // 描述
        String describe = "text/plain;charset=UTF-8";
        MediaType describeType = MediaType.parse(describe);
        log.info("描述类型:" + describeType.getType());
        log.info("描述子类:" + describeType.getSubtype());
        log.info("描述参数:" + describeType.getParameters());
        // MIME
        String mime = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
        MediaType type = MediaType.parse(mime);
        MediaTypeRegistry registry = MediaTypeRegistry.getDefaultRegistry();
        while (type != null) {
            log.info("MIME类型:" + type);
            type = registry.getSupertype(type);
        }
        // 别名
        String mp4 = "audio/mp4";
        MediaType type1 = MediaType.parse(mp4);
        SortedSet<MediaType> aliases = registry.getAliases(type1);
        log.info("类型:" + type1 + "别名:" + aliases.toString());
    }

    /**
     * 文本内容
     */
    private static void contentHandlerExample() throws Exception {
        BodyContentHandler handler = new BodyContentHandler();
        AutoDetectParser parser = new AutoDetectParser();
        Metadata metadata = new Metadata();
        parser.parse(STREAM, handler, metadata);
        log.info("文本内容:" + handler);
        log.info("元信息:");
        for (String name : metadata.names()) {
            log.info(name + ":" + Arrays.toString(metadata.getValues(name)));
        }
    }

    /**
     * 语言检测
     */
    private static void language() {
        LanguageIdentifier identifier = //
                new LanguageIdentifier("Constructs a language identifier based on a String of text content");
        log.info(identifier.getLanguage());
    }

}
