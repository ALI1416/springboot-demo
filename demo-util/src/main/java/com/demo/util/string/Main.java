package com.demo.util.string;

import cn.z.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>字符串工具</h1>
 *
 * <p>
 * createDate 2022/04/12 14:39:55
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Slf4j
public class Main {

    public static void main(String[] args) {
        /* ==================== 是否为空 ==================== */
        log.warn("是否为空");
        log.info("isNull:" + StringUtils.isNull(null));
        log.info("isNull:" + StringUtils.isNull(1));
        log.info("existNull:" + StringUtils.existNull(null, 1));
        log.info("existNull:" + StringUtils.existNull(2, 1));
        log.info("isEmpty:" + StringUtils.isEmpty(null));
        log.info("isEmpty:" + StringUtils.isEmpty("1"));
        log.info("existEmpty:" + StringUtils.existEmpty(null, "1"));
        log.info("existEmpty:" + StringUtils.existEmpty("2", "1"));
        log.info("isBlack:" + StringUtils.isBlack(" "));
        log.info("isBlack:" + StringUtils.isBlack(" 1"));
        log.info("existBlack:" + StringUtils.existBlack(" ", "1"));
        log.info("existBlack:" + StringUtils.existBlack(" 2", "1"));

        /* ==================== 其他 ==================== */
        log.warn("其他");
        log.info("getRandom:" + StringUtils.getRandom(StringUtils.NUMBER_ALL_LETTER, 100));
        log.info("getRandomNumber:" + StringUtils.getRandomNumber(10));
        log.info("getMask:" + StringUtils.getMask("1234567890"));
        log.info("getSuffix:" + StringUtils.getSuffix("123456.txt"));
        log.info("getSuffix:" + StringUtils.getSuffix("123456"));
        List<String> stringList = new ArrayList<>();
        stringList.add("1.txt");
        stringList.add("1.txt");
        stringList.add("12.txt");
        stringList.add("12.txt");
        stringList.add("12.txt");
        stringList.add("123.txt");
        stringList.add("1234.txt");
        stringList.add("12345");
        stringList.add("12345");
        stringList.add("12345");
        log.info("duplicateRenameString:" + StringUtils.duplicateRenameString(stringList));
        log.info("duplicateRenameFile:" + StringUtils.duplicateRenameFile(stringList));
    }

}
