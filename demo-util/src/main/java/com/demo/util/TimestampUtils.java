package com.demo.util;


import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
     * yyyy-MM-dd HH:mm:ss格式DateTimeFormatter
     */
    public static final DateTimeFormatter DATETIME_FORMAT = DateTimeFormatter.ofPattern(FORMAT_DATETIME);
    /**
     * yyyy-MM-dd格式DateTimeFormatter
     */
    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern(FORMAT_DATE);
    /**
     * HH:mm:ss格式DateTimeFormatter
     */
    public static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern(FORMAT_TIME);

    /**
     * 获取指定日期时间字符串的时间戳
     *
     * @param datetime 日期时间字符串
     * @param format   DateTimeFormatter
     * @return 时间戳
     */
    public static long getTimestamp(String datetime, DateTimeFormatter format) {
        return LocalDateTime.parse(datetime, format).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
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
     * 获取指定日期时间字符串的时间戳
     *
     * @param datetime 日期时间字符串(yyyy-MM-dd HH:mm:ss格式)
     * @return 时间戳
     */
    public static long getTimestamp(String datetime) {
        return getTimestamp(datetime, DATETIME_FORMAT);
    }

    /**
     * <h2>获取(当前/指定)时间戳偏移后的时间戳</h2>
     *
     * <b>例如：</b>
     * <ul>
     *   <li>当前时间戳的日开始的时间戳 getTimestamp(-1,true,true,-1,0)</li>
     *   <li>指定时间戳的日结束的时间戳 getTimestamp(1609459200000L,true,false,-1,0)</li>
     *   <li>当前时间戳+1日的时间戳 getTimestamp(-1,false,true,Calendar.DAY_OF_YEAR,1)</li>
     *   <li>指定时间戳-2月的日结束的时间戳 getTimestamp(1609459200000L,true,false,Calendar.MONTH,-2)</li>
     * </ul>
     * <b>注意：</b>
     * <ul>
     *   <li>`指定填充`启用条件：fillField为Calendar.HOUR_OF_DAY、Calendar.MINUTE、Calendar.SECOND、Calendar.MILLISECOND的其中一个</li>
     *   <li>`指定偏移`启用条件：offsetField > -1 && offsetAmount != 0</li>
     *   <li>`指定填充`启用后`大的单位`会覆盖`小的单位`，例如：
     *     <ul>
     *       <li>填充Calendar.HOUR_OF_DAY为最大，则Calendar.MINUTE、Calendar.SECOND、Calendar.MILLISECOND也都填充为最大</li>
     *       <li>填充Calendar.MINUTE为最小，则Calendar.SECOND、Calendar.MILLISECOND也都填充为最小</li>
     *     </ul>
     *   </li>
     *   <li>`指定填充`和`指定偏移`同时启用，首先处理`指定填充`</li>
     * </ul>
     *
     * @param timestamp    指定时间戳
     *                     <ul>
     *                       <li>当前时间 -1</li>
     *                     </ul>
     * @param fillField    指定填充-填充字段
     *                     <ul>
     *                       <li>月 Calendar.MONTH</li>
     *                       <li>日 Calendar.DAY_OF_YEAR</li>
     *                       <li>时 Calendar.HOUR_OF_DAY</li>
     *                       <li>分 Calendar.MINUTE</li>
     *                       <li>秒 Calendar.SECOND</li>
     *                       <li>毫秒 Calendar.MILLISECOND</li>
     *                       <li>不启用 -1</li>
     *                     </ul>
     * @param fillMinOrMax 指定填充-填充最小值或最大值
     *                     <ul>
     *                       <li>最小值 true</li>
     *                         <ul>
     *                           <li>月 1</li>
     *                           <li>日 1</li>
     *                           <li>时 0</li>
     *                           <li>分 0</li>
     *                           <li>秒 0</li>
     *                           <li>毫秒 0</li>
     *                          </ul>
     *                       <li>最大值 false
     *                         <ul>
     *                           <li>月 12</li>
     *                           <li>日 当前月份的最大日期(28/29/30/31)</li>
     *                           <li>时 23</li>
     *                           <li>分 59</li>
     *                           <li>秒 59</li>
     *                           <li>毫秒 999</li>
     *                          </ul>
     *                     </ul>
     * @param offsetField  指定偏移-偏移字段
     *                     <ul>
     *                       <li>年 Calendar.YEAR</li>
     *                       <li>月 Calendar.MONTH</li>
     *                       <li>周 Calendar.WEEK_OF_YEAR</li>
     *                       <li>日 Calendar.DAY_OF_YEAR</li>
     *                       <li>时 Calendar.HOUR_OF_DAY</li>
     *                       <li>分 Calendar.MINUTE</li>
     *                       <li>秒 Calendar.SECOND</li>
     *                       <li>毫秒 Calendar.MILLISECOND</li>
     *                       <li>不启用 -1</li>
     *                     </ul>
     * @param offsetAmount 指定偏移-偏移数值
     *                     <ul>
     *                       <li>不启用 0</li>
     *                     </ul>
     * @return 时间戳
     * @see Calendar#YEAR
     * @see Calendar#MONTH
     * @see Calendar#WEEK_OF_YEAR
     * @see Calendar#DAY_OF_YEAR
     * @see Calendar#HOUR_OF_DAY
     * @see Calendar#MINUTE
     * @see Calendar#SECOND
     * @see Calendar#MILLISECOND
     */
    public static long getTimestamp(long timestamp, //
                                    int fillField, boolean fillMinOrMax, //
                                    int offsetField, int offsetAmount) {
        // 当前时间戳
        Calendar calendar = Calendar.getInstance();
        // 指定时间戳
        if (timestamp > -1) {
            calendar.setTimeInMillis(timestamp);
        }
        // 填充(switch语句缺少break和default但不存在错误)
        if (fillMinOrMax) {
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
                    // 获取当月的最大日期
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
        // 指定偏移
        if (offsetField > -1 && offsetAmount != 0) {
            calendar.add(offsetField, offsetAmount);
        }
        // 返回时间戳
        return calendar.getTimeInMillis();
    }

    /**
     * 获取当前时间戳<br>
     * 效率低，不推荐使用
     *
     * @return 时间戳
     */
    @Deprecated
    public static long getTimestamp() {
        return getTimestamp(-1, -1, true, -1, 0);
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
     * 如果已经调用过getTimestampStart，请保存变量并+INTERVAL_DAY来替代getTimestampEnd，这样速度更快
     *
     * @return 时间戳
     */
    public static long getTimestampEnd() {
        return getTimestamp(-1, Calendar.HOUR_OF_DAY, false, -1, 0);
    }

    /**
     * 获取指定时间戳23时59分59秒999毫秒的时间戳<br>
     * 如果已经调用过getTimestampStart，请保存变量并+INTERVAL_DAY来替代getTimestampEnd，这样速度更快
     *
     * @param timestamp 指定时间戳
     * @return 时间戳
     */
    public static long getTimestampEnd(long timestamp) {
        return getTimestamp(timestamp, Calendar.HOUR_OF_DAY, false, -1, 0);
    }

    /**
     * 获取当前时间戳+偏移日23时59分59秒999毫秒的时间戳<br>
     * 如果已经调用过getTimestampStart，请保存变量并+INTERVAL_DAY来替代getTimestampEnd，这样速度更快
     *
     * @param dayOffset 相对于当前时间戳的偏移日
     * @return 时间戳
     */
    public static long getTimestampEnd(int dayOffset) {
        return getTimestamp(-1, Calendar.HOUR_OF_DAY, false, Calendar.DAY_OF_YEAR, dayOffset);
    }

    /**
     * 获取指定时间戳+偏移日23时59分59秒999毫秒的时间戳<br>
     * 如果已经调用过getTimestampStart，请保存变量并+INTERVAL_DAY来替代getTimestampEnd，这样速度更快
     *
     * @param timestamp 指定时间戳
     * @param dayOffset 相对于指定时间戳的偏移日
     * @return 时间戳
     */
    public static long getTimestampEnd(long timestamp, int dayOffset) {
        return getTimestamp(timestamp, Calendar.HOUR_OF_DAY, false, Calendar.DAY_OF_YEAR, dayOffset);
    }

    /**
     * 获取(当前时间/指定时间戳)的日期时间字符串
     *
     * @param timestamp 时间戳(-1为当前时间)
     * @param format    SimpleDateFormat
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

    /**
     * 获取(当前时间/指定时间戳)的日期时间字符串
     *
     * @param timestamp 时间戳(-1为当前时间)
     * @param format    日期时间格式
     * @return 日期时间字符串
     */
    public static String getDatetime(long timestamp, String format) {
        return getDatetime(timestamp, DateTimeFormatter.ofPattern(format));
    }

    /**
     * 获取当前日期时间字符串
     *
     * @param format 日期时间格式
     * @return 日期时间字符串
     */
    public static String getDatetime(String format) {
        return getDatetime(-1, DateTimeFormatter.ofPattern(format));
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
