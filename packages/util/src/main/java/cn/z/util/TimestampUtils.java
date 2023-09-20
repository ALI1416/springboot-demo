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
 * <td>G
 * <td>公元前/后
 * <tr>
 * <td>yyyy
 * <td>年
 * <tr>
 * <td>MM
 * <td>月
 * <tr>
 * <td>dd
 * <td>日
 * <tr>
 * <td>hh
 * <td>12小时制
 * <tr>
 * <td>HH
 * <td>24小时制
 * <tr>
 * <td>mm
 * <td>分
 * <tr>
 * <td>ss
 * <td>秒
 * <tr>
 * <td>SSS
 * <td>毫秒
 * <tr>
 * <td>E
 * <td>星期
 * <tr>
 * <td>z
 * <td>时区
 * <tr>
 * <td>D
 * <td>一年中第几天
 * <tr>
 * <td>F
 * <td>当前月份内一周中的第几天
 * <tr>
 * <td>w
 * <td>一年中的第几周
 * <tr>
 * <td>W
 * <td>一月中的第几周
 * <tr>
 * <td>a
 * <td>AM/PM标记
 * <tr>
 * <td>k
 * <td>一天中的第几小时(1-24)
 * <tr>
 * <td>K
 * <td>AM/PM格式一天中的第几小时(0-11)
 * <tr>
 * <td>Y
 * <td>当天所在的周属于的年份，一周从周日开始，周六结束
 * <tr>
 * <td>u
 * <td>星期(1-7)
 * <tr>
 * <td>'
 * <td>文本定界符
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
 * <td>月(一月为0,二月为1,...十二月为11)
 * <tr>
 * <td>DATE/DAY_OF_MONTH
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
 * <td>WEEK_OF_YEAR
 * <td>一年中的第几周
 * <tr>
 * <td>WEEK_OF_MONTH
 * <td>一月中的第几周
 * <tr>
 * <td>DAY_OF_YEAR
 * <td>一年中的第几天
 * <tr>
 * <td>DAY_OF_WEEK
 * <td>周几(周日为1,周一为2,...周六为7)
 * <tr>
 * <td>DAY_OF_WEEK_IN_MONTH
 * <td>当前月份内一周中的第几天
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
        // 当前时间戳
        Calendar calendar = Calendar.getInstance();
        // 指定时间戳
        if (timestamp > -1) {
            calendar.setTimeInMillis(timestamp);
        }
        // 填充(switch语句缺少break但不存在错误)
        if (fillMode) {
            // 最小值
            switch (fillField) {
                case Calendar.MONTH:
                    // 1月
                    calendar.set(Calendar.MONTH, 0);
                case Calendar.DAY_OF_YEAR:
                    calendar.set(Calendar.DAY_OF_MONTH, 1);
                case Calendar.HOUR_OF_DAY:
                    calendar.set(Calendar.HOUR_OF_DAY, 0);
                case Calendar.MINUTE:
                    calendar.set(Calendar.MINUTE, 0);
                case Calendar.SECOND:
                    calendar.set(Calendar.SECOND, 0);
                case Calendar.MILLISECOND:
                    calendar.set(Calendar.MILLISECOND, 0);
            }
        } else {
            // 最大值
            switch (fillField) {
                case Calendar.MONTH:
                    // 12月
                    calendar.set(Calendar.MONTH, 11);
                case Calendar.DAY_OF_YEAR:
                    // 当前月份最大日期
                    calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                case Calendar.HOUR_OF_DAY:
                    calendar.set(Calendar.HOUR_OF_DAY, 23);
                case Calendar.MINUTE:
                    calendar.set(Calendar.MINUTE, 59);
                case Calendar.SECOND:
                    calendar.set(Calendar.SECOND, 59);
                case Calendar.MILLISECOND:
                    calendar.set(Calendar.MILLISECOND, 999);
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
