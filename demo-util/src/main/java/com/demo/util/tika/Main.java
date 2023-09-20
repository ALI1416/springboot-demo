package com.demo.util.tika;

import cn.z.util.TikaUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.mime.MediaTypeRegistry;

import java.util.Arrays;
import java.util.SortedSet;

/**
 * <h1>Tika</h1>
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

    private static final String FILENAME = "E:\\Desktop\\1.txt";

    public static void main(String[] args) throws Exception {
        log.info("---------- 媒体类型 ----------");
        log.info(TikaUtils.mediaType(FILENAME));
        log.info("---------- 文本内容 ----------");
        log.info(TikaUtils.textContent(FILENAME));
        log.info("---------- Metadata ----------");
        Metadata metadata = TikaUtils.metadata(FILENAME);
        for (String name : metadata.names()) {
            log.info(name + ":" + Arrays.toString(metadata.getValues(name)));
        }
        log.info("---------- 编码检测 ----------");
        log.info(TikaUtils.contentEncoding(FILENAME).name());
        log.info("---------- 语言检测 ----------");
        log.info(TikaUtils.language("Constructs a language identifier based on a String of text content"));
        log.info("---------- MIME ----------");
        mime();
    }

    /**
     * MIME
     */
    private static void mime() {
        // MIME
        String mime = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
        MediaType type = MediaType.parse(mime);
        log.info("MIME大类:" + type.getType());
        log.info("MIME子类:" + type.getSubtype());
        log.info("MIME参数:" + type.getParameters());
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

}
