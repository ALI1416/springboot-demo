package cn.z.util;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

/**
 * <h1>时间戳工具</h1>
 *
 * <h2>DateTimeFormatter格式化代码</h2>
 * <table>
 * <tr>
 * <th>标记
 * <th>解释
 * <tr>
 * <td>yyyy / yy
 * <td>年 4位 / 2位
 * <tr>
 * <td>MM / M
 * <td>月 2位 / 1位
 * <tr>
 * <td>dd / d
 * <td>日 2位 / 1位
 * <tr>
 * <td>KK / K
 * <td>12小时制(0-11) 2位 / 1位
 * <tr>
 * <td>hh / h
 * <td>12小时制(1-12) 2位 / 1位
 * <tr>
 * <td>HH / H
 * <td>24小时制(0-23) 2位 / 1位
 * <tr>
 * <td>kk / k
 * <td>24小时制(1-24) 2位 / 1位
 * <tr>
 * <td>mm / m
 * <td>分 2位 / 1位
 * <tr>
 * <td>ss / s
 * <td>秒 2位 / 1位
 * <tr>
 * <td>SSS / SS / S
 * <td>毫秒 3位 / 2位 / 1位
 * <tr>
 * <td>n(最多19位)
 * <td>纳秒
 * <tr>
 * <td>e
 * <td>周几(1周日 2周一 ... 7周六)
 * <tr>
 * <td>QQ / Q
 * <td>当年第几季度 2位 / 1位
 * <tr>
 * <td>W
 * <td>当年第几周
 * <tr>
 * <td>DDD / DD / D
 * <td>当年第几天 3位 / 2位 / 1位
 * <tr>
 * <td>w
 * <td>当月第几周
 * <tr>
 * <td>F
 * <td>当月当周第几天
 * <tr>
 * <td>A(最多19位)
 * <td>当天第几毫秒
 * <tr>
 * <td>N(最多19位)
 * <td>当天第几纳秒
 * <tr>
 * <td>a
 * <td>上午[0-12) / 下午[12-24)
 * <tr>
 * <td>B
 * <td>午夜[0:00] / 凌晨(0-5) / 清晨[5-8) / 上午[8,12) / 中午[12,13) / 下午[13,17) / 晚上[17,23)
 * <tr>
 * <td>'
 * <td>转义字符
 * <tr>
 * <td>''
 * <td>单引号
 * </table>
 *
 * <h2>Calendar常量代码</h2>
 * <table>
 * <tr>
 * <th>字段
 * <th>解释
 * <tr>
 * <td>ERA
 * <td>世纪
 * <tr>
 * <td>YEAR
 * <td>年
 * <tr>
 * <td>MONTH
 * <td>月(0一月 1二月 ... 11十二月)
 * <tr>
 * <td>DATE / DAY_OF_MONTH
 * <td>日
 * <tr>
 * <td>AM_PM
 * <td>上午/下午
 * <tr>
 * <td>HOUR
 * <td>12小时制
 * <tr>
 * <td>HOUR_OF_DAY
 * <td>24小时制
 * <tr>
 * <td>MINUTE
 * <td>分钟
 * <tr>
 * <td>SECOND
 * <td>秒
 * <tr>
 * <td>MILLISECOND
 * <td>毫秒
 * <tr>
 * <td>DAY_OF_WEEK
 * <td>周几(1周日 2周一 ... 7周六)
 * <tr>
 * <td>WEEK_OF_YEAR
 * <td>当年第几周
 * <tr>
 * <td>DAY_OF_YEAR
 * <td>当年第几天
 * <tr>
 * <td>DAY_OF_WEEK_IN_MONTH
 * <td>当月当周第几天
 * <tr>
 * <td>WEEK_OF_MONTH
 * <td>当月第几周
 * <tr>
 * <td>ZONE_OFFSET
 * <td>时区
 * <tr>
 * <td>DST_OFFSET
 * <td>夏令时
 * </table>
 *
 * <p>
 * createDate 2020/11/11 11:11:11
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 */
public class TimestampUtils {

    /* ==================== 常量 ==================== */
    // region 常量

    /**
     * 1秒={@value}毫秒
     */
    public static final int MILLS_OF_SECOND = 1000;
    /**
     * 1分钟={@value}秒
     */
    public static final int SECOND_OF_MINUTE = 60;
    /**
     * 1小时={@value}分钟
     */
    public static final int MINUTE_OF_HOUR = 60;
    /**
     * 1天={@value}小时
     */
    public static final int HOUR_OF_DAY = 24;
    /**
     * 1月={@value}天
     */
    public static final int DAY_OF_MONTH = 30;
    /**
     * 1年={@value}天
     */
    public static final int DAY_OF_YEAR = 365;
    /**
     * 1年={@value}月
     */
    public static final int MONTH_OF_YEAR = 12;
    /**
     * 1分钟={@value}毫秒
     */
    public static final int MILLS_OF_MINUTE = MILLS_OF_SECOND * SECOND_OF_MINUTE;
    /**
     * 1小时={@value}毫秒
     */
    public static final int MILLS_OF_HOUR = MILLS_OF_MINUTE * MINUTE_OF_HOUR;
    /**
     * 1天={@value}毫秒
     */
    public static final int MILLS_OF_DAY = MILLS_OF_HOUR * HOUR_OF_DAY;
    /**
     * 1月={@value DAY_OF_MONTH}天={@value}毫秒
     */
    public static final long MILLS_OF_MONTH = (long) MILLS_OF_DAY * DAY_OF_MONTH;
    /**
     * 1年={@value DAY_OF_YEAR}天={@value}毫秒
     */
    public static final long MILLS_OF_YEAR = (long) MILLS_OF_DAY * DAY_OF_YEAR;
    /**
     * 1天的间隔={@value}毫秒
     */
    public static final long INTERVAL_DAY = MILLS_OF_DAY - 1L;
    /**
     * 日期时间格式字符串{@value}
     */
    public static final String FORMAT_DATETIME = "yyyy-MM-dd HH:mm:ss";
    /**
     * 日期格式字符串{@value}
     */
    public static final String FORMAT_DATE = "yyyy-MM-dd";
    /**
     * 时间格式字符串{@value}
     */
    public static final String FORMAT_TIME = "HH:mm:ss";
    /**
     * 日期时间格式{@value FORMAT_DATETIME}
     */
    public static final DateTimeFormatter DATETIME_FORMAT = DateTimeFormatter.ofPattern(FORMAT_DATETIME);
    /**
     * 日期格式{@value FORMAT_DATE}
     */
    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern(FORMAT_DATE);
    /**
     * 时间格式{@value FORMAT_TIME}
     */
    public static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern(FORMAT_TIME);

    // endregion

    private TimestampUtils() {
    }

    /* ==================== 时间戳 ==================== */
    // region 时间戳

    /**
     * 获取指定日期时间字符串的时间戳
     *
     * @param datetime 日期时间字符串(日期时间格式{@value FORMAT_DATETIME})
     * @return 时间戳
     */
    public static long getTimestamp(String datetime) {
        return getTimestamp(datetime, DATETIME_FORMAT);
    }

    /**
     * 获取指定日期时间字符串的时间戳
     *
     * @param datetime 日期时间字符串
     * @param format   日期时间格式字符串
     * @return 时间戳
     */
    public static long getTimestamp(String datetime, String format) {
        return getTimestamp(datetime, DateTimeFormatter.ofPattern(format));
    }

    /**
     * 获取指定日期时间字符串的时间戳<br>
     * 没有时间：时间默认为0时0分0秒0毫秒<br>
     * 没有日期：日期默认为1970年1月1日<br>
     * 错误格式：返回0
     *
     * @param datetime 日期时间字符串
     * @param format   日期时间格式
     * @return 时间戳
     */
    public static long getTimestamp(String datetime, DateTimeFormatter format) {
        try {
            return LocalDateTime.parse(datetime, format).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        } catch (Exception ignore) {
            try {
                return LocalDate.parse(datetime, format).toEpochDay() * MILLS_OF_DAY;
            } catch (Exception ignore2) {
                try {
                    return LocalTime.parse(datetime, format).toNanoOfDay() / 1000000;
                } catch (Exception ignore3) {
                    return 0;
                }
            }
        }
    }

    /**
     * 获取当前时间戳<br>
     * 请使用{@link System#currentTimeMillis()}
     *
     * @return 时间戳
     */
    @Deprecated
    public static long getTimestamp() {
        return System.currentTimeMillis();
    }

    /**
     * 获取当前时间戳0时0分0秒0毫秒的时间戳
     *
     * @return 时间戳
     */
    public static long getTimestampStart() {
        return getTimestamp(-1, Calendar.HOUR_OF_DAY, true, -1, 0);
    }

    /**
     * 获取指定时间戳0时0分0秒0毫秒的时间戳
     *
     * @param timestamp 指定时间戳
     * @return 时间戳
     */
    public static long getTimestampStart(long timestamp) {
        return getTimestamp(timestamp, Calendar.HOUR_OF_DAY, false, -1, 0);
    }

    /**
     * 获取当前时间戳+偏移日0时0分0秒0毫秒的时间戳
     *
     * @param dayOffset 相对于当前时间戳的偏移日
     * @return 时间戳
     */
    public static long getTimestampStart(int dayOffset) {
        return getTimestamp(-1, Calendar.HOUR_OF_DAY, true, Calendar.DAY_OF_YEAR, dayOffset);
    }

    /**
     * 获取指定时间戳+偏移日0时0分0秒0毫秒的时间戳
     *
     * @param timestamp 指定时间戳
     * @param dayOffset 相对于指定时间戳的偏移日
     * @return 时间戳
     */
    public static long getTimestampStart(long timestamp, int dayOffset) {
        return getTimestamp(timestamp, Calendar.HOUR_OF_DAY, false, Calendar.DAY_OF_YEAR, dayOffset);
    }

    /**
     * 获取当前时间戳23时59分59秒999毫秒的时间戳<br>
     * 如果已经调用过{@link #getTimestampStart()}，请保存变量并+{@link #INTERVAL_DAY}，这样速度更快
     *
     * @return 时间戳
     */
    public static long getTimestampEnd() {
        return getTimestamp(-1, Calendar.HOUR_OF_DAY, false, -1, 0);
    }

    /**
     * 获取指定时间戳23时59分59秒999毫秒的时间戳<br>
     * 如果已经调用过{@link #getTimestampStart(long)}，请保存变量并+{@link #INTERVAL_DAY}，这样速度更快
     *
     * @param timestamp 指定时间戳
     * @return 时间戳
     */
    public static long getTimestampEnd(long timestamp) {
        return getTimestamp(timestamp, Calendar.HOUR_OF_DAY, false, -1, 0);
    }

    /**
     * 获取当前时间戳+偏移日23时59分59秒999毫秒的时间戳<br>
     * 如果已经调用过{@link #getTimestampStart(int)}，请保存变量并+{@link #INTERVAL_DAY}，这样速度更快
     *
     * @param dayOffset 相对于当前时间戳的偏移日
     * @return 时间戳
     */
    public static long getTimestampEnd(int dayOffset) {
        return getTimestamp(-1, Calendar.HOUR_OF_DAY, false, Calendar.DAY_OF_YEAR, dayOffset);
    }

    /**
     * 获取指定时间戳+偏移日23时59分59秒999毫秒的时间戳<br>
     * 如果已经调用过{@link #getTimestampStart(long, int)}，请保存变量并+{@link #INTERVAL_DAY}，这样速度更快
     *
     * @param timestamp 指定时间戳
     * @param dayOffset 相对于指定时间戳的偏移日
     * @return 时间戳
     */
    public static long getTimestampEnd(long timestamp, int dayOffset) {
        return getTimestamp(timestamp, Calendar.HOUR_OF_DAY, false, Calendar.DAY_OF_YEAR, dayOffset);
    }

    /**
     * <h2>获取(当前/指定)时间戳(指定填充字段、填充模式)(指定偏移字段、偏移大小)的时间戳</h2>
     *
     * @param timestamp    指定时间戳(-1为当前时间)
     * @param fillField    填充字段(下级字段也会被填充)<br>
     *                     不启用 -1<br>
     *                     月 Calendar.MONTH<br>
     *                     日 Calendar.DAY_OF_YEAR<br>
     *                     时 Calendar.HOUR_OF_DAY<br>
     *                     分 Calendar.MINUTE<br>
     *                     秒 Calendar.SECOND<br>
     *                     毫秒 Calendar.MILLISECOND<br>
     * @param fillMode     填充模式<br>
     *                     true:最小值:1月、1日、0时、0分、0秒、0毫秒<br>
     *                     false:最大值:12月、28/29/30/31日(当前月份最大日期)、23时、59分、59秒、999毫秒
     * @param offsetField  偏移字段(offsetAmount为0时，此参数任意)
     * @param offsetAmount 偏移大小(0为不偏移)
     * @return 时间戳
     */
    public static long getTimestamp(long timestamp, int fillField, boolean fillMode, int offsetField, int offsetAmount) {
        Calendar calendar;
        if (timestamp < 0) {
            // 当前时间戳
            calendar = Calendar.getInstance();
        } else {
            // 指定时间戳
            calendar = new Calendar.Builder().setInstant(timestamp).build();
        }
        // 填充(switch语句缺少break但不存在错误)
        if (fillMode) {
            // 最小值
            switch (fillField) {
                case Calendar.MONTH: {
                    // 1月
                    calendar.set(Calendar.MONTH, 0);
                }
                case Calendar.DAY_OF_YEAR: {
                    // 1日
                    calendar.set(Calendar.DAY_OF_MONTH, 1);
                }
                case Calendar.HOUR_OF_DAY: {
                    // 0时
                    calendar.set(Calendar.HOUR_OF_DAY, 0);
                }
                case Calendar.MINUTE: {
                    // 0分
                    calendar.set(Calendar.MINUTE, 0);
                }
                case Calendar.SECOND: {
                    // 0秒
                    calendar.set(Calendar.SECOND, 0);
                }
                case Calendar.MILLISECOND: {
                    // 0毫秒
                    calendar.set(Calendar.MILLISECOND, 0);
                }
                default:
            }
        } else {
            // 最大值
            switch (fillField) {
                case Calendar.MONTH: {
                    // 12月
                    calendar.set(Calendar.MONTH, 11);
                }
                case Calendar.DAY_OF_YEAR: {
                    // 当前月份最大日期
                    calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                }
                case Calendar.HOUR_OF_DAY: {
                    // 23时
                    calendar.set(Calendar.HOUR_OF_DAY, 23);
                }
                case Calendar.MINUTE: {
                    // 59分
                    calendar.set(Calendar.MINUTE, 59);
                }
                case Calendar.SECOND: {
                    // 59秒
                    calendar.set(Calendar.SECOND, 59);
                }
                case Calendar.MILLISECOND: {
                    // 999毫秒
                    calendar.set(Calendar.MILLISECOND, 999);
                }
                default:
            }
        }
        // 偏移
        if (offsetField > -1 && offsetAmount != 0) {
            calendar.add(offsetField, offsetAmount);
        }
        // 返回时间戳
        return calendar.getTimeInMillis();
    }

    // endregion

    /* ==================== 日期时间字符串 ==================== */
    // region 日期时间字符串

    /**
     * 获取当前日期时间字符串(日期时间格式{@value FORMAT_DATETIME})
     *
     * @return 日期时间字符串
     */
    public static String getDatetime() {
        return getDatetime(-1, DATETIME_FORMAT);
    }

    /**
     * 获取(当前时间/指定时间戳)日期时间字符串(日期时间格式{@value FORMAT_DATETIME})
     *
     * @param timestamp 时间戳(-1为当前时间)
     * @return 日期时间字符串
     */
    public static String getDatetime(long timestamp) {
        return getDatetime(timestamp, DATETIME_FORMAT);
    }

    /**
     * 获取当前日期时间字符串
     *
     * @param format 日期时间格式字符串
     * @return 日期时间字符串
     */
    public static String getDatetime(String format) {
        return getDatetime(-1, DateTimeFormatter.ofPattern(format));
    }

    /**
     * 获取(当前时间/指定时间戳)的日期时间字符串
     *
     * @param timestamp 时间戳(-1为当前时间)
     * @param format    日期时间格式字符串
     * @return 日期时间字符串
     */
    public static String getDatetime(long timestamp, String format) {
        return getDatetime(timestamp, DateTimeFormatter.ofPattern(format));
    }

    /**
     * 获取当前日期字符串(日期格式{@value FORMAT_DATE})
     *
     * @return 日期字符串
     */
    public static String getDate() {
        return getDatetime(-1, DATE_FORMAT);
    }

    /**
     * 获取(当前时间/指定时间戳)日期字符串(日期格式{@value FORMAT_DATE})
     *
     * @param timestamp 时间戳(-1为当前时间)
     * @return 日期字符串
     */
    public static String getDate(long timestamp) {
        return getDatetime(timestamp, DATE_FORMAT);
    }

    /**
     * 获取当前时间字符串(时间格式{@value FORMAT_TIME})
     *
     * @return 时间字符串
     */
    public static String getTime() {
        return getDatetime(-1, TIME_FORMAT);
    }

    /**
     * 获取(当前时间/指定时间戳)时间字符串(时间格式{@value FORMAT_TIME})
     *
     * @param timestamp 时间戳(-1为当前时间)
     * @return 时间字符串
     */
    public static String getTime(long timestamp) {
        return getDatetime(timestamp, TIME_FORMAT);
    }

    /**
     * 获取(当前时间/指定时间戳)的日期时间字符串
     *
     * @param timestamp 时间戳(-1为当前时间)
     * @param format    日期时间格式
     * @return 日期时间字符串
     */
    public static String getDatetime(long timestamp, DateTimeFormatter format) {
        if (timestamp < 0) {
            // 当前时间戳
            return LocalDateTime.now().format(format);
        } else {
            // 指定时间戳
            return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault()).format(format);
        }
    }

    // endregion

}
