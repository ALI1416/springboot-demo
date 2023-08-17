package com.demo.service.mqtt;

import com.demo.tool.MqttTemp;
import org.springframework.integration.annotation.MessagingGateway;

/**
 * <h1>发送接口2</h1>
 *
 * <p>
 * createDate 2022/07/01 14:51:25
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@MessagingGateway(defaultRequestChannel = MqttSend2Service.NAME)
public interface MqttSend2 extends MqttTemp.DefaultSend {

}
