package com.demo.constant;

/**
 * <h1>Rabbit队列</h1>
 *
 * <p>
 * createDate 2023/09/19 11:45:04
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class RabbitQueue {

    private RabbitQueue() {
    }

    /**
     * 点对点
     */
    public static final String P2P = "p2p";
    /**
     * 工作
     */
    public static final String WORK = "work";
    /**
     * ProtocolBuffers1
     */
    public static final String PROTOCOL_BUFFERS1 = "protocolBuffers1";
    /**
     * ProtocolBuffers2
     */
    public static final String PROTOCOL_BUFFERS2 = "protocolBuffers2";
    /**
     * 死信
     */
    public static final String DEAD_LETTER = "deadLetter";
    /**
     * 死信测试1
     */
    public static final String DEAD_LETTER_TEST1 = "deadLetterTest1";
    /**
     * 死信测试2
     */
    public static final String DEAD_LETTER_TEST2 = "deadLetterTest2";
    /**
     * 死信测试3
     */
    public static final String DEAD_LETTER_TEST3 = "deadLetterTest3";

}
