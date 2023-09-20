package com.demo.property.my;

import cn.z.spring.tool.Yaml2PropertySourceFactory;
import com.demo.entity.po.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.util.unit.DataSize;

import java.time.Duration;
import java.util.List;
import java.util.Map;

/**
 * <h1>读取my.yml的test2配置</h1>
 *
 * <p>
 * createDate 2020/11/11 11:11:11
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 */
// 加载顺序为从左到右顺序加载，后加载的会覆盖先加载的属性值。
// @PropertySource(value = { "classpath:a.yml", "classpath:b.yml" }, factory = Yaml2PropertySourceFactory.class)
// @PropertySource读取外部配置文件。因为只能读取properties类型，所以需要使用Yaml2PropertySourceFactory将yaml文件转换为properties
@PropertySource(value = {"classpath:config/my.yml"}, factory = Yaml2PropertySourceFactory.class)
// prefix前缀。如果是读取所有配置，可以不写prefix
// ignoreInvalidFields = true忽略类型不匹配或不存在的字段，没有指定默认值的会自动赋值(基本类型：默认值；包装类型：null)
// ignoreUnknownFields = false配置中有不能绑定的字段，会报错(ignoreInvalidFields = true时无效)
@ConfigurationProperties(prefix = "test2", ignoreInvalidFields = true)
@Component
@Getter
@Setter
public class Test2Yml {

    /**
     * 布尔类型(没有指定默认值，为该类型的默认值)
     */
    private boolean booleanType;
    /**
     * 字节类型
     */
    private byte byteType;
    /**
     * 字符类型
     */
    private char charType;
    /**
     * 短整数类型
     */
    private short shortType;
    /**
     * 整数类型(指定默认值)
     */
    private int intType = 8;
    /**
     * 长整数类型
     */
    private long longType;
    /**
     * 单精度浮点类型
     */
    private float floatType;
    /**
     * 双精度浮点类型
     */
    private double doubleType;
    /**
     * 字符串类型
     */
    private String stringType;
    /**
     * 对象类型
     */
    private User objType;
    /**
     * 整数数组类型
     */
    private int[] intArrayType;
    /**
     * 字符串列表类型
     */
    private List<String> stringListType;
    /**
     * 对象列表类型
     */
    private List<User> objListType;
    /**
     * Map类型
     */
    private Map<String,String> mapType;
    /**
     * 持续时间类型
     */
    private Duration durationType;
    /**
     * 储存容量类型
     */
    private DataSize dataSizeType;

}
