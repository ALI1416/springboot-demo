package com.demo.property.me;

import com.demo.property.Property;

/**
 * <h1>me.properties的test3属性</h1>
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
public class Test3Property {

    /**
     * Test3Properties
     */
    private static final Test3Properties TEST3 = Property.test3Properties;

    /**
     * 整数类型
     */
    public static final int INT_TYPE = TEST3.getIntType();
    /**
     * 字符串类型
     */
    public static final String STRING_TYPE = TEST3.getStringType();

}
