package com.demo.util.string;

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
        log.info("getRandom:" + StringUtils.getRandom(StringUtils.NUMBER_ALL_LETTER, 100));
        log.info("getMask:" + StringUtils.getMask("123456"));
        log.info("getSuffix:" + StringUtils.getSuffix("123456.txt"));
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
