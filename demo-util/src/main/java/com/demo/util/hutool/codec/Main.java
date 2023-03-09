package com.demo.util.hutool.codec;

import cn.hutool.core.codec.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * <h1>编码</h1>
 *
 * <p>
 * createDate 2022/03/04 10:42:39
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Slf4j
public class Main {

    public static void main(String[] args) {
        /*base32*/
        log.info("---------- base32 ----------");
        String s = "这是一个字符串";
        String base32 = Base32.encode(s);
        log.info("base32:" + base32);
        log.info("base32解码:" + Base32.decodeStr(base32));

        /*base62*/
        String base62 = Base62.encode(s);
        log.info("base62:" + base62);
        log.info("base62解码:" + Base62.decodeStr(base62));
        String base64 = Base64.encode(s);

        /*base64*/
        log.info("---------- base64 ----------");
        log.info("base64:" + base64);
        log.info("base64解码:" + Base64.decodeStr(base64));

        /*摩斯密码*/
        log.info("---------- 摩斯密码 ----------");
        String s2 = "String";
        Morse morseCoder = new Morse();
        String morse = morseCoder.encode(s2);
        log.info("摩斯密码:" + morse);
        log.info("摩斯密码解码:" + morseCoder.decode(morse));

        /*BCD*/
        log.info("---------- BCD ----------");
        String s3 = "123456";
        byte[] bcd = BCD.strToBcd(s3);
        log.info("BCD:" + Arrays.toString(bcd));
        log.info("BCD解码:" + BCD.bcdToStr(bcd));

        /*回转N位密码*/
        log.info("---------- 回转N位密码 ----------");
        String s4 = "1f2e9df6131b480b9fdddc633cf24996";
        String rot13 = Rot.encode13(s4);
        log.info("回转N位密码:" + rot13);
        log.info("回转N位密码解码:" + Rot.decode13(rot13));

        /*PunyCode*/
        log.info("---------- PunyCode ----------");
        String s5 = "字符串String";
        String punyCode = PunyCode.encode(s5);
        log.info("PunyCode:" + punyCode);
        log.info("PunyCode解码:" + PunyCode.decode(punyCode));
    }

}
