package com.demo.service.mqtt;

import com.demo.tool.MqttTemp;
import org.springframework.integration.annotation.MessagingGateway;

/**
 * <h1>发送接口</h1>
 *
 * <p>
 * createDate 2022/07/01 14:51:25
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@MessagingGateway(defaultRequestChannel = MqttSendService.NAME)
public interface MqttSend extends MqttTemp.DefaultSend {

}
