package com.demo.util.phone;

import lombok.extern.slf4j.Slf4j;

/**
 * <h1>手机号码解析</h1>
 *
 * <p>
 * createDate 2022/02/22 11:14:44
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Slf4j
public class Main {

    public static void main(String[] args) {
        log.info(PhoneUtils.getPhoneInfo("18754710000").toString());
        log.info(PhoneUtils.getPhoneInfo("18807730000").toString());
    }

}
