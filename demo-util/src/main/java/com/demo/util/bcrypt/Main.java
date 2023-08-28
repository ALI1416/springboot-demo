package com.demo.util.bcrypt;

import lombok.extern.slf4j.Slf4j;

/**
 * <h1>BCrypt</h1>
 *
 * <p>
 * createDate 2023/08/28 14:16:50
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Slf4j
public class Main {

    public static void main(String[] args) {
        String password = "123456";
        String salt = BCrypt.salt();
        log.info("salt:" + salt);
        String encode = BCrypt.encode(password, salt);
        log.info("encode:" + encode);
        boolean check = BCrypt.check(password, encode);
        log.info("check:" + check);
    }

}
