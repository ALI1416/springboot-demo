package com.demo.property;

/**
 * <h1>application.yml的test属性</h1>
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
public class TestProperty {

    /**
     * TestYml
     */
    private static final TestYml TEST = Property.testYml;

    /**
     * 整数类型
     */
    public static final int INT_TYPE = TEST.getIntType();
    /**
     * 字符串类型
     */
    public static final String STRING_TYPE = TEST.getStringType();

}
