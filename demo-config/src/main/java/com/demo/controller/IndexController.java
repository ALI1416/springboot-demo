package com.demo.controller;

import com.demo.entity.pojo.Result;
import com.demo.property.LoggingFileProperty;
import com.demo.property.LoggingFileYml;
import com.demo.property.TestProperty;
import com.demo.property.TestYml;
import com.demo.property.me.Test3Properties;
import com.demo.property.me.Test3Property;
import com.demo.property.my.Test2Property;
import com.demo.property.my.Test2Yml;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

/**
 * <h1>首页</h1>
 *
 * <p>
 * createDate 2021/09/09 10:35:04
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@RestController
@AllArgsConstructor
@Slf4j
public class IndexController {

    private final LoggingFileYml loggingFileYml;
    private final TestYml testYml;
    private final Test2Yml test2Yml;
    private final Test3Properties test3Properties;

    /**
     * http://localhost:8080/loggingFileYml <br>
     * 使用自动装配获取Yml里的值
     */
    @GetMapping("loggingFileYml")
    public Result loggingFileYml() {
        log.info("使用自动装配获取Yml里的值");
        log.info("日志文件名:" + loggingFileYml.getName());
        return Result.o();
    }

    /**
     * http://localhost:8080/testYml <br>
     * 使用自动装配获取Yml里自定义的值
     */
    @GetMapping("testYml")
    public Result testYml() {
        log.info("使用自动装配获取Yml里自定义的值");
        log.info("整数类型:" + testYml.getIntType());
        log.info("字符串类型:" + testYml.getStringType());
        return Result.o();
    }

    /**
     * http://localhost:8080/test2Yml <br>
     * 使用自动装配获取其他Yml里的值
     */
    @GetMapping("test2Yml")
    public Result test2Yml() {
        log.info("使用自动装配获取其他Yml里的值");
        log.info("布尔类型:" + test2Yml.getByteType());
        log.info("字节类型:" + test2Yml.getByteType());
        log.info("字符类型:" + test2Yml.getCharType());
        log.info("短整数类型:" + test2Yml.getShortType());
        log.info("整数类型:" + test2Yml.getIntType());
        log.info("长整数类型:" + test2Yml.getLongType());
        log.info("单精度浮点类型:" + test2Yml.getFloatType());
        log.info("双精度浮点类型:" + test2Yml.getDoubleType());
        log.info("字符串类型:" + test2Yml.getStringType());
        log.info("对象类型:" + test2Yml.getObjType());
        log.info("整数数组类型:" + Arrays.toString(test2Yml.getIntArrayType()));
        log.info("字符串列表类型:" + test2Yml.getStringListType());
        log.info("对象列表类型:" + test2Yml.getObjListType());
        log.info("Map类型:" + test2Yml.getMapType());
        log.info("持续时间类型:" + test2Yml.getDurationType());
        log.info("储存容量类型:" + test2Yml.getDataSizeType());
        return Result.o();
    }

    /**
     * http://localhost:8080/test3Properties <br>
     * 使用自动装配获取Properties里的值
     */
    @GetMapping("test3Properties")
    public Result test3Properties() {
        log.info("使用自动装配获取Properties里的值");
        log.info("整数类型:" + test3Properties.getIntType());
        log.info("字符串类型:" + test3Properties.getStringType());
        return Result.o();
    }

    /**
     * http://localhost:8080/loggingFileProperty <br>
     * 使用静态常量获取Yml里的值
     */
    @GetMapping("loggingFileProperty")
    public Result loggingFileProperty() {
        log.info("使用静态常量获取Yml里的值");
        log.info("日志文件名:" + LoggingFileProperty.NAME);
        return Result.o();
    }

    /**
     * http://localhost:8080/testProperty <br>
     * 使用静态常量获取Yml里自定义的值
     */
    @GetMapping("testProperty")
    public Result testProperty() {
        log.info("使用静态常量获取Yml里自定义的值");
        log.info("整数类型:" + TestProperty.INT_TYPE);
        log.info("字符串类型:" + TestProperty.STRING_TYPE);
        return Result.o();
    }

    /**
     * http://localhost:8080/test2Property <br>
     * 使用静态常量获取其他Yml里的值
     */
    @GetMapping("test2Property")
    public Result test2Property() {
        log.info("使用静态常量获取其他Yml里的值");
        log.info("布尔类型:" + Test2Property.BOOLEAN_TYPE);
        log.info("字节类型:" + Test2Property.BYTE_TYPE);
        log.info("字符类型:" + Test2Property.CHAR_TYPE);
        log.info("短整数类型:" + Test2Property.SHORT_TYPE);
        log.info("整数类型:" + Test2Property.INT_TYPE);
        log.info("长整数类型:" + Test2Property.LONG_TYPE);
        log.info("单精度浮点类型:" + Test2Property.FLOAT_TYPE);
        log.info("双精度浮点类型:" + Test2Property.DOUBLE_TYPE);
        log.info("字符串类型:" + Test2Property.STRING_TYPE);
        log.info("对象类型:" + Test2Property.OBJ_TYPE);
        log.info("整数数组类型:" + Arrays.toString(Test2Property.INT_ARRAY_TYPE));
        log.info("字符串列表类型:" + Test2Property.STRING_LIST_TYPE);
        log.info("对象列表类型:" + Test2Property.OBJ_LIST_TYPE);
        log.info("Map类型:" + Test2Property.MAP_TYPE);
        log.info("持续时间类型:" + Test2Property.DURATION_TYPE);
        log.info("储存容量类型:" + Test2Property.DATA_SIZE_TYPE);
        return Result.o();
    }

    /**
     * http://localhost:8080/test3Property <br>
     * 使用静态常量获取Properties里的值
     */
    @GetMapping("test3Property")
    public Result test3Property() {
        log.info("使用静态常量获取Properties里的值");
        log.info("整数类型:" + Test3Property.INT_TYPE);
        log.info("字符串类型:" + Test3Property.STRING_TYPE);
        return Result.o();
    }

}
