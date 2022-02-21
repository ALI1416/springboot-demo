package com.demo.property;

/**
 * <h1>application.yml的logging.file属性</h1>
 *
 * <p>配置文件属性值转为静态常量</p>
 *
 * <p>
 * createDate 2022/02/21 15:56:42
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class LoggingFileProperty {

    /**
     * TestYml
     */
    private static final LoggingFileYml LOGGING_FILE = Property.loggingFileYml;

    /**
     * 日志文件名
     */
    public static final String NAME = LOGGING_FILE.getName();

}
