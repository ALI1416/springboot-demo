package com.demo.property.my;

import com.demo.entity.po.User;
import com.demo.property.Property;
import org.springframework.util.unit.DataSize;

import java.time.Duration;
import java.util.List;
import java.util.Map;

/**
 * <h1>my.yml的test2属性</h1>
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
public class Test2Property {

    /**
     * TestYml
     */
    private static final Test2Yml TEST2 = Property.test2Yml;

    /**
     * 布尔类型
     */
    public final static boolean BOOLEAN_TYPE = TEST2.isBooleanType();
    /**
     * 字节类型
     */
    public final static byte BYTE_TYPE = TEST2.getByteType();
    /**
     * 字符类型
     */
    public final static char CHAR_TYPE = TEST2.getCharType();
    /**
     * 短整数类型
     */
    public final static short SHORT_TYPE = TEST2.getShortType();
    /**
     * 整数类型
     */
    public final static int INT_TYPE = TEST2.getIntType();
    /**
     * 长整数类型
     */
    public final static long LONG_TYPE = TEST2.getLongType();
    /**
     * 单精度浮点类型
     */
    public final static float FLOAT_TYPE = TEST2.getFloatType();
    /**
     * 双精度浮点类型
     */
    public final static double DOUBLE_TYPE = TEST2.getDoubleType();
    /**
     * 字符串类型
     */
    public final static String STRING_TYPE = TEST2.getStringType();
    /**
     * 对象类型
     */
    public final static User OBJ_TYPE = TEST2.getObjType();
    /**
     * 整数数组类型
     */
    public final static int[] INT_ARRAY_TYPE = TEST2.getIntArrayType();
    /**
     * 字符串列表类型
     */
    public final static List<String> STRING_LIST_TYPE = TEST2.getStringListType();
    /**
     * 对象列表类型
     */
    public final static List<User> OBJ_LIST_TYPE = TEST2.getObjListType();
    /**
     * Map类型
     */
    public final static Map<String, String> MAP_TYPE = TEST2.getMapType();
    /**
     * 持续时间类型
     */
    public final static Duration DURATION_TYPE = TEST2.getDurationType();
    /**
     * 储存容量类型
     */
    public final static DataSize DATA_SIZE_TYPE = TEST2.getDataSizeType();

}
