package cn.z.redis.annotation;

import java.lang.annotation.*;

/**
 * <h1>Redis订阅</h1>
 *
 * <p>
 * 订阅主题：<br>
 * 直接模式{@link ModeEnum#DIRECT}：<code>发送主题</code>和<code>接收主题</code>完全一致<br>
 * 匹配模式{@link ModeEnum#MATCH}：<code>接收主题</code>通配符：<br>
 * <code>?</code>：<code>1<code>个字符<br>
 * <code>*</code>：<code>0+<code>个字符<br>
 * </p>
 *
 * <hr>
 *
 * <p>
 * 方法：第<code>1</code>个参数为<code>消息</code>，第<code>2</code>个参数为<code>主题</code>(可选)<br>
 * 例如1：<br>
 * <code>
 * &#64;Subscribe("direct")<br>
 * public void direct(String msg) {<br>
 * &ensp;&ensp;&ensp;&ensp;log.info(msg);<br>
 * }<br>
 * </code>
 * 例如2：<br>
 * <code>
 * &#64;Subscribe(value = "match*", mode = ModeEnum.MATCH)<br>
 * public void match(User user, String topic) {<br>
 * &ensp;&ensp;&ensp;&ensp;log.info("user {} topic {}", user, topic);<br>
 * }<br>
 * </code>
 * </p>
 *
 * <p>
 * createDate 2023/10/07 16:26:50
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
     * 模式(默认{@link ModeEnum#DIRECT})
     */
    ModeEnum mode() default ModeEnum.DIRECT;

}
