package com.demo.util.timestamp;

import cn.z.util.TimestampUtils;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;

/**
 * <h1>时间戳工具</h1>
 *
 * <p>
 * createDate 2023/08/30 15:06:24
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Slf4j
public class Main {

    public static void main(String[] args) {

        long timestamp = TimestampUtils.getTimestamp();
        String datetime = TimestampUtils.getDatetime();

        /* ==================== 时间戳 ==================== */
        log.info("getTimestamp(datetime):" + new Timestamp(TimestampUtils.getTimestamp(datetime)));
        log.info("getTimestamp(datetime, \"yyyy-MM-dd HH:mm:ss\"):" + new Timestamp(TimestampUtils.getTimestamp(datetime, "yyyy-MM-dd HH:mm:ss")));
        log.info("getTimestamp():" + new Timestamp(timestamp));
        long timestampStart = TimestampUtils.getTimestampStart();
        log.info("getTimestampStart():" + new Timestamp(timestampStart));
        log.info("getTimestampEnd():" + new Timestamp(TimestampUtils.getTimestampEnd()));
        log.info("timestampStart+INTERVAL_DAY:" + new Timestamp(timestampStart + TimestampUtils.INTERVAL_DAY));
        log.info("getTimestampStart(timestamp):" + new Timestamp(TimestampUtils.getTimestampStart(timestamp)));
        log.info("getTimestampEnd(timestamp):" + new Timestamp(TimestampUtils.getTimestampEnd(timestamp)));
        log.info("getTimestampStart(1):" + new Timestamp(TimestampUtils.getTimestampStart(1)));
        log.info("getTimestampEnd(1):" + new Timestamp(TimestampUtils.getTimestampEnd(1)));
        log.info("getTimestampStart(timestamp, 1):" + new Timestamp(TimestampUtils.getTimestampStart(timestamp, 1)));
        log.info("getTimestampEnd(timestamp, 1):" + new Timestamp(TimestampUtils.getTimestampEnd(timestamp, 1)));

        /* ==================== 日期时间字符串 ==================== */
        log.info("getDatetime():" + datetime);
        log.info("getDatetime(timestamp):" + TimestampUtils.getDatetime(timestamp));
        log.info("getDatetime(\"yyyy-MM-dd HH:mm:ss\"):" + TimestampUtils.getDatetime("yyyy-MM-dd HH:mm:ss"));
        log.info("getDatetime(timestamp, \"yyyy-MM-dd HH:mm:ss\"):" + TimestampUtils.getDatetime(timestamp, "yyyy-MM-dd HH:mm:ss"));
        log.info("getDate():" + TimestampUtils.getDate());
        log.info("getDate(timestamp):" + TimestampUtils.getDate(timestamp));
        log.info("getTime():" + TimestampUtils.getTime());
        log.info("getTime(timestamp):" + TimestampUtils.getTime(timestamp));

    }

}
