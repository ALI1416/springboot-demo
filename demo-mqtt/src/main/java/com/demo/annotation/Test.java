package com.demo.annotation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * <h1>Test</h1>
 *
 * <p>
 * createDate 2023/09/14 18:21:17
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Component
@Slf4j
public class Test {

    @Subscribe("byte")
    public void subscribe(byte[] msg) {
        log.info(Arrays.toString(msg));
    }

    @Subscribe("string")
    public void subscribe(String msg) {
        log.info(msg);
    }

    @Subscribe("int")
    public void subscribe(int msg) {
        log.info(String.valueOf(msg));
    }

    @Subscribe("integer")
    public void subscribe(Integer msg) {
        log.info(String.valueOf(msg));
    }

    @Subscribe("MyClass")
    public void subscribe(MyClass msg) {
        log.info(String.valueOf(msg));
    }

    @Subscribe("topic")
    public void subscribe(String msg, String topic) {
        log.info("msg:{},topic:{}", msg, topic);
    }

    // @Subscribe("all")
    public void subscribe(
            @Header(HeaderEnum.ID) int id,
            @Header(HeaderEnum.QOS) int qos,
            @Header(HeaderEnum.DUPLICATE) int duplicate,
            @Header(HeaderEnum.RETAIN) int retain,
            @Header(HeaderEnum.TOPIC) String topic,
            @Header(HeaderEnum.MSG) String msg
    ) {
        log.info("id:{},qos:{},duplicate:{},retain:{},topic:{},msg:{}", id, qos, duplicate, retain, topic, msg);
    }

    // @Subscribe("part/+/+/+")
    public void subscribe(
            String msg,
            @Header(HeaderEnum.TOPIC_PART) String topicPart0,
            @Header(value = HeaderEnum.TOPIC_PART, index = 1) int topicPart1,
            @Header(value = HeaderEnum.TOPIC_PART, index = 2) Long topicPart2
    ) {
        log.info("msg:{},topicPart0:{},topicPart1:{},topicPart2:{}", msg, topicPart0, topicPart1, topicPart2);
    }

    // @Subscribe("part2/#")
    public void subscribe(
            String msg,
            @Header(HeaderEnum.TOPIC_PART) String[] topicPartArray
    ) {
        log.info("msg:{},topicPartArray:{}", msg, topicPartArray);
    }

    // @Subscribe("part3/#")
    public void subscribe(
            String msg,
            @Header(HeaderEnum.TOPIC_PART) int[] topicPartArray
    ) {
        log.info("msg:{},topicPartArray:{}", msg, topicPartArray);
    }

    // @Subscribe("part4/+/hash/#")
    public void subscribe(
            String msg,
            @Header(HeaderEnum.TOPIC_PART) int topicPart,
            @Header(HeaderEnum.TOPIC_PART) String[] topicPartArray
    ) {
        log.info("msg:{},topicPart:{},topicPartArray:{}", msg, topicPart, topicPartArray);
    }

}
