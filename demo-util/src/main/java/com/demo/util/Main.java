package com.demo.util;

import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
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
        // stringUtils();
        // timestampUtils();
        // digitTimestampUtils();
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
        // String datetime = TimestampUtils.getDatetime();
        // log.info("yyyy-MM-dd HH:mm:ss格式当前时间:" + datetime);
        // log.info("默认yyyy-MM-dd HH:mm:ss格式时间转时间戳:" + new Timestamp(TimestampUtils.getTimestamp(datetime)));
        String date = TimestampUtils.getDate();
        log.info("yyyy-MM-dd格式当前时间:" + date);
        log.info("指定yyyy-MM-dd格式时间转时间戳:" //
                + new Timestamp(TimestampUtils.getTimestamp(date, TimestampUtils.FORMAT_DATE)));
        long timestamp = TimestampUtils.getTimestamp();
        log.info("当前时间戳:" + new Timestamp(timestamp));
        log.info("当前时间戳起始:" + new Timestamp(TimestampUtils.getTimestampStart()));
        log.info("指定时间戳起始:" + new Timestamp(TimestampUtils.getTimestampStart(timestamp)));
        log.info("当前时间戳+1日起始:" + new Timestamp(TimestampUtils.getTimestampStart(1)));
        log.info("指定时间戳+1日起始:" + new Timestamp(TimestampUtils.getTimestampStart(timestamp, 1)));
        log.info("当前时间戳结束:" + new Timestamp(TimestampUtils.getTimestampEnd()));
        log.info("指定时间戳结束:" + new Timestamp(TimestampUtils.getTimestampEnd(timestamp)));
        log.info("当前时间戳+1日结束:" + new Timestamp(TimestampUtils.getTimestampEnd(1)));
        log.info("指定时间戳+1日结束:" + new Timestamp(TimestampUtils.getTimestampEnd(timestamp, 1)));
        log.info("getDatetime(timestamp, \"yyyy年MM月dd日 HH时mm分ss秒SSS毫秒\"):" //
                + TimestampUtils.getDatetime(timestamp, "yyyy年MM月dd日 HH时mm分ss秒SSS毫秒"));
        log.info("getDatetime(\"yyyy年MM月dd日 HH时mm分ss秒SSS毫秒\"):" //
                + TimestampUtils.getDatetime("yyyy年MM月dd日 HH时mm分ss秒SSS毫秒"));
        log.info("getDatetime(timestamp):" + TimestampUtils.getDatetime(timestamp));
        log.info("getDatetime():" + TimestampUtils.getDatetime());
        log.info("getDate(timestamp):" + TimestampUtils.getDate(timestamp));
        log.info("getDate():" + TimestampUtils.getDate());
        log.info("getTime(timestamp):" + TimestampUtils.getTime(timestamp));
        log.info("getTime():" + TimestampUtils.getTime());
        log.info("当前时间:" + new Timestamp(timestamp));
        log.info("年开始:" //
                + new Timestamp(TimestampUtils.getTimestamp(timestamp, Calendar.MONTH, true, -1, 0)));
        log.info("月结束:" //
                + new Timestamp(TimestampUtils.getTimestamp(timestamp, Calendar.DAY_OF_YEAR, false, -1, 0)));
        log.info("日开始:" //
                + new Timestamp(TimestampUtils.getTimestamp(timestamp, Calendar.HOUR_OF_DAY, true, -1, 0)));
        log.info("时结束:" //
                + new Timestamp(TimestampUtils.getTimestamp(timestamp, Calendar.MINUTE, false, -1, 0)));
        log.info("分开始:" //
                + new Timestamp(TimestampUtils.getTimestamp(timestamp, Calendar.SECOND, true, -1, 0)));
        log.info("秒结束:" //
                + new Timestamp(TimestampUtils.getTimestamp(timestamp, Calendar.MILLISECOND, false, -1, 0)));
        log.info("一年前:" //
                + new Timestamp(TimestampUtils.getTimestamp(timestamp, -1, true, Calendar.YEAR, -1)));
        log.info("三月后:" //
                + new Timestamp(TimestampUtils.getTimestamp(timestamp, -1, true, Calendar.MONTH, 3)));
        log.info("两周后:" //
                + new Timestamp(TimestampUtils.getTimestamp(timestamp, -1, true, Calendar.WEEK_OF_YEAR, 2)));
        log.info("三日前:" //
                + new Timestamp(TimestampUtils.getTimestamp(timestamp, -1, false, Calendar.DAY_OF_YEAR, -3)));
        log.info("一小时前:" //
                + new Timestamp(TimestampUtils.getTimestamp(timestamp, -1, true, Calendar.HOUR_OF_DAY, -1)));
        log.info("十分钟后:" //
                + new Timestamp(TimestampUtils.getTimestamp(timestamp, -1, true, Calendar.MINUTE, 10)));
        log.info("五秒前:" //
                + new Timestamp(TimestampUtils.getTimestamp(timestamp, -1, true, Calendar.SECOND, -5)));
        log.info("两毫秒后:" //
                + new Timestamp(TimestampUtils.getTimestamp(timestamp, -1, true, Calendar.MILLISECOND, 2)));
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
        log.info("getTimestampStart(digitTimestamp, 1):" + DigitTimestampUtils.getTimestampStart(digitTimestamp, 1));
        log.info("getTimestampEnd(digitTimestamp):" + DigitTimestampUtils.getTimestampEnd(digitTimestamp));
        log.info("getTimestampEnd(digitTimestamp, 1):" + DigitTimestampUtils.getTimestampEnd(digitTimestamp, 1));
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
