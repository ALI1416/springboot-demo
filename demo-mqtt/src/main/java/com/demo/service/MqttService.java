package com.demo.service;

import cn.z.mqtt.annotation.Header;
import cn.z.mqtt.annotation.HeaderEnum;
import cn.z.mqtt.annotation.Subscribe;
import com.demo.entity.po.MyClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * <h1>MQTT服务</h1>
 *
 * <p>
 * createDate 2023/09/14 18:21:17
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Service
@Slf4j
public class MqttService {

    /**
     * topic:noParameter<br>
     * log:noParameter
     */
    @Subscribe("noParameter")
    public void subscribe() {
        log.info("noParameter");
    }

    /**
     * topic:byte<br>
     * msg:Hex 01 23 45 67 89 AB CD EF<br>
     * log:[1, 35, 69, 103, -119, -85, -51, -17]
     */
    @Subscribe("byte")
    public void subscribe(byte[] msg) {
        log.info(Arrays.toString(msg));
    }

    /**
     * topic:string<br>
     * msg:嘿嘿😀2333<br>
     * log:嘿嘿😀2333
     */
    @Subscribe("string")
    public void subscribe(String msg) {
        log.info(msg);
    }

    /**
     * topic:int<br>
     * msg:123<br>
     * log:123
     * <hr>
     * topic:int<br>
     * msg:asd<br>
     * log:报错
     */
    @Subscribe("int")
    public void subscribe(int msg) {
        log.info(String.valueOf(msg));
    }

    /**
     * topic:Double<br>
     * msg:123.45<br>
     * log:123.45
     * <hr>
     * topic:Double<br>
     * msg:<br>
     * log:null
     */
    @Subscribe("Double")
    public void subscribe(Double msg) {
        log.info(String.valueOf(msg));
    }

    /**
     * topic:MyClass<br>
     * msg:{"string":"asd"}<br>
     * log:MyClass{string='asd'}
     */
    @Subscribe("MyClass")
    public void subscribe(MyClass msg) {
        log.info(String.valueOf(msg));
    }

    /**
     * topic:topic<br>
     * msg:123哈哈<br>
     * log:msg:123哈哈,topic:topic
     */
    @Subscribe("topic")
    public void subscribe(String msg, String topic) {
        log.info("msg:{},topic:{}", msg, topic);
    }

    /**
     * topic:all<br>
     * msg:123哈哈<br>
     * log:id:0,qos:0,retain:false,duplicate:false,topic:all,msg:123哈哈
     */
    @Subscribe("all")
    public void subscribe(
            @Header(HeaderEnum.ID) int id,
            @Header(HeaderEnum.QOS) int qos,
            @Header(HeaderEnum.RETAIN) boolean retain,
            @Header(HeaderEnum.DUPLICATE) boolean duplicate,
            @Header(HeaderEnum.TOPIC) String topic,
            @Header(HeaderEnum.MSG) String msg
    ) {
        log.info("id:{},qos:{},retain:{},duplicate:{},topic:{},msg:{}", id, qos, retain, duplicate, topic, msg);
    }

    /**
     * topic:part/abc/1/true/-23/a<br>
     * msg:123哈哈<br>
     * log:msg:123哈哈,topicPart0:abc,topicPart1:1,topicPart2:true,topicPart3:-23,topicPart4:a
     */
    @Subscribe("part/+/+/+/+/+")
    public void subscribe(
            String msg,
            @Header(HeaderEnum.TOPIC_PART) String topicPart0,
            @Header(value = HeaderEnum.TOPIC_PART, index = 1) int topicPart1,
            @Header(value = HeaderEnum.TOPIC_PART, index = 2) Boolean topicPart2,
            @Header(value = HeaderEnum.TOPIC_PART, index = 3) byte topicPart3,
            @Header(value = HeaderEnum.TOPIC_PART, index = 4) Character topicPart4
    ) {
        log.info("msg:{},topicPart0:{},topicPart1:{},topicPart2:{},topicPart3:{},topicPart4:{}", msg, topicPart0, topicPart1, topicPart2, topicPart3, topicPart4);
    }

    /**
     * topic:part2<br>
     * msg:123哈哈<br>
     * log:msg:123哈哈,topicPartArray:[]
     * <hr>
     * topic:part2/a/b/c/<br>
     * msg:123哈哈<br>
     * log:msg:123哈哈,topicPartArray:[a, b, c, ]
     */
    @Subscribe("part2/#")
    public void subscribe(
            String msg,
            @Header(HeaderEnum.TOPIC_PART) String[] topicPartArray
    ) {
        log.info("msg:{},topicPartArray:{}", msg, topicPartArray);
    }

    /**
     * topic:part3/12/34/56<br>
     * msg:123哈哈<br>
     * log:msg:123哈哈,topicPartArray:[12, 34, 56]
     */
    @Subscribe("part3/#")
    public void subscribe(
            String msg,
            @Header(HeaderEnum.TOPIC_PART) int[] topicPartArray
    ) {
        log.info("msg:{},topicPartArray:{}", msg, topicPartArray);
    }

    /**
     * topic:part4/12/hash/aa/bb/cc<br>
     * msg:123哈哈<br>
     * log:msg:123哈哈,topicPartArray:[aa, bb, cc],topicPart:12
     */
    @Subscribe("part4/+/hash/#")
    public void subscribe(
            String msg,
            @Header(value = HeaderEnum.TOPIC_PART, index = 1) String[] topicPartArray,
            @Header(HeaderEnum.TOPIC_PART) int topicPart
    ) {
        log.info("msg:{},topicPartArray:{},topicPart:{}", msg, topicPartArray, topicPart);
    }

    /**
     * topic:part5/{"string":"asd"}<br>
     * msg:123哈哈<br>
     * log:msg:123哈哈,topicPart:MyClass{string='asd'}
     */
    @Subscribe("part5/+")
    public void subscribe(
            String msg,
            @Header(HeaderEnum.TOPIC_PART) MyClass topicPart
    ) {
        log.info("msg:{},topicPart:{}", msg, topicPart);
    }

    /**
     * 第三个及以后参数都必须带注解
     */
    // @Subscribe("")
    public void error(String msg, String topic, int c) {
    }

    /**
     * 使用了注解，后面参数必须带注解
     */
    // @Subscribe("")
    public void error(@Header String msg, String topic) {
    }

    /**
     * 主题片段匹配位置的值超出最大值
     */
    // @Subscribe("error/+")
    public void error(@Header(value = HeaderEnum.TOPIC_PART, index = 1) int topic) {
    }

    /**
     * 不能是数组类型
     */
    // @Subscribe("error/+/hash/#")
    public void error(@Header(HeaderEnum.TOPIC_PART) String[] topic) {
    }

    /**
     * 必须是数组类型
     */
    // @Subscribe("error/+/hash/#")
    public void error(@Header(value = HeaderEnum.TOPIC_PART, index = 1) String topic) {
    }

}
