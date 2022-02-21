package com.demo;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * <h1>AppTest</h1>
 *
 * <p>
 * createDate 2020/11/11 11:11:11
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@SpringBootTest
@Slf4j
class AppTest {

    /**
     * contextLoads
     */
    @Test
    void contextLoads() {
        log.info("contextLoads");
    }

}
