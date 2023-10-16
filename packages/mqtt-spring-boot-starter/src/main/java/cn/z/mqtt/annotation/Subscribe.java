package cn.z.mqtt.annotation;

import java.lang.annotation.*;

/**
 * <h1>MQTT订阅</h1>
 *
 * <p>
 * 订阅主题：用<code>/</code>划分为多个层次<br>
 * <code>+</code>匹配<code>0</code>个或<code>1</code>个层次<br>
 * <code>#</code>匹配<code>0</code>个或多个层次<br>
 * 例如：<br>
 * <code>only</code>只能匹配<code>only</code><br>
 * <code>one/+</code>可以匹配<code>one/</code>、<code>one/two</code>，但不能匹配<code>one</code>、<code>one/two/</code>、<code>one/two/three</code><br>
 * <code>more/#</code>可以匹配<code>more</code>、<code>more/</code>、<code>more/two</code>
 * </p>
 * <hr>
 *
 * <p>
 * QoS：服务质量<br>
 * <code>0</code>：最多一次<br>
 * <code>1</code>：至少一次<br>
 * <code>2</code>：仅一次
 * </p>
 * <hr>
 *
 * <p>
 * 方法：第<code>1</code>个参数为<code>消息</code>，第<code>2</code>个参数为<code>主题</code>(可选)<br>
 * 例如：<br>
 * <code>
 * &#64;Subscribe("topic")<br>
 * public void subscribe(String msg, String topic) {<br>
 * &ensp;&ensp;&ensp;&ensp;log.info("msg:{},topic:{}", msg, topic);<br>
 * }<br>
 * </code>
 * <code>消息</code>的类型可以为<code>byte[]</code>、<code>String</code>、<code>Void</code>、8种基本类型及包装类型、以及入参为<code>byte[]</code>构造函数的自定义类型<br>
 * <code>主题</code>的类型可以为<code>String</code>、<code>Void</code>、8种基本类型及包装类型、以及入参为<code>String</code>构造函数的自定义类型
 * </p>
 * <hr>
 *
 * <p>
 * 参数使用注解{@link Header#value()}指定接收的数据类型<br>
 * {@link HeaderEnum#ID}、{@link HeaderEnum#QOS}使用<code>int</code>接收<br>
 * {@link HeaderEnum#RETAIN}、{@link HeaderEnum#DUPLICATE}使用<code>boolean</code>接收<br>
 * {@link HeaderEnum#MSG}使用<code>消息</code>类型接收<br>
 * {@link HeaderEnum#TOPIC}使用<code>主题</code>类型接收<br>
 * {@link HeaderEnum#TOPIC_PART}使用<code>主题</code>或<code>主题数组</code>类型接收<br>
 * 例如：<br>
 * <code>
 * &#64;Subscribe("all")<br>
 * public void subscribe(<br>
 * &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#64;Header(HeaderEnum.ID) int id,<br>
 * &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#64;Header(HeaderEnum.QOS) int qos,<br>
 * &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#64;Header(HeaderEnum.RETAIN) boolean retain,<br>
 * &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#64;Header(HeaderEnum.DUPLICATE) boolean duplicate,<br>
 * &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#64;Header(HeaderEnum.TOPIC) String topic,<br>
 * &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#64;Header(HeaderEnum.MSG) String msg<br>
 * ) {<br>
 * &ensp;&ensp;&ensp;&ensp;log.info("id:{},qos:{},retain:{},duplicate:{},topic:{},msg:{}", id, qos, retain, duplicate, topic, msg);<br>
 * }
 * </code>
 * </p>
 * <hr>
 *
 * <p>
 * 参数使用注解{@link Header#index()}指定<code>主题片段</code>匹配位置<br>
 * <code>+</code>不能用数组接收，<code>#</code>只能用数组接收<br>
 * 例如：<br>
 * <code>
 * &#64;Subscribe("part/+/hash/#")<br>
 * public void subscribe(<br>
 * &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;String msg,<br>
 * &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#64;Header(value = HeaderEnum.TOPIC_PART, index = 1) String[] topicPartArray,<br>
 * &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#64;Header(HeaderEnum.TOPIC_PART) int topicPart<br>
 * ) {<br>
 * &ensp;&ensp;&ensp;&ensp;log.info("msg:{},topicPartArray:{},topicPart:{}", msg, topicPartArray, topicPart);<br>
 * }
 * </code>
 * </p>
 *
 * <p>
 * createDate 2023/09/14 18:05:12
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Subscribe {

    /**
     * 主题
     */
    String value();

    /**
     * QoS(默认0)
     */
    int qos() default 0;

}
