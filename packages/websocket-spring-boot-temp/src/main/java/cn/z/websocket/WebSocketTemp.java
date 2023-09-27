package cn.z.websocket;

import org.springframework.messaging.simp.SimpMessagingTemplate;

/**
 * <h1>WebSocket模板</h1>
 *
 * <p>
 * createDate 2023/08/15 15:00:38
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class WebSocketTemp {

    /**
     * WebSocket模板
     */
    private final SimpMessagingTemplate simpMessagingTemplate;

    /**
     * 构造函数(自动注入)
     *
     * @param simpMessagingTemplate SimpMessagingTemplate
     */
    public WebSocketTemp(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    /**
     * 发送
     *
     * @param topic 主题
     * @param data  数据
     */
    public void send(String topic, Object data) {
        simpMessagingTemplate.convertAndSend(topic, data);
    }

    /**
     * 发送
     *
     * @param topic    主题
     * @param username 用户名
     * @param data     数据
     */
    public void send(String topic, String username, Object data) {
        simpMessagingTemplate.convertAndSendToUser(username, topic, data);
    }

}
