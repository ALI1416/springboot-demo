package com.demo.util.useragent;

import lombok.extern.slf4j.Slf4j;

/**
 * <h1>UserAgent解析</h1>
 *
 * <p>
 * createDate 2023/08/21 17:02:31
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Slf4j
public class Main {

    public static void main(String[] args) {
        log.info(UserAgent.parse("Mozilla/5.0 (Windows NT 10.0; Win64; x64) " + //
                "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/95.0.4638.69 Safari/537.36").toString());
    }

}
