package cn.z.redis.annotation;

import cn.z.redis.RedisException;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.MethodIntrospector;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * <h1>Redis注解处理</h1>
 *
 * <p>
 * createDate 2023/10/07 16:34:24
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class RedisAnnotationProcessor implements ApplicationContextAware, SmartInitializingSingleton, DisposableBean {

    /**
     * 日志实例
     */
    private static final Logger log = LoggerFactory.getLogger(RedisAnnotationProcessor.class);

    /**
     * Redis消息监听容器
     */
    private final RedisMessageListenerContainer container;

    /**
     * 构造函数(自动注入)
     *
     * @param factory RedisConnectionFactory
     */
    public RedisAnnotationProcessor(RedisConnectionFactory factory) {
        container = new RedisMessageListenerContainer();
        container.setConnectionFactory(factory);
    }

    /**
     * ApplicationContext
     */
    private ApplicationContext applicationContext;

    /**
     * ApplicationContextAware
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * SmartInitializingSingleton
     */
    @Override
    public void afterSingletonsInstantiated() {
        if (applicationContext == null) {
            throw new RedisException("找不到 ApplicationContext");
        }
        // 所有Bean
        String[] beanNamesForTypeArray = applicationContext.getBeanNamesForType(Object.class, false, true);
        for (String beanNamesForType : beanNamesForTypeArray) {
            // 跳过@Lazy注解
            if (applicationContext.findAnnotationOnBean(beanNamesForType, Lazy.class) == null) {
                Object bean = applicationContext.getBean(beanNamesForType);
                // 含有@Subscribe注解的方法
                Map<Method, Subscribe> annotatedMethodMap = MethodIntrospector.selectMethods(
                        bean.getClass(),
                        (MethodIntrospector.MetadataLookup<Subscribe>) method ->
                                AnnotatedElementUtils.findMergedAnnotation(method, Subscribe.class));
                annotatedMethodMap.forEach((method, subscribe) -> processSubscribe(bean, method, subscribe));
            }
        }
        // 启动
        container.afterPropertiesSet();
        container.start();
    }

    /**
     * DisposableBean
     */
    @Override
    public void destroy() throws Exception {
        container.destroy();
    }

    /**
     * 处理
     *
     * @param bean      Bean
     * @param method    Method
     * @param subscribe Subscribe
     */
    private void processSubscribe(Object bean, Method method, Subscribe subscribe) {
        // 主题
        String topic = subscribe.value();
        // 模式
        Topic mode;
        switch (subscribe.mode()) {
            case MATCH: {
                mode = new PatternTopic(topic);
                break;
            }
            default: {
                mode = new ChannelTopic(topic);
            }
        }
        // 回调
        Parameter[] parameters = method.getParameters();
        int parameterLength = parameters.length;
        if (parameterLength > 2) {
            throw new RedisException("方法 " + method + " 最多有 2 个参数");
        }
        Object[] objectArray = new Object[parameterLength];
        MessageListener callback = (message, pattern) -> {
            try {
                switch (parameterLength) {
                    // 第二个参数：主题
                    case 2: {
                        objectArray[1] = new String(message.getChannel(), StandardCharsets.UTF_8);
                        // 无break
                    }
                    // 第一个参数：数据
                    case 1: {
                        objectArray[0] = JSON.parseObject(message.getBody(), parameters[0].getType(), JSONReader.Feature.SupportAutoType);
                        break;
                    }
                }
                method.invoke(bean, objectArray);
            } catch (Exception e) {
                log.error("方法 " + method + " 调用失败", e);
            }
        };
        // 订阅
        container.addMessageListener(callback, mode);
    }

}
