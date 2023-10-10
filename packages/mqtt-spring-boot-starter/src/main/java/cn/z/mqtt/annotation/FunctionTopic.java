package cn.z.mqtt.annotation;

import java.util.function.Function;

/**
 * <h1>主题函数</h1>
 *
 * <p>
 * createDate 2020/11/28 20:25:11
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@FunctionalInterface
public interface FunctionTopic extends Function<String, Object> {

    /**
     * 应用
     *
     * @param topic 主题
     * @return Object
     */
    @Override
    Object apply(String topic);

}
