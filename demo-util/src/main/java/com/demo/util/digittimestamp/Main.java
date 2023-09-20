package com.demo.util.digittimestamp;

import cn.z.tool.DigitTimestamp;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;

/**
 * <h1>数字时间戳</h1>
 *
 * <p>
 * createDate 2023/08/30 14:03:15
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Slf4j
public class Main {

    public static void main(String[] args) {

        long timestamp = DigitTimestamp.getTimestamp();
        long digitTimestamp = DigitTimestamp.getDigitTimestamp();

        /* ==================== 数字时间戳-时间戳 ==================== */
        log.warn("数字时间戳-时间戳");
        log.info("getTimestamp():" + new Timestamp(timestamp));
        long timestampStart = DigitTimestamp.getTimestampStart(digitTimestamp);
        log.info("getTimestampStart(digitTimestamp):" + new Timestamp(timestampStart));
        log.info("getTimestampStart(digitTimestamp, 1):" + new Timestamp(DigitTimestamp.getTimestampStart(digitTimestamp, 1)));
        log.info("getTimestampEnd(digitTimestamp):" + new Timestamp(DigitTimestamp.getTimestampEnd(digitTimestamp)));
        log.info("timestampStart+INTERVAL_DAY:" + new Timestamp(timestampStart + DigitTimestamp.INTERVAL_DAY));
        log.info("getTimestampEnd(digitTimestamp, 1):" + new Timestamp(DigitTimestamp.getTimestampEnd(digitTimestamp, 1)));

        /* ==================== 时间戳-数字时间戳 ==================== */
        log.warn("时间戳-数字时间戳");
        log.info("getDigitTimestamp():" + digitTimestamp);
        log.info("getDigitTimestamp(timestamp):" + DigitTimestamp.getDigitTimestamp(timestamp));
        long digitTimestampStart = DigitTimestamp.getDigitTimestampStart();
        log.info("getDigitTimestampStart():" + digitTimestampStart);
        log.info("getDigitTimestampStart(timestamp):" + DigitTimestamp.getDigitTimestampStart(timestamp));
        log.info("getDigitTimestampStart(1):" + DigitTimestamp.getDigitTimestampStart(1));
        log.info("getDigitTimestampStart(timestamp, 1):" + DigitTimestamp.getDigitTimestampStart(timestamp, 1));
        log.info("getDigitTimestampEnd():" + DigitTimestamp.getDigitTimestampEnd());
        log.info("digitTimestampStart+DIGIT_INTERVAL_DAY:" + (digitTimestampStart + DigitTimestamp.DIGIT_INTERVAL_DAY));
        log.info("getDigitTimestampEnd(timestamp):" + DigitTimestamp.getDigitTimestampEnd(timestamp));
        log.info("getDigitTimestampEnd(1):" + DigitTimestamp.getDigitTimestampEnd(1));
        log.info("getDigitTimestampEnd(timestamp, 1):" + DigitTimestamp.getDigitTimestampEnd(timestamp, 1));

        /* ==================== 填充缺损的数字时间戳long型 ==================== */
        log.warn("填充缺损的数字时间戳long型");
        log.info("complement(20220415123918679L):" + DigitTimestamp.complement(20220415123918679L));
        log.info("complement(2022041512391867L):" + DigitTimestamp.complement(2022041512391867L));
        log.info("complement(202204151239186L):" + DigitTimestamp.complement(202204151239186L));
        log.info("complement(20220415123918L):" + DigitTimestamp.complement(20220415123918L));
        log.info("complement(2022041512391L):" + DigitTimestamp.complement(2022041512391L));
        log.info("complement(202204151239L):" + DigitTimestamp.complement(202204151239L));
        log.info("complement(20220415123L):" + DigitTimestamp.complement(20220415123L));
        log.info("complement(2022041512L):" + DigitTimestamp.complement(2022041512L));
        log.info("complement(202204151L):" + DigitTimestamp.complement(202204151L));
        log.info("complement(20220415L):" + DigitTimestamp.complement(20220415L));
        log.info("complement(2022041L):" + DigitTimestamp.complement(2022041L));
        log.info("complement(2022040L):" + DigitTimestamp.complement(2022040L));
        log.info("complement(202204L):" + DigitTimestamp.complement(202204L));
        log.info("complement(20221L):" + DigitTimestamp.complement(20221L));
        log.info("complement(20220L):" + DigitTimestamp.complement(20220L));
        log.info("complement(2022L):" + DigitTimestamp.complement(2022L));

        /* ==================== 填充缺损的数字时间戳String型 ==================== */
        log.warn("填充缺损的数字时间戳String型");
        log.info("complement(\"20220415123918679\"):" + DigitTimestamp.complement("20220415123918679"));
        log.info("complement(\"2022041512391867\"):" + DigitTimestamp.complement("2022041512391867"));
        log.info("complement(\"202204151239186\"):" + DigitTimestamp.complement("202204151239186"));
        log.info("complement(\"20220415123918\"):" + DigitTimestamp.complement("20220415123918"));
        log.info("complement(\"2022041512391\"):" + DigitTimestamp.complement("2022041512391"));
        log.info("complement(\"202204151239\"):" + DigitTimestamp.complement("202204151239"));
        log.info("complement(\"20220415123\"):" + DigitTimestamp.complement("20220415123"));
        log.info("complement(\"2022041512\"):" + DigitTimestamp.complement("2022041512"));
        log.info("complement(\"202204151\"):" + DigitTimestamp.complement("202204151"));
        log.info("complement(\"20220415\"):" + DigitTimestamp.complement("20220415"));
        log.info("complement(\"2022041\"):" + DigitTimestamp.complement("2022041"));
        log.info("complement(\"2022040\"):" + DigitTimestamp.complement("2022040"));
        log.info("complement(\"202204\"):" + DigitTimestamp.complement("202204"));
        log.info("complement(\"20221\"):" + DigitTimestamp.complement("20221"));
        log.info("complement(\"20220\"):" + DigitTimestamp.complement("20220"));
        log.info("complement(\"2022\"):" + DigitTimestamp.complement("2022"));

    }

}
