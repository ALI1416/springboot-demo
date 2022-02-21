package com.demo.property;

import com.demo.property.me.Test3Properties;
import com.demo.property.my.Test2Yml;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <h1>总配置</h1>
 *
 * <p>配置文件属性值转为静态变量</p>
 *
 * <p>
 * createDate 2022/02/21 15:52:11
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Component
public class Property {

    public static TestYml testYml;
    public static Test2Yml test2Yml;
    public static LoggingFileYml loggingFileYml;
    public static Test3Properties test3Properties;

    @Autowired
    private Property(TestYml testYml, Test2Yml test2Yml, LoggingFileYml loggingFileYml,
                     Test3Properties test3Properties) {
        Property.testYml = testYml;
        Property.test2Yml = test2Yml;
        Property.loggingFileYml = loggingFileYml;
        Property.test3Properties = test3Properties;
    }

}
