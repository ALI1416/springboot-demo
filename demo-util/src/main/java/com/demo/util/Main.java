package com.demo.util;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>Main</h1>
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
        base62Utils();
        stringUtils();
        timestampUtils();
        digitTimestampUtils();
    }

    private static void base62Utils() {
        log.info("---------- base62Utils ----------");
        long n = 1234567890123456789L;
        String encoder = Base62Utils.encoder(n);
        log.info("encoder:" + encoder);
        log.info("decoder:" + Base62Utils.decoder(encoder));
        String encoder2 = Base62Utils.encoder2(n);
        log.info("encoder2:" + encoder2);
        log.info("decoder2:" + Base62Utils.decoder2(encoder));
    }

    private static void stringUtils() {
        log.info("---------- stringUtils ----------");
        log.info("getRandom:" + StringUtils.getRandom(StringUtils.BASE64_ALPHABET, 100));
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

    private static void timestampUtils() {
        log.info("---------- timestampUtils ----------");
        String datetime = TimestampUtils.getDatetime();
        log.info("getDatetime():" + datetime);
        log.info("getTimestamp:" + TimestampUtils.getTimestamp(datetime, TimestampUtils.FORMAT_DATETIME));
        log.info("getTimestamp():" + TimestampUtils.getTimestamp());
        log.info("getTimestampStart():" + TimestampUtils.getTimestampStart());
        log.info("getTimestampStart(1):" + TimestampUtils.getTimestampStart(1));
        log.info("getTimestampEnd():" + TimestampUtils.getTimestampEnd());
        log.info("getTimestampEnd(1):" + TimestampUtils.getTimestampEnd(1));
    }

    private static void digitTimestampUtils() {
        log.info("---------- digitTimestampUtils ----------");
        long digitTimestamp = DigitTimestampUtils.getDigitTimestamp();
        log.info("getDigitTimestamp():" + digitTimestamp);
        log.info("getDigitTimestampStart():" + DigitTimestampUtils.getDigitTimestampStart());
        log.info("getDigitTimestampStart(1):" + DigitTimestampUtils.getDigitTimestampStart(1));
        log.info("getDigitTimestampEnd():" + DigitTimestampUtils.getDigitTimestampEnd());
        log.info("getDigitTimestampEnd(1):" + DigitTimestampUtils.getDigitTimestampEnd(1));
        log.info("getTimestampStart(digitTimestamp):" + DigitTimestampUtils.getTimestampStart(digitTimestamp));
        log.info("getTimestampStart(digitTimestamp,1):" + DigitTimestampUtils.getTimestampStart(digitTimestamp, 1));
        log.info("getTimestampEnd(digitTimestamp):" + DigitTimestampUtils.getTimestampEnd(digitTimestamp));
        log.info("getTimestampEnd(digitTimestamp,1):" + DigitTimestampUtils.getTimestampEnd(digitTimestamp, 1));
        log.info("complement(20220415123918679L):" + DigitTimestampUtils.complement(20220415123918679L));
        log.info("complement(2022041512391867L):" + DigitTimestampUtils.complement(2022041512391867L));
        log.info("complement(202204151239186L):" + DigitTimestampUtils.complement(202204151239186L));
        log.info("complement(20220415123918L):" + DigitTimestampUtils.complement(20220415123918L));
        log.info("complement(2022041512391L):" + DigitTimestampUtils.complement(2022041512391L));
        log.info("complement(202204151239L):" + DigitTimestampUtils.complement(202204151239L));
        log.info("complement(20220415123L):" + DigitTimestampUtils.complement(20220415123L));
        log.info("complement(2022041512L):" + DigitTimestampUtils.complement(2022041512L));
        log.info("complement(202204151L):" + DigitTimestampUtils.complement(202204151L));
        log.info("complement(20220415L):" + DigitTimestampUtils.complement(20220415L));
        log.info("complement(2022041L):" + DigitTimestampUtils.complement(2022041L));
        log.info("complement(2022040L):" + DigitTimestampUtils.complement(2022040L));
        log.info("complement(202204L):" + DigitTimestampUtils.complement(202204L));
        log.info("complement(20221L):" + DigitTimestampUtils.complement(20221L));
        log.info("complement(20220L):" + DigitTimestampUtils.complement(20220L));
        log.info("complement(2022L):" + DigitTimestampUtils.complement(2022L));
        log.info("complement(\"20220415123918679\"):" + DigitTimestampUtils.complement("20220415123918679"));
        log.info("complement(\"2022041512391867\"):" + DigitTimestampUtils.complement("2022041512391867"));
        log.info("complement(\"202204151239186\"):" + DigitTimestampUtils.complement("202204151239186"));
        log.info("complement(\"20220415123918\"):" + DigitTimestampUtils.complement("20220415123918"));
        log.info("complement(\"2022041512391\"):" + DigitTimestampUtils.complement("2022041512391"));
        log.info("complement(\"202204151239\"):" + DigitTimestampUtils.complement("202204151239"));
        log.info("complement(\"20220415123\"):" + DigitTimestampUtils.complement("20220415123"));
        log.info("complement(\"2022041512\"):" + DigitTimestampUtils.complement("2022041512"));
        log.info("complement(\"202204151\"):" + DigitTimestampUtils.complement("202204151"));
        log.info("complement(\"20220415\"):" + DigitTimestampUtils.complement("20220415"));
        log.info("complement(\"2022041\"):" + DigitTimestampUtils.complement("2022041"));
        log.info("complement(\"2022040\"):" + DigitTimestampUtils.complement("2022040"));
        log.info("complement(\"202204\"):" + DigitTimestampUtils.complement("202204"));
        log.info("complement(\"20221\"):" + DigitTimestampUtils.complement("20221"));
        log.info("complement(\"20220\"):" + DigitTimestampUtils.complement("20220"));
        log.info("complement(\"2022\"):" + DigitTimestampUtils.complement("2022"));
    }

}
