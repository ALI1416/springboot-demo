package com.demo.property;

/**
 * <h1>ip2region属性</h1>
 *
 * <p>
 * createDate 2020/11/21 10:27:45
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class Ip2RegionProperty {

    private final static Ip2RegionAppYml IP_2_REGION = Yml.ip2RegionAppYml;

    /**
     * 数据文件在本项目中resources文件夹下的路径
     */
    public final static String RESOURCE_PATH = IP_2_REGION.getResourcePath();
    /**
     * 数据文件引用路径(本机绝对地址)
     */
    public final static String REFERENCE_PATH = IP_2_REGION.getReferencePath();

}
