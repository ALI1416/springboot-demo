package com.demo.util;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * <h1>时间戳工具</h1>
 *
 * <h2>SimpleDateFormat格式化代码</h2>
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
 * <td>L
 * <td>月(独立窗体)
 * <tr>
 * <td>u
 * <td>星期(1-7)
 * <tr>
 * <td>Z
 * <td>RFC822时区
 * <tr>
 * <td>X
 * <td>ISO8601时区
 * <tr>
 * <td>'
 * <td>文本定界符(a-zA-Z都被保留)
 * <tr>
 * <td>''
 * <td>单引号(双单引号)
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
 * <td>月
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
 * <td>一年中的第几周
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
     * 1月={@value}天(近似)
     */
    public static final int DAY_OF_MONTH = 30;
    /**
     * 1年={@value}天(近似)
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
     * 1月={@value}毫秒(近似)
     *
     * @see #DAY_OF_MONTH
     */
    public static final long MILLS_OF_MONTH = (long) MILLS_OF_DAY * DAY_OF_MONTH;
    /**
     * 1年={@value}毫秒(近似)
     *
     * @see #DAY_OF_YEAR
     */
    public static final long MILLS_OF_YEAR = (long) MILLS_OF_DAY * DAY_OF_YEAR;
    /**
     * 1天的间隔={@value}毫秒
     *
     * @see #MILLS_OF_DAY
     */
    public static final long INTERVAL_DAY = MILLS_OF_DAY - 1L;
    /**
     * yyyy-MM-dd HH:mm:ss格式字符串{@value}
     */
    public static final String FORMAT_DATETIME = "yyyy-MM-dd HH:mm:ss";
    /**
     * yyyy-MM-dd格式字符串{@value}
     */
    public static final String FORMAT_DATE = "yyyy-MM-dd";
    /**
     * HH:mm:ss格式字符串{@value}
     */
    public static final String FORMAT_TIME = "HH:mm:ss";
    /**
     * yyyy-MM-dd HH:mm:ss格式SimpleDateFormat
     */
    public static final SimpleDateFormat DATETIME_FORMAT = new SimpleDateFormat(FORMAT_DATETIME);
    /**
     * yyyy-MM-dd格式SimpleDateFormat
     */
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(FORMAT_DATE);
    /**
     * HH:mm:ss格式SimpleDateFormat
     */
    public static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat(FORMAT_TIME);

    /**
     * 获取指定日期时间字符串的时间戳
     *
     * @param datetime 日期时间字符串
     * @param format   SimpleDateFormat
     * @return 时间戳，转换失败返回-1
     * @see java.text.DateFormat#parse(String)
     */
    public static long getTimestamp(String datetime, SimpleDateFormat format) {
        Date date;
        try {
            date = format.parse(datetime);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
        return date.getTime();
    }

    /**
     * 获取指定日期时间字符串的时间戳
     *
     * @param datetime 日期时间字符串
     * @param format   日期时间格式字符串
     * @return 时间戳，转换失败返回-1
     * @see java.text.SimpleDateFormat#SimpleDateFormat(String)
     */
    public static long getTimestamp(String datetime, String format) {
        return getTimestamp(datetime, new SimpleDateFormat(format));
    }

    /**
     * 获取指定日期时间字符串的时间戳
     *
     * @param datetime 日期时间字符串(yyyy-MM-dd HH:mm:ss格式)
     * @return 时间戳，转换失败返回-1
     */
    public static long getTimestamp(String datetime) {
        return getTimestamp(datetime, DATETIME_FORMAT);
    }

    /**
     * 获取(当前/指定)时间戳的(指定偏移字段、偏移大小)的(开始/结束/当前)时间戳
     *
     * @param timestamp    指定时间戳(-1为当前时间)
     * @param isStart      开始(true)/结束(false)/当前(null)
     * @param offsetField  偏移字段(offsetAmount为0时，此参数任意)
     * @param offsetAmount 偏移大小(0为不偏移)
     * @return 时间戳
     * @see Calendar
     */
    public static long getTimestamp(long timestamp, Boolean isStart, int offsetField, int offsetAmount) {
        // 当前时间戳
        Calendar calendar = Calendar.getInstance();
        // 指定时间戳
        if (timestamp > -1) {
            calendar.setTimeInMillis(timestamp);
        }
        if (isStart != null) {
            // 设置时分秒毫秒
            if (isStart) {
                // 开始时间
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
            } else {
                // 结束时间
                calendar.set(Calendar.HOUR_OF_DAY, 23);
                calendar.set(Calendar.MINUTE, 59);
                calendar.set(Calendar.SECOND, 59);
                calendar.set(Calendar.MILLISECOND, 999);
            }
        }
        // 指定偏移
        if (offsetField > -1 && offsetAmount > 0) {
            calendar.add(offsetField, offsetAmount);
        }
        // 返回时间戳
        return calendar.getTimeInMillis();
    }

    /**
     * 获取当前时间戳
     *
     * @return 时间戳
     */
    public static long getTimestamp() {
        return getTimestamp(-1, null, -1, 0);
    }

    /**
     * 获取当前时间戳0时0分0秒0毫秒的时间戳
     *
     * @return 时间戳
     */
    public static long getTimestampStart() {
        return getTimestamp(-1, true, -1, 0);
    }

    /**
     * 获取指定时间戳0时0分0秒0毫秒的时间戳
     *
     * @param timestamp 指定时间戳
     * @return 时间戳
     */
    public static long getTimestampStart(long timestamp) {
        return getTimestamp(timestamp, true, -1, 0);
    }

    /**
     * 获取当前时间戳+偏移天0时0分0秒0毫秒的时间戳
     *
     * @param dayOffset 相对于当前时间戳的偏移天
     * @return 时间戳
     */
    public static long getTimestampStart(int dayOffset) {
        return getTimestamp(-1, true, Calendar.DAY_OF_YEAR, dayOffset);
    }

    /**
     * 获取指定时间戳+偏移天0时0分0秒0毫秒的时间戳
     *
     * @param timestamp 指定时间戳
     * @param dayOffset 相对于指定时间戳的偏移天
     * @return 时间戳
     */
    public static long getTimestampStart(long timestamp, int dayOffset) {
        return getTimestamp(timestamp, true, Calendar.DAY_OF_YEAR, dayOffset);
    }

    /**
     * 获取当前时间戳23时59分59秒999毫秒的时间戳<br>
     * 如果已经调用过getTimestampStart，请保存变量并+INTERVAL_DAY来替代getTimestampEnd，这样速度更快
     *
     * @return 时间戳
     */
    public static long getTimestampEnd() {
        return getTimestamp(-1, false, -1, 0);
    }

    /**
     * 获取指定时间戳23时59分59秒999毫秒的时间戳<br>
     * 如果已经调用过getTimestampStart，请保存变量并+INTERVAL_DAY来替代getTimestampEnd，这样速度更快
     *
     * @param timestamp 指定时间戳
     * @return 时间戳
     */
    public static long getTimestampEnd(long timestamp) {
        return getTimestamp(timestamp, false, -1, 0);
    }

    /**
     * 获取当前时间戳+偏移天23时59分59秒999毫秒的时间戳<br>
     * 如果已经调用过getTimestampStart，请保存变量并+INTERVAL_DAY来替代getTimestampEnd，这样速度更快
     *
     * @param dayOffset 相对于当前时间戳的偏移天
     * @return 时间戳
     */
    public static long getTimestampEnd(int dayOffset) {
        return getTimestamp(-1, false, Calendar.DAY_OF_YEAR, dayOffset);
    }

    /**
     * 获取指定时间戳+偏移天23时59分59秒999毫秒的时间戳<br>
     * 如果已经调用过getTimestampStart，请保存变量并+INTERVAL_DAY来替代getTimestampEnd，这样速度更快
     *
     * @param timestamp 指定时间戳
     * @param dayOffset 相对于指定时间戳的偏移天
     * @return 时间戳
     */
    public static long getTimestampEnd(long timestamp, int dayOffset) {
        return getTimestamp(timestamp, false, Calendar.DAY_OF_YEAR, dayOffset);
    }

    /**
     * 获取(当前时间/指定时间戳)的日期时间字符串
     *
     * @param timestamp 时间戳(-1为当前时间)
     * @param format    SimpleDateFormat
     * @return 日期时间字符串
     * @see java.text.DateFormat#format(Date)
     */
    public static String getDatetime(long timestamp, SimpleDateFormat format) {
        Date date;
        if (timestamp < 0) {
            // 当前时间戳
            date = new Date();
        } else {
            // 指定时间戳
            date = new Date(timestamp);
        }
        return format.format(date);
    }

    /**
     * 获取(当前时间/指定时间戳)的日期时间字符串
     *
     * @param timestamp 时间戳(-1为当前时间)
     * @param format    日期时间格式
     * @return 日期时间字符串
     * @see java.text.SimpleDateFormat#SimpleDateFormat(String)
     */
    public static String getDatetime(long timestamp, String format) {
        return getDatetime(timestamp, new SimpleDateFormat(format));
    }

    /**
     * 获取当前日期时间字符串
     *
     * @param format 日期时间格式
     * @return 日期时间字符串
     * @see java.text.SimpleDateFormat#SimpleDateFormat(String)
     */
    public static String getDatetime(String format) {
        return getDatetime(-1, new SimpleDateFormat(format));
    }

    /**
     * 获取(当前时间/指定时间戳)日期时间字符串(yyyy-MM-dd HH:mm:ss格式)
     *
     * @param timestamp 时间戳(-1为当前时间)
     * @return 日期时间字符串
     */
    public static String getDatetime(long timestamp) {
        return getDatetime(timestamp, DATETIME_FORMAT);
    }

    /**
     * 获取当前日期时间字符串(yyyy-MM-dd HH:mm:ss格式)
     *
     * @return 日期时间字符串
     */
    public static String getDatetime() {
        return getDatetime(-1, DATETIME_FORMAT);
    }

    /**
     * 获取(当前时间/指定时间戳)日期时间字符串(yyyy-MM-dd格式)
     *
     * @param timestamp 时间戳(-1为当前时间)
     * @return 日期时间字符串
     */
    public static String getDate(long timestamp) {
        return getDatetime(timestamp, DATE_FORMAT);
    }

    /**
     * 获取当前日期时间字符串(yyyy-MM-dd格式)
     *
     * @return 日期时间字符串
     */
    public static String getDate() {
        return getDatetime(-1, DATE_FORMAT);
    }

    /**
     * 获取(当前时间/指定时间戳)日期时间字符串(HH:mm:ss格式)
     *
     * @param timestamp 时间戳(-1为当前时间)
     * @return 日期时间字符串
     */
    public static String getTime(long timestamp) {
        return getDatetime(timestamp, TIME_FORMAT);
    }

    /**
     * 获取当前日期时间字符串(HH:mm:ss格式)
     *
     * @return 日期时间字符串
     */
    public static String getTime() {
        return getDatetime(-1, TIME_FORMAT);
    }

}
