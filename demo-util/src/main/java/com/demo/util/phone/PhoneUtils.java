package com.demo.util.phone;

import me.ihxq.projects.pna.PhoneNumberInfo;
import me.ihxq.projects.pna.PhoneNumberLookup;

/**
 * <h1>手机号码工具</h1>
 *
 * <p>
 * createDate 2022/02/22 11:15:57
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class PhoneUtils {

    /**
     * PhoneNumberLookup实例
     */
    private static final PhoneNumberLookup PHONE_NUMBER_LOOKUP = new PhoneNumberLookup();

    /**
     * 获取手机号码信息
     *
     * @param number 手机号码
     * @return 错误：attribution=null, isp=null
     * @see me.ihxq.projects.pna.PhoneNumberLookup#lookup(String phoneNumber)
     */
    public static PhoneNumberInfo getPhoneInfo(String number) {
        return PHONE_NUMBER_LOOKUP.lookup(number).orElseGet(() -> new PhoneNumberInfo(number, null, null));
    }

}
